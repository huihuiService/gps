package com.twinmask.gps;

import com.twinmask.gps.msgdb.MessageHandlerBootstrap;
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
public class MessageHandleApplication {

    static final Logger logger = LoggerFactory.getLogger(MessageHandleApplication.class);

    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(1);

    public static void main(String[] args) {
        UncaughtExceptionHandlers.registerDefaultUncaughtExceptionHandler();

        final ConfigurableApplicationContext applicationContext = SpringApplication.run(MessageHandleApplication.class,
                args);

        registerAppListener(applicationContext);

        MessageHandlerBootstrap messageServiceBootstrap = applicationContext.getBean(MessageHandlerBootstrap.class);
        messageServiceBootstrap.start();

        logger.info("MessageHandleApplication start success");
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
                    logger.info("MessageHandleApplication is stopping...");
                    MessageHandlerBootstrap clientGatewayBootstrap = applicationContext.getBean(MessageHandlerBootstrap.class);
                    clientGatewayBootstrap.stop();
                    logger.info("stop MessageHandleApplication finished");
                }
            }
        });
    }

}
