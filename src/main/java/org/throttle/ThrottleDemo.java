package org.throttle;

import java.time.LocalTime;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class ThrottleDemo {

    public static final int RATE = 8;

    public static void main(String[] args) {
        Throttle t = new Throttle(RATE);

        int counter = 0;
        while (true) {
            if (t.isResourceAllowed()) {
                System.out.printf("%s Resource usage\n", LocalTime.now());
                counter++;
                if (counter > RATE) {
                    counter = 0;
                    System.out.println();
                }
            }
        }
    }
}
