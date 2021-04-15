package com.touke.trade.enums;


/**
 * 
 * @author allah
 *
 */
public enum ErrorCodeEnum {

    SUCCESS("0000", "成功"),
    
    PARAM_ERROR("1001", "缺少参数"),
    LEGAL_ERROR("1002", "参数不合法"),
    FREQUENT_OPERATION("1003","请求频繁"),
    ACCESS_DENIED("1004", "无权访问"),
    AUTH_NO_MATCH_METHOD("1005", "请求方式不匹配"),
    AUTH_NO_TOKEN("1006", "token不存在"),
    AUTH_NO_LOGIN("1007", "用户未登陆"),
    SYSTEM_ERROR("9999", "系统异常"),


   
    RESOURCE_ING("11001", "资源正在被操作，请稍后再试"),
    NO_FIND_USER("11004","没有查询到此用户"),
    
    //证券类
    TK_SECURITY_NOFIND("11200","未找到该证券信息"),
    
    //账户类
    TK_ACCOUNT_NOFIND("11300","未持有该证券信息"),
    TK_ACCOUNT_INSUFFICIENT("11301","该证券不足"),
    
    BUSINESS("11000", "业务异常");

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
