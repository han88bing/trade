package com.touke.trade.vo.response;



import com.touke.trade.enums.ErrorCodeEnum;

import lombok.Data;

/**
 * 主站统一返回
 * @author allah
 *
 * @param <T>
 */
@Data
public class CommResult<T> {

    //结果状态（如：0000成功 ，0001 参数不正确，1001 用户不存在）
    protected String rspCode;


    //消息提示
    protected String message;

    //结果集
    private T data;



    public CommResult(String rspCode, String message) {
        this.rspCode = rspCode;
        this.message = message;
    }

    public CommResult(String rspCode, String message, T data) {
        this.rspCode = rspCode;
        this.message = message;
        this.data = data;
    }

    public static <T> CommResult<T> ok() {
        return new CommResult<T>();
    }

    public static <T> CommResult<T> ok(T data) {
        return new CommResult<T>(data);
    }

    public static <T> CommResult<T> ok(String msg) {
        return new CommResult<T>(ErrorCodeEnum.SUCCESS.getCode(),msg, null);
    }

    public static <T> CommResult<T> ok(T data, String msg) {
        return new CommResult<T>(ErrorCodeEnum.SUCCESS.getCode(),msg, data);
    }

    public static <T> CommResult<T> fail() {
        return new CommResult<T>(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
    }

    public static <T> CommResult<T> fail(T data) {
        return new CommResult<T>(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage(),data);
    }

    public static <T> CommResult<T> fail(String rspCode, String message) {
        return new CommResult<T>(rspCode, message);
    }

    public static <T> CommResult<T> fail(String rspCode, String message, T data) {
        return new CommResult<T>(rspCode, message, data);
    }

    //业务异常
    public static <T> CommResult<T> fail(String message) {
        return new CommResult<T>(ErrorCodeEnum.SYSTEM_ERROR.getCode(),message);
    }


    public static <T> CommResult<T> fail(ErrorCodeEnum errorCodeEnum) {
        return new CommResult<T>(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
    }


    public boolean isOk(){
        return ErrorCodeEnum.SUCCESS.getCode().equals(getRspCode());
    }


    public CommResult() {
    	setOK();
    }

    public CommResult(T data) {
    	setOK();
        this.data = data;
    }


    private void setOK() {
        rspCode = ErrorCodeEnum.SUCCESS.getCode();
        message =  ErrorCodeEnum.SUCCESS.getMessage();
    }
    

}
