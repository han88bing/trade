/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : tuoke

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 15/04/2021 21:34:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tk_menu
-- ----------------------------
DROP TABLE IF EXISTS `tk_menu`;
CREATE TABLE `tk_menu`  (
  `id` int(10) NOT NULL COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(10) NULL DEFAULT NULL COMMENT '父级ID',
  `url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `perm` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `type` tinyint(2) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `is_del` tinyint(2) NULL DEFAULT NULL COMMENT '删除状态 0-删除  1-正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tk_role
-- ----------------------------
DROP TABLE IF EXISTS `tk_role`;
CREATE TABLE `tk_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tk_role
-- ----------------------------

-- ----------------------------
-- Table structure for tk_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tk_role_menu`;
CREATE TABLE `tk_role_menu`  (
  `id` int(10) NOT NULL COMMENT '主键ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tk_security_info
-- ----------------------------
DROP TABLE IF EXISTS `tk_security_info`;
CREATE TABLE `tk_security_info`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券代码',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '证券状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_security_info
-- ----------------------------
INSERT INTO `tk_security_info` VALUES (1, 'Infosys', 'INF', 1, '2021-01-25 17:07:50', '2021-01-25 17:07:52');
INSERT INTO `tk_security_info` VALUES (2, 'Relsys', 'REL', 1, '2021-01-25 17:08:20', '2021-01-25 17:08:23');
INSERT INTO `tk_security_info` VALUES (3, 'Itcsys', 'ITC', 1, '2021-01-25 17:08:46', '2021-01-25 17:08:48');

-- ----------------------------
-- Table structure for tk_trade
-- ----------------------------
DROP TABLE IF EXISTS `tk_trade`;
CREATE TABLE `tk_trade`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易员名称(冗余)',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `security_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券代码',
  `security_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券名称（冗余）',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '数量',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态 1-初始化  2-成功 3-关闭',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型 buy-购买 sell-卖出',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注（关闭原因以及后台数据变更原因）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_orderNo`(`order_no`) USING BTREE COMMENT '订单号',
  INDEX `idx_userId_code`(`user_id`, `security_code`) USING BTREE COMMENT '用户ID + 证券代码'
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_trade
-- ----------------------------
INSERT INTO `tk_trade` VALUES (1, 1, 'zhangsan', 6125566442258903027, 'INF', 'Infosys', 10, 2, 'Buy', NULL, '2021-01-25 09:46:06', '2021-01-25 09:46:06');
INSERT INTO `tk_trade` VALUES (2, 1, 'zhangsan', 4121802163361752251, 'INF', 'Infosys', 1, 2, 'Sell', NULL, '2021-01-25 09:53:43', '2021-01-25 09:53:43');
INSERT INTO `tk_trade` VALUES (3, 1, 'zhangsan', 4121802142973886389, 'INF', 'Infosys', 1, 3, 'Sell', NULL, '2021-01-25 09:55:47', '2021-01-25 09:55:47');
INSERT INTO `tk_trade` VALUES (4, 1, 'zhangsan', 620210125180122666, 'ITC', 'Itcsys', 50, 2, 'Buy', NULL, '2021-01-25 10:01:22', '2021-01-25 10:01:22');
INSERT INTO `tk_trade` VALUES (5, 1, 'zhangsan', 620210125180704976, 'ITC', 'Itcsys', 50, 2, 'Buy', NULL, '2021-01-25 10:07:04', '2021-01-25 10:07:41');
INSERT INTO `tk_trade` VALUES (6, 1, 'zhangsan', 620210125180705850, 'ITC', 'Itcsys', 50, 3, 'Buy', NULL, '2021-01-25 10:07:05', '2021-01-25 10:07:59');

-- ----------------------------
-- Table structure for tk_trade_record
-- ----------------------------
DROP TABLE IF EXISTS `tk_trade_record`;
CREATE TABLE `tk_trade_record`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易员名称(冗余)',
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `security_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券代码',
  `security_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券名称（冗余）',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '数量',
  `version` int(5) NULL DEFAULT NULL COMMENT '版本号  ',
  `operation_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型 INSERT-新增  UPDATE-更新 CANCEL-关闭',
  `trade_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单类型 Buy-购买 Sell -卖出',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId_tradeType`(`user_id`, `trade_type`) USING BTREE COMMENT '用户id+交易类型'
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '交易记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_trade_record
-- ----------------------------
INSERT INTO `tk_trade_record` VALUES (1, 1, 'zhangsan', 6125566442258903027, 'INF', 'Infosys', 10, 1, 'INSERT', 'Buy', '2021-01-25 09:46:06');
INSERT INTO `tk_trade_record` VALUES (2, 1, 'zhangsan', 6125566442258903027, 'INF', 'Infosys', 10, 2, 'UPDATE', 'Buy', '2021-01-25 09:52:47');
INSERT INTO `tk_trade_record` VALUES (3, 1, 'zhangsan', 4121802163361752251, 'INF', 'Infosys', 1, 1, 'INSERT', 'Sell', '2021-01-25 09:53:43');
INSERT INTO `tk_trade_record` VALUES (4, 1, 'zhangsan', 4121802163361752251, 'INF', 'Infosys', 1, 2, 'UPDATE', 'Sell', '2021-01-25 09:55:23');
INSERT INTO `tk_trade_record` VALUES (5, 1, 'zhangsan', 4121802142973886389, 'INF', 'Infosys', 1, 1, 'INSERT', 'Sell', '2021-01-25 09:55:47');
INSERT INTO `tk_trade_record` VALUES (6, 1, 'zhangsan', 4121802142973886389, 'INF', 'Infosys', 1, 2, 'CANCEL', 'Sell', '2021-01-25 09:56:28');
INSERT INTO `tk_trade_record` VALUES (7, 1, 'zhangsan', 620210125180122666, 'ITC', 'Itcsys', 50, 1, 'INSERT', 'Buy', '2021-01-25 10:01:22');
INSERT INTO `tk_trade_record` VALUES (8, 1, 'zhangsan', 620210125180122666, 'ITC', 'Itcsys', 50, 2, 'UPDATE', 'Buy', '2021-01-25 10:04:06');
INSERT INTO `tk_trade_record` VALUES (9, 1, 'zhangsan', 620210125180704976, 'ITC', 'Itcsys', 50, 1, 'INSERT', 'Buy', '2021-01-25 10:07:04');
INSERT INTO `tk_trade_record` VALUES (10, 1, 'zhangsan', 620210125180705850, 'ITC', 'Itcsys', 50, 1, 'INSERT', 'Buy', '2021-01-25 10:07:05');
INSERT INTO `tk_trade_record` VALUES (11, 1, 'zhangsan', 620210125180704976, 'ITC', 'Itcsys', 50, 2, 'UPDATE', 'Buy', '2021-01-25 10:07:41');
INSERT INTO `tk_trade_record` VALUES (12, 1, 'zhangsan', 620210125180705850, 'ITC', 'Itcsys', 50, 2, 'CANCEL', 'Buy', '2021-01-25 10:07:59');

-- ----------------------------
-- Table structure for tk_user
-- ----------------------------
DROP TABLE IF EXISTS `tk_user`;
CREATE TABLE `tk_user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作员名称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(255) NULL DEFAULT NULL COMMENT '用户状态 1-正常 2-禁用',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_phone`(`phone`) USING BTREE COMMENT '用户手机号码'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_user
-- ----------------------------
INSERT INTO `tk_user` VALUES (1, 'zhangsan', NULL, '15000216601', NULL, 1, '2021-01-25 17:11:57', '2021-01-25 17:11:54');
INSERT INTO `tk_user` VALUES (2, 'lisi', NULL, '13090876534', NULL, 1, '2021-01-25 17:12:39', '2021-01-25 17:12:36');

-- ----------------------------
-- Table structure for tk_user_account
-- ----------------------------
DROP TABLE IF EXISTS `tk_user_account`;
CREATE TABLE `tk_user_account`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '交易员ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易员名称（冗余）',
  `security_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券代码',
  `security_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证券名称（冗余）',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '总持有量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId_code`(`user_id`, `security_code`) USING BTREE COMMENT '用户ID+证券代码'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户持有证券信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_user_account
-- ----------------------------
INSERT INTO `tk_user_account` VALUES (1, 1, 'zhangsan', 'INF', 'Infosys', 9, '2021-01-25 09:52:47', '2021-01-25 09:55:23');
INSERT INTO `tk_user_account` VALUES (2, 1, 'zhangsan', 'ITC', 'Itcsys', 100, '2021-01-25 10:04:06', '2021-01-25 10:07:41');

-- ----------------------------
-- Table structure for tk_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tk_user_role`;
CREATE TABLE `tk_user_role`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(10) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(10) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tk_user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
