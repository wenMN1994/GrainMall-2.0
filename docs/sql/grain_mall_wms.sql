/*
 Navicat MySQL Data Transfer

 Source Server         : CentOS_192.168.31.70
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 192.168.31.70:3306
 Source Schema         : grain_mall_wms

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 13/09/2020 21:51:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`  (
  `message_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `to_exchange` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息发送的交换机',
  `routing_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息的路由键',
  `class_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息的类型',
  `message_status` int(1) NULL DEFAULT 0 COMMENT '消息状态：0-新建、1-已发送 、2-错误抵达、3-已抵达',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'MQ消息日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '事务回滚日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wms_purchase
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase`;
CREATE TABLE `wms_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '采购单id',
  `assignee_id` bigint(20) NULL DEFAULT NULL COMMENT '采购人id',
  `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '采购人名',
  `phone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `priority` int(4) NULL DEFAULT NULL COMMENT '优先级',
  `status` int(4) NULL DEFAULT NULL COMMENT '状态',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '总金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase
-- ----------------------------
INSERT INTO `wms_purchase` VALUES (2, 1, 'admin', '13500000001', 1, 3, 1, 33970.0000, '2020-06-29 16:21:13', '2020-06-30 17:21:57');
INSERT INTO `wms_purchase` VALUES (3, 2, 'DragonWen', '18129954693', 1, 3, 1, 376002.0000, '2020-06-29 22:05:28', '2020-06-30 17:28:46');
INSERT INTO `wms_purchase` VALUES (4, 1, 'admin', '13500000001', 1, 2, 1, 33970.0000, '2020-06-30 16:36:04', '2020-06-30 17:25:48');
INSERT INTO `wms_purchase` VALUES (5, 2, 'DragonWen', '18129954693', 1, 1, 2, 46975.0000, '2020-06-30 16:56:48', '2020-06-30 16:56:48');
INSERT INTO `wms_purchase` VALUES (6, 2, 'DragonWen', '18129954693', NULL, 4, 3, 19980.0000, '2020-06-30 17:26:24', '2020-06-30 17:29:52');

-- ----------------------------
-- Table structure for wms_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_purchase_detail`;
CREATE TABLE `wms_purchase_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) NULL DEFAULT NULL COMMENT '采购单id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '采购商品id',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '采购数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '采购金额',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态[0新建，1已分配，2正在采购，3已完成，4采购失败]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '采购信息详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_purchase_detail
-- ----------------------------
INSERT INTO `wms_purchase_detail` VALUES (1, 2, 1, 10, 9990.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (2, 2, 2, 20, 23980.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (3, 3, 3, 99, 178101.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (4, 3, 4, 99, 197901.0000, 1, 3);
INSERT INTO `wms_purchase_detail` VALUES (5, 4, 5, 10, 9990.0000, 1, 2);
INSERT INTO `wms_purchase_detail` VALUES (6, 4, 6, 20, 23980.0000, 1, 2);
INSERT INTO `wms_purchase_detail` VALUES (7, 5, 7, 15, 26985.0000, 2, 1);
INSERT INTO `wms_purchase_detail` VALUES (8, 5, 8, 10, 19990.0000, 2, 1);
INSERT INTO `wms_purchase_detail` VALUES (10, 6, 9, 20, 19980.0000, 3, 4);

-- ----------------------------
-- Table structure for wms_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_info`;
CREATE TABLE `wms_ware_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库名',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `areacode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_info
-- ----------------------------
INSERT INTO `wms_ware_info` VALUES (1, '谷粒广州仓库', '广东省广州市黄埔区广园快速路', '510000');
INSERT INTO `wms_ware_info` VALUES (2, '谷粒北京仓库', ' 北京市朝阳区金盏乡黎各庄7号东方朝鑫物流园', '100000');
INSERT INTO `wms_ware_info` VALUES (3, '谷粒上海仓库', '上海市浦东新区园中路288号', '200000');

-- ----------------------------
-- Table structure for wms_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task`;
CREATE TABLE `wms_ware_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `consignee_tel` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `delivery_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '付款方式【 1:在线付款 2:货到付款】',
  `task_status` tinyint(2) NULL DEFAULT NULL COMMENT '任务状态',
  `order_body` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单描述',
  `tracking_no` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `task_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作单备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task
-- ----------------------------
INSERT INTO `wms_ware_order_task` VALUES (5, NULL, '202009021020266141300981882913501185', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (6, NULL, '202009021120102891300996913931677697', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (7, NULL, '202009021130416001300999561808375810', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (8, NULL, '202009021135582171301000889817296897', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (9, NULL, '202009021814510161301101271260229634', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (10, NULL, '202009022201432511301158365103632386', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (11, NULL, '202009022204526281301159159425048577', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (12, NULL, '202009022207353271301159841825718274', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (13, NULL, '202009022222274731301163583748866050', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (14, NULL, '202009022222328871301163606456827905', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (15, NULL, '202009031023255441301345021261750274', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (16, NULL, '202009031330235991301392073190502401', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (17, NULL, '202009081024581281303157348919390209', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (18, NULL, '202009081028340171303158254410903554', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (19, NULL, '202009101754284241303995246270980098', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (20, NULL, '202009101809174251303998974994653186', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (21, NULL, '202009102145463731304053454603681794', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wms_ware_order_task` VALUES (22, NULL, '202009102247097201304068903685038082', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wms_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_order_task_detail`;
CREATE TABLE `wms_ware_order_task_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '购买个数',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '工作单id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `lock_status` int(1) NULL DEFAULT NULL COMMENT '锁定状态：1-锁定，2-解锁，3-扣减',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '库存工作单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_order_task_detail
-- ----------------------------
INSERT INTO `wms_ware_order_task_detail` VALUES (9, 1, '', 1, 5, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (10, 2, '', 1, 6, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (11, 2, '', 1, 7, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (12, 2, '', 1, 8, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (13, 2, '', 1, 9, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (14, 1, '', 1, 9, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (15, 2, '', 1, 10, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (16, 1, '', 1, 11, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (17, 1, '', 1, 12, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (18, 2, '', 1, 13, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (19, 2, '', 1, 14, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (20, 3, '', 1, 15, 1, 2);
INSERT INTO `wms_ware_order_task_detail` VALUES (21, 3, '', 1, 16, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (22, 1, '', 1, 17, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (23, 2, '', 1, 18, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (24, 3, '', 1, 19, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (25, 3, '', 2, 20, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (26, 2, '', 5, 21, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (27, 2, '', 5, 22, 1, 1);
INSERT INTO `wms_ware_order_task_detail` VALUES (28, 3, '', 3, 22, 1, 1);

-- ----------------------------
-- Table structure for wms_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `wms_ware_sku`;
CREATE TABLE `wms_ware_sku`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '仓库id',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存数',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) NULL DEFAULT 0 COMMENT '锁定库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wms_ware_sku
-- ----------------------------
INSERT INTO `wms_ware_sku` VALUES (1, 1, 1, 110, '华为荣耀9X 幻夜黑 4G+64G', 1);
INSERT INTO `wms_ware_sku` VALUES (2, 3, 1, 198, '华为荣耀9X 幻夜黑 6G+128G', 7);
INSERT INTO `wms_ware_sku` VALUES (3, 4, 1, 198, '华为荣耀9X 幻夜黑 8G+128G', 0);
INSERT INTO `wms_ware_sku` VALUES (4, 2, 1, 20, '华为荣耀9X 幻夜黑 6G+64G', 11);

SET FOREIGN_KEY_CHECKS = 1;
