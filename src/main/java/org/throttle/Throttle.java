package org.throttle;

import java.util.function.Consumer;

/**
 * Created by englishman on 2/1/16.
 */
public interface Throttle<R> {

    /**
     *
     * @param task
     */
    public void execute(Consumer<R> task);
}
