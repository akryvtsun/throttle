package org.throttle.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.impl.ThrottleStrategy;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by englishman on 2/1/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class BurstThrottleStrategyTest {

    static final long T = 0L;                       // in millis
    static final double RATE = 4;                   // in TPS
    static final double THRESHOLD = 1000 / RATE;    // in millis
    static final int SHIFT = 10;                    // in millis

    @Mock
    TimeService time;

    @Mock
    ThrottleInformer informer;

    ThrottleStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new BurstThrottleStrategy(RATE, time, 2, informer);
    }

    @Test
    public void first_request_should_executed_without_delay() throws Exception {
        doFirstRequest();
        verify(time, never()).delay(anyLong());
    }

    private void doFirstRequest() throws InterruptedException {
        when(time.getTime()).thenReturn(T);
        strategy.acquire();
    }
}