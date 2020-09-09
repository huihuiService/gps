package com.twinmask.gps.msgservice.protocol;

import com.twinmask.gps.msgservice.entity.MyChannel;
import net.jodah.expiringmap.ExpiringMap;

import java.util.concurrent.TimeUnit;

/**
 * @author Leo
 */
public class SessionManage {

    public static volatile ExpiringMap<String, MyChannel> CHANNEL_MAP = ExpiringMap.builder()
            .expiration(1, TimeUnit.HOURS)
            .variableExpiration()
            .build();

}
