package com.twinmask.gps.msgdb.dao.master.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EquipmentInfo {
    private Integer id;

    private String belongUser;

    private String terProtocol;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Integer offlineTimes;

    private String terName;

    private String terPwd;

    private String terPhone;

    private String latitudeLongitude;

    private String wifi;

    private String serialNum;

    private String terId;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(String belongUser) {
        this.belongUser = belongUser == null ? null : belongUser.trim();
    }

    public String getTerProtocol() {
        return terProtocol;
    }

    public void setTerProtocol(String terProtocol) {
        this.terProtocol = terProtocol == null ? null : terProtocol.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOfflineTimes() {
        return offlineTimes;
    }

    public void setOfflineTimes(Integer offlineTimes) {
        this.offlineTimes = offlineTimes;
    }

    public String getTerName() {
        return terName;
    }

    public void setTerName(String terName) {
        this.terName = terName == null ? null : terName.trim();
    }

    public String getTerPwd() {
        return terPwd;
    }

    public void setTerPwd(String terPwd) {
        this.terPwd = terPwd == null ? null : terPwd.trim();
    }

    public String getTerPhone() {
        return terPhone;
    }

    public void setTerPhone(String terPhone) {
        this.terPhone = terPhone == null ? null : terPhone.trim();
    }

    public String getLatitudeLongitude() {
        return latitudeLongitude;
    }

    public void setLatitudeLongitude(String latitudeLongitude) {
        this.latitudeLongitude = latitudeLongitude == null ? null : latitudeLongitude.trim();
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi == null ? null : wifi.trim();
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum == null ? null : serialNum.trim();
    }

    public String getTerId() {
        return terId;
    }

    public void setTerId(String terId) {
        this.terId = terId == null ? null : terId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}