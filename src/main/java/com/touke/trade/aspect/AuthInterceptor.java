package com.touke.trade.aspect;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.touke.trade.enums.ErrorCodeEnum;
import com.touke.trade.util.http.HttpResponseUtil;
import com.touke.trade.vo.response.CommResult;

import lombok.extern.slf4j.Slf4j;



/**
 * 验证token
 * @author allah
 *
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {



	
	@Autowired
	Environment env;
	

	/**
	 * 验证token  是否存在
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		//1 目前只支持 get post   所有的请求未 异步请求
		String reqMethod = request.getMethod();
		if(!Arrays.asList(new String[]{"post","get"}).contains(reqMethod.toLowerCase())) {
			return  HttpResponseUtil.buildResposne(request, response,CommResult.fail(ErrorCodeEnum.AUTH_NO_MATCH_METHOD));
		}
		
		String token = request.getHeader("token");
		String servletPath = request.getServletPath();
		log.info("请求路径是:{}, token是:{}", servletPath, token);
		
		if (StringUtils.isEmpty(token)) {
			return HttpResponseUtil.buildResposne(request, response,CommResult.fail(ErrorCodeEnum.AUTH_NO_TOKEN));
		}
		
		return true;
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
	
	
}
