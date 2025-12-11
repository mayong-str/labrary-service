package cn.deyuan.librarydemo.exception;

import org.apache.commons.lang3.ClassUtils;

/**
 * author: admin
 * date: 2025/12/11 15:26
 */
public interface BizExceptionInt {
    Integer getCode();

    String getMsg();
}
