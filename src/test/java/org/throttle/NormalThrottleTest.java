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
public class NormalThrottleTest {

    static final long T = 0L;        // initial time
    static final double RATE = 3;    // freq in TPS
    static final double THRESHOLD = 1000 / RATE;

    @Mock
    TimeService timer;

    Throttle t;

    @Before
    public void setUp() {
        t = new NormalThrottle(RATE, timer);
    }

    @Test
    public void allow_the_first_resource_usage_attempt() {
        when(timer.get()).thenReturn(T);
        assertTrue(t.isResourceAvailable());
    }

    @Test
    public void reject_resource_usage_attempt_under_time_threshold() {
        // do first usage
        when(timer.get()).thenReturn(T);
        t.isResourceAvailable();
        // do next usage under threshold
        when(timer.get()).thenReturn((long) (T + (THRESHOLD - 1)));
        assertFalse(t.isResourceAvailable());
    }

    @Test
    public void allow_resourse_usage_above_time_threshold() {
        // do first usage
        when(timer.get()).thenReturn(T);
        t.isResourceAvailable();
        // do next usage above threshold
        when(timer.get()).thenReturn((long) (T + (THRESHOLD + 1)));
        assertTrue(t.isResourceAvailable());
    }
}
