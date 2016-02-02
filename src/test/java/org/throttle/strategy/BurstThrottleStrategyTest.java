package org.throttle.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.impl.ThrottleStrategy;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by englishman on 2/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BurstThrottleStrategyTest {

    static final long T = 0L;                       // in millis
    static final double RATE = 4;                   // in TPS
    static final double THRESHOLD = 1000 / RATE;    // in millis
    static final int SHIFT = 10;                    // in millis
    static final int QUEUE_SIZE = 2;

    @Mock
    TimeService time;

    @Mock
    ThrottleInformer informer;

    ThrottleStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new BurstThrottleStrategy(RATE, time, QUEUE_SIZE, informer);
    }

    @Test
    public void first_request_should_executed_without_delay() throws Exception {
        doFirstRequest();
        verify(time, never()).delay(anyLong());
    }

    @Test
    public void speed_up_processing_if_queue_is_full() throws Exception {
        doFirstRequest();
        when(time.getTime()).thenReturn((long)(T + (THRESHOLD - SHIFT)));
        when(informer.getQueueSize()).thenReturn(QUEUE_SIZE + 1);

        strategy.acquire();

        verify(time, times(1)).delay(1);
    }

    @Test
    public void not_first_request_under_threshold_should_delay() throws Exception {
        doFirstRequest();
        when(time.getTime()).thenReturn((long)(T + (THRESHOLD - SHIFT)));
        when(informer.getQueueSize()).thenReturn(QUEUE_SIZE - 1);

        strategy.acquire();

        verify(time, times(1)).delay(SHIFT);
    }

    @Test
    public void not_first_request_above_threshold_should_executed_without_delay() throws Exception {
        doFirstRequest();
        when(time.getTime()).thenReturn((long)(T + (THRESHOLD + SHIFT)));

        strategy.acquire();

        verify(time, never()).delay(anyLong());
    }

    private void doFirstRequest() throws InterruptedException {
        when(time.getTime()).thenReturn(T);
        strategy.acquire();
    }
}