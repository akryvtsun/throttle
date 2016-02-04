package org.throttle.impl;

/**
 * Created by ax01220 on 2/4/2016.
 */
abstract class AbstractThrottleImpl<R> {

    protected final R resource;
    protected final ThrottleStrategy strategy;

    public AbstractThrottleImpl(R resource, ThrottleStrategy strategy) {
        this.resource = resource;
        this.strategy = strategy;
    }
}
