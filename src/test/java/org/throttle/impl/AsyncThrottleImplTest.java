package org.throttle.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.Throttle;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ax01220 on 2/4/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AsyncThrottleImplTest {

    static final Object RESOURCE = new Object();

    @Mock
    ThrottleStrategy strategy;

    @Mock
    BlockingQueue<Consumer<Object>> queue;

    @Mock
    Consumer<Object> task;

    @Test
    public void successfully_task_add() throws Exception {
        Throttle<Object> throttle = new AsyncThrottleImpl(RESOURCE, strategy, queue);

        throttle.execute(task);

        verify(queue, times(1)).add(task);
    }

    @Test
    public void queue_size_check() throws Exception {
        when(queue.size()).thenReturn(1);
        AsyncThrottleImpl throttle = new AsyncThrottleImpl(RESOURCE, strategy, queue);

        assertEquals(1, throttle.getQueueSize());
    }

    @Test
    public void execute_task_check() throws Exception {
        when(queue.take()).thenReturn(task);
        AsyncThrottleImpl throttle = new AsyncThrottleImpl(RESOURCE, strategy, queue);

        throttle.executeTask();

        verify(strategy, times(1)).acquire();
        verify(task, times(1)).accept(RESOURCE);
    }
}