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
     * @param threshold
     * @param <R>
     * @return
     */
    public static <R> Throttle<R> createBurstThrottle(R resource, double rate, int threshold) {
        TimeService time = new TimeServiceImpl();
        Informer holder = new Informer();
        ThrottleStrategy strategy = new BurstThrottleStrategy(rate, time, threshold, holder);
        AsyncThrottleImpl asyncThrottle = new AsyncThrottleImpl<>(resource, strategy, EXECUTOR);
        holder.setDelegate(asyncThrottle);
        return asyncThrottle;
    }

    private static class Informer implements ThrottleInformer {

        private ThrottleInformer delegate;

        void setDelegate(ThrottleInformer delegate) {
            this.delegate = delegate;
        }

        @Override
        public int getQueueSize() {
            return delegate.getQueueSize();
        }
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
        // TODO implement via Proxy
        return null;
    }
}
