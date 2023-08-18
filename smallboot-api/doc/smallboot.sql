/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql_3306
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26-log)
 Source Host           : 127.0.0.1:3306
 Source Schema         : smallboot

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26-log)
 File Encoding         : 65001

 Date: 18/08/2023 18:07:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oms_logistic
-- ----------------------------
DROP TABLE IF EXISTS `oms_logistic`;
CREATE TABLE `oms_logistic`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `logistics_company` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物流公司',
  `logistics_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '快递公司编码',
  `logistics_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '物流单号',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人电话（SF快递查询物流必用字段）',
  `trace_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '事件轨迹集',
  `deliverer_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '送货人名称',
  `deliverer_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '送货人电话号码',
  `location` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `status` int(2) NOT NULL DEFAULT 0 COMMENT '物流状态',
  `status_ex` int(2) NULL DEFAULT NULL COMMENT '增值物流状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-物流信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_logistic
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `wx_openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话',
  `user_sex` tinyint(4) NOT NULL DEFAULT -1 COMMENT '性别(0->未知;1->男;2->女)',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付类型(1->微信 2->支付宝)',
  `pay_no` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付流水号',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `un_pay_end_time` datetime NULL DEFAULT NULL COMMENT '未支付结束时间',
  `total_price` int(11) NOT NULL DEFAULT 0 COMMENT '商品原价总金额(单位:分)',
  `freight` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '运费(单位:分 0:包邮)',
  `pay_price` int(11) NULL DEFAULT 0 COMMENT '实付总金额(单位:分)',
  `order_status` tinyint(4) UNSIGNED NOT NULL DEFAULT 1 COMMENT '订单状态(1->待支付 2->已取消 3->待发货 4->待收货 5->已完成 6->已退款)',
  `order_source` tinyint(4) UNSIGNED NULL DEFAULT 1 COMMENT '订单来源(1->微信小程序 2->支付宝小程序)',
  `order_remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单备注',
  `buyer_msg` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '买家留言',
  `is_buyer_rate` tinyint(2) NULL DEFAULT 0 COMMENT '买家是否已经评价(0->否 1->是)',
  `order_end_time` datetime NULL DEFAULT NULL COMMENT '订单完成时间',
  `order_close_time` datetime NULL DEFAULT NULL COMMENT '订单关闭时间',
  `deliver_type` tinyint(4) NULL DEFAULT NULL COMMENT '发货方式(1->快递 2->无需发货)',
  `receiver_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收货人地址',
  `stock_check_type` tinyint(2) NOT NULL COMMENT '库存校验类型(1->下单校验 2->支付校验)',
  `after_sale_end_time` datetime NULL DEFAULT NULL COMMENT '售后处理截止时间',
  `auto_receipt_time` datetime NULL DEFAULT NULL COMMENT '自动收货时间',
  `is_send_coupon` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否发放优惠券(1->是，0->否)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`order_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------
INSERT INTO `oms_order` VALUES (1690998996027056128, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 0, NULL, NULL, NULL, '2023-08-14 16:14:13', 1280, 0, 1280, 2, 1, '测试下单', NULL, 0, NULL, '2023-08-14 16:14:10', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-14 16:09:13', '2023-08-14 16:14:10', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1691001927841357824, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 0, NULL, NULL, NULL, '2023-08-14 16:25:52', 980, 0, 980, 2, 1, '支付订单', NULL, 0, NULL, '2023-08-14 16:25:49', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-14 16:20:52', '2023-08-14 16:25:49', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1691003357125943296, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 0, NULL, '1691003364960903168', '2023-08-14 16:26:34', '2023-08-14 16:31:32', 980, 0, 980, 3, 1, '支付成功', NULL, 0, NULL, NULL, 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-14 16:26:32', '2023-08-14 16:26:34', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1691008708147683328, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 0, NULL, NULL, NULL, '2023-08-14 16:52:48', 460, 0, 460, 2, 1, '测试', NULL, 0, NULL, '2023-08-14 16:47:58', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-14 16:47:48', '2023-08-14 16:47:58', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1691008787554246656, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 0, NULL, NULL, NULL, '2023-08-16 11:00:07', 460, 0, 460, 2, 1, NULL, NULL, 0, NULL, '2023-08-14 16:53:04', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-14 16:48:07', '2023-08-16 10:59:23', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1691755568333078528, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-16 18:20:33', 210, 0, 210, 2, 1, '11', NULL, 0, NULL, '2023-08-16 18:15:43', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-16 18:15:34', '2023-08-16 18:15:43', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1691755568333078529, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, '1691755707986624512', '2023-08-16 18:16:07', '2023-08-16 18:20:33', 210, 0, 210, 3, 1, '11', NULL, 0, NULL, NULL, 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-16 18:15:34', '2023-08-16 18:16:07', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1691756567714086912, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-16 18:24:32', 210, 0, 210, 2, 1, '88', NULL, 0, NULL, '2023-08-16 18:24:29', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-16 18:19:32', '2023-08-16 18:24:29', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1691756737994440704, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, '1691756764259172352', '2023-08-16 18:20:19', '2023-08-16 18:25:12', 470, 0, 470, 3, 1, '嘻嘻嘻嘻', NULL, 0, NULL, NULL, 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-16 18:20:12', '2023-08-16 18:20:19', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1692349466218610688, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 09:40:30', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 09:40:27', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 09:35:30', '2023-08-18 09:40:27', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692351884775931904, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 09:50:06', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 09:50:04', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 09:45:06', '2023-08-18 09:50:04', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692351997791453184, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 09:50:33', 360, 0, 360, 2, 1, '22', NULL, 0, NULL, '2023-08-18 09:50:30', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 09:45:33', '2023-08-18 09:50:30', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692354448699441152, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:00:18', 360, 0, 360, 2, 1, '22', NULL, 0, NULL, '2023-08-18 10:00:15', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 09:55:18', '2023-08-18 10:00:15', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692354576868982784, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:00:48', 360, 0, 360, 2, 1, '22', NULL, 0, NULL, '2023-08-18 10:00:45', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 09:55:48', '2023-08-18 10:00:45', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692354615574020096, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:00:57', 360, 0, 360, 2, 1, NULL, NULL, 0, NULL, '2023-08-18 10:00:55', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 09:55:57', '2023-08-18 10:00:55', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692355768634327040, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:05:32', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:05:29', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:00:32', '2023-08-18 10:05:29', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692356960554860544, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:10:17', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:10:14', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:05:17', '2023-08-18 10:10:14', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692357091815604224, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:10:48', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:10:45', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:05:48', '2023-08-18 10:10:45', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692357422121238528, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:12:07', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:12:04', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:07:07', '2023-08-18 10:12:04', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692357475330179072, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:12:19', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:12:16', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:07:19', '2023-08-18 10:12:16', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692357580552683520, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:12:44', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:12:41', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:07:44', '2023-08-18 10:12:41', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692357665151795200, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:13:05', 360, 0, 360, 2, 1, '111', NULL, 0, NULL, '2023-08-18 10:13:02', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:08:05', '2023-08-18 10:13:02', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692358145420574720, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:14:59', 360, 0, 360, 2, 1, '11', NULL, 0, NULL, '2023-08-18 10:14:56', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:09:59', '2023-08-18 10:14:56', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692358190622588928, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:15:10', 360, 0, 360, 2, 1, '22', NULL, 0, NULL, '2023-08-18 10:10:13', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:10:10', '2023-08-18 10:10:13', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1692359592967159808, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 10:37:10', 360, 0, 360, 1, 1, '11', NULL, 0, NULL, '2023-08-18 10:20:41', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 10:15:44', '2023-08-18 10:37:05', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692372828466724864, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 11:13:20', 1500, 0, 1500, 2, 1, '11', NULL, 0, NULL, '2023-08-18 11:13:17', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 11:08:20', '2023-08-18 11:13:17', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692373035610816512, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 11:14:09', 1500, 0, 1500, 2, 1, '11', NULL, 0, NULL, '2023-08-18 11:14:06', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 11:09:09', '2023-08-18 11:14:06', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1692373856251559936, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-08-18 11:17:25', 1500, 0, 1500, 2, 1, '11', NULL, 0, NULL, '2023-08-18 11:17:22', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-08-18 11:12:25', '2023-08-18 11:17:22', 1, 0, 0);

-- ----------------------------
-- Table structure for oms_order_after_sale
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_after_sale`;
CREATE TABLE `oms_order_after_sale`  (
  `after_sale_no` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `user_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户电话',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `after_type` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '售后类型(1-退款 2-退货退款 3-换货)',
  `after_status` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '售后状态(1->用户申请售后 2->用户撤销申请 3->同意申请 4->拒绝申请 5->申请退款 6->同意退款 7->拒绝退款 8->退款中 9->售后完成 10->已关闭)',
  `after_reason` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款,退/换货 原因',
  `after_explain` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `pay_price` int(11) NOT NULL DEFAULT 0 COMMENT '订单实付总金额(单位:分)',
  `freight` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '订单运费(单位:分 0:包邮)',
  `procedure_price` int(11) NULL DEFAULT NULL COMMENT '手续费(单位:分)',
  `apply_refund_price` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '申请退款金额 (单位：分)',
  `refund_price` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '实际退款金额 (单位：分)',
  `refund_time` datetime NULL DEFAULT NULL COMMENT '退款时间',
  `cert_img_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '凭证图',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '售后申请时间',
  `return_logistics_company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 物流公司',
  `return_logistics_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 物流公司编码',
  `return_logistics_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 物流单号',
  `return_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退/换货 收货地址',
  `again_logistics_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商店重发物流单号',
  `handler_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '处理人ID',
  `handler_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人姓名',
  `handler_result_for_refund` tinyint(1) NULL DEFAULT NULL COMMENT '处理人结果-处理退款（1->同意 0->拒绝）',
  `handler_remark_for_refund` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人备注-处理退款',
  `handler_time_for_refund` datetime NULL DEFAULT NULL COMMENT '处理人处理时间-处理退款',
  `handler_result_for_apply` tinyint(1) NULL DEFAULT NULL COMMENT '处理人结果-处理申请（1->同意 0->拒绝）',
  `handler_remark_for_apply` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人备注-处理申请',
  `handler_time_for_apply` datetime NULL DEFAULT NULL COMMENT '处理人处理时间-处理申请',
  `seller_auto_close_time` datetime NULL DEFAULT NULL COMMENT '售后卖家自动关闭时间',
  `buyer_auto_close_time` datetime NULL DEFAULT NULL COMMENT '售后买家自动关闭时间',
  `close_time` datetime NULL DEFAULT NULL COMMENT '售后关闭时间',
  `close_remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '售后关闭备注',
  `receiver_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人电话',
  `receiver_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '收货人地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`after_sale_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-售后表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_after_sale
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_after_sale_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_after_sale_item`;
CREATE TABLE `oms_order_after_sale_item`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `after_sale_no` bigint(20) NOT NULL COMMENT '售后编号',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `order_item_id` bigint(20) NOT NULL COMMENT '订单详情id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-售后详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_after_sale_item
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户ID',
  `spu_id` bigint(20) NOT NULL COMMENT '商品ID',
  `sku_id` bigint(20) NOT NULL COMMENT '商品sku-id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `cover_img` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面图',
  `spec_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品规格属性',
  `num` int(11) UNSIGNED NOT NULL COMMENT '数量',
  `price` int(11) NOT NULL DEFAULT 0 COMMENT '单价(单位:分)  ',
  `total_price` int(11) NOT NULL DEFAULT 0 COMMENT '总价(单位:分)',
  `type` tinyint(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型(101->实物 102->虚拟-优惠券)',
  `status` tinyint(4) UNSIGNED NOT NULL COMMENT '状态(1->待支付 2->已取消 3->未发货 4->已发货 5->已完成)',
  `is_rate` tinyint(2) NULL DEFAULT 0 COMMENT '买家是否已经评价(0->否 1->是)',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '优惠券ID',
  `coupon_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券名字',
  `coupon_num` int(11) NULL DEFAULT NULL COMMENT '优惠券数量',
  `is_send_coupon` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否发放优惠券(1->是，0->否)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE COMMENT '订单编号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------
INSERT INTO `oms_order_item` VALUES (1690998996073193472, 1, 1690998996027056128, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 5, 100, 500, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:09:13', '2023-08-14 16:14:09');
INSERT INTO `oms_order_item` VALUES (1690998996073193473, 1, 1690998996027056128, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 3, 260, 780, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:09:13', '2023-08-14 16:14:09');
INSERT INTO `oms_order_item` VALUES (1691001927853940736, 1, 1691001927841357824, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:20:52', '2023-08-14 16:25:48');
INSERT INTO `oms_order_item` VALUES (1691001927853940737, 1, 1691001927841357824, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 3, 260, 780, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:20:52', '2023-08-14 16:25:48');
INSERT INTO `oms_order_item` VALUES (1691003357138526208, 1, 1691003357125943296, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-14 16:26:32', '2023-08-14 16:26:34');
INSERT INTO `oms_order_item` VALUES (1691003357138526209, 1, 1691003357125943296, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 3, 260, 780, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-14 16:26:32', '2023-08-14 16:26:34');
INSERT INTO `oms_order_item` VALUES (1691008708164460544, 1, 1691008708147683328, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:47:48', '2023-08-14 16:47:57');
INSERT INTO `oms_order_item` VALUES (1691008708164460545, 1, 1691008708147683328, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:47:48', '2023-08-14 16:47:57');
INSERT INTO `oms_order_item` VALUES (1691008787566829568, 1, 1691008787554246656, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:48:07', '2023-08-14 16:53:04');
INSERT INTO `oms_order_item` VALUES (1691008787566829569, 1, 1691008787554246656, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-14 16:48:07', '2023-08-14 16:53:04');
INSERT INTO `oms_order_item` VALUES (1691755568794451968, 1, 1691755568333078528, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-16 18:15:34', '2023-08-16 18:15:43');
INSERT INTO `oms_order_item` VALUES (1691755568794451969, 1, 1691755568333078529, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-16 18:15:34', '2023-08-16 18:16:06');
INSERT INTO `oms_order_item` VALUES (1691755568794451970, 1, 1691755568333078529, 1, 1534420706752856064, 1688482481032142848, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284024536563712\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-16 18:15:34', '2023-08-16 18:16:06');
INSERT INTO `oms_order_item` VALUES (1691755568794451971, 1, 1691755568333078528, 1, 1534420706752856064, 1688482481032142848, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284024536563712\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-16 18:15:34', '2023-08-16 18:15:43');
INSERT INTO `oms_order_item` VALUES (1691756567726669824, 1, 1691756567714086912, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-16 18:19:32', '2023-08-16 18:24:28');
INSERT INTO `oms_order_item` VALUES (1691756567726669825, 1, 1691756567714086912, 1, 1534420706752856064, 1688482481032142848, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284024536563712\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-16 18:19:32', '2023-08-16 18:24:28');
INSERT INTO `oms_order_item` VALUES (1691756738015412224, 1, 1691756737994440704, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-16 18:20:12', '2023-08-16 18:20:18');
INSERT INTO `oms_order_item` VALUES (1691756738015412225, 1, 1691756737994440704, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-16 18:20:12', '2023-08-16 18:20:18');
INSERT INTO `oms_order_item` VALUES (1691756738015412226, 1, 1691756737994440704, 1, 1534420706752856064, 1688482481032142848, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284024536563712\",\"attrValueName\":\"蓝色\"}]', 1, 10, 10, 101, 3, 0, NULL, NULL, NULL, 0, '2023-08-16 18:20:12', '2023-08-16 18:20:18');
INSERT INTO `oms_order_item` VALUES (1692349466268942336, 1, 1692349466218610688, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:35:30', '2023-08-18 09:40:26');
INSERT INTO `oms_order_item` VALUES (1692349466268942337, 1, 1692349466218610688, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:35:30', '2023-08-18 09:40:26');
INSERT INTO `oms_order_item` VALUES (1692351884901761024, 1, 1692351884775931904, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:45:06', '2023-08-18 09:50:03');
INSERT INTO `oms_order_item` VALUES (1692351884901761025, 1, 1692351884775931904, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:45:06', '2023-08-18 09:50:03');
INSERT INTO `oms_order_item` VALUES (1692351997804036096, 1, 1692351997791453184, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:45:33', '2023-08-18 09:50:30');
INSERT INTO `oms_order_item` VALUES (1692351997804036097, 1, 1692351997791453184, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:45:33', '2023-08-18 09:50:30');
INSERT INTO `oms_order_item` VALUES (1692354448712024064, 1, 1692354448699441152, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:55:18', '2023-08-18 10:00:14');
INSERT INTO `oms_order_item` VALUES (1692354448712024065, 1, 1692354448699441152, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:55:18', '2023-08-18 10:00:14');
INSERT INTO `oms_order_item` VALUES (1692354576885760000, 1, 1692354576868982784, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:55:48', '2023-08-18 10:00:45');
INSERT INTO `oms_order_item` VALUES (1692354576885760001, 1, 1692354576868982784, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:55:48', '2023-08-18 10:00:45');
INSERT INTO `oms_order_item` VALUES (1692354615590797312, 1, 1692354615574020096, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:55:57', '2023-08-18 10:00:54');
INSERT INTO `oms_order_item` VALUES (1692354615590797313, 1, 1692354615574020096, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 09:55:57', '2023-08-18 10:00:54');
INSERT INTO `oms_order_item` VALUES (1692355768646909952, 1, 1692355768634327040, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:00:32', '2023-08-18 10:05:29');
INSERT INTO `oms_order_item` VALUES (1692355768646909953, 1, 1692355768634327040, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:00:32', '2023-08-18 10:05:29');
INSERT INTO `oms_order_item` VALUES (1692356960567443456, 1, 1692356960554860544, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:05:17', '2023-08-18 10:10:13');
INSERT INTO `oms_order_item` VALUES (1692356960567443457, 1, 1692356960554860544, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:05:17', '2023-08-18 10:10:13');
INSERT INTO `oms_order_item` VALUES (1692357091823992832, 1, 1692357091815604224, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:05:48', '2023-08-18 10:10:44');
INSERT INTO `oms_order_item` VALUES (1692357091823992833, 1, 1692357091815604224, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:05:48', '2023-08-18 10:10:44');
INSERT INTO `oms_order_item` VALUES (1692357422133821440, 1, 1692357422121238528, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:07:07', '2023-08-18 10:12:03');
INSERT INTO `oms_order_item` VALUES (1692357422133821441, 1, 1692357422121238528, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:07:07', '2023-08-18 10:12:03');
INSERT INTO `oms_order_item` VALUES (1692357475342761984, 1, 1692357475330179072, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:07:19', '2023-08-18 10:12:16');
INSERT INTO `oms_order_item` VALUES (1692357475342761985, 1, 1692357475330179072, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:07:19', '2023-08-18 10:12:16');
INSERT INTO `oms_order_item` VALUES (1692357580569460736, 1, 1692357580552683520, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:07:44', '2023-08-18 10:12:41');
INSERT INTO `oms_order_item` VALUES (1692357580569460737, 1, 1692357580552683520, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:07:44', '2023-08-18 10:12:41');
INSERT INTO `oms_order_item` VALUES (1692357665164378112, 1, 1692357665151795200, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:08:05', '2023-08-18 10:13:01');
INSERT INTO `oms_order_item` VALUES (1692357665164378113, 1, 1692357665151795200, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:08:05', '2023-08-18 10:13:01');
INSERT INTO `oms_order_item` VALUES (1692358145433157632, 1, 1692358145420574720, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:09:59', '2023-08-18 10:14:56');
INSERT INTO `oms_order_item` VALUES (1692358145433157633, 1, 1692358145420574720, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:09:59', '2023-08-18 10:14:56');
INSERT INTO `oms_order_item` VALUES (1692358190635171840, 1, 1692358190622588928, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:10:10', '2023-08-18 10:10:12');
INSERT INTO `oms_order_item` VALUES (1692358190635171841, 1, 1692358190622588928, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:10:10', '2023-08-18 10:10:12');
INSERT INTO `oms_order_item` VALUES (1692359592979742720, 1, 1692359592967159808, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 1, 260, 260, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:15:44', '2023-08-18 10:20:41');
INSERT INTO `oms_order_item` VALUES (1692359592979742721, 1, 1692359592967159808, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 10:15:44', '2023-08-18 10:20:41');
INSERT INTO `oms_order_item` VALUES (1692372828496084992, 1, 1692372828466724864, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 11:08:20', '2023-08-18 11:13:16');
INSERT INTO `oms_order_item` VALUES (1692372828496084993, 1, 1692372828466724864, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 5, 260, 1300, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 11:08:20', '2023-08-18 11:13:16');
INSERT INTO `oms_order_item` VALUES (1692373035623399424, 1, 1692373035610816512, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 11:09:09', '2023-08-18 11:14:06');
INSERT INTO `oms_order_item` VALUES (1692373035623399425, 1, 1692373035610816512, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 5, 260, 1300, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 11:09:09', '2023-08-18 11:14:06');
INSERT INTO `oms_order_item` VALUES (1692373856264142848, 1, 1692373856251559936, 1, 1661570067979304960, 1688482275322503168, '测试商品', 'http://127.0.0.1:9001/test/2023-08-07/fa65ccfa-56de-4daf-a9b6-f39437d1f946%40%40img.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', 2, 100, 200, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 11:12:25', '2023-08-18 11:17:21');
INSERT INTO `oms_order_item` VALUES (1692373856264142849, 1, 1692373856251559936, 1, 1534420706752856064, 1688482481032142849, '熊猫限定帆布袋', 'http://127.0.0.1:9001/test/2023-08-07/b2fd26a9-eaa2-4a56-9060-04c56e046b24%40%40%E7%BE%8E%E5%9B%BE84.png', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', 5, 260, 1300, 101, 2, 0, NULL, NULL, NULL, 0, '2023-08-18 11:12:25', '2023-08-18 11:17:21');

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '租户ID',
  `auto_receive_overtime` int(11) NULL DEFAULT NULL COMMENT '发货后？毫秒后自动确认收货',
  `un_pay_close_overtime` int(11) NULL DEFAULT NULL COMMENT '待付款订单？毫秒后自动关闭',
  `buyer_apply_after_sale_handle_overtime` int(11) NULL DEFAULT NULL COMMENT '买家发起售后申请？毫秒后，卖家未处理，自动关闭',
  `after_sale_buyer_deliver_overtime` int(11) NULL DEFAULT NULL COMMENT '待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭',
  `buyer_apply_after_sale_overtime` int(11) NULL DEFAULT NULL COMMENT '买家确认收货？毫秒后无法发起售后申请',
  `stock_check_type` tinyint(4) NULL DEFAULT NULL COMMENT '减库存设置（1：提交订单减库存 2：付款减库存）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-订单配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_shipping
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_shipping`;
CREATE TABLE `oms_order_shipping`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `receiver_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收货人电话',
  `receiver_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '收货人地址',
  `deliverer_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货人',
  `deliverer_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货人电话',
  `deliverer_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发货地址',
  `deliver_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `logistics_company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司',
  `logistics_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流公司编码',
  `logistics_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物流单号',
  `receipt_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `auto_receipt_time` datetime NULL DEFAULT NULL COMMENT '自动收货时间',
  `wx_notice_msg` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信通知消息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_no`(`order_no`) USING BTREE COMMENT '订单编号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单配送表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_shipping
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_shipping_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_shipping_item`;
CREATE TABLE `oms_order_shipping_item`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `order_no` bigint(20) NOT NULL COMMENT '订单编号',
  `order_item_id` bigint(20) NOT NULL COMMENT '订单详情id',
  `shipping_id` bigint(20) NOT NULL COMMENT '配送id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-订单配送详情表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_shipping_item
-- ----------------------------

-- ----------------------------
-- Table structure for pms_attr_key
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_key`;
CREATE TABLE `pms_attr_key`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `attr_key_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性key名称',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-属性key' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_attr_key
-- ----------------------------
INSERT INTO `pms_attr_key` VALUES (1532283711813451776, 1, '尺寸', 1, '2022-06-02 16:51:20', '2023-05-25 11:37:50', 0, 1, 0);
INSERT INTO `pms_attr_key` VALUES (1661577306127466496, 1, '颜色', 2, '2023-05-25 11:37:55', '2023-05-25 11:38:00', 1, 1, 0);

-- ----------------------------
-- Table structure for pms_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_value`;
CREATE TABLE `pms_attr_value`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `attr_key_id` bigint(20) NOT NULL COMMENT '属性key',
  `attr_value_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '属性value值',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-属性value' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_attr_value
-- ----------------------------
INSERT INTO `pms_attr_value` VALUES (1532284250869596160, 1, 1532283711813451776, 'M', 1, '2022-06-02 16:53:29', '2022-06-02 16:53:51', 0, 0, 0);
INSERT INTO `pms_attr_value` VALUES (1532284264874377216, 1, 1532283711813451776, 'X', 1, '2022-06-02 16:53:32', '2023-05-25 11:36:47', 0, 1, 0);
INSERT INTO `pms_attr_value` VALUES (1532284292691001344, 1, 1532283711813451776, 'XL', 1, '2022-06-02 16:53:39', '2023-05-25 11:36:45', 0, 1, 0);
INSERT INTO `pms_attr_value` VALUES (1532284305848532992, 1, 1532283711813451776, 'L', 1, '2022-06-02 16:53:42', '2023-05-25 11:36:44', 0, 1, 0);
INSERT INTO `pms_attr_value` VALUES (1661577389468286976, 1, 1661577306127466496, '蓝色', 1, '2023-05-25 11:38:15', '2023-05-25 11:38:31', 1, 1, 0);
INSERT INTO `pms_attr_value` VALUES (1661577430681518080, 1, 1661577306127466496, '绿色', 2, '2023-05-25 11:38:25', '2023-05-25 11:38:25', 1, 1, 0);

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `parent_id` bigint(20) NOT NULL COMMENT '父分类id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `sort` int(11) UNSIGNED NOT NULL COMMENT '排序',
  `is_show` tinyint(1) NOT NULL COMMENT '是否显示(0->否,1->是)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES (1532285889399619584, 1, 0, 'NICE', 1, 1, '2022-06-02 16:59:59', '2022-06-02 16:59:59', 0, 0, 0);
INSERT INTO `pms_category` VALUES (1532285975026335744, 1, 0, '六一专属', 1, 1, '2022-06-02 17:00:20', '2022-06-02 17:00:20', 0, 0, 0);
INSERT INTO `pms_category` VALUES (1533982865094737920, 1, 0, 'GOODS', 1, 1, '2022-06-07 09:23:10', '2022-06-07 09:23:10', 0, 0, 0);

-- ----------------------------
-- Table structure for pms_category_attr_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_attr_relation`;
CREATE TABLE `pms_category_attr_relation`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `attr_id` bigint(20) NOT NULL COMMENT '属性id',
  `sort` int(11) UNSIGNED NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-分类关联属性' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_attr_relation
-- ----------------------------

-- ----------------------------
-- Table structure for pms_category_spu_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_spu_relation`;
CREATE TABLE `pms_category_spu_relation`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `category_id` bigint(20) NOT NULL COMMENT '分类id',
  `spu_id` bigint(20) NOT NULL COMMENT '商品id',
  `sort` int(11) UNSIGNED NOT NULL COMMENT '排序',
  `is_show` tinyint(1) NOT NULL COMMENT '是否显示(0->否,1->是)',
  `is_put` tinyint(1) NOT NULL COMMENT '是否上架(0->否,1->是)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商城-分类关联商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_category_spu_relation
-- ----------------------------
INSERT INTO `pms_category_spu_relation` VALUES (1661567883463884800, 1, 1532285889399619584, 1534420706752856064, 1, 1, 1, '2023-05-25 11:00:29', '2023-05-25 11:00:29', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1661610951370473472, 1, 1533982865094737920, 1661570067979304960, 1, 1, 1, '2023-05-25 13:51:37', '2023-05-25 13:51:37', 1, 1, 0);

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `spu_id` bigint(20) NOT NULL COMMENT '商品ID',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格编码',
  `spec_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品规格-属性',
  `cost_price` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '成本价(单位:分)',
  `sell_price` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '销售单价(单位:分)',
  `presell_price` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '预售价格(单位:分)',
  `limit_count` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '每人限购',
  `total_stock` int(11) UNSIGNED NOT NULL COMMENT '总库存',
  `use_stock` int(11) UNSIGNED NOT NULL COMMENT '已用库存',
  `virtual_use_stock` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '虚拟-已用库存(虚拟销量)',
  `usable_stock` int(11) UNSIGNED NOT NULL COMMENT '可用库存',
  `cover_img` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `is_show` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否显示(0->否 1->是)',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品规格' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_sku
-- ----------------------------
INSERT INTO `pms_sku` VALUES (1692477838160318464, 1, 1534420706752856064, '', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284024536563712\",\"attrValueName\":\"蓝色\"}]', NULL, 10, 0, 0, 100, 0, 0, 100, 'http://127.0.0.1:9001/test/2023-05-24/d3338de5-c8da-46d0-9fa6-10aaaacdd9e7%40%40%E7%BE%8E%E5%9B%BE13.png', 1, 1, '2023-08-18 18:05:36', '2023-08-18 18:05:36', 0);
INSERT INTO `pms_sku` VALUES (1692477838160318465, 1, 1534420706752856064, '', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284264874377216\",\"attrValueName\":\"X\"},{\"attrKeyId\":\"1532281238671458304\",\"attrKeyName\":\"颜色\",\"attrValueId\":\"1532284050088263680\",\"attrValueName\":\"红色\"}]', NULL, 260, 0, 0, 1000, 0, 0, 1000, 'http://127.0.0.1:9001/test/2023-05-24/8e633476-272a-48c2-b61c-aca4a8155318%40%40%E7%BE%8E%E5%9B%BE21.jpg', 1, 1, '2023-08-18 18:05:36', '2023-08-18 18:05:36', 0);
INSERT INTO `pms_sku` VALUES (1692477880984162304, 1, 1661570067979304960, '', '[{\"attrKeyId\":\"1532283711813451776\",\"attrKeyName\":\"尺寸\",\"attrValueId\":\"1532284305848532992\",\"attrValueName\":\"L\"}]', NULL, 100, 0, 0, 10, 0, 0, 10, 'http://127.0.0.1:9001/test/2023-05-25/c9519b3d-bb4b-4d55-84a3-aa7c5d4ab794%40%40%E7%BE%8E%E5%9B%BE13.png', 1, 1, '2023-08-18 18:05:46', '2023-08-18 18:05:46', 0);

-- ----------------------------
-- Table structure for pms_spu
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu`;
CREATE TABLE `pms_spu`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `type` tinyint(2) UNSIGNED NULL DEFAULT NULL COMMENT '类型(101->实物 102->虚拟-优惠券)',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '优惠券ID',
  `coupon_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券名字',
  `coupon_num` int(11) NULL DEFAULT NULL COMMENT '优惠券数量',
  `cover_img` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `slide_img_list` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '轮播图',
  `detail_img_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品详情图',
  `line_price` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '商品划线价格(单位:分)',
  `freight` int(11) UNSIGNED NOT NULL COMMENT '运费(单位:分 0:包邮)',
  `attr_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品属性-配置规格时使用',
  `is_presell` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否预售(0->否；1->是)',
  `presell_start_time` datetime NULL DEFAULT NULL COMMENT '预售开始时间',
  `presell_end_time` datetime NULL DEFAULT NULL COMMENT '预售结束时间',
  `presell_deliver_day` int(11) NULL DEFAULT NULL COMMENT '预售-发货日期(购买之后？天之后发货)',
  `is_put` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否上架(0->下架；1->上架)',
  `is_show` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否显示(0->隐藏；1->显示)',
  `service_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品关联服务',
  `explain_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品关联说明',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品详情-文案',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu
-- ----------------------------
INSERT INTO `pms_spu` VALUES (1534420706752856064, 1, '熊猫限定帆布袋', 1, 101, 1, '买一送一', 100, 'http://127.0.0.1:886/2023-08-18/1692477731486502912-美图28.jpg', '[{\"name\":\"美图14.jpg\",\"url\":\"http://127.0.0.1:886/2023-08-18/1692477771990896640-美图14.jpg\"}]', '[{\"name\":\"美图35.jpg\",\"url\":\"http://127.0.0.1:886/2023-08-18/1692477795856486400-美图35.jpg\"}]', NULL, 0, NULL, 0, '2021-08-25 09:00:00', '2021-08-26 23:59:59', 15, 1, 1, '[{\"code\":\"btn\",\"name\":\"添加\",\"value\":\"add\",\"sort\":1,\"remark\":\"this is the add.\"}]', '[{\"code\":\"btn\",\"name\":\"添加\",\"value\":\"add\",\"sort\":1,\"remark\":\"this is the add.\"}]', NULL, '2022-06-08 14:22:59', '2023-08-18 18:05:36', 0, 1, 0);
INSERT INTO `pms_spu` VALUES (1661570067979304960, 1, '测试商品', 2, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-08-18/1692477666567065600-美图13.png', '[{\"name\":\"img.png\",\"url\":\"http://127.0.0.1:9001/test/2023-08-07/b770655b-08fd-45d2-8981-7a8f16beca0c%40%40img.png\"}]', '[{\"name\":\"美图36.jpg\",\"url\":\"http://127.0.0.1:886/2023-08-18/1692477867436478464-美图36.jpg\"}]', NULL, 0, NULL, 0, NULL, NULL, NULL, 1, 1, '[]', '[]', NULL, '2023-05-25 11:09:09', '2023-08-18 18:05:46', 1, 1, 0);

-- ----------------------------
-- Table structure for pms_spu_rate
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_rate`;
CREATE TABLE `pms_spu_rate`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父ID',
  `order_item_id` bigint(20) NOT NULL COMMENT '订单详情id',
  `spu_id` bigint(20) NOT NULL COMMENT '商品id',
  `sku_id` bigint(20) NOT NULL COMMENT '商品sku-id',
  `spec_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品规格属性',
  `operator_type` tinyint(2) NOT NULL COMMENT '操作人类型(1->买家 2->商家)',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人id',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人名称',
  `operator_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人头像',
  `resource_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评价图片或视频',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评价内容',
  `is_show` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否显示(0->隐藏；1->显示)',
  `desc_level` tinyint(4) NULL DEFAULT NULL COMMENT '描述相符',
  `logistics_level` tinyint(4) NULL DEFAULT NULL COMMENT '物流服务',
  `service_level` tinyint(4) NULL DEFAULT NULL COMMENT '服务态度',
  `is_img` tinyint(1) NOT NULL COMMENT '是否含有图片(0->否,1->是)',
  `is_video` tinyint(1) NOT NULL COMMENT '是否含有视频(0->否,1->是)',
  `rate_type` tinyint(4) NOT NULL COMMENT '评价分类(1->好评,2->差评,3->一般)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品评价' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_rate
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu_rate_reply_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_rate_reply_relation`;
CREATE TABLE `pms_spu_rate_reply_relation`  (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品id',
  `product_rate_id` bigint(20) NOT NULL COMMENT '商品评价信息id',
  `replier_id` bigint(20) NOT NULL COMMENT '回复人id',
  `replier_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复人名称',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容',
  `is_show` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否显示(0->隐藏；1->显示)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-商品评价关联回复' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_spu_rate_reply_relation
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dict_type_id` int(11) UNSIGNED NOT NULL COMMENT '字典类型id',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典值',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态(0->停用 1->正常)',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` int(11) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) UNSIGNED NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础模块-数据字典' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES (165, 3, 'element_icon', 'AddLocation', 'AddLocation', 1, 1, '', 1, '2020-08-30 03:05:05', 1, '2022-07-22 09:57:53', 0);
INSERT INTO `t_sys_dict` VALUES (166, 3, 'element_icon', 'Aim', 'Aim', 1, 2, '', 1, '2020-08-30 03:05:24', 1, '2022-07-22 09:58:01', 0);
INSERT INTO `t_sys_dict` VALUES (167, 3, 'element_icon', 'AlarmClock', 'AlarmClock', 1, 3, '', 1, '2020-08-30 03:05:31', 1, '2022-07-22 09:58:07', 0);
INSERT INTO `t_sys_dict` VALUES (168, 3, 'element_icon', 'Apple', 'Apple', 1, 4, '', 1, '2020-08-30 03:12:30', 1, '2022-07-22 09:58:15', 0);
INSERT INTO `t_sys_dict` VALUES (169, 3, 'element_icon', 'ArrowDown', 'ArrowDown', 1, 5, '', 1, '2020-08-30 03:14:05', 1, '2022-07-22 09:58:38', 0);
INSERT INTO `t_sys_dict` VALUES (170, 3, 'element_icon', 'ArrowDownBold', 'ArrowDownBold', 1, 6, '', 1, '2020-08-30 03:14:28', 1, '2022-07-22 09:58:45', 0);
INSERT INTO `t_sys_dict` VALUES (171, 3, 'element_icon', 'ArrowLeft', 'ArrowLeft', 1, 7, '', 1, '2020-08-30 03:14:56', 1, '2022-07-22 09:58:51', 0);
INSERT INTO `t_sys_dict` VALUES (172, 3, 'element_icon', 'ArrowRight', 'ArrowRight', 1, 8, '', 1, '2020-08-30 03:15:49', 1, '2022-07-22 09:59:05', 0);
INSERT INTO `t_sys_dict` VALUES (173, 3, 'element_icon', 'ArrowRightBold', 'ArrowRightBold', 1, 9, '', 1, '2020-08-30 03:16:51', 1, '2022-07-22 09:59:13', 0);
INSERT INTO `t_sys_dict` VALUES (174, 3, 'element_icon', 'ArrowUp', 'ArrowUp', 1, 10, '', 1, '2020-08-30 03:18:32', 1, '2022-07-22 09:59:19', 0);
INSERT INTO `t_sys_dict` VALUES (181, 6, 'oauth_type', 'gitee', '1', 1, 1, '', 1, '2020-12-06 13:16:39', 0, '2023-02-13 11:17:41', 0);
INSERT INTO `t_sys_dict` VALUES (182, 6, 'oauth_type', 'github', '2', 1, 2, NULL, 1, '2020-12-06 13:16:54', 0, '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict` VALUES (183, 6, 'oauth_type', 'qq', '3', 1, 3, NULL, 1, '2020-12-06 13:17:03', 0, '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict` VALUES (184, 3, 'element_icon', 'Monitor', 'Monitor', 1, 11, NULL, 0, '2023-02-01 16:42:06', 0, '2023-02-01 16:42:06', 0);
INSERT INTO `t_sys_dict` VALUES (185, 3, 'element_icon', 'Menu', 'Menu', 1, 12, NULL, 0, '2023-02-01 16:42:33', 0, '2023-02-01 16:42:33', 0);

-- ----------------------------
-- Table structure for t_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_type`;
CREATE TABLE `t_sys_dict_type`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型名称(展示用)',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态(0->停用 1->正常)',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `is_fixed` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否固定(0->否 1->是)',
  `create_by` int(11) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) UNSIGNED NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础模块-数据字典类型' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_dict_type
-- ----------------------------
INSERT INTO `t_sys_dict_type` VALUES (3, 'element_icon', 'Element-Icon图标', 1, 2, 0, 1, '2020-08-30 02:52:36', 1, '2020-08-30 02:52:38', 0);
INSERT INTO `t_sys_dict_type` VALUES (6, 'oauth_type', '第三方帐号授权类型', 1, 3, 0, 1, '2020-12-06 13:11:27', 1, '2020-12-06 13:21:45', 0);

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu`  (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称 - 英文',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单访问路径',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父类菜单ID',
  `sort` int(11) NULL DEFAULT NULL COMMENT '菜单排序',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
  `hidden` tinyint(1) NULL DEFAULT 1 COMMENT '是否隐藏(1:隐藏 0:显示)',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向路径',
  `breadcrumb` tinyint(1) NULL DEFAULT 1 COMMENT '面包屑是否显示(1:显示 0:隐藏)',
  `is_show_children` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否显示子菜单(1:显示 0:隐藏)',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES (1, '首页', 'dashboard', 'House', '/', 0, 1, 'Layout', 0, '/dashboard', 1, 0, 1, '2020-08-22 15:01:51', 1, '2023-02-14 17:07:35', 0);
INSERT INTO `t_sys_menu` VALUES (2, '首页', 'dashboard', '', 'dashboard', 1, 1, 'dashboard/index', 0, '', 0, 1, 1, '2020-08-22 15:01:51', 1, '2023-02-14 15:37:51', 0);
INSERT INTO `t_sys_menu` VALUES (3, '系统管理', 'system', 'Setting', '/system', 0, 2, 'Layout', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu` VALUES (4, '用户管理', 'sys-user', '', 'user', 3, 1, 'system/user/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (5, '个人中心', 'personal-center', NULL, 'personal-center', 3, 2, 'system/personal-center/index', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (6, '角色管理', 'role', NULL, 'role', 3, 3, 'system/role/list', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (7, '角色权限', 'roleForm', NULL, 'roleForm', 3, 4, 'system/role/form', 1, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (9, '菜单管理', 'menu', '', 'menu', 3, 5, 'system/menu/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-02-14 17:13:13', 0);
INSERT INTO `t_sys_menu` VALUES (10, '数据字典', 'dict', NULL, 'dict', 3, 6, 'system/dict/index', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (11, '系统属性', 'property', '', 'property', 3, 7, 'system/property/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-02-14 15:37:51', 0);
INSERT INTO `t_sys_menu` VALUES (12, '微信公众号管理', 'wxmp', 'Setting', '/wx/mp', 0, 3, 'Layout', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu` VALUES (13, '公众号列表', 'account', '', 'account', 12, 1, 'wxmp/account/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (14, '自定义菜单', 'custom-menu', '', 'custom-menu', 12, 2, 'wxmp/custom-menu/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (15, '模板消息', 'template-msg', '', 'template-msg', 12, 3, 'wxmp/template-msg/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (16, '用户', 'wxmp-user', '', 'user', 12, 4, 'wxmp/user/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (17, '素材管理', 'material', '', 'material', 12, 5, 'wxmp/material/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (18, '消息自动回复', 'msg-auto-reply', '', 'msg-auto-reply', 12, 6, 'wxmp/msg-auto-reply/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (19, '商城管理', 'mall', 'Setting', '/mall', 0, 4, 'Layout', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu` VALUES (20, '分类', 'category', NULL, 'category', 19, 1, 'mall/category/index', 0, NULL, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu` VALUES (21, '商品', 'product', '', 'product', 19, 3, 'mall/product/index', 0, '', 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-05-25 11:14:24', 0);
INSERT INTO `t_sys_menu` VALUES (22, '规格', 'attr', '', 'attr', 19, 2, 'mall/attr/index', 0, '', 1, 1, 1, '2023-05-25 11:14:14', 1, '2023-05-25 11:14:14', 0);
INSERT INTO `t_sys_menu` VALUES (23, '订单', 'order', '', 'order', 19, 4, 'mall/order/index', 0, '', 1, 1, 1, '2023-05-25 11:14:14', 1, '2023-05-25 11:14:14', 0);
INSERT INTO `t_sys_menu` VALUES (24, '用户', 'ums-user', '', 'user', 19, 5, 'mall/user/index', 0, '', 1, 1, 1, '2023-05-25 11:14:14', 1, '2023-05-25 11:14:14', 0);

-- ----------------------------
-- Table structure for t_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `btn_id` int(11) NULL DEFAULT NULL COMMENT '按钮ID',
  `btn_perm` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '按钮权限标识',
  `url_perm` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'URL权限标识',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单关联权限表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_permission
-- ----------------------------
INSERT INTO `t_sys_permission` VALUES (1, '查看用户分页列表', 4, 78, 'sys:user:list:page', 'GET:/web/api/system/user/listPage', 1, '2020-08-22 15:01:51', 1, '2023-02-13 16:54:33');
INSERT INTO `t_sys_permission` VALUES (2, '编辑用户', 4, 75, 'sys:user:edit', 'PUT:/web/api/system/user', 1, '2020-08-22 15:01:51', 1, '2023-02-13 16:51:45');
INSERT INTO `t_sys_permission` VALUES (3, '新增用户', 4, 76, 'sys:user:add', 'POST:/web/api/system/user', 1, '2020-08-22 15:01:51', 1, '2023-02-13 16:13:06');
INSERT INTO `t_sys_permission` VALUES (4, '删除用户', 4, 77, 'sys:user:delete', 'DELETE:/web/api/system/user', 1, '2020-08-22 15:01:51', 1, '2023-02-13 16:13:16');

-- ----------------------------
-- Table structure for t_sys_property
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_property`;
CREATE TABLE `t_sys_property`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性key',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性value',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `create_by` int(10) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(10) UNSIGNED NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-系统属性' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_property
-- ----------------------------
INSERT INTO `t_sys_property` VALUES (10, 'hello', 'world', 'hello world !', 0, '2021-09-07 10:45:45', 0, '2021-09-07 10:45:45', 0);
INSERT INTO `t_sys_property` VALUES (12, 'test', '测试', 'this is test data.', 1, '2023-02-15 11:19:46', 1, '2023-02-15 14:18:15', 0);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(1:开启(默认) 0:禁用)',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色管理表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES (9, '超级管理员', 'super_admin', 1, 1, '2020-08-22 15:01:51', 1, '2023-02-14 13:58:22');
INSERT INTO `t_sys_role` VALUES (10, '测试用户', 'test', 1, 1, '2022-08-05 20:00:36', 1, '2022-08-06 15:59:10');

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5551 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色菜单关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_menu
-- ----------------------------
INSERT INTO `t_sys_role_menu` VALUES (1324, 10, 1, 1, '2023-03-31 09:48:32', 1, '2023-03-31 09:48:32');
INSERT INTO `t_sys_role_menu` VALUES (1325, 10, 2, 1, '2023-03-31 09:48:32', 1, '2023-03-31 09:48:32');
INSERT INTO `t_sys_role_menu` VALUES (5528, 9, 1, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5529, 9, 2, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5530, 9, 3, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5531, 9, 4, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5532, 9, 5, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5533, 9, 6, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5534, 9, 7, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5535, 9, 9, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5536, 9, 10, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5537, 9, 11, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5538, 9, 12, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5539, 9, 13, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5540, 9, 14, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5541, 9, 15, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5542, 9, 16, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5543, 9, 17, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5544, 9, 18, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5545, 9, 19, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5546, 9, 20, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5547, 9, 22, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5548, 9, 21, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5549, 9, 23, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_menu` VALUES (5550, 9, 24, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');

-- ----------------------------
-- Table structure for t_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1949 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色关联权限表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission` VALUES (1945, 9, 1, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_permission` VALUES (1946, 9, 2, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_permission` VALUES (1947, 9, 3, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');
INSERT INTO `t_sys_role_permission` VALUES (1948, 9, 4, 0, '2023-08-18 18:03:47', 0, '2023-08-18 18:03:47');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `sex` tinyint(2) NULL DEFAULT 0 COMMENT '性别(0:未知 1:男  2:女)',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理 - 用户基础信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES (1, 'admin', '3014dcb9ee3639535d5d9301b32c840c', '郑清', 1, '15188888888', 'zhengqingya@it.com', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', 1, '2020-08-22 15:01:51', 0, '2023-02-10 14:30:54', 0);
INSERT INTO `t_sys_user` VALUES (2, 'test', '3014dcb9ee3639535d5d9301b32c840c', '测试号', 1, '', '', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80', 1, '2020-08-22 15:01:51', 1, '2023-02-13 11:56:39', 0);

-- ----------------------------
-- Table structure for t_sys_user_re_oauth
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_re_oauth`;
CREATE TABLE `t_sys_user_re_oauth`  (
  `user_re_oauth_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户id（关联表`t_sys_user`字段`user_id`）',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '三方id',
  `oauth_type` tinyint(4) NOT NULL COMMENT '第三方授权类型',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_re_oauth_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理 - 用户三方授权表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user_re_oauth
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户角色关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES (17, 1, 9, 1, '2023-02-14 13:59:07', 1, '2023-02-14 13:59:07');
INSERT INTO `t_sys_user_role` VALUES (18, 2, 10, 1, '2023-02-14 13:59:20', 1, '2023-02-14 13:59:20');

-- ----------------------------
-- Table structure for t_wx_mp_account
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_account`;
CREATE TABLE `t_wx_mp_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '账号类型（1：测试号；2：服务号；3：订阅号）',
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AppID',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密钥',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器地址',
  `token` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '令牌',
  `aes_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息加密密钥',
  `qr_code_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '二维码地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-账号' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_account
-- ----------------------------
INSERT INTO `t_wx_mp_account` VALUES (1, '郑清的测试账号', 1, 'gh_42e1dbad014e', 'wxe01d9bde2cc81b89', 'f292d6cb69755a7105863d97910a9579', '/wx/mp/portal/wxe01d9bde2cc81b89', 'test', '4J9GOBZ4VTElUFm0EvRDV6aJu5spvYXGkn1RJela56U', 'http://mmbiz.qpic.cn/mmbiz_jpg/8ytq3xBRtdjkBkb2JfibmdJCkT4t2ZSxo8PBwzZ6cgic64mMVicxibpqAyQY0kuiaHhMc3Yjh5J2ATy8kkQgfzbIFJA/0', '2023-03-16 15:39:45', '2023-06-05 10:44:01', 1, 1);

-- ----------------------------
-- Table structure for t_wx_mp_msg_auto_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_msg_auto_reply`;
CREATE TABLE `t_wx_mp_msg_auto_reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AppID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `type` tinyint(1) NOT NULL COMMENT '类型（1：关注时回复；2：关键词回复）',
  `match_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关键词',
  `is_exact_match` tinyint(1) NOT NULL COMMENT '是否精确匹配（0：否；1：是）',
  `reply_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）',
  `reply_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复消息内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-消息自动回复' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_msg_auto_reply
-- ----------------------------
INSERT INTO `t_wx_mp_msg_auto_reply` VALUES (1, 'wxe01d9bde2cc81b89', '关注', 1, '', 1, 'text', '谢谢关注！', '2023-03-20 17:54:30', '2023-03-20 19:34:08', 1, 1);
INSERT INTO `t_wx_mp_msg_auto_reply` VALUES (2, 'wxe01d9bde2cc81b89', '文本消息', 2, 'hello', 1, 'text', '自动回复：你好', '2023-03-20 19:30:47', '2023-03-20 19:30:47', 1, 1);
INSERT INTO `t_wx_mp_msg_auto_reply` VALUES (3, 'wxe01d9bde2cc81b89', '图片消息', 2, '图片', 1, 'image', 'hm_a1Quvy6P39bspNEXRaIGBhVqBzDfHKxRCKhAZEtK7pyYDG2Hjc4B2L7Yg-p57', '2023-03-21 15:12:44', '2023-03-27 15:55:24', 1, 1);

-- ----------------------------
-- Table structure for t_wx_mp_template_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_template_msg`;
CREATE TABLE `t_wx_mp_template_msg`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AppID',
  `template_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板标题',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板内容',
  `data_list` json NOT NULL COMMENT '模板数据',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-模板消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_template_msg
-- ----------------------------
INSERT INTO `t_wx_mp_template_msg` VALUES (15, 'wxe01d9bde2cc81b89', 'alASmcPvowZ2ZgaK2BWMWHMvsnXyz_tP_vQH5EEY8Kc', '早上好，单身狗☀', '{{txt1.DATA}}\n\n单身狗所在地区：{{city.DATA}}\n日期：{{date.DATA}} {{week.DATA}}\n\n{{txt2.DATA}}\n\n天气：{{wea.DATA}}\n最高气温：{{tem1.DATA}}\n最低气温：{{tem2.DATA}}\n风向：{{win.DATA}}\n风力：{{win_speed.DATA}}\n风速：{{win_meter.DATA}}\n湿度：{{humidity.DATA}}\n能见度：{{visibility.DATA}}\n气压：{{pressure.DATA}}\n空气质量：{{air.DATA}}\npm2.5含量：{{air_pm25.DATA}}\n空气等级：{{air_level.DATA}}\n温馨小提示：{{air_tips.DATA}}\n\n{{end1.DATA}}\n{{end2.DATA}}\n\n{{author.DATA}}', '[{\"name\": \"txt1\", \"color\": \"#20B2AA\", \"value\": \"今天心情不好，我问小狗怎么办，小狗说：汪汪汪☀☀☀☀☀☀☀☀☀☀☀☀☀\"}, {\"name\": \"city\", \"color\": \"#F093FB\", \"value\": \"四川成都\"}, {\"name\": \"date\", \"color\": \"#F093FB\", \"value\": \"2023-03-22\"}, {\"name\": \"week\", \"color\": \"#F093FB\", \"value\": \"星期三\"}, {\"name\": \"txt2\", \"color\": \"#1E90FF\", \"value\": \"单身狗出门也要看下今天的天气状况哦╰(*°▽°*)╯\"}, {\"name\": \"wea\", \"color\": \"#000\", \"value\": \"晴\"}, {\"name\": \"tem1\", \"color\": \"#000\", \"value\": \"25°\"}, {\"name\": \"tem2\", \"color\": \"#000\", \"value\": \"20°\"}, {\"name\": \"win\", \"color\": \"#000\", \"value\": \"南风\"}, {\"name\": \"win_speed\", \"color\": \"#000\", \"value\": \"2级\"}, {\"name\": \"win_meter\", \"color\": \"#000\", \"value\": \"6km/h\"}, {\"name\": \"humidity\", \"color\": \"#000\", \"value\": \"44%\"}, {\"name\": \"visibility\", \"color\": \"#000\", \"value\": \"10km\"}, {\"name\": \"pressure\", \"color\": \"#000\", \"value\": \"1000\"}, {\"name\": \"air\", \"color\": \"#000\", \"value\": \"60\"}, {\"name\": \"air_pm25\", \"color\": \"#000\", \"value\": \"31\"}, {\"name\": \"air_level\", \"color\": \"#000\", \"value\": \"良\"}, {\"name\": \"air_tips\", \"color\": \"#000\", \"value\": \"空气好，可以外出活动!\"}, {\"name\": \"end1\", \"color\": \"#FF4500\", \"value\": \"How are you still single? \"}, {\"name\": \"end2\", \"color\": \"#FF4500\", \"value\": \"你怎么还单着？\"}, {\"name\": \"author\", \"color\": \"#DDA0DD\", \"value\": \"zhengqingya\"}]', '2023-03-21 18:52:15', '2023-03-22 10:39:39', 1, 1, 0);
INSERT INTO `t_wx_mp_template_msg` VALUES (16, 'wxe01d9bde2cc81b89', 'xEUCJF0EhbiHCIttO17lyXXmCxJHX6TeI9Sq7m6WV_I', 'test', '{{txt1.DATA}}\n\n所在地区：{{city.DATA}}', '[{\"name\": \"txt1\", \"color\": \"#E60E0E\", \"value\": \"测试\"}, {\"name\": \"city\", \"color\": \"#15F4D6\", \"value\": \"四川成都\"}]', '2023-03-21 18:52:15', '2023-03-22 10:39:31', 1, 1, 0);

-- ----------------------------
-- Table structure for t_wx_mp_user
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_user`;
CREATE TABLE `t_wx_mp_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AppID',
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信openid',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `head_img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `subscribe_scene` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户关注的渠道来源：ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他',
  `subscribe_time` datetime NOT NULL COMMENT '关注时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_user
-- ----------------------------
INSERT INTO `t_wx_mp_user` VALUES (7, 'wxe01d9bde2cc81b89', 'ojplN5tMax4tNacU3tKeWCnL7qEU', '', '', 'ADD_SCENE_QR_CODE', '2023-03-15 14:44:06', '2023-03-20 16:35:26', '2023-03-20 16:35:26', 1, 1);

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) NOT NULL COMMENT '租户ID',
  `openid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(0:未知 1:男  2:女)',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1645325190512640002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES (1, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', '郑清', '15183388888', 1, '2003-12-16', 'http://127.0.0.1:886/2023-08-18/1692476648525533184-rL2j7qr7QFFtcf03310edaa01a8d782ed57866374edc.jpeg', '2023-04-10 15:13:33', '2023-08-18 18:01:29', 0, 0, 0);
INSERT INTO `ums_user` VALUES (1645325190512640001, 1, '666', '郑清', '15183388888', 1, '2022-06-10', 'http://127.0.0.1:886/2023-08-18/1692453663244705792-img.png', '2022-06-10 16:10:24', '2023-08-18 16:38:00', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
