package org.throttle.demo;

import org.throttle.RegularThrottleStrategy;
import org.throttle.ThrottleStrategy;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class ThrottleDemo {

    public static final int RATE = 4;

    public static void main(String[] args) throws InterruptedException {
        ThrottleStrategy t = new RegularThrottleStrategy(RATE);

        LocalTime lastAccessTime = LocalTime.now();
        while (true) {
            if (t.isResourceAvailable()) {
                LocalTime currentTime = LocalTime.now();
                LocalTime diff = currentTime.minus(lastAccessTime.toNanoOfDay(), ChronoUnit.NANOS);
                lastAccessTime = currentTime;

                System.out.printf("%s Resource usage (delay %s)\n", currentTime, diff);

                Thread.sleep(200);
            }
        }
    }
}
