package org.throttle.strategy;

import org.throttle.impl.ThrottleStrategy;

/**
 * Created by englishman on 1/30/16.
 */
public class BurstThrottleStrategy implements ThrottleStrategy {

    private final double threshold;
    private final int sizeThreshold;
    private final ThrottleInformer informer;
    private final TimeService timer;

    private boolean firstUsage = true;
    private long lastTime;

    public BurstThrottleStrategy(double rate, int sizeThreshold, ThrottleInformer informer) {
        this(rate, new TimeServiceImpl(), sizeThreshold, informer);
    }

    BurstThrottleStrategy(double rate, TimeService timer, int sizeThreshold, ThrottleInformer informer) {
        this.threshold = 1000 / rate;
        this.timer = timer;
        this.sizeThreshold = sizeThreshold;
        this.informer = informer;
    }

    @Override
    public void acquire() throws InterruptedException {
        long currentTime = timer.getTime();

        long timeShift = 0;

        if (firstUsage) {
            firstUsage = false;
        }
        else if (informer.getQueueSize() <= sizeThreshold) {
            long gapDuration = currentTime - lastTime;
            if (gapDuration < threshold) {
                timeShift = (long) threshold - gapDuration;
                timer.delay(timeShift);
            }
        }

        lastTime = currentTime + timeShift;
    }
}
