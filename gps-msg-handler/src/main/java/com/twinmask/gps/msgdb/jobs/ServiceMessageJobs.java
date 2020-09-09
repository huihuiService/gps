package com.twinmask.gps.msgdb.jobs;

import com.twinmask.gps.GpsInfo;
import com.twinmask.gps.comm.RedisConstants;
import com.twinmask.gps.comm.lifecycle.GpsSmartLifecycle;
import com.twinmask.gps.redis.gps.RedisGpsQueueService;
import com.twinmask.gps.utils.NamingThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Leo
 */
@Component
public class ServiceMessageJobs extends GpsSmartLifecycle {

    static final Logger logger = LoggerFactory.getLogger(ServiceMessageJobs.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(getExecutorSize(), new NamingThreadFactory("ServiceMessageJobs"));

    private volatile boolean shutdown = false;

    @Autowired
    RedisGpsQueueService redisGpsQueueService;

    @Override
    protected void doStart() {
        for (int i = 0; i < getExecutorSize(); i++) {
            executorService.execute(() -> {
                while (!shutdown) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        getMessageFromRedis();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    protected void doStop() {
        shutdown = true;
        try {
            boolean isTerminate = false;
            try {
                isTerminate = executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (Throwable e) {
                logger.warn("awaitTermination interrupt", e);
            }
            if (!isTerminate) {
                executorService.shutdownNow();
                logger.warn("Force shutdown templateExecutor");
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    private int getExecutorSize() {
        return Runtime.getRuntime().availableProcessors() * 2 + 1;
    }

    private void getMessageFromRedis() {
        List<GpsInfo> gpsInfos = new ArrayList<>();
        while (!shutdown) {
            int count = redisGpsQueueService.createOrGetVerifyDBQueue(RedisConstants.GPS_ONE_QUEUE).drainTo(gpsInfos, 200);
            if (count == 0) {
                break;
            }
            //调用Service处理数据
            logger.trace("ServiceMessageJobs getMessageFromRedis size:{}", gpsInfos.size());
            gpsInfos.clear();
        }
    }
}
