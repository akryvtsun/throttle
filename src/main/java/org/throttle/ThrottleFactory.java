package org.throttle;

import org.throttle.strategy.BurstThrottleStrategy;
import org.throttle.strategy.RegularThrottleStrategy;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by englishman on 2/1/16.
 */
public class ThrottleFactory {

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();

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
        return new AsyncThrottleImpl<>(resource, strategy, EXECUTOR);
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
        return new AsyncThrottleImpl<>(resource, strategy, EXECUTOR);
    }

    /**
     *
     * @param clazz
     * @param resource
     * @param rate
     * @param <R>
     * @return
     */
    public static <R> R createSyncRegularThrottle(Class<R> clazz, R resource, double rate) {
        return null;
    }
}
