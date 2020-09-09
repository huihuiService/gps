package com.twinmask.gps;

import com.twinmask.gps.msgdb.MessageDbBootstrap;
import com.twinmask.gps.utils.UncaughtExceptionHandlers;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@ComponentScan({"com.twinmask.gps.redis", "com.twinmask.gps"})
@EnableScheduling
@EnableCaching
@ImportResource({"classpath:dubbo.xml"})
//@MapperScan(value = {"com.twinmask.gps.**.dao","com.twinmask.gps.**.mapper"})
public class MessageDbApplication {

    static final Logger logger = LoggerFactory.getLogger(MessageDbApplication.class);

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) {
        UncaughtExceptionHandlers.registerDefaultUncaughtExceptionHandler();

        final ConfigurableApplicationContext applicationContext = SpringApplication.run(MessageDbApplication.class,
                args);

        registerAppListener(applicationContext);

        MessageDbBootstrap messageServiceBootstrap = applicationContext.getBean(MessageDbBootstrap.class);
        messageServiceBootstrap.start();

        logger.info("MessageDbApplication start success");
        try {
            COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            logger.warn("", e);
        }
    }

    private static void registerAppListener(ConfigurableApplicationContext applicationContext) {
        applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                if (event instanceof ContextClosedEvent) {
                    logger.info("MessageDbApplication is stopping...");
                    MessageDbBootstrap clientGatewayBootstrap = applicationContext.getBean(MessageDbBootstrap.class);
                    clientGatewayBootstrap.stop();
                    logger.info("stop MessageDbApplication finished");
                }
            }
        });
    }

}
