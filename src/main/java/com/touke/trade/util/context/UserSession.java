package com.touke.trade.util.context;

import lombok.Data;

/**
 * 用户缓存信息
 * @author 9570
 *
 */
@Data
public class UserSession {
	
	/**
	 * 手机号码
	 */
    private String phone; 
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户ID
     */
    private Integer userId;

}
