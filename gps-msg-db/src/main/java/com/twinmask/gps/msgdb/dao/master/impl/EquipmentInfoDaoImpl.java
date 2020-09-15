package com.twinmask.gps.msgdb.dao.master.impl;

import com.twinmask.gps.msgdb.dao.master.IEquipmentInfoDao;
import com.twinmask.gps.msgdb.dao.master.mapper.EquipmentInfoMapper;
import com.twinmask.gps.msgdb.dao.master.mapper.GpsMapper;
import com.twinmask.gps.msgdb.dao.master.pojo.EquipmentInfo;
import com.twinmask.gps.msgdb.dao.support.MybatisBaseDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Description
 * @Author tusdao-xh
 * @Date 2020-09-09 18:51
 * @Version 1.0
 **/
@Repository("equipmentInfoDao")
public class EquipmentInfoDaoImpl extends MybatisBaseDao implements IEquipmentInfoDao {

    private static final String MAPPER_NAMESPACE = "com.twinmask.gps.msgdb.dao.master.mapper.EquipmentInfoMapper";

    @Override
    protected String getNamespace() {
        return MAPPER_NAMESPACE;
    }

    @Resource
    private EquipmentInfoMapper mapper;

    @Override
    public int insert(EquipmentInfo record) {

        return mapper.insert(record);
    }

    @Override
    public int delete(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public EquipmentInfo selectByPrimaryKey(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

}
