package com.twinmask.gps;

import com.twinmask.gps.msgservice.MessageServiceBootstrap;
import com.twinmask.gps.utils.UncaughtExceptionHandlers;
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
public class MessageServiceApplication {

    static final Logger logger = LoggerFactory.getLogger(MessageServiceApplication.class);

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) {
        UncaughtExceptionHandlers.registerDefaultUncaughtExceptionHandler();

        final ConfigurableApplicationContext applicationContext = SpringApplication.run(MessageServiceApplication.class,
                args);

        registerAppListener(applicationContext);

        MessageServiceBootstrap messageServiceBootstrap = applicationContext.getBean(MessageServiceBootstrap.class);
        messageServiceBootstrap.start();

        logger.info("MessageServiceApplication start success");
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
                    logger.info("MessageServiceApplication is stopping...");
                    MessageServiceBootstrap clientGatewayBootstrap = applicationContext.getBean(MessageServiceBootstrap.class);
                    clientGatewayBootstrap.stop();
                    logger.info("stop MessageServiceApplication finished");
                }
            }
        });
    }

}
