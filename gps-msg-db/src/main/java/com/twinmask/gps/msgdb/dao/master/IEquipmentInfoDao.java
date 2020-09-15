package com.twinmask.gps.msgdb.dao.master;

import com.twinmask.gps.msgdb.dao.master.pojo.EquipmentInfo;
import com.twinmask.gps.msgdb.dao.master.pojo.EquipmentInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @Author tusdao-xh
 * @Date 2020-09-09 18:52
 * @Version 1.0
 **/
public interface IEquipmentInfoDao {

    List<EquipmentInfo> selectAll(String terName);


    EquipmentInfo selectByPrimaryKey(Integer id);

    int insert(EquipmentInfo record);

    int updateSelective(EquipmentInfo record);

    int delete(Integer id);
}
