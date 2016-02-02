package org.throttle.strategy;

import org.throttle.ThrottleInformer;
import org.throttle.ThrottleStrategy;
import org.throttle.TimeService;

/**
 * Created by englishman on 1/30/16.
 */
public class BurstThrottleStrategy implements ThrottleStrategy {

    private final double rate;
    private final int threshold;
    private final ThrottleInformer informer;
    private final TimeService timer;

    public BurstThrottleStrategy(double rate, TimeService timer, int threshold, ThrottleInformer informer) {
        this.rate = rate;
        this.timer = timer;
        this.threshold = threshold;
        this.informer = informer;
    }

    @Override
    public void acquire() throws InterruptedException {
        // TODO implement logic
    }
}
