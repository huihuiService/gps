package com.twinmask.gps.msgdb.controller;


import com.twinmask.gps.msgdb.dao.master.impl.EquipmentInfoDaoImpl;
import com.twinmask.gps.msgdb.dao.master.pojo.EquipmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author
 * @Date 2020-09-09 18:02
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "/gps/equipment_info")
public class EquipmentInfoController {

    @Autowired
    private EquipmentInfoDaoImpl equipmentInfoDao;


    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> addEquipmentInfo(HttpServletRequest request, @RequestBody EquipmentInfo equipmentInfo) {
        Map<String, Object> response = new HashMap<String, Object>();
        int count = equipmentInfoDao.insert(equipmentInfo);
        if (count > 0) {
            response.put("code", 200);
            response.put("msg", "添加成功");
            response.put("data", null);
        } else {
            response.put("code", 500);
            response.put("msg", "添加失败");
            response.put("data", null);
        }

        return response;
    }

}
