package com.touke.trade.aspect.xianliu;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by robbyzhan on 2020/7/11.
 */
@Aspect
@Configuration
public class TokenBucketInterceptor {
    @Autowired
    private TokenBucket tokenBucket;

    @Around("execution(public * *(..)) && @annotation(com.touke.trade.aspect.xianliu.Bucket)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        tokenBucket.getToken();
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("系统繁忙, 请稍后再试");
        }
    }
}