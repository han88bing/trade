package com.touke.trade.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touke.trade.aspect.xianliu.Bucket;
import com.touke.trade.aspect.xianliu.BucketVolatile;
import com.touke.trade.aspect.xianliu.Limit;
import com.touke.trade.enums.LimitType;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class LimiterController {
    private final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();
    private final AtomicInteger ATOMIC_INTEGER_4 = new AtomicInteger();
    private final AtomicInteger ATOMIC_INTEGER_5 = new AtomicInteger();

    @Limit(key = "limitTest", period = 10, count = 3)
    @GetMapping("/limitTest1")
    public int testLimiter1() {
        return ATOMIC_INTEGER_1.incrementAndGet();
    }

    @Limit(key = "customer_limit_test", period = 2, count = 200, limitType = LimitType.CUSTOMER)
    @GetMapping("/limitTest2")
    public int testLimiter2() {
        return ATOMIC_INTEGER_2.incrementAndGet();
    }

    @Limit(key = "ip_limit_test", period = 10, count = 3, limitType = LimitType.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {
        return ATOMIC_INTEGER_3.incrementAndGet();
    }

    // 令牌桶限流
    @Bucket
    @GetMapping("/limitTest4")
    public int testLimiter4() {
        return ATOMIC_INTEGER_4.incrementAndGet();
    }

    // 令牌桶限流(使用原子类)
    @BucketVolatile
    @GetMapping("/limitTest5")
    public int testLimiter5() {
        return ATOMIC_INTEGER_5.incrementAndGet();
    }

}
