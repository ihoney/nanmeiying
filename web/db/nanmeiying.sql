/*
Navicat MySQL Data Transfer

Source Server         : localconn
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : nanmeiying

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2015-07-19 12:29:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(11) COLLATE utf8_bin NOT NULL COMMENT '（手机号）账户（需校验11位）',
  `password` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `forbidden` int(1) NOT NULL DEFAULT '0' COMMENT '是否被禁用 0没有 1禁用',
  `validTime` date DEFAULT '2015-12-12' COMMENT '有效日期',
  `loginTime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '登录时间',
  `loginIp` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '登录IP',
  `loginStr` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '登录字符串',
  `loginStatus` int(1) DEFAULT '0' COMMENT '登录状态 0未登录 1登陆路中',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_c_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('26', '11111111111', 'admin', '0', '2015-07-30', '2015-07-19 12:23:15', '127.0.0.1', 'test', '0');
INSERT INTO `customer` VALUES ('27', '13511111111', 'adminroot', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('28', '13336746733', 'adminroot', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('31', '13336746833', 'adminroot', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('32', '13021662367', 'wuzq1234', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('33', '13021662368', 'wuzq123', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('34', '13021662369', 'wuzq123', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('35', '13021662370', 'wuzq123', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('36', '13021662390', 'wuzq123', '0', '2015-12-12', null, null, null, '0');
INSERT INTO `customer` VALUES ('37', '13723998798', '13723998798', '0', '2015-12-12', null, null, null, '0');

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(30) COLLATE utf8_bin NOT NULL,
  `loginStatus` int(1) DEFAULT '0' COMMENT '登录状态 0未登录 1登录中',
  `sessionId` varchar(50) COLLATE utf8_bin DEFAULT '' COMMENT '保存登录的sessionId',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_m_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', 'nanmeiying', 'adminroot88', '1', '784553F1CA4B92B668E5031EF6C24D93');
INSERT INTO `manager` VALUES ('2', 'admin', 'admin', '0', '60CFC476EC8548847C1281FDC80E5B85');
