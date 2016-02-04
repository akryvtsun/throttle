package org.throttle.strategy;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

/**
 * Created by englishman on 2/1/16.
 */
public class BurstThrottleStrategyTest extends AbstractThrottleStrategyTest {

    static final long T = 0L;                       // in millis
    static final double RATE = 4;                   // in TPS
    static final double THRESHOLD = 1000 / RATE;    // in millis
    static final int SHIFT = 10;                    // in millis
    static final int QUEUE_SIZE = 2;

    @Mock
    ThrottleInformer informer;

    @Before
    public void setUp() throws Exception {
        strategy = new BurstThrottleStrategy(RATE, time, QUEUE_SIZE, informer);
    }

    @Test
    public void first_request_should_executed_without_delay() throws Exception {
        askRequestInTime(T);
        verifyNoTimeDelays();
    }

    @Test
    public void speed_up_processing_if_queue_is_full() throws Exception {
        askRequestInTime(T);

        when(informer.getQueueSize()).thenReturn(QUEUE_SIZE + 1);
        askRequestInTime((long)(T + (THRESHOLD - SHIFT)));
        verifyNoTimeDelays();
    }

    @Test
    public void not_first_request_under_threshold_should_delay() throws Exception {
        askRequestInTime(T);

        when(informer.getQueueSize()).thenReturn(QUEUE_SIZE - 1);
        askRequestInTime((long)(T + (THRESHOLD - SHIFT)));
        verifyTimeDelay(SHIFT);
    }

    @Test
    public void not_first_request_above_threshold_should_executed_without_delay() throws Exception {
        askRequestInTime(T);

        askRequestInTime((long)(T + (THRESHOLD + SHIFT)));
        verifyNoTimeDelays();
    }
}