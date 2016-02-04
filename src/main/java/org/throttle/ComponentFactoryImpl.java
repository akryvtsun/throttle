package org.throttle;

import org.throttle.impl.*;
import org.throttle.strategy.BurstThrottleStrategy;
import org.throttle.strategy.RegularThrottleStrategy;
import org.throttle.strategy.ThrottleInformer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * Created by ax01220 on 2/4/2016.
 */
class ComponentFactoryImpl<R> implements ComponentFactory<R> {
    @Override
    public BlockingQueue<Consumer<R>> createBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Override
    public ThrottleStrategy createRegularThrottleStrategy(double rate) {
        return new RegularThrottleStrategy(rate);
    }

    @Override
    public ThrottleStrategy createBurstThrottleStrategy(double rate, int sizeThreshold, ThrottleInformer informer) {
        return new BurstThrottleStrategy(rate, sizeThreshold, informer);
    }

    @Override
    public AsyncThrottle<R> createAsyncThrottleImpl(R resource, ThrottleStrategy strategy, BlockingQueue<Consumer<R>> queue) {
        return new AsyncThrottleImpl<>(resource, strategy, queue);
    }

    @Override
    public SyncThrottle<R> createSyncThrottleImpl(R resource, ThrottleStrategy strategy) {
        return new SyncThrottleImpl<>(resource, strategy);
    }

    @Override
    public Executor createExecutor() {
        return Executors.newCachedThreadPool();
    }
}
