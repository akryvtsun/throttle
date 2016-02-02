package org.throttle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by englishman on 2/1/16.
 */
class SyncThrottleImpl<R> implements InvocationHandler {

    private final R resource;
    private final ThrottleStrategy strategy;

    SyncThrottleImpl(R resource, ThrottleStrategy strategy) {
        this.resource = resource;
        this.strategy = strategy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        strategy.acquire();
        return method.invoke(resource, args);
    }
}
