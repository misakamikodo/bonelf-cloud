/*
 Navicat Premium Data Transfer

 Source Server         : 60bonelf
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : api.bonelf.com:3306
 Source Schema         : bonelf-user

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 23/08/2021 00:31:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bnf_user
-- ----------------------------
DROP TABLE IF EXISTS `bnf_user`;
CREATE TABLE `bnf_user`  (
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `username` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义用户名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `union_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `open_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_password` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码MD5',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别 0 未设置 1男 2女',
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `user_language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '语言',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '上次登录时间',
  `user_status` tinyint NULL DEFAULT 0 COMMENT '状态 0正常 1冻结',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uk_phone`(`phone`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  UNIQUE INDEX `uk_open_id`(`open_id`) USING BTREE,
  UNIQUE INDEX `uk_union_id`(`union_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bnf_user
-- ----------------------------
INSERT INTO `bnf_user` VALUES (1328231759195709441, NULL, '13758233011', 'ccykirito@163.com', NULL, 'oSxri4irwmzqj4VnxfwOtiMjZxaw', '$2a$10$4uN8mbylLWjj9S4J6cDAvOw42l9r0GbCIDesF7FQ6wHv/rzVqaXIC', '用户126ed3bf6a40a001', 'http://localhost/image/defaultAvatar.jpg', NULL, NULL, NULL, NULL, NULL, '2020-11-16 15:25:37', 0, '2020-11-16 15:01:43', '2020-11-16 15:25:37');
INSERT INTO `bnf_user` VALUES (1395312296267362306, NULL, NULL, NULL, NULL, 'oSxri4irwmzqj4VnxfwOtiMjZxaw--', NULL, '手机用户', 'http://127.0.0.1/image/defaultAvatar.jpg', NULL, NULL, NULL, NULL, NULL, '2021-05-20 17:35:50', 0, '2021-05-20 17:35:50', NULL);
INSERT INTO `bnf_user` VALUES (1396505956568862721, '13758233010', '13758233010', NULL, NULL, NULL, NULL, '手机用户', 'http://127.0.0.1/image/defaultAvatar.jpg', NULL, NULL, NULL, NULL, NULL, '2021-05-24 00:39:01', 0, '2021-05-24 00:39:01', NULL);

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

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Function structure for lat_lng_distance
-- ----------------------------
DROP FUNCTION IF EXISTS `lat_lng_distance`;
delimiter ;;
CREATE FUNCTION `lat_lng_distance`()
  DETERMINISTIC

;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
