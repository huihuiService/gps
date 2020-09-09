package com.twinmask.gps.msgdb.dao.master;

import com.twinmask.gps.msgdb.dao.master.pojo.GpsInfo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Leo
 */
public interface IGpsDao {

    void saveGpsInfo(List<GpsInfo> pinganReports);

    List<GpsInfo> getGpsInfos(String imei, Timestamp startTime, Timestamp endTime, String db, String tableDate);
}
