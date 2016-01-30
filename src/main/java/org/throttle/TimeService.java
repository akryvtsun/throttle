package org.throttle;

/**
 * Created by ax01220 on 1/29/2016.
 */
interface TimeService {
    /**
     *
     * @return
     */
    long getTime();

    /**
     *
     * @param interval in milliseconds
     * @throws InterruptedException
     */
    void delay(long interval) throws InterruptedException;
}
