package com.kmob.paysdk.parallel;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * 参考了 <a href="https://github.com/netty/netty">Netty</a> FastThreadLocal 的设计, 有一些改动
 * 
 * 
 * @author verne
 */
public class InternalForkJoinWorkerThread extends ForkJoinWorkerThread {

    private InternalThreadLocalMap threadLocalMap;

    public InternalForkJoinWorkerThread(ForkJoinPool pool) {
        super(pool);
    }

    /**
     * Returns the internal data structure that keeps the thread-local variables bound to this
     * thread. Note that this method is for internal use only, and thus is subject to change at any
     * time.
     */
    public final InternalThreadLocalMap threadLocalMap() {
        return threadLocalMap;
    }

    /**
     * Sets the internal data structure that keeps the thread-local variables bound to this thread.
     * Note that this method is for internal use only, and thus is subject to change at any time.
     */
    public final void setThreadLocalMap(InternalThreadLocalMap threadLocalMap) {
        this.threadLocalMap = threadLocalMap;
    }
}
