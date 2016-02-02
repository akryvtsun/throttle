package org.throttle;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * Created by englishman on 1/29/16.
 */
class AsyncThrottleImpl<R>  implements Throttle<R>, ThrottleInformer {

    private final R resource;
    private final ThrottleStrategy strategy;

    private final BlockingQueue<Consumer<R>> queue = new LinkedBlockingQueue<>();

    AsyncThrottleImpl(R resource, ThrottleStrategy strategy) {
        this.resource = resource;
        this.strategy = strategy;

        doExecute();
    }

    private void doExecute() {
        new Thread(() -> {
            while (true) {
                try {
                    executeTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void executeTask() throws InterruptedException {
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
}
