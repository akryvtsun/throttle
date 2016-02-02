package org.throttle.strategy;

/**
 * Created by englishman on 1/29/16.
 */
class TimeServiceImpl implements TimeService {

    @Override
    public long getTime() {
        return System.currentTimeMillis();
    }

    @Override
    public void delay(long interval) throws InterruptedException {
        Thread.sleep(interval);
    }
}
