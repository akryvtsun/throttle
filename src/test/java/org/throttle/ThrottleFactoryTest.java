package org.throttle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.impl.ThrottleStrategy;

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
    ComponentFactory<Object> factory;

    @Mock
    Executor executor;

    @Mock
    ThrottleStrategy strategy;

    @Mock
    BlockingQueue<Consumer<Object>> queue;

    @Test
    public void testCreateAsyncRegularThrottle() throws Exception {
        when(factory.createExecutor()).thenReturn(executor);

        ThrottleFactory<Object> throttleFactory = new ThrottleFactory<>(factory);
        throttleFactory.createAsyncRegularThrottle(new Object(), 4);

        verify(factory, times(1)).createExecutor();
    }

    @Test
    public void testCreateAsyncBurstThrottle() throws Exception {

    }

    @Test
    public void testCreateSyncRegularThrottle() throws Exception {

    }
}