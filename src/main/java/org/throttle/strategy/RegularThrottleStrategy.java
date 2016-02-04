package org.throttle.strategy;

import org.throttle.impl.ThrottleStrategy;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class RegularThrottleStrategy implements ThrottleStrategy {

    private final double threshold;
    private final TimeService timer;

    private boolean firstUsage = true;
    private long lastTime;

    public RegularThrottleStrategy(double rate) {
        this(rate, new TimeServiceImpl());
    }

    RegularThrottleStrategy(double rate, TimeService timer) {
        this.threshold = 1000 / rate;
        this.timer = timer;
    }

    @Override
    public void acquire() throws InterruptedException {
        long currentTime = timer.getTime();

        long timeShift = 0;

        if (firstUsage) {
            firstUsage = false;
        }
        else {
            long gapDuration = currentTime - lastTime;
            if (gapDuration < threshold) {
                timeShift = (long) threshold - gapDuration;
                timer.delay(timeShift);
            }
        }

        lastTime = currentTime + timeShift;
    }

}
