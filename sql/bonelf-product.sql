/*
 Navicat Premium Data Transfer

 Source Server         : 60bonelf
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : api.bonelf.com:3306
 Source Schema         : bonelf-product

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 23/08/2021 00:30:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bnf_sku
-- ----------------------------
DROP TABLE IF EXISTS `bnf_sku`;
CREATE TABLE `bnf_sku`  (
  `sku_id` bigint NOT NULL COMMENT '商品规格id',
  `spu_id` bigint NULL DEFAULT NULL COMMENT '商品编号',
  `sku_value_ids` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '逗号分割',
  `specs` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格信息 颜色:红;容量:21',
  `sku_image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格图片',
  `stock` int NULL DEFAULT NULL COMMENT '库存（实际在redis bonelf:sku:stock:{sku_id}）',
  `origin_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '原始价格',
  `sell_price` decimal(10, 2) NOT NULL COMMENT '销售价格',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认（0：否，1：是）',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除（0：否，1：是）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '多规格 商品规格信息 （保留）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bnf_sku_key
-- ----------------------------
DROP TABLE IF EXISTS `bnf_sku_key`;
CREATE TABLE `bnf_sku_key`  (
  `sku_key_id` bigint NOT NULL COMMENT '商品规格id',
  `spu_id` bigint NOT NULL COMMENT '商品id',
  `sku_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格名称',
  `sort` int NULL DEFAULT 0 COMMENT '级别 升序 1开始',
  `specification` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格信息',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sku_key_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品规格键' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bnf_sku_value
-- ----------------------------
DROP TABLE IF EXISTS `bnf_sku_value`;
CREATE TABLE `bnf_sku_value`  (
  `sku_value_id` bigint NOT NULL COMMENT '商品规格id',
  `sku_key_id` bigint NOT NULL COMMENT '规格键值id',
  `sku_value_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格名称',
  `sku_image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格图片 保留',
  `sort` int NULL DEFAULT 0 COMMENT '级别 升序 1开始',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`sku_value_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品规格值' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for bnf_spu
-- ----------------------------
DROP TABLE IF EXISTS `bnf_spu`;
CREATE TABLE `bnf_spu`  (
  `spu_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `spu_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品编号',
  `spu_code` varchar(46) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '货号',
  `category_id` bigint NULL DEFAULT 0 COMMENT '分类Id 分类表未定义',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称 冗余字段 减少关联',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品标题（名称）',
  `subtitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '副标题',
  `main_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品主图',
  `main_video` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品视频',
  `pics` json NULL COMMENT '从图[\"string\",\"string\"]',
  `origin_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '商品价格 多规格下保存为所有规格最低价',
  `market_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '市场价（划线价） 多规格下保存为所有规格最高市场价',
  `base_sell_count` int NULL DEFAULT 0 COMMENT '基础销售额',
  `sales` int NULL DEFAULT 0 COMMENT '总销量（实际）',
  `stock` int NULL DEFAULT 0 COMMENT '库存 多规格下为所有规格总库存',
  `keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关键字 也可使用标签代替或者共存',
  `brand` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '品牌',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品详情内容 富文本',
  `status` tinyint NULL DEFAULT 0 COMMENT '商品状态（0：未上架，1：已上架）',
  `is_enable_sku` tinyint NULL DEFAULT NULL COMMENT '是否支持Sku',
  `add_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '上架时间',
  `sort` int NULL DEFAULT 99 COMMENT '排序值',
  `property` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '产品参数',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识（0：未删除，1：已删除）',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`spu_id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE,
  INDEX `idx_keyword`(`keywords`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `branch_id` bigint NOT NULL COMMENT 'branch transaction id',
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'global transaction id',
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'undo_log context,such as serialization',
  `rollback_info` longblob NOT NULL COMMENT 'rollback info',
  `log_status` int NOT NULL COMMENT '0:normal status,1:defense status',
  `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
  `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'AT transaction mode undo table' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
