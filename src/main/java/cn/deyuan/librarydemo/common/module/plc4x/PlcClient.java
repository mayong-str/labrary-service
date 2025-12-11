package cn.deyuan.librarydemo.common.module.plc4x;

import cn.deyuan.librarydemo.dao.entity.PlcInfoConfigDO;
import cn.deyuan.librarydemo.exception.BizException;
import cn.deyuan.plc4xmc.service.PlcReadWriteService;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;
import org.apache.plc4x.java.api.types.PlcResponseCode;
import org.apache.plc4x.java.api.value.PlcValue;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * author: admin
 * date: 2025/12/11 15:18
 */
@Slf4j
public class PlcClient {
    public static PlcConnection get3EConnection(PlcInfoConfigDO plcInfoConfigDO, PlcReadWriteService plcReadWriteService) {
        String url = "mc://" + plcInfoConfigDO.getIp() + ":" + plcInfoConfigDO.getPort();
        return plcReadWriteService.createConnection(url);
    }

    public static Map<String, PlcValue> readTags(PlcConnection connection, List<Plc4xReadParams> params) {
        if (connection == null) {
            return Collections.EMPTY_MAP;
        }
        try {
            PlcReadRequest.Builder builder = connection.readRequestBuilder();
            for (Plc4xReadParams param : params) {
                switch (param.getType()) {
                    case Plc4xReadParams.TYPE_BOOL:
                        builder.addTagAddress(param.getAddress(), "%" + param.getAddress().replace("W", "X") + ":BOOL");
                        break;
                    case Plc4xReadParams.TYPE_INT:
                        builder.addTagAddress(param.getAddress(), "%" + param.getAddress() + ":INT");
                        break;
                    case Plc4xReadParams.TYPE_WORD:
                        builder.addTagAddress(param.getAddress(), "%" + param.getAddress() + ":WORD");
                        break;
                    case Plc4xReadParams.TYPE_REAL:
                        builder.addTagAddress(param.getAddress(), "%" + param.getAddress().replace("W", "D") + ":REAL");
                        break;
                    case Plc4xReadParams.TYPE_STRING:
                        builder.addTagAddress(param.getAddress(), "%" + param.getAddress() + ":STRING[" + param.getLength() + "]");
                        break;
                    default:
                        break;
                }
            }
            return getStringPlcValueMap(builder);
        } catch (Exception e) {
            log.error("读取失败", e);
            throw new BizException("读取失败");
        }
    }

    @NotNull
    private static Map<String, PlcValue> getStringPlcValueMap(PlcReadRequest.Builder builder) throws InterruptedException, ExecutionException, TimeoutException {
        PlcReadRequest readRequest = builder.build();
        PlcReadResponse response = readRequest.execute().get(200, TimeUnit.MILLISECONDS);
        Map<String, PlcValue> result = new HashMap<>();
        for (String name : response.getTagNames()) {
            PlcResponseCode plcResponseCode = response.getResponseCode(name);
            if (plcResponseCode == PlcResponseCode.OK) {
                PlcValue value = response.getPlcValue(name);
                result.put(name, value);
            } else {
                throw new BizException("读取失败");
            }
        }
        return result;
    }

    public static Map<String, PlcValue> read3ETags(PlcConnection connection, List<Plc4xReadParams> params) {
        if (connection == null) {
            return Collections.EMPTY_MAP;
        }
        try {
            PlcReadRequest.Builder builder = connection.readRequestBuilder();
            for (Plc4xReadParams param : params) {
                switch (param.getType()) {
                    case Plc4xReadParams.TYPE_BOOL:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":boolean:" + param.getAddressBit());
                        break;
                    case Plc4xReadParams.TYPE_INT:
                        if (param.getAddressBit() == 16) {
                            builder.addTagAddress(param.getAddress(), param.getAddress() + ":integer");
                        } else if (param.getAddressBit() == 32) {
                            builder.addTagAddress(param.getAddress(), param.getAddress() + ":long");
                        } else {
                            builder.addTagAddress(param.getAddress(), param.getAddress() + ":integer");
                        }
                        break;
                    case Plc4xReadParams.TYPE_REAL:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":float");
                        break;
                    case Plc4xReadParams.TYPE_STRING:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":string:" + param.getAddressBit());
                        break;
                    default:
                        break;
                }
            }
            return getStringPlcValueMap(builder);
        } catch (Exception e) {
            log.error("读取失败", e);
            throw new BizException("读取失败");
        }
    }

    public static void write3ETags(PlcConnection connection, List<Plc4xWriteParams> params) {
        if (connection == null) {
            return;
        }
        try {
            PlcWriteRequest.Builder builder = connection.writeRequestBuilder();
            for (Plc4xWriteParams param : params) {
                switch (param.getType()) {
                    case Plc4xWriteParams.TYPE_BOOL:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":boolean:" + param.getAddressBit(), param.getValue());
                        break;
                    case Plc4xWriteParams.TYPE_INT:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":integer", param.getValue());
                        break;
                    case Plc4xWriteParams.TYPE_FLOAT:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":float", param.getValue());
                        break;
                    case Plc4xReadParams.TYPE_STRING:
                        builder.addTagAddress(param.getAddress(), param.getAddress() + ":string:" + param.getAddressBit(), param.getValue());
                    default:
                        break;
                }
            }
            PlcWriteRequest writeRequest = builder.build();
            PlcWriteResponse response = writeRequest.execute().get();
            for (String name : response.getTagNames()){
                log.error("写入结果:{}", response.getResponseCode(name));
                if(!Objects.equals(response.getResponseCode(name), PlcResponseCode.OK)){
                    throw new BizException("参数:" + name + ",写入失败,错误码:" + response.getResponseCode(name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("写入失败");
        }
    }
}
