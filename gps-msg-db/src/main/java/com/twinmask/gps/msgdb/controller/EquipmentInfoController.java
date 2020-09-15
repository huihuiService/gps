package com.twinmask.gps.msgdb.controller;


import com.twinmask.gps.msgdb.dao.master.impl.EquipmentInfoDaoImpl;
import com.twinmask.gps.msgdb.dao.master.pojo.EquipmentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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




    @RequestMapping(value = "/get", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> getEquipmentInfoList(HttpServletRequest request, @RequestParam(value = "terName", required = false) String terName) {
        Map<String, Object> response = new HashMap<String, Object>();
        List<EquipmentInfo> list  = equipmentInfoDao.selectAll(terName);

        response.put("code", 200);
        response.put("msg", "获取成功");
        response.put("data", list);

        return response;
    }

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

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> updateEquipmentInfo(HttpServletRequest request, @RequestBody EquipmentInfo equipmentInfo) {
        Map<String, Object> response = new HashMap<String, Object>();
        int count = equipmentInfoDao.updateSelective(equipmentInfo);
        if (count > 0) {
            response.put("code", 200);
            response.put("msg", "修改成功");
            response.put("data", null);
        } else {
            response.put("code", 500);
            response.put("msg", "修改失败");
            response.put("data", null);
        }

        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Map<String, Object> deleteEquipmentInfoById(HttpServletRequest request, @RequestParam(value = "id") Integer id) {
        Map<String, Object> response = new HashMap<String, Object>();

        // 判断是否存在， 此步骤也可以不做
        EquipmentInfo temp = equipmentInfoDao.selectByPrimaryKey(id);
        if (temp == null) {
            response.put("code", 500);
            response.put("msg", "根据主键未获取到数据");
            response.put("data", null);
            return response;
        }

        // 删除数据
        int count = equipmentInfoDao.delete(id);
        if (count > 0) {
            response.put("code", 200);
            response.put("msg", "删除成功");
            response.put("data", null);
        } else {
            response.put("code", 500);
            response.put("msg", "删除失败");
            response.put("data", null);
        }

        return response;
    }

}
