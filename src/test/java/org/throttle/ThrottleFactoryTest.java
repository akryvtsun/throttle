package org.throttle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.impl.SyncThrottle;
import org.throttle.impl.ThrottleStrategy;

import java.io.Closeable;
import java.util.Formatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

/**
 * Created by ax01220 on 2/4/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThrottleFactoryTest {

    @Mock
    ComponentFactory<Closeable> factory;

    @Mock
    Executor executor;

    @Mock
    ThrottleStrategy strategy;

    @Mock
    BlockingQueue<Consumer<Closeable>> queue;

    @Mock
    SyncThrottle<Closeable> syncThrottle;

    @Test
    public void testCreateAsyncRegularThrottle() {
        when(factory.createExecutor()).thenReturn(executor);

        ThrottleFactory<Closeable> throttleFactory = new ThrottleFactory<>(factory);
        throttleFactory.createAsyncRegularThrottle(new Formatter(), 4);

        verify(factory, times(1)).createExecutor();
    }

    @Test
    public void testCreateAsyncBurstThrottle() throws Exception {
        when(factory.createExecutor()).thenReturn(executor);

        ThrottleFactory<Closeable> throttleFactory = new ThrottleFactory<>(factory);
        throttleFactory.createAsyncBurstThrottle(new Formatter(), 4, 2);

        verify(factory, times(1)).createExecutor();
    }

    @Test
    public void testCreateSyncRegularThrottle() throws Exception {
        when(factory.createExecutor()).thenReturn(executor);
        when(factory.createSyncThrottleImpl(anyObject(), anyObject())).thenReturn(syncThrottle);

        ThrottleFactory<Closeable> throttleFactory = new ThrottleFactory<>(factory);
        throttleFactory.createSyncRegularThrottle(Closeable.class, new Formatter(), 4);

        verify(factory, times(1)).createExecutor();
    }
}