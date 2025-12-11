package cn.deyuan.librarydemo.common.module.plc4x;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * author: admin
 * date: 2025/12/11 14:28
 */
@Data
public class Plc4xReadParams implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final int TYPE_WORD= 1;
    public static final int TYPE_INT = 2;
    public static final int TYPE_BOOL = 3;
    public static final int TYPE_REAL = 4;
    public static final int TYPE_STRING = 5;

    private String address;
    private Integer type;
    private Integer length;
    private Integer addressBit;

    // 读取基本类型
    public Plc4xReadParams(String address, Integer type, Integer addressBit) {
        this.address = address;
        this.type = type;
        this.addressBit = addressBit;
    }

    // 读取字符串类型
    public Plc4xReadParams(String address, Integer type, Integer length, Integer addressBit) {
        this.address = address;
        this.type = type;
        this.length = length;
        this.addressBit = addressBit;
    }
}
