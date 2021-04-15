package com.touke.trade.aspect;



import com.touke.trade.enums.ErrorCodeEnum;
import com.touke.trade.exception.BusinessException;
import com.touke.trade.util.lock.RedisLock;
import com.touke.trade.util.lock.impl.RedisDistributedLock;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


@Component
@Scope
@Aspect
@Slf4j
public class RedisLockAspect {


    @Autowired
    private RedisDistributedLock redisDistributedLock;


    @Autowired
    private SpELGenerate spELGenerate;
    
    @Pointcut("@annotation(com.touke.trade.util.lock.RedisLock)")
    public void controllerMethodPointcut() {
    }

    @Around("controllerMethodPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisLock redis = method.getAnnotation(RedisLock.class);
        String lockName = pjp.getTarget().getClass().getName() + "_" + method.getName();
        List<String> lockContent = getLock(pjp);
        /*重试次数*/
        int retryTimes = redis.action().equals(RedisLock.LockFailAction.CONTINUE) ? redis.retryTimes() : 0;

        boolean lock = redisDistributedLock.locks(lockName, lockContent, redis.expire(), retryTimes);
        if (!lock) {
            if(!redis.needWarn()){
                return null;
            }
            log.info("get lock failed : " + lockName);
            throw new BusinessException(ErrorCodeEnum.RESOURCE_ING);
        }
        //得到锁,执行方法，释放锁
        log.info("get lock success : " + lockName);
        try {
            return pjp.proceed();
        } catch (Exception e) {
            log.error("execute locked method occured an exception", e);
            throw e;
        } finally {
            lockContent.forEach(key -> {
                final boolean releaseResult = redisDistributedLock.releaseLock(lockName + "_" + key);
                if (!releaseResult){
                    log.info("release lock : " + lockName + "_" + key + " failed");
                }
            });
        }
    }
    
    /**
     * 获得锁名
     * @param joinPoint
     * @return
     */
    private List<String> getLock(ProceedingJoinPoint joinPoint) {
        List<String> res = new ArrayList<>();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取到方法的所有参数名称的字符串数组
        Method method = methodSignature.getMethod();
        RedisLock redis = (RedisLock) method.getAnnotation(RedisLock.class);
        String lock = redis.lock();
       // 通过spel表达式获得注解中参数中锁的维度
       //  @RedisLock(value = "#userInfo.pid + '_' + #userInfo.userWid")
        String str = spELGenerate.generateKeyBySpEL(redis.value(), joinPoint);
//        if (JSONArray.isValidArray(str)) {
//            List<String> jsons = JSONArray.parseArray(str,String.class);
//            jsons.forEach(json -> {
//                res.add((ThreadCache.get(CommonConstants.CURRENT_THREAD_USER) == null ? "" : ((UserInfoDetailVo) ThreadCache.get(CommonConstants.CURRENT_THREAD_USER)).getPid()) + "_" + json);
//            });
//        } else {
//            lock += (ThreadCache.get(CommonConstants.CURRENT_THREAD_USER) == null ? "" : ((UserInfoDetailVo) ThreadCache.get(CommonConstants.CURRENT_THREAD_USER)).getPid()) + "_" + str;
//            res.add(lock);
//        }
        return res;
    }

    /**
     *AQS实现可重入锁
     */

//    @Override
//    protected boolean tryAcquire(int arg) {
//        //AQS中的int值，当没有线程获得锁时为0
//        int state = getState();
//        Thread t = Thread.currentThread();
//        //第一个线程进入
//        if (state == 0) {
//            //由于可能有多个线程同时进入这里，所以需要使用CAS操作保证原子性，这里不会出现线程安全性问题
//            if (compareAndSetState(0, 1)) {
//                //设置获得独占锁的线程
//                setExclusiveOwnerThread(Thread.currentThread());
//                return true;
//            }
//        } else if (getExclusiveOwnerThread() == t) {
//            //已经获得锁的线程和当前线程是同一个，那么state加一，由于不会有多个线程同时进入这段代码块，所以没有线程安全性问题，可以直接使用setState方法
//            setState(state + 1);
//            return true;
//        }
//        //其他情况均无法获得锁
//        return false;
//    }
//
//    @Override
//    protected boolean tryRelease(int arg) {
//        //锁的获取和释放使一一对应的，那么调用此方法的一定是当前线程，如果不是，抛出异常
//        if (Thread.currentThread() != getExclusiveOwnerThread()) {
//            throw new RuntimeException();
//        }
//
//        int state = getState() - arg;
//
//        boolean flag = false;
//
//        //如果state减一后的值为0了，那么表示线程重入次数已经降低为0，可以释放锁了。
//        if (state == 0) {
//            setExclusiveOwnerThread(null);
//            flag = true;
//        }
//
//        //无论是否释放锁，都需要更改state的值
//        setState(state);
//
//        //只有state的值为0了，才真正释放了锁，返回true
//        return flag;
//    }
}
