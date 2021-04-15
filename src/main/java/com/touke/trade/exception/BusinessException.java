package com.touke.trade.exception;

import com.touke.trade.enums.ErrorCodeEnum;

/**
 * 自定义异常
 * @author allah
 *
 */
public class BusinessException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6954767627339264946L;

	private String rspCode;

    private String data;

    private ErrorCodeEnum errorCodeEnum;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }

    public BusinessException(String message) {
        super(message);
        this.rspCode = ErrorCodeEnum.BUSINESS.getCode();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
        this.rspCode = errorCodeEnum.getCode();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum, String data) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
        this.rspCode = errorCodeEnum.getCode();
        this.data = data;
    }


    public BusinessException(String rspCode, String message) {
        super(message);
        this.rspCode = rspCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
