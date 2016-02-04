package org.throttle.strategy;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.throttle.impl.ThrottleStrategy;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by ax01220 on 2/4/2016.
 */
@RunWith(MockitoJUnitRunner.class)
abstract class AbstractThrottleStrategyTest {

    @Mock
    protected TimeService time;

    protected ThrottleStrategy strategy;

    protected void askRequestInTime(long time) throws InterruptedException {
        when(this.time.getTime()).thenReturn(time);
        strategy.acquire();
    }

    protected void verifyNoTimeDelays() throws InterruptedException {
        verify(time, never()).delay(anyLong());
    }

    protected void verifyTimeDelay(long duration) throws InterruptedException {
        verify(time, times(1)).delay(duration);
    }
}
