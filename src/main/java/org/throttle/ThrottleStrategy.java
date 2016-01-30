package org.throttle;

/**
 * Created by ax01220 on 1/29/2016.
 */
public interface ThrottleStrategy {
    void acquire() throws InterruptedException;
}
