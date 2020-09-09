package com.twinmask.gps.msgservice;

import com.twinmask.gps.msgservice.protocol.ServerMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageServiceBootstrap {

    static final Logger logger = LoggerFactory.getLogger(MessageServiceBootstrap.class);

    @Autowired
    ServerMain serverMain;

    public void start() {
        serverMain.start(10000,false);
    }

    public void stop() {
        serverMain.stop();
    }
}
