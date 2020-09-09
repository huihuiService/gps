package com.twinmask.gps.msgservice.protocol.parser;

import com.twinmask.gps.GpsInfo;
import com.twinmask.gps.comm.RedisConstants;
import com.twinmask.gps.comm.lifecycle.GpsSmartLifecycle;
import com.twinmask.gps.redis.gps.RedisGpsQueueService;
import com.twinmask.gps.utils.NamingThreadFactory;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Leo
 */
@Component
public class ServerParserHandler extends GpsSmartLifecycle implements ParserSupport {

    static final Logger logger = LoggerFactory.getLogger(ServerParserHandler.class);

    private final LinkedBlockingQueue<GpsInfo> msgQueue = new LinkedBlockingQueue<>(500000);

    private static ExecutorService executorService = Executors
            .newSingleThreadExecutor(new NamingThreadFactory("ServerParserHandler"));

    private volatile boolean shutdown = false;

    @Autowired
    RedisGpsQueueService redisGpsQueueService;

    @Override
    public void doParse(final ChannelHandlerContext ctx, final byte[] msgBytes) {
        //添加到消息队列
        try {
            //根据协议获取IMEI，保存IMEI和Session的对应关系

            GpsInfo gpsInfo = new GpsInfo();
            gpsInfo.setGpsTime(new Timestamp((System.currentTimeMillis() / 1000L) * 1000L));
            gpsInfo.setImei("12345678998765");
            gpsInfo.setLat(0.0);
            gpsInfo.setLng(0.0);

            // 放入队列
            msgQueue.put(gpsInfo);
        } catch (Exception ex) {
            logger.trace("ServerParserHandler error:", ex);
        }
        logger.trace("ServerParserHandler message:{}", new String(msgBytes, StandardCharsets.UTF_8));
    }

    @Override
    protected void doStart() {
        executorService.execute(() -> {
            while (!shutdown) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    saveToRedis();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void saveToRedis() {
        List<GpsInfo> gpsInfos = new ArrayList<>();
        while (!shutdown) {
            int count = msgQueue.drainTo(gpsInfos, 2000);
            if (count == 0) {
                logger.info("ServerParserHandler saveToRedis size:0");
                break;
            }
            redisGpsQueueService.createOrGetVerifyDBQueue(RedisConstants.GPS_ONE_QUEUE).addAll(gpsInfos);
            logger.info("ServerParserHandler saveToRedis size:{}", gpsInfos.size());
            gpsInfos.clear();
        }
    }

}
