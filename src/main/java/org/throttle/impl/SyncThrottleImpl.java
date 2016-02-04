package org.throttle.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by englishman on 2/1/16.
 */
public final class SyncThrottleImpl<R> extends AbstractThrottleImpl<R> implements InvocationHandler {

    public SyncThrottleImpl(R resource, ThrottleStrategy strategy) {
        super(resource, strategy);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        strategy.acquire();
        return method.invoke(resource, args);
    }
}
