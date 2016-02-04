package org.throttle.impl;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

/**
 * Created by englishman on 1/29/16.
 */
public final class AsyncThrottleImpl<R> extends AbstractThrottleImpl<R> implements AsyncThrottle<R> {

    private final BlockingQueue<Consumer<R>> queue;

    public AsyncThrottleImpl(R resource, ThrottleStrategy strategy, BlockingQueue<Consumer<R>> queue) {
        super(resource, strategy);
        this.queue = queue;
    }

    void executeTask() throws InterruptedException {
        Consumer<R> task = queue.take();
        strategy.acquire();
        task.accept(resource);
    }

    @Override
    public void execute(Consumer<R> task) {
        queue.add(task);
    }

    @Override
    public int getQueueSize() {
        return queue.size();
    }

    @Override
    public void run() {
        while (true) {
            try {
                executeTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
