/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : bonelf-user

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 02/12/2020 13:05:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bnf_user
-- ----------------------------
DROP TABLE IF EXISTS `bnf_user`;
CREATE TABLE `bnf_user`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `username` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义用户名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `union_id` int(11) NULL DEFAULT NULL,
  `open_id` int(11) NULL DEFAULT NULL,
  `user_password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码MD5',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(2) NULL DEFAULT NULL COMMENT '性别 0 未设置 1男 2女',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `user_language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '语言',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `user_status` tinyint(2) NULL DEFAULT NULL COMMENT '状态 0正常 1冻结',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  UNIQUE INDEX `uk_open_id`(`open_id`) USING BTREE,
  UNIQUE INDEX `uk_union_id`(`union_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bnf_user
-- ----------------------------
INSERT INTO `bnf_user` VALUES (1328231759195709441, NULL, '13758233010', NULL, NULL, NULL, '40083589e55d00030c33b82eb655bc8b', '用户126ed3bf6a40a001', 'http://localhost/image/defaultAvatar.jpg', NULL, NULL, NULL, NULL, NULL, '2020-11-16 15:25:37', 0, '2020-11-16 15:01:43', '2020-11-16 15:25:37');

-- ----------------------------
-- Function structure for lat_lng_distance
-- ----------------------------
DROP FUNCTION IF EXISTS `lat_lng_distance`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `lat_lng_distance`(lat1 FLOAT, lon1 FLOAT, lat2 FLOAT, lon2 FLOAT) RETURNS float
    DETERMINISTIC
BEGIN
    RETURN ROUND(6378.138 * 2 * ASIN(SQRT(POW(SIN((lat1 * PI() / 180 - lat2 * PI() / 180) / 2), 2)
           + COS(lat1 * PI() / 180) * COS(lat2 * PI() / 180)
           * POW(SIN(( lon1 * PI() / 180 - lon2 * PI() / 180 ) / 2),2))),2);
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
