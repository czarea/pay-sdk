package com.kmob.paysdk.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 默认线程创建工厂
 * 
 * @author verne
 */
public class DefaultThreadFactory implements ThreadFactory {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultThreadFactory.class);
    private static final AtomicInteger poolId = new AtomicInteger();

    private final AtomicInteger nextId = new AtomicInteger();
    private final String prefix;
    private final boolean daemon;
    private final int priority;
    private final ThreadGroup group;

    public DefaultThreadFactory() {
        this("pool-" + poolId.incrementAndGet(), false, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String prefix) {
        this(prefix, false, Thread.NORM_PRIORITY);
    }

    public DefaultThreadFactory(String prefix, boolean daemon, int priority) {
        this.prefix = prefix + " #";
        this.daemon = daemon;
        this.priority = priority;
        SecurityManager s = System.getSecurityManager();
        group = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {

        String name = prefix + nextId.getAndIncrement();
        Thread t = new InternalThread(group, r, name, 0);
        try {
            if (t.isDaemon()) {
                if (!daemon) {
                    t.setDaemon(false);
                }
            } else {
                if (daemon) {
                    t.setDaemon(true);
                }
            }

            if (t.getPriority() != priority) {
                t.setPriority(priority);
            }
        } catch (Exception ignored) { /* doesn't matter even if failed to set. */ }

        LOGGER.debug("Creates new {}.", t);

        return t;
    }

    public ThreadGroup getThreadGroup() {
        return group;
    }

}
