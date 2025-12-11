package cn.deyuan.librarydemo.service;

import cn.deyuan.librarydemo.common.module.plc4x.Plc4xReadParams;
import org.apache.plc4x.java.api.value.PlcValue;

import java.util.List;
import java.util.Map;

/**
 * author: admin
 * date: 2025/12/11 14:23
 */
public interface PlcService {


    Map<String, PlcValue> read(Long deviceId, List<Plc4xReadParams> params);

    // 读取三菱PLC数据(使用mc协议中的3E帧)
    Map<String,PlcValue> read3E(Long deviceId, List<Plc4xReadParams> params);

    void write(Long deviceId, Map<String, PlcValue> values);

    // 写入三菱PLC数据(使用mc协议中的3E帧)
    void write3E(Long deviceId, Map<String, PlcValue> values);

    // 获取PLC的IP地址
    Integer getPlcIp();
}
