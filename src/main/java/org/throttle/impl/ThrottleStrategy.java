package org.throttle.impl;

/**
 * Created by ax01220 on 1/29/2016.
 */
public interface ThrottleStrategy {
    /**
     *
     * @throws InterruptedException
     */
    void acquire() throws InterruptedException;
}
