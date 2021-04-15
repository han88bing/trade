package com.touke.trade.util.lock;



import java.lang.annotation.*;

/**
 * 分布式锁
 * @author allah
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {
    /**锁的内容*/
    String value() default "";
    /**重试次数*/
    int retryTimes() default 20;
    /**失效时间*/
    long expire() default 2000L;

    boolean needWarn() default true;

    /**
     * 当获取失败时候动作
     */
    RedisLock.LockFailAction action() default RedisLock.LockFailAction.CONTINUE;

    /**锁名*/
    String lock() default "";

    enum LockFailAction{
        /** 放弃 */
        GIVEUP,
        /** 继续 */
        CONTINUE;
    }
}
