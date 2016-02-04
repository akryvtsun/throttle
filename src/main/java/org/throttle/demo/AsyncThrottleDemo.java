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

        final Printer resource = new PrinterImpl();
        Throttle<Printer> throttle = ThrottleFactory.createAsyncRegularThrottle(resource, RATE);

        while (true) {
            throttle.execute(r -> {
                try {
                    r.printf("%s Resource usage\n", LocalTime.now());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Thread.sleep(200);
        }
    }
}
