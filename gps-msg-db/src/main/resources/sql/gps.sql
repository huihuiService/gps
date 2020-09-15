/*
 Navicat Premium Data Transfer

 Source Server         : aliyun_lin__docker_mysql
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 39.105.17.34:8306
 Source Schema         : gps

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 15/09/2020 15:17:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for equipment_info
-- ----------------------------
DROP TABLE IF EXISTS `equipment_info`;
CREATE TABLE `equipment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `belong_user` varchar(255) DEFAULT NULL COMMENT '所属用户',
  `ter_protocol` varchar(255) DEFAULT NULL COMMENT '终端协议',
  `end_time` datetime DEFAULT NULL COMMENT '到期时间',
  `offline_times` int(11) DEFAULT NULL COMMENT '离线时间',
  `ter_name` varchar(255) DEFAULT NULL COMMENT '终端名称',
  `ter_pwd` varchar(255) DEFAULT NULL COMMENT '终端密码',
  `ter_phone` varchar(255) DEFAULT NULL COMMENT '终端电话',
  `latitude_longitude` varchar(255) DEFAULT NULL COMMENT '经纬度',
  `wifi` varchar(255) DEFAULT NULL COMMENT 'wifi',
  `serial_num` varchar(255) DEFAULT NULL COMMENT '序列号',
  `ter_id` varchar(255) DEFAULT NULL COMMENT '终端ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `imei` varchar(50) NOT NULL,
  `id_name` varchar(50) DEFAULT NULL,
  `phone` varchar(13) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
