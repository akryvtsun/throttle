package org.throttle;

import org.throttle.strategy.BurstThrottleStrategy;
import org.throttle.strategy.RegularThrottleStrategy;

import java.lang.reflect.Proxy;
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
    public static <R> Throttle<R> createAsyncRegularThrottle(R resource, double rate) {
        ThrottleStrategy strategy = createRegularStrategy(rate);
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
    public static <R> Throttle<R> createAsyncBurstThrottle(R resource, double rate, int threshold) {
        TimeService time = new TimeServiceImpl();
        InformerHolder holder = new InformerHolder();
        ThrottleStrategy strategy = new BurstThrottleStrategy(rate, time, threshold, holder);
        AsyncThrottleImpl asyncThrottle = new AsyncThrottleImpl<>(resource, strategy, EXECUTOR);
        // break cyclic dependency between strategy and throttle
        holder.setDelegate(asyncThrottle);
        return asyncThrottle;
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
        ThrottleStrategy strategy = createRegularStrategy(rate);
        SyncThrottleImpl<R> syncThrottle = new SyncThrottleImpl<>(resource, strategy);

        return (R) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[] {clazz}, syncThrottle);
    }

    private static class InformerHolder implements ThrottleInformer {

        private ThrottleInformer delegate;

        void setDelegate(ThrottleInformer delegate) {
            this.delegate = delegate;
        }

        @Override
        public int getQueueSize() {
            return delegate.getQueueSize();
        }
    }

    private static ThrottleStrategy createRegularStrategy(double rate) {
        TimeService time = new TimeServiceImpl();
        return new RegularThrottleStrategy(rate, time);
    }
}
