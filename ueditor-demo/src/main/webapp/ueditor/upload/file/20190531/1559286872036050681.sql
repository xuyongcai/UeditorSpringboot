/*
 Navicat Premium Data Transfer

 Source Server         : local_mysql
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : interact

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 29/05/2019 09:34:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `classes_id` int(11) DEFAULT NULL,
  `major` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone_number` int(11) DEFAULT NULL,
  `qq_number` int(255) DEFAULT NULL,
  `mail_address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userFace` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `enabled` tinyint(255) DEFAULT NULL,
  `role_id` int(11) unsigned DEFAULT NULL COMMENT '角色id',
  `gmt_create` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_username` (`username`) USING BTREE,
  KEY `id` (`id`) USING BTREE,
  KEY `fk_user_role_id` (`role_id`),
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'xiaochai', '123456', '小柴', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (2, 'zhangsan', '123456', '张三', 1, NULL, '12', 10086, 11111, NULL, NULL, NULL, 1, NULL);
INSERT INTO `user` VALUES (3, 'lisi', '123456', '李四', 2, NULL, '13', 10010, 12222, NULL, NULL, NULL, 2, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
