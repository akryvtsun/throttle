package org.throttle.demo;

import org.throttle.Throttle;
import org.throttle.ThrottleFactory;

import java.time.LocalTime;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class AsyncThrottleDemo {

    public static final int RATE = 4;

    public static void main(String[] args) throws InterruptedException {

        final PrinterImpl resource = new PrinterImpl();
        Throttle<Printer> throttle = ThrottleFactory.createAsyncRegularThrottle(resource, RATE);

        while (true) {
            throttle.execute(r -> {
                r.printf("%s Resource usage\n", LocalTime.now());

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(200);
        }
    }
}
