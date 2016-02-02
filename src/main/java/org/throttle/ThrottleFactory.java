package org.throttle;

import org.throttle.strategy.BurstThrottleStrategy;
import org.throttle.strategy.RegularThrottleStrategy;

/**
 * Created by englishman on 2/1/16.
 */
public class ThrottleFactory {

    /**
     *
     * @param resource
     * @param rate in TPS
     * @param <R>
     * @return
     */
    public static <R> Throttle<R> createRegularThrottle(R resource, double rate) {
        TimeService time = new TimeServiceImpl();
        ThrottleStrategy strategy = new RegularThrottleStrategy(rate, time);
        return new AsyncThrottleImpl<>(resource, strategy);
    }

    /**
     *
     * @param resource
     * @param rate
     * @param size
     * @param <R>
     * @return
     */
    public static <R> Throttle<R> createBurstThrottle(R resource, double rate, int size) {
        TimeService time = new TimeServiceImpl();
        // TODO remove cyclic dependency
        ThrottleStrategy strategy = new BurstThrottleStrategy(rate, time, null);
        return new AsyncThrottleImpl<>(resource, strategy);
    }
}
