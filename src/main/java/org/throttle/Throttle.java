package org.throttle;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class Throttle {

    private final int threshold;
    private final TimeSupplier timer;

    private boolean firstTimeUsage = true;
    private long lastTime = -1L;    // undefined after creation

    public Throttle(int rate) {
        this(rate, () -> System.currentTimeMillis() );
    }

    Throttle(int rate, TimeSupplier timer) {
        this.threshold = 1000 / rate;
        this.timer = timer;
    }

    public boolean isAllowed() {
        long currentTime = timer.get();
        boolean verdict = getVerdict(currentTime);
        firstTimeUsage = false;
        lastTime = currentTime;
        return verdict;
    }

    private boolean getVerdict(long currentTime) {
        return firstTimeUsage
                ? true
                : currentTime - lastTime >= threshold;
    }
}
