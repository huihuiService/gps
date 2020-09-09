package com.twinmask.gps.msgdb.dao.master.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EquipmentInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EquipmentInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andBelongUserIsNull() {
            addCriterion("belong_user is null");
            return (Criteria) this;
        }

        public Criteria andBelongUserIsNotNull() {
            addCriterion("belong_user is not null");
            return (Criteria) this;
        }

        public Criteria andBelongUserEqualTo(String value) {
            addCriterion("belong_user =", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotEqualTo(String value) {
            addCriterion("belong_user <>", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserGreaterThan(String value) {
            addCriterion("belong_user >", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserGreaterThanOrEqualTo(String value) {
            addCriterion("belong_user >=", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserLessThan(String value) {
            addCriterion("belong_user <", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserLessThanOrEqualTo(String value) {
            addCriterion("belong_user <=", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserLike(String value) {
            addCriterion("belong_user like", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotLike(String value) {
            addCriterion("belong_user not like", value, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserIn(List<String> values) {
            addCriterion("belong_user in", values, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotIn(List<String> values) {
            addCriterion("belong_user not in", values, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserBetween(String value1, String value2) {
            addCriterion("belong_user between", value1, value2, "belongUser");
            return (Criteria) this;
        }

        public Criteria andBelongUserNotBetween(String value1, String value2) {
            addCriterion("belong_user not between", value1, value2, "belongUser");
            return (Criteria) this;
        }

        public Criteria andTerProtocolIsNull() {
            addCriterion("ter_protocol is null");
            return (Criteria) this;
        }

        public Criteria andTerProtocolIsNotNull() {
            addCriterion("ter_protocol is not null");
            return (Criteria) this;
        }

        public Criteria andTerProtocolEqualTo(String value) {
            addCriterion("ter_protocol =", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolNotEqualTo(String value) {
            addCriterion("ter_protocol <>", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolGreaterThan(String value) {
            addCriterion("ter_protocol >", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolGreaterThanOrEqualTo(String value) {
            addCriterion("ter_protocol >=", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolLessThan(String value) {
            addCriterion("ter_protocol <", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolLessThanOrEqualTo(String value) {
            addCriterion("ter_protocol <=", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolLike(String value) {
            addCriterion("ter_protocol like", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolNotLike(String value) {
            addCriterion("ter_protocol not like", value, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolIn(List<String> values) {
            addCriterion("ter_protocol in", values, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolNotIn(List<String> values) {
            addCriterion("ter_protocol not in", values, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolBetween(String value1, String value2) {
            addCriterion("ter_protocol between", value1, value2, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andTerProtocolNotBetween(String value1, String value2) {
            addCriterion("ter_protocol not between", value1, value2, "terProtocol");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("end_time is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("end_time is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("end_time =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("end_time <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("end_time >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("end_time >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("end_time <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("end_time <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("end_time in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("end_time not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("end_time between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("end_time not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesIsNull() {
            addCriterion("offline_times is null");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesIsNotNull() {
            addCriterion("offline_times is not null");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesEqualTo(Integer value) {
            addCriterion("offline_times =", value, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesNotEqualTo(Integer value) {
            addCriterion("offline_times <>", value, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesGreaterThan(Integer value) {
            addCriterion("offline_times >", value, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("offline_times >=", value, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesLessThan(Integer value) {
            addCriterion("offline_times <", value, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesLessThanOrEqualTo(Integer value) {
            addCriterion("offline_times <=", value, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesIn(List<Integer> values) {
            addCriterion("offline_times in", values, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesNotIn(List<Integer> values) {
            addCriterion("offline_times not in", values, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesBetween(Integer value1, Integer value2) {
            addCriterion("offline_times between", value1, value2, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andOfflineTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("offline_times not between", value1, value2, "offlineTimes");
            return (Criteria) this;
        }

        public Criteria andTerNameIsNull() {
            addCriterion("ter_name is null");
            return (Criteria) this;
        }

        public Criteria andTerNameIsNotNull() {
            addCriterion("ter_name is not null");
            return (Criteria) this;
        }

        public Criteria andTerNameEqualTo(String value) {
            addCriterion("ter_name =", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameNotEqualTo(String value) {
            addCriterion("ter_name <>", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameGreaterThan(String value) {
            addCriterion("ter_name >", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameGreaterThanOrEqualTo(String value) {
            addCriterion("ter_name >=", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameLessThan(String value) {
            addCriterion("ter_name <", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameLessThanOrEqualTo(String value) {
            addCriterion("ter_name <=", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameLike(String value) {
            addCriterion("ter_name like", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameNotLike(String value) {
            addCriterion("ter_name not like", value, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameIn(List<String> values) {
            addCriterion("ter_name in", values, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameNotIn(List<String> values) {
            addCriterion("ter_name not in", values, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameBetween(String value1, String value2) {
            addCriterion("ter_name between", value1, value2, "terName");
            return (Criteria) this;
        }

        public Criteria andTerNameNotBetween(String value1, String value2) {
            addCriterion("ter_name not between", value1, value2, "terName");
            return (Criteria) this;
        }

        public Criteria andTerPwdIsNull() {
            addCriterion("ter_pwd is null");
            return (Criteria) this;
        }

        public Criteria andTerPwdIsNotNull() {
            addCriterion("ter_pwd is not null");
            return (Criteria) this;
        }

        public Criteria andTerPwdEqualTo(String value) {
            addCriterion("ter_pwd =", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdNotEqualTo(String value) {
            addCriterion("ter_pwd <>", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdGreaterThan(String value) {
            addCriterion("ter_pwd >", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdGreaterThanOrEqualTo(String value) {
            addCriterion("ter_pwd >=", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdLessThan(String value) {
            addCriterion("ter_pwd <", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdLessThanOrEqualTo(String value) {
            addCriterion("ter_pwd <=", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdLike(String value) {
            addCriterion("ter_pwd like", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdNotLike(String value) {
            addCriterion("ter_pwd not like", value, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdIn(List<String> values) {
            addCriterion("ter_pwd in", values, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdNotIn(List<String> values) {
            addCriterion("ter_pwd not in", values, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdBetween(String value1, String value2) {
            addCriterion("ter_pwd between", value1, value2, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPwdNotBetween(String value1, String value2) {
            addCriterion("ter_pwd not between", value1, value2, "terPwd");
            return (Criteria) this;
        }

        public Criteria andTerPhoneIsNull() {
            addCriterion("ter_phone is null");
            return (Criteria) this;
        }

        public Criteria andTerPhoneIsNotNull() {
            addCriterion("ter_phone is not null");
            return (Criteria) this;
        }

        public Criteria andTerPhoneEqualTo(String value) {
            addCriterion("ter_phone =", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneNotEqualTo(String value) {
            addCriterion("ter_phone <>", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneGreaterThan(String value) {
            addCriterion("ter_phone >", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("ter_phone >=", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneLessThan(String value) {
            addCriterion("ter_phone <", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneLessThanOrEqualTo(String value) {
            addCriterion("ter_phone <=", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneLike(String value) {
            addCriterion("ter_phone like", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneNotLike(String value) {
            addCriterion("ter_phone not like", value, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneIn(List<String> values) {
            addCriterion("ter_phone in", values, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneNotIn(List<String> values) {
            addCriterion("ter_phone not in", values, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneBetween(String value1, String value2) {
            addCriterion("ter_phone between", value1, value2, "terPhone");
            return (Criteria) this;
        }

        public Criteria andTerPhoneNotBetween(String value1, String value2) {
            addCriterion("ter_phone not between", value1, value2, "terPhone");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeIsNull() {
            addCriterion("latitude_longitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeIsNotNull() {
            addCriterion("latitude_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeEqualTo(String value) {
            addCriterion("latitude_longitude =", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeNotEqualTo(String value) {
            addCriterion("latitude_longitude <>", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeGreaterThan(String value) {
            addCriterion("latitude_longitude >", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("latitude_longitude >=", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeLessThan(String value) {
            addCriterion("latitude_longitude <", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeLessThanOrEqualTo(String value) {
            addCriterion("latitude_longitude <=", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeLike(String value) {
            addCriterion("latitude_longitude like", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeNotLike(String value) {
            addCriterion("latitude_longitude not like", value, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeIn(List<String> values) {
            addCriterion("latitude_longitude in", values, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeNotIn(List<String> values) {
            addCriterion("latitude_longitude not in", values, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeBetween(String value1, String value2) {
            addCriterion("latitude_longitude between", value1, value2, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLongitudeNotBetween(String value1, String value2) {
            addCriterion("latitude_longitude not between", value1, value2, "latitudeLongitude");
            return (Criteria) this;
        }

        public Criteria andWifiIsNull() {
            addCriterion("wifi is null");
            return (Criteria) this;
        }

        public Criteria andWifiIsNotNull() {
            addCriterion("wifi is not null");
            return (Criteria) this;
        }

        public Criteria andWifiEqualTo(String value) {
            addCriterion("wifi =", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotEqualTo(String value) {
            addCriterion("wifi <>", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiGreaterThan(String value) {
            addCriterion("wifi >", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiGreaterThanOrEqualTo(String value) {
            addCriterion("wifi >=", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiLessThan(String value) {
            addCriterion("wifi <", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiLessThanOrEqualTo(String value) {
            addCriterion("wifi <=", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiLike(String value) {
            addCriterion("wifi like", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotLike(String value) {
            addCriterion("wifi not like", value, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiIn(List<String> values) {
            addCriterion("wifi in", values, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotIn(List<String> values) {
            addCriterion("wifi not in", values, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiBetween(String value1, String value2) {
            addCriterion("wifi between", value1, value2, "wifi");
            return (Criteria) this;
        }

        public Criteria andWifiNotBetween(String value1, String value2) {
            addCriterion("wifi not between", value1, value2, "wifi");
            return (Criteria) this;
        }

        public Criteria andSerialNumIsNull() {
            addCriterion("serial_num is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumIsNotNull() {
            addCriterion("serial_num is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumEqualTo(String value) {
            addCriterion("serial_num =", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumNotEqualTo(String value) {
            addCriterion("serial_num <>", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumGreaterThan(String value) {
            addCriterion("serial_num >", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumGreaterThanOrEqualTo(String value) {
            addCriterion("serial_num >=", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumLessThan(String value) {
            addCriterion("serial_num <", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumLessThanOrEqualTo(String value) {
            addCriterion("serial_num <=", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumLike(String value) {
            addCriterion("serial_num like", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumNotLike(String value) {
            addCriterion("serial_num not like", value, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumIn(List<String> values) {
            addCriterion("serial_num in", values, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumNotIn(List<String> values) {
            addCriterion("serial_num not in", values, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumBetween(String value1, String value2) {
            addCriterion("serial_num between", value1, value2, "serialNum");
            return (Criteria) this;
        }

        public Criteria andSerialNumNotBetween(String value1, String value2) {
            addCriterion("serial_num not between", value1, value2, "serialNum");
            return (Criteria) this;
        }

        public Criteria andTerIdIsNull() {
            addCriterion("ter_id is null");
            return (Criteria) this;
        }

        public Criteria andTerIdIsNotNull() {
            addCriterion("ter_id is not null");
            return (Criteria) this;
        }

        public Criteria andTerIdEqualTo(String value) {
            addCriterion("ter_id =", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdNotEqualTo(String value) {
            addCriterion("ter_id <>", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdGreaterThan(String value) {
            addCriterion("ter_id >", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdGreaterThanOrEqualTo(String value) {
            addCriterion("ter_id >=", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdLessThan(String value) {
            addCriterion("ter_id <", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdLessThanOrEqualTo(String value) {
            addCriterion("ter_id <=", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdLike(String value) {
            addCriterion("ter_id like", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdNotLike(String value) {
            addCriterion("ter_id not like", value, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdIn(List<String> values) {
            addCriterion("ter_id in", values, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdNotIn(List<String> values) {
            addCriterion("ter_id not in", values, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdBetween(String value1, String value2) {
            addCriterion("ter_id between", value1, value2, "terId");
            return (Criteria) this;
        }

        public Criteria andTerIdNotBetween(String value1, String value2) {
            addCriterion("ter_id not between", value1, value2, "terId");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}