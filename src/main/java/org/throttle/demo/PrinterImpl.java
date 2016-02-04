package org.throttle.demo;

import java.io.PrintStream;

/**
 * Created by englishman on 2/1/16.
 */
public final class PrinterImpl implements Printer {

    private PrintStream resource = System.out;

    @Override
    public void printf(String format, Object... args) throws Exception {
        resource.printf(format, args);
        resource.flush();
        // emulate resource usage delay
        Thread.sleep(20);
    }
}
