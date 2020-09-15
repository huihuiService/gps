package com.twinmask.gps.msgdb.dao.master.impl;

import com.twinmask.gps.msgdb.dao.master.IGpsDao;
import com.twinmask.gps.msgdb.dao.master.mapper.GpsMapper;
import com.twinmask.gps.msgdb.dao.master.pojo.GpsInfo;
import com.twinmask.gps.msgdb.dao.support.MybatisBaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Repository("pinganReportDao")
public class GpsDaoImpl extends MybatisBaseDao implements IGpsDao {

    private static final String MAPPER_NAMESPACE = "com.twinmask.gps.msgdb.dao.master.mapper.GpsMapper";

    @Resource
    private GpsMapper mapper;

    @Override
    protected String getNamespace() {
        return MAPPER_NAMESPACE;
    }


    @Override
    public void saveGpsInfo(List<GpsInfo> gpsInfos) {
        super.batchMethodBySingle("saveGpsInfos", gpsInfos);
    }

    @Override
    public List<GpsInfo> getGpsInfos(String imei, Timestamp startTime, Timestamp endTime, String db, String tableDate) {
        return mapper.queryGpsInfos(imei, startTime, endTime, db, tableDate);
    }



}
