/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80300 (8.3.0)
 Source Host           : localhost:3306
 Source Schema         : gemini-mini

 Target Server Type    : MySQL
 Target Server Version : 80300 (8.3.0)
 File Encoding         : 65001

 Date: 23/04/2024 15:22:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `title` varchar(255) DEFAULT NULL COMMENT '模块标题',
  `type` tinyint(1) DEFAULT NULL COMMENT '操作类型',
  `type_name` varchar(255) DEFAULT NULL COMMENT '操作类型中文',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `param` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `result` varchar(2000) DEFAULT NULL COMMENT '请求结果',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态: 1 成功; 0 失败',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT NULL COMMENT '消耗时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
INSERT INTO `sys_log` (`id`, `user_id`, `title`, `type`, `type_name`, `description`, `param`, `result`, `status`, `oper_time`, `cost_time`) VALUES (4, 100, '菜单管理', 3, '删除', '删除菜单', '14', '{msg=操作成功, code=200}', 1, '2024-04-22 14:38:14', 43);
INSERT INTO `sys_log` (`id`, `user_id`, `title`, `type`, `type_name`, `description`, `param`, `result`, `status`, `oper_time`, `cost_time`) VALUES (5, 100, '用户管理', 2, '编辑', '编辑用户', 'UserVO(id=114, username=ronaldo, password=$2a$10$/NME1hSuGTk2Fq/Kw1DwkuD0OBqReIhH7hV6MStg9WssNUCxSTrt., nickname=罗纳尔多, avatar=null, mobile=182828382384, email=2139dsfji@qq.com, status=1, createTime=Sun Apr 21 22:02:23 CST 2024, updateTime=Sun Apr 21 22:26:58 CST 2024, menuIds=[9, 10, 11])', '{msg=操作成功, code=200}', 1, '2024-04-22 14:39:17', 38);
INSERT INTO `sys_log` (`id`, `user_id`, `title`, `type`, `type_name`, `description`, `param`, `result`, `status`, `oper_time`, `cost_time`) VALUES (6, 100, '菜单管理', 2, '编辑', '编辑菜单', 'MenuVO(id=12, parentId=9, name=后台日志管理, perm=system:log1, createTime=Sun Apr 21 21:41:55 CST 2024, updateTime=null)', '{msg=操作成功, code=200}', 1, '2024-04-22 15:03:32', 79);
INSERT INTO `sys_log` (`id`, `user_id`, `title`, `type`, `type_name`, `description`, `param`, `result`, `status`, `oper_time`, `cost_time`) VALUES (7, 100, '菜单管理', 2, '编辑', '编辑菜单', 'MenuVO(id=12, parentId=9, name=后台日志管理, perm=system:log, createTime=Sun Apr 21 21:41:55 CST 2024, updateTime=Mon Apr 22 15:03:32 CST 2024)', '{msg=操作成功, code=200}', 1, '2024-04-22 15:03:36', 13);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` int DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '菜单名字',
  `perm` varchar(255) DEFAULT NULL COMMENT '权限标识符',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (8, 0, '系统管理', 'system', 1, '2024-04-21 21:19:37', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (9, 0, '系统管理', 'system', 0, '2024-04-21 21:20:00', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (10, 9, '用户管理', 'system:user', 0, '2024-04-21 21:40:55', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (11, 9, '菜单权限管理', 'system:menu', 0, '2024-04-21 21:41:12', NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (12, 9, '后台日志管理', 'system:log', 0, '2024-04-21 21:41:55', '2024-04-22 15:03:36');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (13, 9, '测试管理', 'system:test', 1, '2024-04-21 21:43:03', '2024-04-21 21:43:08');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `perm`, `deleted`, `create_time`, `update_time`) VALUES (14, 0, '测试菜单', 'test', 1, '2024-04-22 13:26:04', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `mobile`, `email`, `status`, `deleted`, `create_time`, `update_time`) VALUES (100, 'super', '$2a$10$1mVTi2t1NbT58AHpwjSTM.2XDaPEhZefYyjijBKq30ily/5QISkhO', '超管', '/Users/edison/home/work/gemini/upload1713852242626图像 3.jpeg', '188888888', '2328738@qq.com', 1, 0, NULL, '2024-04-23 14:04:25');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `mobile`, `email`, `status`, `deleted`, `create_time`, `update_time`) VALUES (114, 'ronaldo', '$2a$10$/NME1hSuGTk2Fq/Kw1DwkuD0OBqReIhH7hV6MStg9WssNUCxSTrt.', '罗纳尔多', NULL, '182828382384', '2139dsfji@qq.com', 1, 0, '2024-04-21 22:02:23', '2024-04-22 14:39:17');
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `avatar`, `mobile`, `email`, `status`, `deleted`, `create_time`, `update_time`) VALUES (115, 'messi', '$2a$10$fFEjXP3JQOHRfvoO1FyJW.3qEqPlPzFasRfbukIA7592/goSQquRO', '梅西', NULL, NULL, NULL, 0, 1, '2024-04-21 22:04:19', '2024-04-21 22:08:52');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu` (
  `user_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sys_user_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_menu` (`user_id`, `menu_id`) VALUES (114, 9);
INSERT INTO `sys_user_menu` (`user_id`, `menu_id`) VALUES (114, 10);
INSERT INTO `sys_user_menu` (`user_id`, `menu_id`) VALUES (114, 11);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
