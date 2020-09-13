/*
 Navicat MySQL Data Transfer

 Source Server         : CentOS_192.168.31.70
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 192.168.31.70:3306
 Source Schema         : grain_mall_oms

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 13/09/2020 21:50:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '使用的优惠券',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '订单总额',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '应付总额',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '运费金额',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促销优化金额（促销价、满减、阶梯价）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分抵扣金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券抵扣金额',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '后台调整订单使用的折扣金额',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '订单来源[0->PC订单；1->app订单]',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `delivery_company` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT '自动确认时间（天）',
  `integration` int(11) NULL DEFAULT NULL COMMENT '可以获得的积分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '可以获得的成长值',
  `bill_type` tinyint(4) NULL DEFAULT NULL COMMENT '发票类型[0->不开发票；1->电子发票；2->纸质发票]',
  `bill_header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票抬头',
  `bill_content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发票内容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收票人电话',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收票人邮箱',
  `receiver_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_post_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人邮编',
  `receiver_province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份/直辖市',
  `receiver_city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `confirm_status` tinyint(4) NULL DEFAULT NULL COMMENT '确认收货状态[0->未确认；1->已确认]',
  `delete_status` tinyint(4) NULL DEFAULT NULL COMMENT '删除状态【0->未删除；1->已删除】',
  `use_integration` int(11) NULL DEFAULT NULL COMMENT '下单时使用的积分',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '确认收货时间',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1, 2, '202009021020266141300981882913501185', NULL, '2020-09-02 10:20:27', NULL, 999.0000, 1001.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 999, 999, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (2, 2, '202009021120102891300996913931677697', NULL, '2020-09-02 11:20:11', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (3, 2, '202009021130416001300999561808375810', NULL, '2020-09-02 11:30:42', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (4, 2, '202009021135582171301000889817296897', NULL, '2020-09-02 11:35:59', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (5, 2, '202009021814510161301101271260229634', NULL, '2020-09-02 18:14:52', NULL, 2198.0000, 2200.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 2198, 2198, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (6, 2, '202009022201432511301158365103632386', NULL, '2020-09-02 22:01:46', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (7, 2, '202009022204526281301159159425048577', NULL, '2020-09-02 22:04:54', NULL, 999.0000, 1001.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 999, 999, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (8, 2, '202009022207353271301159841825718274', NULL, '2020-09-02 22:07:37', NULL, 999.0000, 1001.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 999, 999, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (9, 2, '202009022222274731301163583748866050', NULL, '2020-09-02 22:22:28', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (10, 2, '202009022222328871301163606456827905', NULL, '2020-09-02 22:22:34', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (11, 2, '202009031023255441301345021261750274', NULL, '2020-09-03 10:23:26', NULL, 1799.0000, 1801.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 4, NULL, NULL, 7, 1799, 1799, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (12, 2, '202009031330235991301392073190502401', NULL, '2020-09-03 13:30:24', NULL, 1799.0000, 1801.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 1799, 1799, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (13, 2, '202009061142205181302452044778930177', NULL, NULL, NULL, NULL, 799.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (14, 2, '202009061209346011302458898623979521', NULL, NULL, NULL, NULL, 799.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (15, 2, '202009061530220481302509429274943490', NULL, NULL, NULL, NULL, 799.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (16, 2, '202009081024581281303157348919390209', NULL, '2020-09-08 10:24:59', NULL, 999.0000, 1001.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 999, 999, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (17, 2, '202009081028340171303158254410903554', NULL, '2020-09-08 10:28:35', NULL, 1199.0000, 1201.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 1199, 1199, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (18, 2, '202009081030428191303158794658234370', NULL, '2020-09-08 10:30:43', NULL, NULL, 799.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (19, 2, '202009101754284241303995246270980098', NULL, '2020-09-10 17:54:29', NULL, 1799.0000, 1802.0000, 3.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 1799, 1799, NULL, NULL, NULL, NULL, NULL, '温文星', '18129954693', '518000', '广东省', '深圳市', '南山区', '蛇口街道望海路利安商务A座二楼', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (20, 2, '202009101808059781303998675353554945', NULL, '2020-09-10 18:08:06', NULL, NULL, 799.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (21, 2, '202009101809174251303998974994653186', NULL, '2020-09-10 18:09:18', NULL, 3598.0000, 3600.0000, 2.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 3598, 3598, NULL, NULL, NULL, NULL, NULL, '温文星', '18475536452', '518116', '广东省', '深圳市', '龙岗区', '龙城街道爱联新屯村围肚南二巷四号', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (22, 2, '202009102145463731304053454603681794', NULL, '2020-09-10 21:45:47', NULL, 5995.0000, 5998.0000, 3.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 5995, 5995, NULL, NULL, NULL, NULL, NULL, '温文星', '18129954693', '518000', '广东省', '深圳市', '南山区', '蛇口街道望海路利安商务A座二楼', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (23, 2, '202009102247097201304068903685038082', NULL, '2020-09-10 22:47:11', NULL, 11392.0000, 11395.0000, 3.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 1, NULL, NULL, 7, 11392, 11392, NULL, NULL, NULL, NULL, NULL, '温文星', '18129954693', '518000', '广东省', '深圳市', '南山区', '蛇口街道望海路利安商务A座二楼', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oms_order` VALUES (24, 2, '202009102250099491304069659616055298', NULL, '2020-09-10 22:50:10', NULL, NULL, 799.0000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分类id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku编号',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品sku图片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku价格',
  `sku_quantity` int(11) NULL DEFAULT NULL COMMENT '商品购买的数量',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品销售属性组合（JSON）',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品促销分解金额',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '优惠券优惠分解金额',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '积分优惠分解金额',
  `real_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '该商品经过优惠后的分解金额',
  `gift_integration` int(11) NULL DEFAULT NULL COMMENT '赠送积分',
  `gift_growth` int(11) NULL DEFAULT NULL COMMENT '赠送成长值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单项信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (1, NULL, '202009021020266141300981882913501185', 2, '华为荣耀9X', NULL, '华为', 225, 1, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 4G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 999.0000, 1, '颜色：幻夜黑;版本：4G+64G', 0.0000, 0.0000, 0.0000, 999.0000, 999, 999);
INSERT INTO `oms_order_item` VALUES (2, NULL, '202009021120102891300996913931677697', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (3, NULL, '202009021130416001300999561808375810', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (4, NULL, '202009021135582171301000889817296897', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (5, NULL, '202009021814510161301101271260229634', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (6, NULL, '202009021814510161301101271260229634', 2, '华为荣耀9X', NULL, '华为', 225, 1, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 4G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 999.0000, 1, '颜色：幻夜黑;版本：4G+64G', 0.0000, 0.0000, 0.0000, 999.0000, 999, 999);
INSERT INTO `oms_order_item` VALUES (7, NULL, '202009022201432511301158365103632386', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (8, NULL, '202009022204526281301159159425048577', 2, '华为荣耀9X', NULL, '华为', 225, 1, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 4G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 999.0000, 1, '颜色：幻夜黑;版本：4G+64G', 0.0000, 0.0000, 0.0000, 999.0000, 999, 999);
INSERT INTO `oms_order_item` VALUES (9, NULL, '202009022207353271301159841825718274', 2, '华为荣耀9X', NULL, '华为', 225, 1, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 4G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 999.0000, 1, '颜色：幻夜黑;版本：4G+64G', 0.0000, 0.0000, 0.0000, 999.0000, 999, 999);
INSERT INTO `oms_order_item` VALUES (10, NULL, '202009022222274731301163583748866050', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (11, NULL, '202009022222328871301163606456827905', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (12, NULL, '202009031023255441301345021261750274', 2, '华为荣耀9X', NULL, '华为', 225, 3, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+128G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1799.0000, 1, '颜色：幻夜黑;版本：6G+128G', 0.0000, 0.0000, 0.0000, 1799.0000, 1799, 1799);
INSERT INTO `oms_order_item` VALUES (13, NULL, '202009031330235991301392073190502401', 2, '华为荣耀9X', NULL, '华为', 225, 3, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+128G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1799.0000, 1, '颜色：幻夜黑;版本：6G+128G', 0.0000, 0.0000, 0.0000, 1799.0000, 1799, 1799);
INSERT INTO `oms_order_item` VALUES (14, NULL, '202009061142205181302452044778930177', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 799.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (15, NULL, '202009061209346011302458898623979521', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 799.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (16, NULL, '202009061530220481302509429274943490', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 799.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (17, NULL, '202009081024581281303157348919390209', 2, '华为荣耀9X', NULL, '华为', 225, 1, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 4G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 999.0000, 1, '颜色：幻夜黑;版本：4G+64G', 0.0000, 0.0000, 0.0000, 999.0000, 999, 999);
INSERT INTO `oms_order_item` VALUES (18, NULL, '202009081028340171303158254410903554', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 1, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 1199.0000, 1199, 1199);
INSERT INTO `oms_order_item` VALUES (19, NULL, '202009081030428191303158794658234370', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 799.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (20, NULL, '202009101754284241303995246270980098', 2, '华为荣耀9X', NULL, '华为', 225, 3, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+128G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1799.0000, 1, '颜色：幻夜黑;版本：6G+128G', 0.0000, 0.0000, 0.0000, 1799.0000, 1799, 1799);
INSERT INTO `oms_order_item` VALUES (21, NULL, '202009101808059781303998675353554945', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 799.0000, NULL, NULL);
INSERT INTO `oms_order_item` VALUES (22, NULL, '202009101809174251303998974994653186', 2, '华为荣耀9X', NULL, '华为', 225, 3, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+128G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1799.0000, 2, '颜色：幻夜黑;版本：6G+128G', 0.0000, 0.0000, 0.0000, 3598.0000, 3598, 3598);
INSERT INTO `oms_order_item` VALUES (23, NULL, '202009102145463731304053454603681794', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 5, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 5995.0000, 5995, 5995);
INSERT INTO `oms_order_item` VALUES (24, NULL, '202009102247097201304068903685038082', 2, '华为荣耀9X', NULL, '华为', 225, 2, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+64G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1199.0000, 5, '颜色：幻夜黑;版本：6G+64G', 0.0000, 0.0000, 0.0000, 5995.0000, 5995, 5995);
INSERT INTO `oms_order_item` VALUES (25, NULL, '202009102247097201304068903685038082', 2, '华为荣耀9X', NULL, '华为', 225, 3, '华为荣耀9X 麒麟810 4000mAh超强续航 4800万超清夜拍 6.59英寸升降全面屏 全网通 幻夜黑 6G+128G', 'https://grain-mall-dragon.oss-cn-shenzhen.aliyuncs.com/2020-06-29/bfa7e6cf-98cc-4d19-82c0-e957395e99d3_幻夜黑主图.jpg', 1799.0000, 3, '颜色：幻夜黑;版本：6G+128G', 0.0000, 0.0000, 0.0000, 5397.0000, 5397, 5397);
INSERT INTO `oms_order_item` VALUES (26, NULL, '202009102250099491304069659616055298', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 799.0000, NULL, NULL);

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `operate_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人[用户；系统；后台管理员]',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  `order_status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单操作历史记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '退货商品id',
  `order_sn` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申请时间',
  `member_username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员用户名',
  `return_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `return_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人姓名',
  `return_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货人电话',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `sku_img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `sku_brand` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品销售属性(JSON)',
  `sku_count` int(11) NULL DEFAULT NULL COMMENT '退货数量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品单价',
  `sku_real_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品实际支付单价',
  `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '凭证图片，以逗号隔开',
  `handle_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理备注',
  `handle_man` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人员',
  `receive_man` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '收货时间',
  `receive_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货备注',
  `receive_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货电话',
  `company_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单退货申请' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退货原因名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '退货原因' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) NULL DEFAULT NULL COMMENT '秒杀订单超时关闭时间(分)',
  `normal_order_overtime` int(11) NULL DEFAULT NULL COMMENT '正常订单超时时间(分)',
  `confirm_overtime` int(11) NULL DEFAULT NULL COMMENT '发货后自动确认收货时间（天）',
  `finish_overtime` int(11) NULL DEFAULT NULL COMMENT '自动完成交易时间，不能申请退货（天）',
  `comment_overtime` int(11) NULL DEFAULT NULL COMMENT '订单完成后自动好评时间（天）',
  `member_level` tinyint(2) NULL DEFAULT NULL COMMENT '会员等级【0-不限会员等级，全部通用；其他-对应的其他会员等级】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单配置信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单号（对外业务号）',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `alipay_trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付宝交易流水号',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '支付总金额',
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '交易内容',
  `payment_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '确认时间',
  `callback_content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调内容',
  `callback_time` datetime(0) NULL DEFAULT NULL COMMENT '回调时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_sn`(`order_sn`) USING BTREE,
  UNIQUE INDEX `alipay_trade_no`(`alipay_trade_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oms_payment_info
-- ----------------------------
INSERT INTO `oms_payment_info` VALUES (1, '202009031330235991301392073190502401', NULL, '2020090322001458870501109022', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-03 13:30:44');
INSERT INTO `oms_payment_info` VALUES (2, '202009081024581281303157348919390209', NULL, '2020090822001458870501118763', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-08 10:25:20');
INSERT INTO `oms_payment_info` VALUES (3, '202009081028340171303158254410903554', NULL, '2020090822001458870501116894', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-08 10:28:49');
INSERT INTO `oms_payment_info` VALUES (4, '202009081030428191303158794658234370', NULL, '2020090822001458870501117783', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-08 10:30:59');
INSERT INTO `oms_payment_info` VALUES (5, '202009101754284241303995246270980098', NULL, '2020091022001458870501122450', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-10 17:54:46');
INSERT INTO `oms_payment_info` VALUES (6, '202009101808059781303998675353554945', NULL, '2020091022001458870501124023', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-10 18:08:27');
INSERT INTO `oms_payment_info` VALUES (7, '202009101809174251303998974994653186', NULL, '2020091022001458870501124024', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-10 18:09:41');
INSERT INTO `oms_payment_info` VALUES (8, '202009102145463731304053454603681794', NULL, '2020091022001458870501124534', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-10 21:46:12');
INSERT INTO `oms_payment_info` VALUES (9, '202009102247097201304068903685038082', NULL, '2020091022001458870501124537', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-10 22:47:28');
INSERT INTO `oms_payment_info` VALUES (10, '202009102250099491304069659616055298', NULL, '2020091022001458870501124538', NULL, NULL, 'TRADE_SUCCESS', NULL, NULL, NULL, '2020-09-10 22:50:29');

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) NULL DEFAULT NULL COMMENT '退款的订单',
  `refund` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金额',
  `refund_sn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款交易流水号',
  `refund_status` tinyint(1) NULL DEFAULT NULL COMMENT '退款状态',
  `refund_channel` tinyint(4) NULL DEFAULT NULL COMMENT '退款渠道[1-支付宝，2-微信，3-银联，4-汇款]',
  `refund_content` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '退款信息' ROW_FORMAT = Dynamic;

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

SET FOREIGN_KEY_CHECKS = 1;
