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
 * Created by englishman on 1/30/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegularThrottleStrategyTest {

    static final long T = 0L;                       // in millis
    static final double RATE = 4;                   // in TPS
    static final double THRESHOLD = 1000 / RATE;    // in millis
    static final int SHIFT = 10;                    // in millis

    @Mock
    TimeService time;

    ThrottleStrategy strategy;

    @Before
    public void setUp() throws Exception {
        strategy = new RegularThrottleStrategy(RATE, time);
    }

    @Test
    public void first_request_should_executed_without_delay() throws Exception {
        askRequestInTime(T);
        verifyNoTimeDelays();
    }

    @Test
    public void not_first_request_under_threshold_should_delay() throws Exception {
        askRequestInTime(T);

        askRequestInTime((long)(T + (THRESHOLD - SHIFT)));
        verifyTimeDelay(SHIFT);
    }

    @Test
    public void verify_3_sequential_requests_with_high_freq() throws Exception {
        askRequestInTime(T);

        askRequestInTime((long)(T + (THRESHOLD - SHIFT)));
        verifyTimeDelay(SHIFT);

        askRequestInTime((long)(T + THRESHOLD + SHIFT));
        verifyTimeDelay((long) (THRESHOLD - SHIFT));
    }

    @Test
    public void not_first_request_above_threshold_should_executed_without_delay() throws Exception {
        askRequestInTime(T);

        askRequestInTime((long)(T + (THRESHOLD + SHIFT)));
        verifyNoTimeDelays();
    }

    private void askRequestInTime(long time) throws InterruptedException {
        when(this.time.getTime()).thenReturn(time);
        strategy.acquire();
    }

    private void verifyNoTimeDelays() throws InterruptedException {
        verify(time, never()).delay(anyLong());
    }

    private void verifyTimeDelay(long duration) throws InterruptedException {
        verify(time, times(1)).delay(duration);
    }
}