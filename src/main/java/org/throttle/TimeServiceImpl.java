package org.throttle;

/**
 * Created by englishman on 1/29/16.
 */
class TimeServiceImpl implements TimeService {
    @Override
    public Long get() {
        return System.currentTimeMillis();
    }
}
