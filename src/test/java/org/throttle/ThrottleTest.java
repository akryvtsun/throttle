package org.throttle;

import org.junit.Test;

/**
 * Created by englishman on 1/28/16.
 */
public class ThrottleTest {

    @Test
    public void testName() throws Exception {
        Throttle limiter = new Throttle();

        for (int i = 0; i < 10; i++) {
            if (limiter.isAllowed()) {
                System.out.println("Seng message");
            }
        }
    }
}
