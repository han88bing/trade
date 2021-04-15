package com.touke.trade.aspect.xianliu;
import com.google.common.util.concurrent.AtomicDouble;
import com.touke.trade.constants.BucketConstants;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by robbyzhan on 2020/7/11.
 */
@Component
public class TokenBucketVolatile {
    private volatile AtomicLong lastGetTimeStamp = new AtomicLong(System.currentTimeMillis());
    private volatile AtomicDouble remainTokens = new AtomicDouble(BucketConstants.BUCKET_VOLUMN);

    public void getToken() {
        // 更新现有 token存量, 上限100
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this) {
            if (remainTokens.addAndGet((currentTimeMillis - lastGetTimeStamp.get()) * BucketConstants.ADD_RATE) >
                    BucketConstants.BUCKET_VOLUMN) {
                remainTokens.set(BucketConstants.BUCKET_VOLUMN);
            }
            lastGetTimeStamp.set(currentTimeMillis);
        }
        if (remainTokens.addAndGet(-1) < 0) throw new RuntimeException("获取令牌错误");
    }

}