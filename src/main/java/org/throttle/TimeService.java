package org.throttle;

/**
 * Created by ax01220 on 1/29/2016.
 */
interface TimeService {
    long getTime();
    void delay(long interval) throws InterruptedException;
}
