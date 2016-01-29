package org.throttle;

/**
 * Created by ax01220 on 1/29/2016.
 */
public class Throttle {

    private final int threshold;
    private final TimeSupplier timer;

    private boolean firstTimeUsage = true;
    private long lastTime;

    public Throttle(int rate) {
        this(rate, () -> System.currentTimeMillis() );
    }

    Throttle(int rate, TimeSupplier timer) {
        this.threshold = 1000 / rate;
        this.timer = timer;
    }

    public boolean isResourceAllowed() {
        long currentTime = timer.get();

        boolean verdict = getVerdict(currentTime);
        firstTimeUsage = false;

        if (verdict)
            lastTime = currentTime;
        return verdict;
    }

    private boolean getVerdict(long currentTime) {
        return firstTimeUsage
                ? true
                : currentTime - lastTime >= threshold;
    }
}
