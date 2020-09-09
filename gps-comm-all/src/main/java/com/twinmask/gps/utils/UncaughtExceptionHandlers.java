package com.twinmask.gps.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UncaughtExceptionHandlers {

    static final Logger logger = LoggerFactory.getLogger(UncaughtExceptionHandlers.class);

    public static void registerDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    logger.error(String.format("Caught an exception in: %s", t), e);
                } catch (Throwable errorInLogging) {
                    // If logging fails, e.g. due to missing memory, at least try to log the
                    // message and the cause for the failed logging.
                    System.err.println(e.getMessage());
                    System.err.println(errorInLogging.getMessage());
                }
            }
        });
        logger.info("Register defaultUncaughtExceptionHandler success");
    }

}
