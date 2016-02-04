package org.throttle.demo;

import org.throttle.ThrottleFactory;

import java.util.Random;

/**
 * Created by englishman on 2/1/16.
 */
public class SyncThrottleDemo {

    public static final int RATE = 4;

    public static void main(String[] args) throws Exception {

        final Printer resource = new PrinterImpl();
        Printer throttle = ThrottleFactory.createSyncRegularThrottle(Printer.class, resource, RATE);
        Random random = new Random();

        long lastTime = System.currentTimeMillis();
        while (true) {
            // resource usage
            throttle.printf("Resource usage");

            // log time gap durations between real calls
            long currentTime = System.currentTimeMillis();
            System.out.println(" (pause duration: " + (currentTime - lastTime) + ")");
            System.out.flush();
            lastTime = currentTime;

            // emulate delay between resource requests
            Thread.sleep(random.nextInt(100) + 50);
        }
    }
}
