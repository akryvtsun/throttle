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

    @Mock
    TimeSupplier timer;

    Throttle limiter;

    @Before
    public void setUp() {
        limiter = new Throttle(timer);
    }

    @Test
    public void allows_the_first_resource_usage() {
        when(timer.get()).thenReturn(0L);
        assertTrue(limiter.isAllowed());
    }

    @Test
    public void reject_fast_the_second_resource_usage() throws Exception {
        when(timer.get()).thenReturn(0L);
        assertTrue(limiter.isAllowed());
        assertFalse(limiter.isAllowed());
    }
}
