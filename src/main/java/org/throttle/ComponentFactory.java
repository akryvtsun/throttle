package org.throttle;

import org.throttle.impl.AsyncThrottle;
import org.throttle.impl.SyncThrottle;
import org.throttle.impl.ThrottleStrategy;
import org.throttle.strategy.ThrottleInformer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Created by ax01220 on 2/4/2016.
 */
interface ComponentFactory<R> {
    BlockingQueue<Consumer<R>> createBlockingQueue();

    ThrottleStrategy createRegularThrottleStrategy(double rate);
    ThrottleStrategy createBurstThrottleStrategy(double rate, int sizeThreshold, ThrottleInformer informer);

    AsyncThrottle<R> createAsyncThrottleImpl(R resource, ThrottleStrategy strategy, BlockingQueue<Consumer<R>> queue);
    SyncThrottle<R> createSyncThrottleImpl(R resource, ThrottleStrategy strategy);

    Executor createExecutor();
}
