/*
Navicat MySQL Data Transfer

Source Server         : localconn
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : nanmeiying

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2015-07-09 01:53:35
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_c_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('26', '13333333333', 'rootadmin', '0', '2017-04-03');
INSERT INTO `customer` VALUES ('27', '13511111111', 'adminroot', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('28', '13336746733', 'adminroot', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('31', '13336746833', 'adminroot', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('32', '13021662367', 'wuzq1234', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('33', '13021662368', 'wuzq123', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('34', '13021662369', 'wuzq123', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('35', '13021662370', 'wuzq123', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('36', '13021662390', 'wuzq123', '0', '2015-12-12');
INSERT INTO `customer` VALUES ('37', '13723998798', '13723998798', '0', '2015-12-12');

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
INSERT INTO `manager` VALUES ('1', 'nanmeiying', 'nanmeiying', '0', null);
INSERT INTO `manager` VALUES ('2', 'admin', 'admin', '0', 'F43FD6C1026C980605ABE0CCEDA2827A');
