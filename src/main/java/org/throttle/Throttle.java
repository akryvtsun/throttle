package org.throttle;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class Throttle {

    private final TimeSupplier timer;
    private long lastTime = -1L;    // undefined after creation

//    public Throttle() {
//        super(() -> {
//            System.currentTimeMillis();
//        });
//    }

    Throttle(TimeSupplier timer) {
        this.timer = timer;
    }

    public boolean isAllowed() {
        long currentTime = timer.get();
        boolean verdict = currentTime - lastTime > 0;
        lastTime = currentTime;
        return verdict;
    }
}
