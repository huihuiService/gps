package com.twinmask.gps.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description 带命名前缀的线程工厂
 * @project: mds-common
 * @Date:2018年9月3日
 * @version 1.0
 * @Company: yitd
 * @author gavinlong
 */
public class NamingThreadFactory implements ThreadFactory {

	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	final int priority;
	final boolean isDaemon;

	public NamingThreadFactory(String namePrefix) {
		this(namePrefix, Thread.NORM_PRIORITY);
	}

	public NamingThreadFactory(String namePrefix, int priority) {
		this(namePrefix, priority, false);
	}

	public NamingThreadFactory(String namePrefix, boolean isDaemon) {
		this(namePrefix, Thread.NORM_PRIORITY, isDaemon);
	}

	public NamingThreadFactory(String namePrefix, int priority, boolean isDaemon) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		this.namePrefix = namePrefix + "-";
		this.priority = priority;
		this.isDaemon = isDaemon;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon() != isDaemon) {
			t.setDaemon(isDaemon);
		}
		t.setPriority(priority);
		return t;
	}

}
