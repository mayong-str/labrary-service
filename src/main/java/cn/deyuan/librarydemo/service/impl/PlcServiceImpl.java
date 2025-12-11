package cn.deyuan.librarydemo.service.impl;

import cn.deyuan.librarydemo.common.module.plc4x.Plc4xReadParams;
import cn.deyuan.librarydemo.common.module.plc4x.PlcClient;
import cn.deyuan.librarydemo.service.PlcService;
import cn.deyuan.librarydemo.dao.PlcInfoConfigDao;
import cn.deyuan.librarydemo.dao.entity.PlcInfoConfigDO;
import cn.deyuan.librarydemo.exception.BizException;
import cn.deyuan.plc4xmc.service.PlcReadWriteService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.value.PlcValue;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * author: admin
 * date: 2025/12/11 14:50
 */
@Log4j2
@Service
public class PlcServiceImpl implements PlcService {
    private final static Map<Long, PlcConnection> CONNECTIONS = new ConcurrentHashMap<>();
    private final static Set<Long> FAILED_CONNECTIONS = new HashSet<>();
    private final static Map<Long, PlcInfoConfigDO> IP_MAPPING = new ConcurrentHashMap<>();
    private final static Map<Long, Integer> RETRY_COUNT_MAP = new ConcurrentHashMap<>();

    @Resource
    private PlcInfoConfigDao plcInfoConfigDao;

    @Resource
    private PlcReadWriteService plcReadWriteService;


    @PostConstruct
    public void init() {
        log.error("开始初始化PLC连接");
        QueryWrapper<PlcInfoConfigDO> queryWrapper = new QueryWrapper<>();
        List<PlcInfoConfigDO> plcInfoConfigDOList = plcInfoConfigDao.selectList(queryWrapper);
        for (PlcInfoConfigDO plcInfoConfigDO : plcInfoConfigDOList) {
            if (plcInfoConfigDO != null) {
                IP_MAPPING.put(plcInfoConfigDO.getId(), plcInfoConfigDO);
            }
        }
        for (Map.Entry<Long, PlcInfoConfigDO> entry : IP_MAPPING.entrySet()) {
            try {
                PlcInfoConfigDO plcInfoConfigDO = entry.getValue();
                PlcConnection connection = PlcClient.get3EConnection(plcInfoConfigDO, plcReadWriteService);
                if (connection != null) {
                    CONNECTIONS.put(entry.getKey(), connection);
                } else {
                    log.error("PLC connection failed for edgId: {}, IP: {}", entry.getKey(), entry.getValue());
                    FAILED_CONNECTIONS.add(entry.getKey());
                }
            } catch (Exception e) {
                log.error("Error establishing PLC connection for edgId: {}", entry.getKey(), e);
                FAILED_CONNECTIONS.add(entry.getKey());
            }
        }
        log.error("PLC初始化完成，成功连接{}个设备", CONNECTIONS.size());
        log.error("PLC初始化完成，连接失败{}个设备", FAILED_CONNECTIONS.size());
    }



    @Override
    public Map<String, PlcValue> read3E(Long deviceId, List<Plc4xReadParams> params) {
        PlcConnection connection = CONNECTIONS.get(deviceId);
        if (connection == null) {
            PlcInfoConfigDO plcInfoConfigDO = plcInfoConfigDao.selectById(deviceId);
            if (Objects.nonNull(plcInfoConfigDO)) {
                FAILED_CONNECTIONS.add(deviceId);
            }
        }
        try {
            return PlcClient.read3ETags(connection, params);
        } catch (Exception e) {
            if (Objects.nonNull(e.getMessage()) && e.getMessage().contains("Connection is not possible")) {
                FAILED_CONNECTIONS.add(deviceId);
            }
            if (e instanceof TimeoutException) {
                FAILED_CONNECTIONS.add(deviceId);
            }
            // 检查连接是否仍然有效，如果无效则添加到失败连接列表中
            try {
                if (!connection.isConnected()) {
                    FAILED_CONNECTIONS.add(deviceId);
                    CONNECTIONS.remove(deviceId);
                }
            } catch (Exception connectionException) {
                // 如果检查连接状态时出错，也将设备标记为连接失败
                FAILED_CONNECTIONS.add(deviceId);
                CONNECTIONS.remove(deviceId);
            }
            throw new BizException("PLC读取失败 设备ID：" + deviceId);
        }
    }

    @Override
    public void write3E(Long deviceId, Map<String, PlcValue> values) {

    }

    @Override
    public Integer getPlcIp() {
        return 0;
    }

    @Override
    public Map<String, PlcValue> read(Long deviceId, List<Plc4xReadParams> params) {
        return Map.of();
    }
    @Override
    public void write(Long deviceId, Map<String, PlcValue> values) {

    }
}
