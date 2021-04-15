package com.touke.trade.aspect.xianliu;

import org.springframework.stereotype.Component;

import com.touke.trade.constants.BucketConstants;

import java.util.concurrent.locks.ReentrantLock;
@Component
public class TokenBucket {

    private long lastGetTimeStamp = System.currentTimeMillis();
    private double remainTokens = BucketConstants.BUCKET_VOLUMN;
    private ReentrantLock lock = new ReentrantLock();

    public void getToken() {
        try {
            lock.lock();
            long currentTimeMillis = System.currentTimeMillis();
            // 更新现有 token存量, 上限100
            remainTokens += (currentTimeMillis - lastGetTimeStamp) * BucketConstants.ADD_RATE;
            if (remainTokens > BucketConstants.BUCKET_VOLUMN) remainTokens = BucketConstants.BUCKET_VOLUMN;
            lastGetTimeStamp = currentTimeMillis;
            if (remainTokens < 1) {
                throw new RuntimeException();
            }
            remainTokens -= 1;      // 成功获取 token, 令牌 -1
        } catch (Exception e) {
            throw new RuntimeException("获取令牌错误");
        } finally {
            lock.unlock();
        }

    }
}
