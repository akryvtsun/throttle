package org.throttle;

import org.throttle.impl.AsyncThrottle;
import org.throttle.impl.SyncThrottle;
import org.throttle.impl.ThrottleStrategy;
import org.throttle.strategy.ThrottleInformer;

import java.lang.reflect.Proxy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * TODO remove static methods here for more testability
 * TODO Use EntityFactory interface internally
 * TODO Allows user send Executor as ThrottleFactory parameter
 * TODO use fluent API?
 * TODO implement createSyncBurstThrottle
 *
 * Created by englishman on 2/1/16.
 */
public final class ThrottleFactory<R> {

    private final ComponentFactory<R> factory;
    private final Executor executor;

    public ThrottleFactory() {
        this(new ComponentFactoryImpl());
    }

    ThrottleFactory(ComponentFactory<R> factory) {
        this.factory = factory;
        this.executor = factory.createExecutor();
    }

    /**
     *
     * @param resource
     * @param rate in TPS
     * @return
     */
    public Throttle<R> createAsyncRegularThrottle(R resource, double rate) {
        ThrottleStrategy strategy = factory.createRegularThrottleStrategy(rate);
        BlockingQueue<Consumer<R>> queue = factory.createBlockingQueue();

        AsyncThrottle<R> asyncThrottle = factory.createAsyncThrottleImpl(resource, strategy, queue);
        executor.execute(asyncThrottle);

        return asyncThrottle;
    }

    /**
     *
     * @param resource
     * @param rate
     * @param threshold
     * @return
     */
    public Throttle<R> createAsyncBurstThrottle(R resource, double rate, int threshold) {
        InformerHolder holder = new InformerHolder();
        ThrottleStrategy strategy = factory.createBurstThrottleStrategy(rate, threshold, holder);
        BlockingQueue<Consumer<R>> queue = factory.createBlockingQueue();

        AsyncThrottle<R> asyncThrottle = factory.createAsyncThrottleImpl(resource, strategy, queue);
        // break cyclic dependency between strategy and throttle
        holder.setDelegate(asyncThrottle);
        executor.execute(asyncThrottle);

        return asyncThrottle;
    }

    /**
     *
     * @param clazz
     * @param resource
     * @param rate
     * @param
     * @return
     */
    public R createSyncRegularThrottle(Class<R> clazz, R resource, double rate) {
        ThrottleStrategy strategy = factory.createRegularThrottleStrategy(rate);
        SyncThrottle<R> syncThrottle = factory.createSyncThrottleImpl(resource, strategy);

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
}
