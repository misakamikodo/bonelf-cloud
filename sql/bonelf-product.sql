/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : bonelf-product

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 02/12/2020 13:05:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bnf_sku
-- ----------------------------
DROP TABLE IF EXISTS `bnf_sku`;
CREATE TABLE `bnf_sku`  (
  `sku_id` bigint(20) NOT NULL COMMENT '商品规格id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT '商品编号',
  `sku_value_ids` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逗号分割',
  `specs` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格信息 颜色:红;容量:21',
  `sku_image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格图片',
  `stock` int(11) NULL DEFAULT NULL COMMENT '库存（实际在redis bonelf:sku:stock:{sku_id}）',
  `origin_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '原始价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '销售价格',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认（0：否，1：是）',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：否，1：是）',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '多规格 商品规格信息 （保留）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bnf_sku
-- ----------------------------
INSERT INTO `bnf_sku` VALUES (1, 1, '1,4', '颜色:直男黑;尺码:L', NULL, 100, 0.00, 10.00, 0, 0, '2020-11-13 14:14:57', '2020-11-14 11:01:46');
INSERT INTO `bnf_sku` VALUES (2, 1, '1,5', '颜色:直男黑;尺码:XL', NULL, 100, 0.00, 100.00, 0, 0, '2020-11-13 14:15:55', '2020-11-14 11:01:46');

-- ----------------------------
-- Table structure for bnf_sku_key
-- ----------------------------
DROP TABLE IF EXISTS `bnf_sku_key`;
CREATE TABLE `bnf_sku_key`  (
  `sku_key_id` bigint(20) NOT NULL COMMENT '商品规格id',
  `spu_id` bigint(20) NOT NULL COMMENT '商品id',
  `sku_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格名称',
  `sort` int(5) NULL DEFAULT 0 COMMENT '级别 升序 1开始',
  `specification` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格信息',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`sku_key_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品规格键' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bnf_sku_key
-- ----------------------------
INSERT INTO `bnf_sku_key` VALUES (1, 1, '颜色', 1, NULL, '2020-11-12 16:39:14', '2020-11-13 11:01:11');
INSERT INTO `bnf_sku_key` VALUES (2, 1, '尺码', 2, NULL, '2020-11-13 11:02:30', '2020-11-13 11:02:30');

-- ----------------------------
-- Table structure for bnf_sku_value
-- ----------------------------
DROP TABLE IF EXISTS `bnf_sku_value`;
CREATE TABLE `bnf_sku_value`  (
  `sku_value_id` bigint(20) NOT NULL COMMENT '商品规格id',
  `sku_key_id` bigint(20) NOT NULL COMMENT '规格键值id',
  `sku_value_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格名称',
  `sku_image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格图片 保留',
  `sort` int(5) NULL DEFAULT 0 COMMENT '级别 升序 1开始',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`sku_value_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品规格值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bnf_sku_value
-- ----------------------------
INSERT INTO `bnf_sku_value` VALUES (1, 1, '直男黑', NULL, 1, '2020-11-13 11:01:28', '2020-11-13 11:04:46');
INSERT INTO `bnf_sku_value` VALUES (2, 1, '屎黄色', NULL, 1, '2020-11-13 11:01:40', '2020-11-13 11:04:46');
INSERT INTO `bnf_sku_value` VALUES (3, 1, '少女粉', NULL, 1, '2020-11-13 11:02:11', '2020-11-13 11:04:46');
INSERT INTO `bnf_sku_value` VALUES (4, 2, 'L', NULL, 1, '2020-11-13 11:02:47', '2020-11-13 11:04:46');
INSERT INTO `bnf_sku_value` VALUES (5, 2, 'XL', NULL, 1, '2020-11-13 11:02:58', '2020-11-13 11:04:46');
INSERT INTO `bnf_sku_value` VALUES (6, 2, 'XXL', NULL, 1, '2020-11-13 11:03:08', '2020-11-13 11:04:46');
INSERT INTO `bnf_sku_value` VALUES (7, 2, 'S', NULL, 1, '2020-11-13 11:03:14', '2020-11-13 11:04:46');

-- ----------------------------
-- Table structure for bnf_spu
-- ----------------------------
DROP TABLE IF EXISTS `bnf_spu`;
CREATE TABLE `bnf_spu`  (
  `spu_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `spu_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品编号',
  `spu_code` varchar(46) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货号',
  `category_id` bigint(20) NULL DEFAULT 0 COMMENT '分类Id 分类表未定义',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称 冗余字段 减少关联',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品标题（名称）',
  `subtitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '副标题',
  `main_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品主图',
  `main_video` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品视频',
  `pics` json NULL COMMENT '从图[\"string\",\"string\"]',
  `origin_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品价格 多规格下保存为所有规格最低价',
  `market_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '市场价（划线价） 多规格下保存为所有规格最高市场价',
  `base_sell_count` int(10) NULL DEFAULT 0 COMMENT '基础销售额',
  `sales` int(11) NULL DEFAULT 0 COMMENT '总销量（实际）',
  `stock` int(11) NULL DEFAULT 0 COMMENT '库存 多规格下为所有规格总库存',
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键字 也可使用标签代替或者共存',
  `brand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情内容 富文本',
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '商品状态（0：未上架，1：已上架）',
  `is_enable_sku` tinyint(2) NULL DEFAULT NULL COMMENT '是否支持Sku',
  `add_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '上架时间',
  `sort` int(5) NULL DEFAULT 99 COMMENT '排序值',
  `property` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品参数',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识（0：未删除，1：已删除）',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`spu_id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE,
  INDEX `idx_keyword`(`keywords`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bnf_spu
-- ----------------------------
INSERT INTO `bnf_spu` VALUES (1, '123444', '0715', 13, NULL, '商品1-下架', '商品副标题1', 'https://play-img.oss-cn-hangzhou.aliyuncs.com/temp/fa334feee74142179339e72bb6748da5-banner-5.png', 'https://play-img.oss-cn-hangzhou.aliyuncs.com/temp/9830d2f8725848bebe331110293a35ed-高乐.mp4', NULL, 1.01, 0.00, 0, 0, 20, 'sku', '1', '<p><img class=\"wscnph\" src=\"https://play-img.oss-cn-hangzhou.aliyuncs.com/temp/819282f8a42c46c2a1f65f8cdab38832-banner-2.png\" />龙翔水饺</p>', 1, NULL, '2020-09-24 16:12:44', 99, '{\"label\":\"颜色\",\"value\":\"黑色\"}', 0, '2020-09-10 09:53:54', '2020-09-24 16:12:44');

SET FOREIGN_KEY_CHECKS = 1;
