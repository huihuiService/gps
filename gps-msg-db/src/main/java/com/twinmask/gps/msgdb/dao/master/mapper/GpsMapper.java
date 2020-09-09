package com.twinmask.gps.msgdb.dao.master.mapper;

import com.twinmask.gps.msgdb.dao.master.pojo.GpsInfo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface GpsMapper {

    List<GpsInfo> queryGpsInfos(@Param("imei") String imei, @Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime, @Param("db") String db, @Param("tableDate") String tableDate);
}
