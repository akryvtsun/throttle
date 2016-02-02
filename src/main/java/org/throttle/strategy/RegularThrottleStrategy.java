package org.throttle.strategy;

import org.throttle.ThrottleStrategy;

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

        if (firstUsage) {
            firstUsage = false;
        }
        else {
            long delay = currentTime - lastTime;
            if (delay < threshold)
                timer.delay((long) threshold - delay);
        }

        lastTime = currentTime;
    }

}
