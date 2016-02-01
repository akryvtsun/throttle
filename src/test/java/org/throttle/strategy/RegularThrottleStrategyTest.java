package org.throttle.strategy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.ThrottleStrategy;
import org.throttle.TimeService;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Created by englishman on 1/30/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegularThrottleStrategyTest {

    @Mock
    TimeService time;

    @Test
    public void first_request_should_executed_without_delay() throws Exception {
        ThrottleStrategy strategy = new RegularThrottleStrategy(3, time);
        strategy.acquire();
        verify(time, never()).delay(anyLong());
    }
}