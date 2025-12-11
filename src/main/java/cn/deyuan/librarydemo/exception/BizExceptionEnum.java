package cn.deyuan.librarydemo.exception;

import java.util.Objects;

/**
 * author: admin
 * date: 2025/12/11 15:25
 */

public enum BizExceptionEnum implements BizExceptionInt {
    SYSTEM_ERROR(1000, "系统异常"),
    PARAM_ERROR(1001, "参数异常"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    DATA_EXIST(1003, "数据已存在"),
    DATA_NOT_ALLOW_DELETE(1004, "数据不允许删除"),
    DATA_NOT_ALLOW_EDIT(1005, "数据不允许编辑"),
    DATA_NOT_ALLOW_ADD(1006, "数据不允许添加"),
    DATA_NOT_ALLOW_UPDATE(1007, "数据不允许更新"),
    DATA_NOT_ALLOW_DELETE_BY_ID(1008, "数据不允许删除"),
    DATA_UPDATE_FAIL(1009, "数据更新失败"),
    LOGIN_FAIL(1010, "用户名或密码错误"),
    LOGIN_TOKEN_ERROR(1010, "登录过期，请重新登录"),
    OLD_PASSWORD_ERROR(1011, "旧密码错误"),
    UN_LOGIN(1012, "请登录后再进行操作"),
    USER_NOT_EXIST(1013, "用户不存在"),
    USER_NOT_ALLOW_LOGIN(1014, "用户不允许登录"),
    USERNAME_EXIST(1015, "用户名已存在"),
    USER_UPDATE_FAIL(1016, "用户信息更新失败"),
    USER_AUTHORIZATION_UPDATE_FAIL(1017, "用户权限修改失败"),
    USER_LOCK_FAIL(1018, "用户锁定失败"),
    USER_UN_LOCK_FAIL(1018, "用户解锁失败"),
    REQUEST_EXPIRE(1019, "请求过期"),
    EQUIPMENT_NOT_EXIST(1020, "设备不存在"),
    EQUIPMENT_CODE_EXIST(1021, "设备编码已经存在"),
    DATA_DELETE_FAIL(1022, "数据删除失败"),
    FILE_UPLOAD_FAIL(1023, "文件上传失败"),
    BOOK_NOT_EXIST(1024, "该书籍不存在！"),
    RF_ID_NOT_EXIST(1025, "读取rfId失败！"),
    BOOK_BORROW_STATUS_NOT_EXIST(1026, "获取借书状态失败！"),
    BOOK_RETURN_STATUS_NOT_EXIST(1027, "获取还书状态失败！"),
    BOOK_UPDATE_ERROR(1028, "修改图书索引失败！"),
    POLLING_INTERRUPTED(1029, "设备通信轮询被中断！"),
    OPERATION_TIMEOUT(1030, "设备操作超时，请检查设备状态后重试！")
    ;

    private final Integer code;

    private final String msg;

    BizExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static BizExceptionInt getByCode(Integer code) {
        for (BizExceptionEnum value : values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
