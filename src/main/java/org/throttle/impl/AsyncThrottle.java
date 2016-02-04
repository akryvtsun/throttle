package org.throttle.impl;

import org.throttle.Throttle;
import org.throttle.strategy.ThrottleInformer;

/**
 * Created by ax01220 on 2/4/2016.
 */
public interface AsyncThrottle<R> extends Throttle<R>, ThrottleInformer, Runnable {
}
