package org.throttle;

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
    public static <R> ThrottleImpl<R> createRegularThrottle(R resource, double rate) {
        TimeService time = new TimeServiceImpl();
        ThrottleStrategy strategy = new RegularThrottleStrategy(rate, time);
        return new ThrottleImpl<>(resource, strategy);
    }
}
