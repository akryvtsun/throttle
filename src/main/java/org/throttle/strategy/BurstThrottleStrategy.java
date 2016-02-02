package org.throttle.strategy;

import org.throttle.ThrottleInformer;
import org.throttle.ThrottleStrategy;
import org.throttle.TimeService;

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

    public BurstThrottleStrategy(double rate, TimeService timer, int sizeThreshold, ThrottleInformer informer) {
        this.threshold = 1000 / rate;
        this.timer = timer;
        this.sizeThreshold = sizeThreshold;
        this.informer = informer;
    }

    @Override
    public void acquire() throws InterruptedException {
        long currentTime = timer.getTime();

        if (firstUsage) {
            firstUsage = false;
        }
        else {
            long delay = currentTime - lastTime;
            if (delay < threshold) {
                long effectiveDelay = getEffectiveDelay(delay);

                timer.delay(effectiveDelay);
            }
        }

        lastTime = currentTime;
    }

    private long getEffectiveDelay(long delay) {
        return informer.getQueueSize() > sizeThreshold
                ? 1     // execute ASAP
                : (long) threshold - delay;
    }
}
