package cn.deyuan.librarydemo.exception;

import cn.deyuan.librarydemo.utils.SerializeUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * author: admin
 * date: 2025/12/11 15:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BizException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(String msg) {
        super(msg);
        this.code = BizExceptionEnum.SYSTEM_ERROR.getCode();
        this.msg = msg;
    }

    public BizException(BizExceptionInt bizExceptionInt) {
        super(bizExceptionInt.getMsg());
        this.code = bizExceptionInt.getCode();
        this.msg = bizExceptionInt.getMsg();
    }

    public BizException(BizExceptionInt bizExceptionInt, String msg) {
        super(msg);
        this.code = bizExceptionInt.getCode();
        this.msg = msg;
    }

    @Override
    public String toString() {
        return SerializeUtils.serialize(this);
    }
}
