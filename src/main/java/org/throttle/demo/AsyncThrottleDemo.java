package org.throttle.demo;

import org.throttle.Throttle;
import org.throttle.ThrottleFactory;

import java.io.PrintStream;
import java.time.LocalTime;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class AsyncThrottleDemo {

    public static final int RATE = 4;

    public static void main(String[] args) throws InterruptedException {

        Throttle<PrintStream> t = ThrottleFactory.createAsyncRegularThrottle(System.out, RATE);

        while (true) {
            t.execute(r -> {
                r.printf("%s Resource usage\n", LocalTime.now());
                r.flush();

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
