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

    static final long T = 0L;       // initial time
    static final long RATE = 10;    // freq in TPS
    static final long THRESHOLD = 1000 / RATE;

    @Mock
    TimeSupplier timer;

    Throttle limiter;

    @Before
    public void setUp() {
        limiter = new Throttle(10, timer);
    }

    @Test
    public void allow_the_first_resource_usage() {
        // set time T
        when(timer.get()).thenReturn(T);
        assertTrue(limiter.isResourceAllowed());
    }

    @Test
    public void reject_the_second_resource_usage_under_time_threshold() {
        // set time T
        when(timer.get()).thenReturn(T);
        assertTrue(limiter.isResourceAllowed());
        // set time T + 90ms
        when(timer.get()).thenReturn(T + (THRESHOLD - 10L));
        assertFalse(limiter.isResourceAllowed());
    }

    @Test
    public void allow_the_second_resourse_usage_above_time_threshold() {
        // set time T
        when(timer.get()).thenReturn(T);
        assertTrue(limiter.isResourceAllowed());
        // set time T + 110ms
        when(timer.get()).thenReturn(T + (THRESHOLD + 10L));
        assertTrue(limiter.isResourceAllowed());
    }

    @Test
    public void reject_more_then_two_fast_sequental_resourse_requests_and_allow_above_threshold() {
        // set time T
        when(timer.get()).thenReturn(T);
        assertTrue(limiter.isResourceAllowed());
        // set time T + 80ms
        when(timer.get()).thenReturn(T + (THRESHOLD - 20L));
        assertFalse(limiter.isResourceAllowed());
        // set time T + 90ms
        when(timer.get()).thenReturn(T + (THRESHOLD - 10L));
        assertFalse(limiter.isResourceAllowed());
        // set time T + 100ms
        when(timer.get()).thenReturn(T + THRESHOLD);
        assertTrue(limiter.isResourceAllowed());
    }
}
