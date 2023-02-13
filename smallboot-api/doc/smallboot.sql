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

 Date: 13/02/2023 18:17:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
INSERT INTO `t_sys_dict` VALUES (75, 1, 'permission_btn', '添加', 'add', 1, 1, '', 1, '2020-08-22 15:01:51', 1, '2021-08-28 00:31:47', 0);
INSERT INTO `t_sys_dict` VALUES (76, 1, 'permission_btn', '删除', 'delete', 1, 2, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict` VALUES (77, 1, 'permission_btn', '编辑', 'edit', 1, 3, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict` VALUES (78, 1, 'permission_btn', '查询', 'query', 1, 4, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict` VALUES (79, 1, 'permission_btn', '重置', 'reset', 1, 5, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict` VALUES (80, 1, 'permission_btn', '详情', 'detail', 1, 6, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict` VALUES (81, 1, 'permission_btn', '保存', 'save', 1, 7, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:00', 0);
INSERT INTO `t_sys_dict` VALUES (98, 1, 'permission_btn', '权限', 'permission', 1, 24, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (117, 1, 'permission_btn', '开启授权', 'open_authorization', 1, 27, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (141, 1, 'permission_btn', '刷新', 'refresh', 1, 28, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (153, 1, 'permission_btn', '导出', 'export', 1, 29, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (154, 1, 'permission_btn', '表格列过滤', 'column_filter', 1, 30, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (155, 2, 'file_suffix', '.java', '.java', 1, 1, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (156, 2, 'file_suffix', '.xml', '.xml', 1, 2, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (157, 2, 'file_suffix', '.py', '.py', 1, 3, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (158, 2, 'file_suffix', '.vue', '.vue', 1, 4, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (159, 2, 'file_suffix', '.md', '.md', 1, 5, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (160, 2, 'file_suffix', '.php', '.php', 1, 6, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (161, 2, 'file_suffix', '.html', '.html', 1, 7, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (162, 2, 'file_suffix', '.js', '.js', 1, 8, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
INSERT INTO `t_sys_dict` VALUES (163, 2, 'file_suffix', '.jsp', '.jsp', 1, 9, NULL, 1, '2020-08-22 15:01:51', 0, '2021-08-28 00:03:01', 0);
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
INSERT INTO `t_sys_dict` VALUES (180, 1, 'permission_btn', '设计表', 'design_table', 1, 31, NULL, 1, '2020-09-06 19:09:40', 0, '2021-08-28 00:03:02', 0);
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
INSERT INTO `t_sys_dict_type` VALUES (1, 'permission_btn', '权限按钮', 1, 1, 0, 1, '2020-08-22 15:01:51', 1, '2021-08-28 23:00:04', 0);
INSERT INTO `t_sys_dict_type` VALUES (2, 'file_suffix', '文件后缀名', 1, 4, 0, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
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
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单链接url',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父类菜单ID',
  `sort` int(11) NULL DEFAULT NULL COMMENT '菜单排序',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名',
  `hidden` tinyint(1) NULL DEFAULT 1 COMMENT '是否隐藏 1:隐藏 0:显示',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向路径',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '菜单状态 1：启用  0：禁用',
  `type` tinyint(4) NULL DEFAULT 0 COMMENT '菜单类型 0菜单 1按钮',
  `always_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否总是显示 0:不显示 1:显示',
  `breadcrumb` tinyint(1) NULL DEFAULT 1 COMMENT '面包屑 0 false 1 true',
  `create_by` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` int(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES (2, '系统管理', 'system', 'Setting', '/system', 0, 2, 'Layout', 0, NULL, 1, 0, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu` VALUES (3, '菜单管理', 'menu', '', 'menu', 2, 9, 'system/menu/index', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (4, '用户管理', 'user', '', 'user', 2, 1, 'system/user/index', 0, '', 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (5, '角色管理', 'role', NULL, 'role', 2, 3, 'system/role/list', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (6, '角色权限', 'roleForm', NULL, 'roleForm', 2, 8, 'system/role/form', 1, NULL, 1, 0, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (8, '个人中心', 'personal-center', NULL, 'personal-center', 2, 2, 'system/personal-center/index', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (10, '数据字典', 'dict', NULL, 'dict', 2, 10, 'system/dict/index', 0, NULL, 1, 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51', 0);
INSERT INTO `t_sys_menu` VALUES (11, '首页', 'dashboard', 'Loading', '/', 0, 0, 'Layout', 0, 'dashboard', 1, 0, 0, 0, 1, '2020-08-22 15:01:51', 0, '2023-02-01 16:39:06', 0);
INSERT INTO `t_sys_menu` VALUES (12, '首页', 'Dashboard', '', 'dashboard', 11, 1, 'dashboard/index', 0, '', 1, 0, 0, 0, 1, '2020-08-22 15:01:51', 1, '2022-07-15 16:53:46', 0);

-- ----------------------------
-- Table structure for t_sys_oauth_client
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_oauth_client`;
CREATE TABLE `t_sys_oauth_client`  (
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端ID，唯一标识',
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端访问秘钥，BCryptPasswordEncoder加密算法加密',
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可访问资源id(英文逗号分隔)',
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权范围(英文逗号分隔)',
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '授权类型(英文逗号分隔)',
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '重定向uri',
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '@PreAuthorize(\"hasAuthority(\' admin \')\")可以在方法上标志 用户或者说client 需要说明样的权限\r\n\n\n指定客户端所拥有的Spring Security的权限值\r\n(英文逗号分隔)',
  `access_token_validity` int(11) NOT NULL COMMENT '令牌有效期(单位:秒)',
  `refresh_token_validity` int(11) NOT NULL COMMENT '刷新令牌有效期(单位:秒)',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预留字段,在Oauth的流程中没有实际的使用(JSON格式数据)',
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 \' false \'\r\n可选值包括 \' true \',\' false \', \' read \',\' write \'.\r\n该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为\' true \'或支持的scope值,则会跳过用户Approve的页面, 直接授权',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统管理-oauth2授权客户端' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_oauth_client
-- ----------------------------
INSERT INTO `t_sys_oauth_client` VALUES ('app', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `t_sys_oauth_client` VALUES ('client', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `t_sys_oauth_client` VALUES ('mini', '123456', '', 'all', 'password,refresh_token', '', NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');
INSERT INTO `t_sys_oauth_client` VALUES ('web', '123456', '', 'all', 'password,refresh_token,captcha', '', NULL, 3600, 259200, NULL, NULL, '2022-04-02 09:31:10', '2022-06-16 12:39:37');
INSERT INTO `t_sys_oauth_client` VALUES ('zq_app_id', '123456', 'res1', 'all', 'authorization_code,refresh_token', 'http://127.0.0.1:10020/index.html', NULL, 3600, 259200, NULL, 'true', '2022-04-02 09:31:10', '2022-06-16 12:25:03');

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单关联权限表' ROW_FORMAT = COMPACT;

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
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-系统属性' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_property
-- ----------------------------
INSERT INTO `t_sys_property` VALUES (9, 'test', '测试', 'this is test data.', 0, '2021-09-07 10:43:26', 0, '2021-09-07 10:43:26', 0);
INSERT INTO `t_sys_property` VALUES (10, 'hello', 'world', 'hello world !', 0, '2021-09-07 10:45:45', 0, '2021-09-07 10:45:45', 0);

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
INSERT INTO `t_sys_role` VALUES (1, '普通用户', 'persion', 1, 1, '2020-08-22 15:01:51', 0, '2023-02-01 16:33:02');
INSERT INTO `t_sys_role` VALUES (9, '超级管理员', 'super_admin', 1, 1, '2020-08-22 15:01:51', 1, '2022-07-15 11:36:42');
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
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色菜单关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_menu
-- ----------------------------
INSERT INTO `t_sys_role_menu` VALUES (141, 9, 2, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');
INSERT INTO `t_sys_role_menu` VALUES (142, 9, 4, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');
INSERT INTO `t_sys_role_menu` VALUES (143, 9, 8, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');
INSERT INTO `t_sys_role_menu` VALUES (144, 9, 5, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');
INSERT INTO `t_sys_role_menu` VALUES (145, 9, 6, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');
INSERT INTO `t_sys_role_menu` VALUES (146, 9, 3, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');
INSERT INTO `t_sys_role_menu` VALUES (147, 9, 10, 1, '2023-02-13 18:05:59', 1, '2023-02-13 18:05:59');

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
) ENGINE = InnoDB AUTO_INCREMENT = 836 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色关联权限表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission` VALUES (4, 1, 4, 1, '2022-06-14 15:29:57', 1, '2022-06-14 15:30:04');
INSERT INTO `t_sys_role_permission` VALUES (824, 1, 411, 1, '2022-07-21 18:07:56', 1, '2022-07-21 18:07:56');
INSERT INTO `t_sys_role_permission` VALUES (825, 1, 412, 1, '2022-07-21 18:07:56', 1, '2022-07-21 18:07:56');
INSERT INTO `t_sys_role_permission` VALUES (826, 9, 1, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission` VALUES (827, 9, 2, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission` VALUES (828, 9, 3, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission` VALUES (829, 9, 4, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission` VALUES (830, 9, 5, 1, '2022-07-21 18:08:26', 1, '2022-07-21 18:08:26');
INSERT INTO `t_sys_role_permission` VALUES (834, 1, 421, 1, '2022-07-22 14:38:12', 1, '2022-07-22 14:38:12');
INSERT INTO `t_sys_role_permission` VALUES (835, 1, 424, 1, '2022-07-22 14:38:12', 1, '2022-07-22 14:38:12');

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理 - 用户基础信息表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户角色关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES (1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-22 15:01:51');
INSERT INTO `t_sys_user_role` VALUES (4, 1, 9, 1, '2022-06-14 09:53:59', 1, '2022-06-14 09:54:01');
INSERT INTO `t_sys_user_role` VALUES (12, 2, 1, 1, '2022-07-15 17:34:06', 1, '2022-07-15 17:34:06');

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
