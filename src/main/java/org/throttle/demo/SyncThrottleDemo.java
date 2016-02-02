package org.throttle.demo;

import org.throttle.ThrottleFactory;

import java.time.LocalTime;

/**
 * Created by englishman on 2/1/16.
 */
public class SyncThrottleDemo {

    public static final int RATE = 4;

    public static void main(String[] args) throws InterruptedException {

        final PrinterImpl resource = new PrinterImpl();
        Printer throttle = ThrottleFactory.createSyncRegularThrottle(Printer.class, resource, RATE);

        while (true) {
            throttle.printf("%s Resource usage\n", LocalTime.now());

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.sleep(200);
        }
    }
}
