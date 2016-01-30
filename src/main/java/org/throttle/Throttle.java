package org.throttle;

import java.util.function.Consumer;

/**
 * Created by englishman on 1/29/16.
 */
public class Throttle<R> {

    private final ThrottleStrategy strategy;

    public static <R> Throttle<R> createRegularThrottle(double rate) {
        TimeService time = new TimeServiceImpl();
        ThrottleStrategy strategy = new RegularThrottleStrategy(rate, time);
        return new Throttle<>(strategy);
    }

    Throttle(ThrottleStrategy strategy) {
        this.strategy = strategy;
    }

    public ThrottleFuture execute(Consumer<R> task) {
        return null;
    }
}
