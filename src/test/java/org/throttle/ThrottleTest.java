package org.throttle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by englishman on 1/28/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ThrottleTest {

    static final long T = 0L;        // initial time
    static final double RATE = 3;    // freq in TPS
    static final double THRESHOLD = 1000 / RATE;

    @Mock
    TimeSupplier timer;

    Throttle limiter;

    @Before
    public void setUp() {
        limiter = new Throttle(RATE, timer);
        // set time T
        when(timer.get()).thenReturn(T);
    }

    @Test
    public void allow_the_first_resource_usage() {
        assertTrue(limiter.isResourceAvailable());
    }

    @Test
    public void reject_the_second_resource_usage_under_time_threshold() {
        assertTrue(limiter.isResourceAvailable());
        // set time T + 90ms
        when(timer.get()).thenReturn((long) (T + (THRESHOLD - 10)));
        assertFalse(limiter.isResourceAvailable());
    }

    @Test
    public void allow_the_second_resourse_usage_above_time_threshold() {
        assertTrue(limiter.isResourceAvailable());
        // set time T + 110ms
        when(timer.get()).thenReturn((long) (T + (THRESHOLD + 10)));
        assertTrue(limiter.isResourceAvailable());
    }

    @Test
    public void reject_more_then_two_fast_sequental_resourse_requests_and_allow_above_threshold() {
        assertTrue(limiter.isResourceAvailable());
        // set time T + 80ms
        when(timer.get()).thenReturn((long) (T + (THRESHOLD - 20)));
        assertFalse(limiter.isResourceAvailable());
        // set time T + 90ms
        when(timer.get()).thenReturn((long) (T + (THRESHOLD - 10)));
        assertFalse(limiter.isResourceAvailable());
        // set time T + 100ms
        when(timer.get()).thenReturn((long) (T + THRESHOLD) + 10);
        assertTrue(limiter.isResourceAvailable());
    }
}
