package org.throttle;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * Created by englishman on 1/29/16.
 */
public class Throttle<R> {

    private final R resource;
    private final ThrottleStrategy strategy;

    private final BlockingQueue<Consumer<R>> queue = new LinkedBlockingQueue<>();

    /**
     *
     * @param resource
     * @param rate in TPS
     * @param <R>
     * @return
     */
    public static <R> Throttle<R> createRegularThrottle(R resource, double rate) {
        TimeService time = new TimeServiceImpl();
        ThrottleStrategy strategy = new RegularThrottleStrategy(rate, time);
        return new Throttle<>(resource, strategy);
    }

    Throttle(R resource, ThrottleStrategy strategy) {
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

    public void execute(Consumer<R> task) {
        queue.add(task);
    }
}
