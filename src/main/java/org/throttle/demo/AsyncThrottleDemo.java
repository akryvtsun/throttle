package org.throttle.demo;

import org.throttle.Throttle;
import org.throttle.ThrottleFactory;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class AsyncThrottleDemo {

    public static final int RATE = 4;

    public static void main(String[] args) throws InterruptedException {

        final Printer resource = new PrinterImpl();
        ThrottleFactory<Printer> factory = new ThrottleFactory<>();
        Throttle<Printer> throttle = factory.createAsyncBurstThrottle(resource, RATE, 4);
        Random random = new Random();

        final AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());
        while (true) {
            throttle.execute(r -> {
                try {
                    r.printf("Resource usage");

                    // log time gap durations between real calls
                    long currentTime = System.currentTimeMillis();
                    System.out.println(" (pause duration: " + (currentTime - lastTime.longValue()) + ")");
                    System.out.flush();
                    lastTime.set(currentTime);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // emulate delay between resource requests
            Thread.sleep(random.nextInt(200) + 100);
        }
    }
}
