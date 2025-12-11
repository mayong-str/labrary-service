package cn.deyuan.librarydemo.common.module.plc4x;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * author: admin
 * date: 2025/12/11 14:43
 */
@Data
public class Plc4xWriteParams implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final int TYPE_WORD = 1;
    public static final int TYPE_INT = 2;
    public static final int TYPE_BOOL = 3;
    public static final int TYPE_REAL = 4;
    public static final int TYPE_STRING = 5;
    public static final int TYPE_FLOAT = 6;
    public static final int TYPE_DOUBLE = 7;

    private String address;
    private Integer type;
    private Integer length;
    private Integer addressBit;
    private Object value;

    // 写入基本类型
    public Plc4xWriteParams(String address, Object value) {
        this.address = address;
        this.value = value;
    }

    // 写入基本类型
    public Plc4xWriteParams(String address, Integer type, Object value, Integer addressBit) {
        this.address = address;
        this.type = type;
        this.value = value;
        this.addressBit = addressBit;
    }

    // 写入字符串类型
    public Plc4xWriteParams(String address, Integer type, Object value, Integer length, Integer addressBit) {
        this.address = address;
        this.type = type;
        this.value = value;
        this.length = length;
        this.addressBit = addressBit;
    }
}
