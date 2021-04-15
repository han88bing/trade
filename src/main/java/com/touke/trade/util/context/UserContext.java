package com.touke.trade.util.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UserContext {

	

	public static int getUserId(){
		return getUserSession().getUserId();
	}
	
	
	public static String getUserPhone(){
		return getUserSession().getPhone();
	}
	
	
	
	/**
	 * 用户信息
	 * @return
	 */
	public static UserSession getUserSession() {
    	
//    	StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
//    	
//    	String token = getToken();
//    	
//    	if(StringUtils.isBlank(token)){
//    		log.error("获取用户信息出错没有获取到token");
//    		throw new RuntimeException("token is missing");
//    	}
    	
//    	
//    	String jsonInfo = stringRedisTemplate.opsForValue().get(RedisConstants.USER_SESSION_INFO + token);
//    	if(StringUtils.isBlank(jsonInfo)){
//    		log.error("获取用户信息出错,token : {}" , token);
//    		throw new RuntimeException("业务异常,请稍后再试");
//    	}
//    		return JSON.parseObject(jsonInfo, UserSession.class);
		UserSession userSession = new UserSession();
		userSession.setName("zhangsan");
		userSession.setPhone("15000216601");
		userSession.setUserId(1);
        return userSession;
	}
	
	
	
	
	/**
	 * 获取token
	 * @return
	 */
	private static String getToken() {
    	return getHeader("token");
	}
	
	/**
	 * 获取header信息
	 * @return
	 */
	private static String getHeader(String key) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
    			.getRequestAttributes();
    	HttpServletRequest request = requestAttributes.getRequest();
    	return request.getHeader(key);
	}
}
