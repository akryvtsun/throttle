package org.throttle.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.ThrottleStrategy;
import org.throttle.TimeService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by englishman on 1/30/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegularThrottleStrategyTest {

    static final long T = 0L;                       // in millis
    static final double RATE = 4;                   // in TPS
    static final double THRESHOLD = 1000 / RATE;    // in millis

    @Mock
    TimeService time;

    ThrottleStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new RegularThrottleStrategy(RATE, time);
    }

    @Test
    public void first_request_should_executed_without_delay() throws Exception {
        doFirstRequest();
        verify(time, never()).delay(anyLong());
    }

    @Test
    public void not_first_request_under_threshold_should_delay() throws Exception {
        doFirstRequest();
        when(time.getTime()).thenReturn((long)(T + (THRESHOLD - 10)));

        strategy.acquire();

        verify(time, times(1)).delay(10);
    }

    @Test
    public void not_first_request_above_threshold_should_executed_without_delay() throws Exception {
        doFirstRequest();
        when(time.getTime()).thenReturn((long)(T + (THRESHOLD + 1)));

        strategy.acquire();

        verify(time, never()).delay(anyLong());
    }

    private void doFirstRequest() throws InterruptedException {
        when(time.getTime()).thenReturn(T);
        strategy.acquire();
    }
}