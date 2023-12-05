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

 Date: 05/12/2023 18:12:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log`  (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DATA_` longblob NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`LOG_NR_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_evt_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BYTES_` longblob NULL,
  `GENERATED_` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_BYTEARR_DEPL`(`DEPLOYMENT_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ge_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property`  (
  `NAME_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ge_property
-- ----------------------------
INSERT INTO `act_ge_property` VALUES ('batch.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('cfg.execution-related-entities-count', 'true', 1);
INSERT INTO `act_ge_property` VALUES ('cfg.task-related-entities-count', 'true', 1);
INSERT INTO `act_ge_property` VALUES ('common.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('entitylink.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('eventsubscription.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('identitylink.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('job.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('next.dbid', '1', 1);
INSERT INTO `act_ge_property` VALUES ('schema.history', 'create(6.8.0.0)', 1);
INSERT INTO `act_ge_property` VALUES ('schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('task.schema.version', '6.8.0.0', 1);
INSERT INTO `act_ge_property` VALUES ('variable.schema.version', '6.8.0.0', 1);

-- ----------------------------
-- Table structure for act_hi_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_actinst`;
CREATE TABLE `act_hi_actinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT 1,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `TRANSACTION_ORDER_` int(11) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_START`(`START_TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_END`(`END_TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_PROCINST`(`PROC_INST_ID_`, `ACT_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ACT_INST_EXEC`(`EXECUTION_ID_`, `ACT_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_attachment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_attachment`;
CREATE TABLE `act_hi_attachment`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `URL_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CONTENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_comment
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_comment`;
CREATE TABLE `act_hi_comment`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACTION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `MESSAGE_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `FULL_MSG_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_comment
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_detail
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_detail`;
CREATE TABLE `act_hi_detail`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TIME_` datetime(3) NOT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint(20) NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_PROC_INST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_ACT_INST`(`ACT_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_TIME`(`TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_NAME`(`NAME_`) USING BTREE,
  INDEX `ACT_IDX_HI_DETAIL_TASK_ID`(`TASK_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_detail
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_entitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_entitylink`;
CREATE TABLE `act_hi_entitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LINK_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REF_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REF_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REF_SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HIERARCHY_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_REF_SCOPE`(`REF_SCOPE_ID_`, `REF_SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_ROOT_SCOPE`(`ROOT_SCOPE_ID_`, `ROOT_SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_ENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_entitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_identitylink`;
CREATE TABLE `act_hi_identitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_USER`(`USER_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_TASK`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_IDENT_LNK_PROCINST`(`PROC_INST_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_procinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_procinst`;
CREATE TABLE `act_hi_procinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT 1,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `END_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `PROC_INST_ID_`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_INST_END`(`END_TIME_`) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_I_BUSKEY`(`BUSINESS_KEY_`) USING BTREE,
  INDEX `ACT_IDX_HI_PRO_SUPER_PROCINST`(`SUPER_PROCESS_INSTANCE_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_taskinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_taskinst`;
CREATE TABLE `act_hi_taskinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT 1,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `CLAIM_TIME_` datetime(3) NULL DEFAULT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PRIORITY_` int(11) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) NULL DEFAULT NULL,
  `FORM_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `LAST_UPDATED_TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_TASK_INST_PROCINST`(`PROC_INST_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_taskinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_tsk_log
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_tsk_log`;
CREATE TABLE `act_hi_tsk_log`  (
  `ID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TIME_STAMP_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DATA_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_tsk_log
-- ----------------------------

-- ----------------------------
-- Table structure for act_hi_varinst
-- ----------------------------
DROP TABLE IF EXISTS `act_hi_varinst`;
CREATE TABLE `act_hi_varinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT 1,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint(20) NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `LAST_UPDATED_TIME_` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_NAME_TYPE`(`NAME_`, `VAR_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_VAR_SCOPE_ID_TYPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_VAR_SUB_ID_TYPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_PROC_INST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_TASK_ID`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_IDX_HI_PROCVAR_EXE`(`EXECUTION_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_hi_varinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_id_bytearray`;
CREATE TABLE `act_id_bytearray`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BYTES_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_bytearray
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_group
-- ----------------------------
DROP TABLE IF EXISTS `act_id_group`;
CREATE TABLE `act_id_group`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_group
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_info
-- ----------------------------
DROP TABLE IF EXISTS `act_id_info`;
CREATE TABLE `act_id_info`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `USER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `VALUE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PASSWORD_` longblob NULL,
  `PARENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_membership
-- ----------------------------
DROP TABLE IF EXISTS `act_id_membership`;
CREATE TABLE `act_id_membership`  (
  `USER_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `GROUP_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`USER_ID_`, `GROUP_ID_`) USING BTREE,
  INDEX `ACT_FK_MEMB_GROUP`(`GROUP_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_priv
-- ----------------------------
DROP TABLE IF EXISTS `act_id_priv`;
CREATE TABLE `act_id_priv`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_PRIV_NAME`(`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_priv
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_priv_mapping
-- ----------------------------
DROP TABLE IF EXISTS `act_id_priv_mapping`;
CREATE TABLE `act_id_priv_mapping`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PRIV_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_PRIV_MAPPING`(`PRIV_ID_`) USING BTREE,
  INDEX `ACT_IDX_PRIV_USER`(`USER_ID_`) USING BTREE,
  INDEX `ACT_IDX_PRIV_GROUP`(`GROUP_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_PRIV_MAPPING` FOREIGN KEY (`PRIV_ID_`) REFERENCES `act_id_priv` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_priv_mapping
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_property
-- ----------------------------
DROP TABLE IF EXISTS `act_id_property`;
CREATE TABLE `act_id_property`  (
  `NAME_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VALUE_` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`NAME_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_property
-- ----------------------------
INSERT INTO `act_id_property` VALUES ('schema.version', '6.8.0.0', 1);

-- ----------------------------
-- Table structure for act_id_token
-- ----------------------------
DROP TABLE IF EXISTS `act_id_token`;
CREATE TABLE `act_id_token`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TOKEN_VALUE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TOKEN_DATE_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `IP_ADDRESS_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `USER_AGENT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TOKEN_DATA_` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_token
-- ----------------------------

-- ----------------------------
-- Table structure for act_id_user
-- ----------------------------
DROP TABLE IF EXISTS `act_id_user`;
CREATE TABLE `act_id_user`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `FIRST_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LAST_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DISPLAY_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EMAIL_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PWD_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PICTURE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_id_user
-- ----------------------------

-- ----------------------------
-- Table structure for act_procdef_info
-- ----------------------------
DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_INFO_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_IDX_INFO_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_INFO_JSON_BA`(`INFO_JSON_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_INFO_JSON_BA` FOREIGN KEY (`INFO_JSON_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_INFO_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_procdef_info
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `DEPLOY_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ENGINE_VERSION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LAST_UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `VERSION_` int(11) NULL DEFAULT NULL,
  `META_INFO_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_SOURCE`(`EDITOR_SOURCE_VALUE_ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_SOURCE_EXTRA`(`EDITOR_SOURCE_EXTRA_VALUE_ID_`) USING BTREE,
  INDEX `ACT_FK_MODEL_DEPLOYMENT`(`DEPLOYMENT_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_MODEL_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MODEL_SOURCE` FOREIGN KEY (`EDITOR_SOURCE_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_MODEL_SOURCE_EXTRA` FOREIGN KEY (`EDITOR_SOURCE_EXTRA_VALUE_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_model
-- ----------------------------

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) NULL DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `ENGINE_VERSION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DERIVED_FROM_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DERIVED_FROM_ROOT_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DERIVED_VERSION_` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_UNIQ_PROCDEF`(`KEY_`, `VERSION_`, `DERIVED_VERSION_`, `TENANT_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_re_procdef
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_actinst
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_actinst`;
CREATE TABLE `act_ru_actinst`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT 1,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CALL_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NOT NULL,
  `END_TIME_` datetime(3) NULL DEFAULT NULL,
  `DURATION_` bigint(20) NULL DEFAULT NULL,
  `TRANSACTION_ORDER_` int(11) NULL DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_START`(`START_TIME_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_END`(`END_TIME_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_PROC`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_PROC_ACT`(`PROC_INST_ID_`, `ACT_ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_EXEC`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_EXEC_ACT`(`EXECUTION_ID_`, `ACT_ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_ACTI_TASK`(`TASK_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_deadletter_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_deadletter_job`;
CREATE TABLE `act_ru_deadletter_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_DEADLETTER_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_`) USING BTREE,
  INDEX `ACT_IDX_DEADLETTER_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_`) USING BTREE,
  INDEX `ACT_IDX_DEADLETTER_JOB_CORRELATION_ID`(`CORRELATION_ID_`) USING BTREE,
  INDEX `ACT_IDX_DJOB_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_DJOB_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_DJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_DEADLETTER_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_DEADLETTER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_deadletter_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_entitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_entitylink`;
CREATE TABLE `act_ru_entitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `LINK_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REF_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REF_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REF_SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ROOT_SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HIERARCHY_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_REF_SCOPE`(`REF_SCOPE_ID_`, `REF_SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_ROOT_SCOPE`(`ROOT_SCOPE_ID_`, `ROOT_SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_ENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`, `LINK_TYPE_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_entitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CONFIGURATION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATED_` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EVENT_SUBSCR_CONFIG_`(`CONFIGURATION_`) USING BTREE,
  INDEX `ACT_IDX_EVENT_SUBSCR_SCOPEREF_`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_EVENT_EXEC`(`EXECUTION_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_event_subscr
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ROOT_PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) NULL DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) NULL DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) NULL DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) NULL DEFAULT NULL,
  `IS_MI_ROOT_` tinyint(4) NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NULL DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_ACT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `START_TIME_` datetime(3) NULL DEFAULT NULL,
  `START_USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `LOCK_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint(4) NULL DEFAULT NULL,
  `EVT_SUBSCR_COUNT_` int(11) NULL DEFAULT NULL,
  `TASK_COUNT_` int(11) NULL DEFAULT NULL,
  `JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `TIMER_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `SUSP_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `DEADLETTER_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `EXTERNAL_WORKER_JOB_COUNT_` int(11) NULL DEFAULT NULL,
  `VAR_COUNT_` int(11) NULL DEFAULT NULL,
  `ID_LINK_COUNT_` int(11) NULL DEFAULT NULL,
  `CALLBACK_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CALLBACK_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REFERENCE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `REFERENCE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BUSINESS_STATUS_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EXEC_BUSKEY`(`BUSINESS_KEY_`) USING BTREE,
  INDEX `ACT_IDC_EXEC_ROOT`(`ROOT_PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_IDX_EXEC_REF_ID_`(`REFERENCE_ID_`) USING BTREE,
  INDEX `ACT_FK_EXE_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_FK_EXE_PARENT`(`PARENT_ID_`) USING BTREE,
  INDEX `ACT_FK_EXE_SUPER`(`SUPER_EXEC_`) USING BTREE,
  INDEX `ACT_FK_EXE_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXE_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_execution
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_external_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_external_job`;
CREATE TABLE `act_ru_external_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_EXTERNAL_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_`) USING BTREE,
  INDEX `ACT_IDX_EXTERNAL_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_`) USING BTREE,
  INDEX `ACT_IDX_EXTERNAL_JOB_CORRELATION_ID`(`CORRELATION_ID_`) USING BTREE,
  INDEX `ACT_IDX_EJOB_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_EJOB_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_EJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  CONSTRAINT `ACT_FK_EXTERNAL_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_EXTERNAL_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_external_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_history_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_history_job`;
CREATE TABLE `act_ru_history_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ADV_HANDLER_CFG_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_history_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `GROUP_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `USER_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_USER`(`USER_ID_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_GROUP`(`GROUP_ID_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_IDENT_LNK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_ATHRZ_PROCEDEF`(`PROC_DEF_ID_`) USING BTREE,
  INDEX `ACT_FK_TSKASS_TASK`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_FK_IDL_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_IDL_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_identitylink
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_`) USING BTREE,
  INDEX `ACT_IDX_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_`) USING BTREE,
  INDEX `ACT_IDX_JOB_CORRELATION_ID`(`CORRELATION_ID_`) USING BTREE,
  INDEX `ACT_IDX_JOB_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_JOB_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_JOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_suspended_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_suspended_job`;
CREATE TABLE `act_ru_suspended_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_SUSPENDED_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_`) USING BTREE,
  INDEX `ACT_IDX_SUSPENDED_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_`) USING BTREE,
  INDEX `ACT_IDX_SUSPENDED_JOB_CORRELATION_ID`(`CORRELATION_ID_`) USING BTREE,
  INDEX `ACT_IDX_SJOB_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_SJOB_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_SJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_SUSPENDED_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_SUSPENDED_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_suspended_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_task
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROPAGATED_STAGE_INST_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DELEGATION_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PRIORITY_` int(11) NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `DUE_DATE_` datetime(3) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `FORM_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CLAIM_TIME_` datetime(3) NULL DEFAULT NULL,
  `IS_COUNT_ENABLED_` tinyint(4) NULL DEFAULT NULL,
  `VAR_COUNT_` int(11) NULL DEFAULT NULL,
  `ID_LINK_COUNT_` int(11) NULL DEFAULT NULL,
  `SUB_TASK_COUNT_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_TASK_CREATE`(`CREATE_TIME_`) USING BTREE,
  INDEX `ACT_IDX_TASK_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_TASK_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_TASK_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_TASK_EXE`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_TASK_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  INDEX `ACT_FK_TASK_PROCDEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_task
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_timer_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_timer_job`;
CREATE TABLE `act_ru_timer_job`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp(3) NULL DEFAULT NULL,
  `LOCK_OWNER_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) NULL DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `ELEMENT_NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_DEFINITION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CORRELATION_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RETRIES_` int(11) NULL DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DUEDATE_` timestamp(3) NULL DEFAULT NULL,
  `REPEAT_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CUSTOM_VALUES_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_EXCEPTION_STACK_ID`(`EXCEPTION_STACK_ID_`) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_CUSTOM_VALUES_ID`(`CUSTOM_VALUES_ID_`) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_CORRELATION_ID`(`CORRELATION_ID_`) USING BTREE,
  INDEX `ACT_IDX_TIMER_JOB_DUEDATE`(`DUEDATE_`) USING BTREE,
  INDEX `ACT_IDX_TJOB_SCOPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_TJOB_SUB_SCOPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_TJOB_SCOPE_DEF`(`SCOPE_DEFINITION_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_EXECUTION`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_PROCESS_INSTANCE`(`PROCESS_INSTANCE_ID_`) USING BTREE,
  INDEX `ACT_FK_TIMER_JOB_PROC_DEF`(`PROC_DEF_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_TIMER_JOB_CUSTOM_VALUES` FOREIGN KEY (`CUSTOM_VALUES_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_EXECUTION` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_PROCESS_INSTANCE` FOREIGN KEY (`PROCESS_INSTANCE_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_TIMER_JOB_PROC_DEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_timer_job
-- ----------------------------

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TASK_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `DOUBLE_` double NULL DEFAULT NULL,
  `LONG_` bigint(20) NULL DEFAULT NULL,
  `TEXT_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TEXT2_` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `ACT_IDX_RU_VAR_SCOPE_ID_TYPE`(`SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_IDX_RU_VAR_SUB_ID_TYPE`(`SUB_SCOPE_ID_`, `SCOPE_TYPE_`) USING BTREE,
  INDEX `ACT_FK_VAR_BYTEARRAY`(`BYTEARRAY_ID_`) USING BTREE,
  INDEX `ACT_IDX_VARIABLE_TASK_ID`(`TASK_ID_`) USING BTREE,
  INDEX `ACT_FK_VAR_EXE`(`EXECUTION_ID_`) USING BTREE,
  INDEX `ACT_FK_VAR_PROCINST`(`PROC_INST_ID_`) USING BTREE,
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_ru_variable
-- ----------------------------

-- ----------------------------
-- Table structure for flw_channel_definition
-- ----------------------------
DROP TABLE IF EXISTS `flw_channel_definition`;
CREATE TABLE `flw_channel_definition`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION_` int(11) NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TYPE_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `IMPLEMENTATION_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_IDX_CHANNEL_DEF_UNIQ`(`KEY_`, `VERSION_`, `TENANT_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_channel_definition
-- ----------------------------

-- ----------------------------
-- Table structure for flw_ev_databasechangelog
-- ----------------------------
DROP TABLE IF EXISTS `flw_ev_databasechangelog`;
CREATE TABLE `flw_ev_databasechangelog`  (
  `ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `AUTHOR` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `FILENAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `MD5SUM` varchar(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `COMMENTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LIQUIBASE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CONTEXTS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `LABELS` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_ev_databasechangelog
-- ----------------------------
INSERT INTO `flw_ev_databasechangelog` VALUES ('1', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2023-12-01 15:16:48', 1, 'EXECUTED', '8:1b0c48c9cf7945be799d868a2626d687', 'createTable tableName=FLW_EVENT_DEPLOYMENT; createTable tableName=FLW_EVENT_RESOURCE; createTable tableName=FLW_EVENT_DEFINITION; createIndex indexName=ACT_IDX_EVENT_DEF_UNIQ, tableName=FLW_EVENT_DEFINITION; createTable tableName=FLW_CHANNEL_DEFIN...', '', NULL, '4.9.1', NULL, NULL, '1415008370');
INSERT INTO `flw_ev_databasechangelog` VALUES ('2', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2023-12-01 15:16:49', 2, 'EXECUTED', '8:0ea825feb8e470558f0b5754352b9cda', 'addColumn tableName=FLW_CHANNEL_DEFINITION; addColumn tableName=FLW_CHANNEL_DEFINITION', '', NULL, '4.9.1', NULL, NULL, '1415008370');
INSERT INTO `flw_ev_databasechangelog` VALUES ('3', 'flowable', 'org/flowable/eventregistry/db/liquibase/flowable-eventregistry-db-changelog.xml', '2023-12-01 15:16:49', 3, 'EXECUTED', '8:3c2bb293350b5cbe6504331980c9dcee', 'customChange', '', NULL, '4.9.1', NULL, NULL, '1415008370');

-- ----------------------------
-- Table structure for flw_ev_databasechangeloglock
-- ----------------------------
DROP TABLE IF EXISTS `flw_ev_databasechangeloglock`;
CREATE TABLE `flw_ev_databasechangeloglock`  (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime NULL DEFAULT NULL,
  `LOCKEDBY` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_ev_databasechangeloglock
-- ----------------------------
INSERT INTO `flw_ev_databasechangeloglock` VALUES (1, b'0', NULL, NULL);

-- ----------------------------
-- Table structure for flw_event_definition
-- ----------------------------
DROP TABLE IF EXISTS `flw_event_definition`;
CREATE TABLE `flw_event_definition`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `VERSION_` int(11) NULL DEFAULT NULL,
  `KEY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `RESOURCE_NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  UNIQUE INDEX `ACT_IDX_EVENT_DEF_UNIQ`(`KEY_`, `VERSION_`, `TENANT_ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_event_definition
-- ----------------------------

-- ----------------------------
-- Table structure for flw_event_deployment
-- ----------------------------
DROP TABLE IF EXISTS `flw_event_deployment`;
CREATE TABLE `flw_event_deployment`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `CATEGORY_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOY_TIME_` datetime(3) NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `PARENT_DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_event_deployment
-- ----------------------------

-- ----------------------------
-- Table structure for flw_event_resource
-- ----------------------------
DROP TABLE IF EXISTS `flw_event_resource`;
CREATE TABLE `flw_event_resource`  (
  `ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `NAME_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `RESOURCE_BYTES_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_event_resource
-- ----------------------------

-- ----------------------------
-- Table structure for flw_ru_batch
-- ----------------------------
DROP TABLE IF EXISTS `flw_ru_batch`;
CREATE TABLE `flw_ru_batch`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `SEARCH_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SEARCH_KEY2_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NOT NULL,
  `COMPLETE_TIME_` datetime(3) NULL DEFAULT NULL,
  `STATUS_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `BATCH_DOC_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_ru_batch
-- ----------------------------

-- ----------------------------
-- Table structure for flw_ru_batch_part
-- ----------------------------
DROP TABLE IF EXISTS `flw_ru_batch_part`;
CREATE TABLE `flw_ru_batch_part`  (
  `ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `REV_` int(11) NULL DEFAULT NULL,
  `BATCH_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `SCOPE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SUB_SCOPE_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SCOPE_TYPE_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SEARCH_KEY_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `SEARCH_KEY2_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `CREATE_TIME_` datetime(3) NOT NULL,
  `COMPLETE_TIME_` datetime(3) NULL DEFAULT NULL,
  `STATUS_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `RESULT_DOC_ID_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `TENANT_ID_` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `FLW_IDX_BATCH_PART`(`BATCH_ID_`) USING BTREE,
  CONSTRAINT `FLW_FK_BATCH_PART_PARENT` FOREIGN KEY (`BATCH_ID_`) REFERENCES `flw_ru_batch` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of flw_ru_batch_part
-- ----------------------------

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
  `freight` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '运费(单位:分 0:包邮)',
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
INSERT INTO `oms_order` VALUES (1703717164000378880, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-09-18 18:31:40', 200, 0, 200, 2, 1, '测试', NULL, 0, NULL, '2023-09-19 09:35:23', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-09-18 18:26:40', '2023-09-20 15:55:10', 1, 0, 0);
INSERT INTO `oms_order` VALUES (1705033774433079296, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, NULL, NULL, '2023-09-22 09:43:25', 40100, 0, 40100, 2, 1, NULL, NULL, 0, NULL, '2023-09-22 09:38:41', 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-09-22 09:38:25', '2023-09-22 09:38:41', 1, 1, 0);
INSERT INTO `oms_order` VALUES (1705045491980734464, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', 1, '郑清', '15183388888', 1, NULL, '1705045499111051264', '2023-09-22 10:25:00', '2023-09-22 10:29:58', 10400, 0, 10400, 3, 1, '测试', NULL, 0, NULL, NULL, 1, NULL, NULL, '', 2, NULL, NULL, 0, '2023-09-22 10:24:58', '2023-09-22 10:25:00', 1, 1, 0);

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
  `order_item_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单详情ids',
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
  `cert_img_list` json NULL COMMENT '凭证图',
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
-- Table structure for oms_order_deliver
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_deliver`;
CREATE TABLE `oms_order_deliver`  (
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
-- Records of oms_order_deliver
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
  `spec_list` json NOT NULL COMMENT '商品规格属性',
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
INSERT INTO `oms_order_item` VALUES (1703717164155568128, 1, 1703717164000378880, 1, 1701132647401549824, 1701132647711928320, '杨枝甘露', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577430681518080\", \"attrValueName\": \"绿色\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-09-18 18:26:40', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1703717164155568129, 1, 1703717164000378880, 1, 1701133260151615488, 1701133260352942080, '西柚柠檬', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-09-18 18:26:40', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1705033774575685632, 1, 1705033774433079296, 1, 1701134202930491392, 1701134203144400896, '吐司', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', 4, 10000, 40000, 101, 2, 0, NULL, NULL, NULL, 0, '2023-09-22 09:38:25', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1705033774575685633, 1, 1705033774433079296, 1, 1701133260151615488, 1701133260352942080, '西柚柠檬', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', 1, 100, 100, 101, 2, 0, NULL, NULL, NULL, 0, '2023-09-22 09:38:25', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1705045492043649024, 1, 1705045491980734464, 1, 1701133018807169024, 1701133019029467136, '茉莉小雪', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577430681518080\", \"attrValueName\": \"绿色\"}]', 1, 100, 100, 101, 3, 0, NULL, NULL, NULL, 0, '2023-09-22 10:24:58', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1705045492047843328, 1, 1705045491980734464, 1, 1701133260151615488, 1701133260352942080, '西柚柠檬', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', 2, 100, 200, 101, 3, 0, NULL, NULL, NULL, 0, '2023-09-22 10:24:58', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1705045492047843329, 1, 1705045491980734464, 1, 1701132775487205376, 1701132775738863616, '水蜜桃', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577430681518080\", \"attrValueName\": \"绿色\"}]', 1, 100, 100, 101, 3, 0, NULL, NULL, NULL, 0, '2023-09-22 10:24:58', '2023-09-28 14:12:20');
INSERT INTO `oms_order_item` VALUES (1705045492047843330, 1, 1705045491980734464, 1, 1701134202930491392, 1701134203144400896, '吐司', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', 1, 10000, 10000, 101, 3, 0, NULL, NULL, NULL, 0, '2023-09-22 10:24:58', '2023-09-28 14:12:20');

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
INSERT INTO `pms_attr_key` VALUES (1661577306127466496, 1, '颜色', 2, '2023-05-25 11:37:55', '2023-08-29 11:40:11', 1, 1, 0);

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
INSERT INTO `pms_attr_value` VALUES (1532284264874377216, 1, 1532283711813451776, 'X', 3, '2022-06-02 16:53:32', '2023-08-31 14:10:14', 0, 1, 0);
INSERT INTO `pms_attr_value` VALUES (1532284292691001344, 1, 1532283711813451776, 'XL', 4, '2022-06-02 16:53:39', '2023-08-31 14:10:18', 0, 1, 0);
INSERT INTO `pms_attr_value` VALUES (1532284305848532992, 1, 1532283711813451776, 'L', 2, '2022-06-02 16:53:42', '2023-08-31 14:10:08', 0, 1, 0);
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
INSERT INTO `pms_category` VALUES (1532285975026335744, 1, 0, '六一专属', 1, 1, '2022-06-02 17:00:20', '2023-09-18 17:08:42', 0, 1, 0);
INSERT INTO `pms_category` VALUES (1533982865094737920, 1, 0, 'GOODS', 3, 1, '2022-06-07 09:23:10', '2023-09-11 17:03:30', 0, 1, 0);

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
INSERT INTO `pms_category_spu_relation` VALUES (1697128804988379136, 1, 1533982865094737920, 1661570067979304960, 1, 1, 1, '2023-08-31 14:06:53', '2023-08-31 14:06:53', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701133399041798144, 1, 1532285975026335744, 1701131118611034112, 1, 1, 1, '2023-09-11 15:19:43', '2023-09-11 15:19:43', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701133413470203904, 1, 1532285975026335744, 1701132647401549824, 1, 1, 1, '2023-09-11 15:19:46', '2023-09-11 15:19:46', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701133429664411648, 1, 1532285975026335744, 1701132492304576512, 1, 1, 1, '2023-09-11 15:19:50', '2023-09-11 15:19:50', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701133441819504640, 1, 1532285975026335744, 1701133018807169024, 1, 1, 1, '2023-09-11 15:19:53', '2023-09-11 15:19:53', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701133453488058368, 1, 1532285975026335744, 1701133260151615488, 1, 1, 1, '2023-09-11 15:19:56', '2023-09-11 15:19:56', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701133466112913408, 1, 1532285975026335744, 1701132775487205376, 1, 1, 1, '2023-09-11 15:19:59', '2023-09-11 15:19:59', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701134230172495872, 1, 1532285889399619584, 1701133818556084224, 1, 1, 1, '2023-09-11 15:23:01', '2023-09-11 15:23:01', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701134241518088192, 1, 1532285889399619584, 1701133961695096832, 1, 1, 1, '2023-09-11 15:23:03', '2023-09-11 15:23:03', 1, 1, 0);
INSERT INTO `pms_category_spu_relation` VALUES (1701134253304082432, 1, 1532285889399619584, 1701134202930491392, 1, 1, 1, '2023-09-11 15:23:06', '2023-09-11 15:23:06', 1, 1, 0);

-- ----------------------------
-- Table structure for pms_sku
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku`;
CREATE TABLE `pms_sku`  (
  `id` bigint(20) NOT NULL COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `spu_id` bigint(20) NOT NULL COMMENT '商品ID',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格编码',
  `spec_list` json NULL COMMENT '商品规格-属性',
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
INSERT INTO `pms_sku` VALUES (1692477838160318464, 1, 1534420706752856064, '', '[{\"attrKeyId\": \"1532281238671458304\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1532284024536563712\", \"attrValueName\": \"蓝色\"}, {\"attrKeyId\": \"1532283711813451776\", \"attrKeyName\": \"尺寸\", \"attrValueId\": \"1532284264874377216\", \"attrValueName\": \"X\"}]', NULL, 10, 0, 0, 100, 0, 0, 100, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-08-18 18:05:36', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1692477838160318465, 1, 1534420706752856064, '', '[{\"attrKeyId\": \"1532281238671458304\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1532284050088263680\", \"attrValueName\": \"红色\"}, {\"attrKeyId\": \"1532283711813451776\", \"attrKeyName\": \"尺寸\", \"attrValueId\": \"1532284264874377216\", \"attrValueName\": \"X\"}]', NULL, 280, 0, 0, 1000, 0, 0, 1000, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-08-18 18:05:36', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1696811322284466176, 1, 1661570067979304960, '', '[{\"attrKeyId\": \"1532283711813451776\", \"attrKeyName\": \"尺寸\", \"attrValueId\": \"1532284305848532992\", \"attrValueName\": \"L\"}, {\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', NULL, 1011, 0, 0, 10, 0, 0, 10, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-08-30 17:05:19', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701131118896246784, 1, 1701131118611034112, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', NULL, 100, 0, 0, 100, 0, 0, 100, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:10:39', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701132492799504384, 1, 1701132492304576512, NULL, '[{\"attrKeyId\": \"1532283711813451776\", \"attrKeyName\": \"尺寸\", \"attrValueId\": \"1532284264874377216\", \"attrValueName\": \"X\"}]', NULL, 100, 0, 0, 11, 0, 0, 11, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:16:06', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701132647711928320, 1, 1701132647401549824, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577430681518080\", \"attrValueName\": \"绿色\"}]', NULL, 100, 0, 0, 11, 0, 0, 11, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:16:43', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701132775738863616, 1, 1701132775487205376, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577430681518080\", \"attrValueName\": \"绿色\"}]', NULL, 100, 0, 0, 11, 1, 0, 10, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:17:14', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701133019029467136, 1, 1701133018807169024, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577430681518080\", \"attrValueName\": \"绿色\"}]', NULL, 100, 0, 0, 1, 1, 0, 0, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:18:12', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701133260352942080, 1, 1701133260151615488, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', NULL, 100, 0, 0, 100, 2, 0, 98, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:19:09', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701133818883239936, 1, 1701133818556084224, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', NULL, 100, 0, 0, 100, 0, 0, 100, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:21:23', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701133962043224064, 1, 1701133961695096832, NULL, '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', NULL, 10000, 0, 0, 10, 0, 0, 10, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:21:57', '2023-09-28 14:13:39', 0);
INSERT INTO `pms_sku` VALUES (1701134203144400896, 1, 1701134202930491392, '', '[{\"attrKeyId\": \"1661577306127466496\", \"attrKeyName\": \"颜色\", \"attrValueId\": \"1661577389468286976\", \"attrValueName\": \"蓝色\"}]', NULL, 10000, 0, 0, 100, 1, 0, 99, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', 1, 1, '2023-09-11 15:22:54', '2023-09-28 14:13:39', 0);

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
  `slide_img_list` json NULL COMMENT '轮播图',
  `detail_img_list` json NULL COMMENT '商品详情图',
  `line_price` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '商品划线价格(单位:分)',
  `freight` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '运费(单位:分 0:包邮)',
  `attr_list` json NULL COMMENT '商品属性',
  `is_put` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否上架(0->下架；1->上架)',
  `is_show` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否显示(0->隐藏；1->显示)',
  `is_presell` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否预售(0->否；1->是)',
  `presell_start_time` datetime NULL DEFAULT NULL COMMENT '预售开始时间',
  `presell_end_time` datetime NULL DEFAULT NULL COMMENT '预售结束时间',
  `presell_deliver_day` int(11) NULL DEFAULT NULL COMMENT '预售-发货日期(购买之后？天之后发货)',
  `service_list` json NULL COMMENT '商品关联服务',
  `explain_list` json NULL COMMENT '商品关联说明',
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
INSERT INTO `pms_spu` VALUES (1534420706752856064, 1, '熊猫限定帆布袋', 1, 101, 1, '买一送一', 100, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, '2021-08-25 09:00:00', '2021-08-26 23:59:59', 15, '[{\"code\": \"btn\", \"name\": \"添加\", \"sort\": 1, \"value\": \"add\", \"remark\": \"this is the add.\"}]', '[{\"code\": \"btn\", \"name\": \"添加\", \"sort\": 1, \"value\": \"add\", \"remark\": \"this is the add.\"}]', NULL, '2022-06-08 14:22:59', '2023-09-28 14:14:16', 0, 1, 0);
INSERT INTO `pms_spu` VALUES (1661570067979304960, 1, '测试商品', 2, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, '[]', '[]', NULL, '2023-05-25 11:09:09', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701131118611034112, 1, '草莓', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:10:39', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701132492304576512, 1, '葡萄', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:16:06', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701132647401549824, 1, '杨枝甘露', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:16:43', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701132775487205376, 1, '水蜜桃', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:17:14', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701133018807169024, 1, '茉莉小雪', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:18:12', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701133260151615488, 1, '西柚柠檬', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:19:09', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701133818556084224, 1, '珍珠奶茶', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:21:23', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701133961695096832, 1, '火腿芝士', 0, 101, NULL, NULL, NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2023-09-11 15:21:57', '2023-09-28 14:14:16', 1, 1, 0);
INSERT INTO `pms_spu` VALUES (1701134202930491392, 1, '吐司', 0, 101, NULL, '', NULL, 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图17.jpg\"}]', NULL, 0, NULL, 1, 1, 0, NULL, NULL, NULL, '[]', '[]', NULL, '2023-09-11 15:22:54', '2023-09-28 14:14:16', 1, 1, 0);

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
  `spec_list` json NOT NULL COMMENT '商品规格属性',
  `operator_type` tinyint(2) NOT NULL COMMENT '操作人类型(1->买家 2->商家)',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人id',
  `operator_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人名称',
  `operator_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作人头像',
  `resource_list` json NOT NULL COMMENT '评价图片或视频',
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
-- Table structure for sms_shop
-- ----------------------------
DROP TABLE IF EXISTS `sms_shop`;
CREATE TABLE `sms_shop`  (
  `shop_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '门店ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `shop_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '门店名称',
  `head_img_list` json NULL COMMENT '门店头图',
  `province_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `area_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区名称',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店详细地址',
  `longitude` decimal(9, 6) NULL DEFAULT NULL COMMENT '门店坐标-经度',
  `latitude` decimal(9, 6) NULL DEFAULT NULL COMMENT '门店坐标-纬度',
  `type` tinyint(4) NULL DEFAULT 1 COMMENT '店铺类型（1:餐饮 2:电商 3:教育）',
  `contact_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系手机号',
  `is_show` tinyint(1) NULL DEFAULT NULL COMMENT '是否显示（1：是 0：否）',
  `snack_status` tinyint(1) NULL DEFAULT 0 COMMENT '堂食状态（1：开启 0：关闭）',
  `takeout_status` tinyint(1) NULL DEFAULT 0 COMMENT '外卖状态（1：开启 0：关闭）',
  `open_status` tinyint(1) NULL DEFAULT NULL COMMENT '门店营业状态（1：营业中 0：未营业）',
  `open_time_list` json NULL COMMENT '营业时间',
  `deliver_distance` int(11) NULL DEFAULT NULL COMMENT '外卖配送距离（单位：米）',
  `deliver_scope_list` json NULL COMMENT '外卖配送范围',
  `deliver_fee_list` json NULL COMMENT '外卖配送费（满?分,配送费?分）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商城-店铺信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_shop
-- ----------------------------
INSERT INTO `sms_shop` VALUES (1, 1, '天府三街测试门店', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\", \"name\": \"美图35.jpg\"}]', '四川省', '成都市', '武侯区', '四川省成都市武侯区天府三街', 104.069038, 30.546109, 1, '小郑', '15183308888', 1, 0, 0, 0, '[{\"endTime\": \"22:00\", \"weekList\": [1, 2, 3, 4, 5, 6, 7], \"startTime\": \"09:00\"}]', 1000, NULL, NULL, '2023-09-15 18:20:55', '2023-09-28 14:24:56', 1, 1, 0);
INSERT INTO `sms_shop` VALUES (2, 1, '高新区天府五街分店', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707240834895855616-美图13.png\", \"name\": \"美图13.png\"}]', '四川省', '成都市', '武侯区', '四川省成都市武侯区天府五街', 104.069398, 30.536950, 1, '小郑', '15183308888', 1, 1, 1, 1, '[{\"endTime\": \"22:00\", \"weekList\": [1, 2, 3, 4, 5, 6, 7], \"startTime\": \"09:00\"}]', 1000, NULL, NULL, '2023-09-15 18:20:55', '2023-09-28 14:24:49', 1, 1, 0);
INSERT INTO `sms_shop` VALUES (4, 1, '四川大学测试店', '[{\"url\": \"http://127.0.0.1:886/2023-09-28/1707280259789041664-美图28.jpg\", \"name\": \"美图28.jpg\"}]', '四川省', '成都市', '双流区', '四川省成都市双流区明远路', 103.999260, 30.557481, 1, '小郑', '15183008888', 1, 0, 0, 0, '[{\"endTime\": \"18:00\", \"weekList\": [1, 2, 3, 4, 5, 6, 7], \"startTime\": \"09:00\"}]', 3, NULL, NULL, '2023-09-26 14:54:42', '2023-09-28 14:34:38', 1, 1, 0);

-- ----------------------------
-- Table structure for t_sys_app_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_app_config`;
CREATE TABLE `t_sys_app_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AppID',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AppSecret',
  `app_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序版本',
  `app_version_obj` json NULL COMMENT '小程序版本详情信息',
  `app_status` int(11) NULL DEFAULT NULL COMMENT '小程序状态',
  `app_index_title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序首页标题',
  `app_type` tinyint(4) NULL DEFAULT NULL COMMENT '小程序类型（1：共享版  2：独立版）',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-小程序配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_app_config
-- ----------------------------
INSERT INTO `t_sys_app_config` VALUES (1, 1, 'SmallBoot', '', '', '0.0.1', NULL, 1, '首页', 2, 1, '2023-10-09 19:16:37', 1, '2023-11-02 18:43:52', 0);
INSERT INTO `t_sys_app_config` VALUES (3, 3, '测试租户', '', '', '', NULL, NULL, '', NULL, 2, '2023-10-23 16:31:46', 1, '2023-11-02 18:43:56', 0);

-- ----------------------------
-- Table structure for t_sys_app_service_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_app_service_config`;
CREATE TABLE `t_sys_app_service_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `component_app_id` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方小程序应用appid',
  `component_app_secret` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方小程序应用appsecret',
  `tp_token` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息验证TOKEN',
  `encoding_aes_key` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '消息加密解密KEY',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-小程序服务商平台配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_app_service_config
-- ----------------------------
INSERT INTO `t_sys_app_service_config` VALUES (2, '抖音小程序服务商平台配置', NULL, NULL, NULL, NULL, 1, '2023-10-09 19:16:37', 1, '2023-10-20 21:45:47');

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性key',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '属性value',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `type` tinyint(4) NOT NULL COMMENT '类型（1:配置 2:属性）',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-系统属性' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES (10, 1, 'hello', 'world', 'hello world !', 2, 0, '2021-09-07 10:45:45', 0, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (12, 1, 'test', '测试', 'this is test data.', 2, 1, '2023-02-15 11:19:46', 1, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (61, 1, 'mall_order_set_auto_receive_millisecond', '864000000', '发货后？毫秒后自动确认收货', 1, 0, '2023-10-07 15:38:41', 0, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (62, 1, 'mall_order_set_un_pay_close_millisecond', '600000', '待付款订单？毫秒后自动关闭', 1, 0, '2023-10-07 15:39:41', NULL, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (63, 1, 'mall_order_set_buyer_apply_after_sale_handle_millisecond', '864000000', '买家发起售后申请？毫秒后，卖家未处理，自动关闭', 1, 0, '2023-10-07 15:40:37', NULL, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (64, 1, 'mall_order_set_after_sale_buyer_deliver_millisecond', '864000000', '待买家发货(买家申请售后，卖家同意后，买家未填写退货返回物流单号)？毫秒后自动关闭', 1, 0, '2023-10-07 15:41:01', NULL, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (65, 1, 'mall_order_set_buyer_apply_after_sale_millisecond', '864000000', '买家确认收货？毫秒后无法发起售后申请', 1, 0, '2023-10-07 15:41:11', NULL, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (66, 1, 'mall_order_set_stock_check_type', '1', '减库存设置（1：提交订单减库存 2：付款减库存）', 1, 0, '2023-10-07 15:41:29', NULL, '2023-10-08 18:25:08', 0);
INSERT INTO `t_sys_config` VALUES (67, 1, 'douyin_app_id', NULL, '抖音小程序appid', 1, 0, '2023-10-11 19:14:09', NULL, '2023-10-11 19:15:37', 0);
INSERT INTO `t_sys_config` VALUES (68, 1, 'douyin_app_secret', NULL, '抖音小程序secret', 1, 0, '2023-10-11 19:15:27', NULL, '2023-10-11 19:15:39', 0);
INSERT INTO `t_sys_config` VALUES (71, 1, 'mall_index_slide_img_list', '[{\"name\":\"美图13.png\",\"url\":\"http://127.0.0.1:886/2023-09-28/1707240834895855616-美图13.png\",\"uid\":\"1695893530664\",\"status\":\"success\"},{\"name\":\"美图35.jpg\",\"url\":\"http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg\",\"uid\":\"1695893530665\",\"status\":\"success\"},{\"name\":\"美图28.jpg\",\"url\":\"http://127.0.0.1:886/2023-09-28/1707280259789041664-美图28.jpg\",\"uid\":\"1695893737396\",\"status\":\"success\"}]', '商城首页轮播图', 1, 1, '2023-10-14 21:10:33', 1, '2023-10-14 21:10:33', 0);
INSERT INTO `t_sys_config` VALUES (72, 1, 'lbs_qq_key', '666', '腾讯地图key', 1, 1, '2023-10-14 21:10:33', 1, '2023-10-14 21:10:33', 0);

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `parent_id` int(11) NOT NULL DEFAULT 0 COMMENT '父ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `leader_user_id` int(11) NULL DEFAULT NULL COMMENT '负责人id',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(0:停用 1:正常)',
  `province_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `area_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区名称',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------
INSERT INTO `t_sys_dept` VALUES (1, 1, 0, '自由科技公司', 1, 1, '', '', 1, '四川省', '成都市', '武侯区', '天府五街', '我的第一个公司', 1, '2023-10-09 19:16:37', 1, '2023-11-20 17:41:51', 0);
INSERT INTO `t_sys_dept` VALUES (9, 1, 0, '测试部门', 100, NULL, '', '', 1, '四川省', '成都市', '武侯区', '天府五街', '测试使用', 1, '2023-10-17 18:46:48', 1, '2023-10-18 15:10:43', 0);
INSERT INTO `t_sys_dept` VALUES (11, 1, 1, '开发部', 100, 1, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 1, '2023-10-20 14:50:42', 1, '2023-10-20 14:50:42', 0);
INSERT INTO `t_sys_dept` VALUES (12, 1, 1, '财务部', 100, 1, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 1, '2023-10-20 14:50:57', 1, '2023-10-20 14:50:57', 0);
INSERT INTO `t_sys_dept` VALUES (14, 3, 0, '测试租户', 1, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46', 0);

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
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 489 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础模块-数据字典' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES (181, 6, 'oauth_type', 'gitee', '1', 1, 1, '', 1, '2020-12-06 13:16:39', 0, '2023-02-13 11:17:41', 0);
INSERT INTO `t_sys_dict` VALUES (182, 6, 'oauth_type', 'github', '2', 1, 2, NULL, 1, '2020-12-06 13:16:54', 0, '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict` VALUES (183, 6, 'oauth_type', 'qq', '3', 1, 3, NULL, 1, '2020-12-06 13:17:03', 0, '2021-08-28 00:03:02', 0);
INSERT INTO `t_sys_dict` VALUES (193, 3, 'element_icon', 'Plus', 'Plus', 1, 20, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (194, 3, 'element_icon', 'Minus', 'Minus', 1, 21, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (195, 3, 'element_icon', 'CirclePlus', 'CirclePlus', 1, 22, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (196, 3, 'element_icon', 'Search', 'Search', 1, 23, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (197, 3, 'element_icon', 'Female', 'Female', 1, 24, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (198, 3, 'element_icon', 'Male', 'Male', 1, 25, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (199, 3, 'element_icon', 'Aim', 'Aim', 1, 26, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (200, 3, 'element_icon', 'House', 'House', 1, 27, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (201, 3, 'element_icon', 'FullScreen', 'FullScreen', 1, 28, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (202, 3, 'element_icon', 'Loading', 'Loading', 1, 29, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (203, 3, 'element_icon', 'Link', 'Link', 1, 30, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (204, 3, 'element_icon', 'Service', 'Service', 1, 31, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (205, 3, 'element_icon', 'Pointer', 'Pointer', 1, 32, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (206, 3, 'element_icon', 'Star', 'Star', 1, 33, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (207, 3, 'element_icon', 'Notification', 'Notification', 1, 34, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (208, 3, 'element_icon', 'Connection', 'Connection', 1, 35, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (209, 3, 'element_icon', 'ChatDotRound', 'ChatDotRound', 1, 36, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (210, 3, 'element_icon', 'Setting', 'Setting', 1, 37, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (211, 3, 'element_icon', 'Clock', 'Clock', 1, 38, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (212, 3, 'element_icon', 'Position', 'Position', 1, 39, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (213, 3, 'element_icon', 'Discount', 'Discount', 1, 40, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (214, 3, 'element_icon', 'Odometer', 'Odometer', 1, 41, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (215, 3, 'element_icon', 'ChatSquare', 'ChatSquare', 1, 42, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (216, 3, 'element_icon', 'ChatRound', 'ChatRound', 1, 43, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (217, 3, 'element_icon', 'ChatLineRound', 'ChatLineRound', 1, 44, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (218, 3, 'element_icon', 'ChatLineSquare', 'ChatLineSquare', 1, 45, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (219, 3, 'element_icon', 'ChatDotSquare', 'ChatDotSquare', 1, 46, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (220, 3, 'element_icon', 'View', 'View', 1, 47, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (221, 3, 'element_icon', 'Hide', 'Hide', 1, 48, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (222, 3, 'element_icon', 'Unlock', 'Unlock', 1, 49, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (223, 3, 'element_icon', 'Lock', 'Lock', 1, 50, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (224, 3, 'element_icon', 'RefreshRight', 'RefreshRight', 1, 51, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (225, 3, 'element_icon', 'RefreshLeft', 'RefreshLeft', 1, 52, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (226, 3, 'element_icon', 'Refresh', 'Refresh', 1, 53, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (227, 3, 'element_icon', 'Bell', 'Bell', 1, 54, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (228, 3, 'element_icon', 'MuteNotification', 'MuteNotification', 1, 55, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (229, 3, 'element_icon', 'User', 'User', 1, 56, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (230, 3, 'element_icon', 'Check', 'Check', 1, 57, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (231, 3, 'element_icon', 'CircleCheck', 'CircleCheck', 1, 58, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (232, 3, 'element_icon', 'Warning', 'Warning', 1, 59, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (233, 3, 'element_icon', 'CircleClose', 'CircleClose', 1, 60, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (234, 3, 'element_icon', 'Close', 'Close', 1, 61, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (235, 3, 'element_icon', 'PieChart', 'PieChart', 1, 62, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (236, 3, 'element_icon', 'More', 'More', 1, 63, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (237, 3, 'element_icon', 'Compass', 'Compass', 1, 64, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (238, 3, 'element_icon', 'Filter', 'Filter', 1, 65, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (239, 3, 'element_icon', 'Switch', 'Switch', 1, 66, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (240, 3, 'element_icon', 'Select', 'Select', 1, 67, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (241, 3, 'element_icon', 'SemiSelect', 'SemiSelect', 1, 68, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (242, 3, 'element_icon', 'CloseBold', 'CloseBold', 1, 69, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (243, 3, 'element_icon', 'EditPen', 'EditPen', 1, 70, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (244, 3, 'element_icon', 'Edit', 'Edit', 1, 71, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (245, 3, 'element_icon', 'Message', 'Message', 1, 72, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (246, 3, 'element_icon', 'MessageBox', 'MessageBox', 1, 73, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (247, 3, 'element_icon', 'TurnOff', 'TurnOff', 1, 74, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (248, 3, 'element_icon', 'Finished', 'Finished', 1, 75, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (249, 3, 'element_icon', 'Delete', 'Delete', 1, 76, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (250, 3, 'element_icon', 'Crop', 'Crop', 1, 77, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (251, 3, 'element_icon', 'SwitchButton', 'SwitchButton', 1, 78, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (252, 3, 'element_icon', 'Operation', 'Operation', 1, 79, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (253, 3, 'element_icon', 'Open', 'Open', 1, 80, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (254, 3, 'element_icon', 'Remove', 'Remove', 1, 81, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (255, 3, 'element_icon', 'ZoomOut', 'ZoomOut', 1, 82, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (256, 3, 'element_icon', 'ZoomIn', 'ZoomIn', 1, 83, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (257, 3, 'element_icon', 'InfoFilled', 'InfoFilled', 1, 84, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (258, 3, 'element_icon', 'CircleCheckFilled', 'CircleCheckFilled', 1, 85, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (259, 3, 'element_icon', 'SuccessFilled', 'SuccessFilled', 1, 86, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (260, 3, 'element_icon', 'WarningFilled', 'WarningFilled', 1, 87, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (261, 3, 'element_icon', 'CircleCloseFilled', 'CircleCloseFilled', 1, 88, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (262, 3, 'element_icon', 'QuestionFilled', 'QuestionFilled', 1, 89, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (263, 3, 'element_icon', 'WarnTriangleFilled', 'WarnTriangleFilled', 1, 90, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (264, 3, 'element_icon', 'UserFilled', 'UserFilled', 1, 91, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (265, 3, 'element_icon', 'MoreFilled', 'MoreFilled', 1, 92, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (266, 3, 'element_icon', 'Tools', 'Tools', 1, 93, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (267, 3, 'element_icon', 'HomeFilled', 'HomeFilled', 1, 94, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (268, 3, 'element_icon', 'Menu', 'Menu', 1, 95, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (269, 3, 'element_icon', 'UploadFilled', 'UploadFilled', 1, 96, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (270, 3, 'element_icon', 'Avatar', 'Avatar', 1, 97, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (271, 3, 'element_icon', 'HelpFilled', 'HelpFilled', 1, 98, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (272, 3, 'element_icon', 'Share', 'Share', 1, 99, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (273, 3, 'element_icon', 'StarFilled', 'StarFilled', 1, 100, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (274, 3, 'element_icon', 'Comment', 'Comment', 1, 101, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (275, 3, 'element_icon', 'Histogram', 'Histogram', 1, 102, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (276, 3, 'element_icon', 'Grid', 'Grid', 1, 103, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (277, 3, 'element_icon', 'Promotion', 'Promotion', 1, 104, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (278, 3, 'element_icon', 'DeleteFilled', 'DeleteFilled', 1, 105, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (279, 3, 'element_icon', 'RemoveFilled', 'RemoveFilled', 1, 106, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (280, 3, 'element_icon', 'CirclePlusFilled', 'CirclePlusFilled', 1, 107, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (281, 3, 'element_icon', 'ArrowLeft', 'ArrowLeft', 1, 108, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (282, 3, 'element_icon', 'ArrowUp', 'ArrowUp', 1, 109, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (283, 3, 'element_icon', 'ArrowRight', 'ArrowRight', 1, 110, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (284, 3, 'element_icon', 'ArrowDown', 'ArrowDown', 1, 111, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (285, 3, 'element_icon', 'ArrowLeftBold', 'ArrowLeftBold', 1, 112, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (286, 3, 'element_icon', 'ArrowUpBold', 'ArrowUpBold', 1, 113, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (287, 3, 'element_icon', 'ArrowRightBold', 'ArrowRightBold', 1, 114, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (288, 3, 'element_icon', 'ArrowDownBold', 'ArrowDownBold', 1, 115, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (289, 3, 'element_icon', 'DArrowRight', 'DArrowRight', 1, 116, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (290, 3, 'element_icon', 'DArrowLeft', 'DArrowLeft', 1, 117, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (291, 3, 'element_icon', 'Download', 'Download', 1, 118, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (292, 3, 'element_icon', 'Upload', 'Upload', 1, 119, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (293, 3, 'element_icon', 'Top', 'Top', 1, 120, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (294, 3, 'element_icon', 'Bottom', 'Bottom', 1, 121, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (295, 3, 'element_icon', 'Back', 'Back', 1, 122, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (296, 3, 'element_icon', 'Right', 'Right', 1, 123, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (297, 3, 'element_icon', 'TopRight', 'TopRight', 1, 124, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (298, 3, 'element_icon', 'TopLeft', 'TopLeft', 1, 125, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (299, 3, 'element_icon', 'BottomRight', 'BottomRight', 1, 126, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (300, 3, 'element_icon', 'BottomLeft', 'BottomLeft', 1, 127, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (301, 3, 'element_icon', 'Sort', 'Sort', 1, 128, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (302, 3, 'element_icon', 'SortUp', 'SortUp', 1, 129, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (303, 3, 'element_icon', 'SortDown', 'SortDown', 1, 130, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (304, 3, 'element_icon', 'Rank', 'Rank', 1, 131, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (305, 3, 'element_icon', 'CaretLeft', 'CaretLeft', 1, 132, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (306, 3, 'element_icon', 'CaretTop', 'CaretTop', 1, 133, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (307, 3, 'element_icon', 'CaretRight', 'CaretRight', 1, 134, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (308, 3, 'element_icon', 'CaretBottom', 'CaretBottom', 1, 135, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (309, 3, 'element_icon', 'DCaret', 'DCaret', 1, 136, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (310, 3, 'element_icon', 'Expand', 'Expand', 1, 137, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (311, 3, 'element_icon', 'Fold', 'Fold', 1, 138, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (312, 3, 'element_icon', 'DocumentAdd', 'DocumentAdd', 1, 139, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (313, 3, 'element_icon', 'Document', 'Document', 1, 140, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (314, 3, 'element_icon', 'Notebook', 'Notebook', 1, 141, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (315, 3, 'element_icon', 'Tickets', 'Tickets', 1, 142, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (316, 3, 'element_icon', 'Memo', 'Memo', 1, 143, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (317, 3, 'element_icon', 'Collection', 'Collection', 1, 144, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (318, 3, 'element_icon', 'Postcard', 'Postcard', 1, 145, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (319, 3, 'element_icon', 'ScaleToOriginal', 'ScaleToOriginal', 1, 146, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (320, 3, 'element_icon', 'SetUp', 'SetUp', 1, 147, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (321, 3, 'element_icon', 'DocumentDelete', 'DocumentDelete', 1, 148, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (322, 3, 'element_icon', 'DocumentChecked', 'DocumentChecked', 1, 149, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (323, 3, 'element_icon', 'DataBoard', 'DataBoard', 1, 150, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (324, 3, 'element_icon', 'DataAnalysis', 'DataAnalysis', 1, 151, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (325, 3, 'element_icon', 'CopyDocument', 'CopyDocument', 1, 152, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (326, 3, 'element_icon', 'FolderChecked', 'FolderChecked', 1, 153, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (327, 3, 'element_icon', 'Files', 'Files', 1, 154, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (328, 3, 'element_icon', 'Folder', 'Folder', 1, 155, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (329, 3, 'element_icon', 'FolderDelete', 'FolderDelete', 1, 156, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (330, 3, 'element_icon', 'FolderRemove', 'FolderRemove', 1, 157, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (331, 3, 'element_icon', 'FolderOpened', 'FolderOpened', 1, 158, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (332, 3, 'element_icon', 'DocumentCopy', 'DocumentCopy', 1, 159, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (333, 3, 'element_icon', 'DocumentRemove', 'DocumentRemove', 1, 160, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (334, 3, 'element_icon', 'FolderAdd', 'FolderAdd', 1, 161, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (335, 3, 'element_icon', 'FirstAidKit', 'FirstAidKit', 1, 162, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (336, 3, 'element_icon', 'Reading', 'Reading', 1, 163, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (337, 3, 'element_icon', 'DataLine', 'DataLine', 1, 164, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (338, 3, 'element_icon', 'Management', 'Management', 1, 165, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (339, 3, 'element_icon', 'Checked', 'Checked', 1, 166, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (340, 3, 'element_icon', 'Ticket', 'Ticket', 1, 167, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (341, 3, 'element_icon', 'Failed', 'Failed', 1, 168, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (342, 3, 'element_icon', 'TrendCharts', 'TrendCharts', 1, 169, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (343, 3, 'element_icon', 'List', 'List', 1, 170, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (344, 3, 'element_icon', 'Microphone', 'Microphone', 1, 171, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (345, 3, 'element_icon', 'Mute', 'Mute', 1, 172, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (346, 3, 'element_icon', 'Mic', 'Mic', 1, 173, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (347, 3, 'element_icon', 'VideoPause', 'VideoPause', 1, 174, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (348, 3, 'element_icon', 'VideoCamera', 'VideoCamera', 1, 175, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (349, 3, 'element_icon', 'VideoPlay', 'VideoPlay', 1, 176, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (350, 3, 'element_icon', 'Headset', 'Headset', 1, 177, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (351, 3, 'element_icon', 'Monitor', 'Monitor', 1, 178, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (352, 3, 'element_icon', 'Film', 'Film', 1, 179, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (353, 3, 'element_icon', 'Camera', 'Camera', 1, 180, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (354, 3, 'element_icon', 'Picture', 'Picture', 1, 181, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (355, 3, 'element_icon', 'PictureRounded', 'PictureRounded', 1, 182, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (356, 3, 'element_icon', 'Iphone', 'Iphone', 1, 183, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (357, 3, 'element_icon', 'Cellphone', 'Cellphone', 1, 184, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (358, 3, 'element_icon', 'VideoCameraFilled', 'VideoCameraFilled', 1, 185, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (359, 3, 'element_icon', 'PictureFilled', 'PictureFilled', 1, 186, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (360, 3, 'element_icon', 'Platform', 'Platform', 1, 187, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (361, 3, 'element_icon', 'CameraFilled', 'CameraFilled', 1, 188, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (362, 3, 'element_icon', 'BellFilled', 'BellFilled', 1, 189, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (363, 3, 'element_icon', 'Location', 'Location', 1, 190, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (364, 3, 'element_icon', 'LocationInformation', 'LocationInformation', 1, 191, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (365, 3, 'element_icon', 'DeleteLocation', 'DeleteLocation', 1, 192, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (366, 3, 'element_icon', 'Coordinate', 'Coordinate', 1, 193, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (367, 3, 'element_icon', 'Bicycle', 'Bicycle', 1, 194, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (368, 3, 'element_icon', 'OfficeBuilding', 'OfficeBuilding', 1, 195, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (369, 3, 'element_icon', 'School', 'School', 1, 196, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (370, 3, 'element_icon', 'Guide', 'Guide', 1, 197, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (371, 3, 'element_icon', 'AddLocation', 'AddLocation', 1, 198, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (372, 3, 'element_icon', 'MapLocation', 'MapLocation', 1, 199, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (373, 3, 'element_icon', 'Place', 'Place', 1, 200, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (374, 3, 'element_icon', 'LocationFilled', 'LocationFilled', 1, 201, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (375, 3, 'element_icon', 'Van', 'Van', 1, 202, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (376, 3, 'element_icon', 'Watermelon', 'Watermelon', 1, 203, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (377, 3, 'element_icon', 'Pear', 'Pear', 1, 204, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (378, 3, 'element_icon', 'NoSmoking', 'NoSmoking', 1, 205, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (379, 3, 'element_icon', 'Smoking', 'Smoking', 1, 206, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (380, 3, 'element_icon', 'Mug', 'Mug', 1, 207, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (381, 3, 'element_icon', 'GobletSquareFull', 'GobletSquareFull', 1, 208, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (382, 3, 'element_icon', 'GobletFull', 'GobletFull', 1, 209, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (383, 3, 'element_icon', 'KnifeFork', 'KnifeFork', 1, 210, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (384, 3, 'element_icon', 'Sugar', 'Sugar', 1, 211, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (385, 3, 'element_icon', 'Bowl', 'Bowl', 1, 212, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (386, 3, 'element_icon', 'MilkTea', 'MilkTea', 1, 213, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (387, 3, 'element_icon', 'Lollipop', 'Lollipop', 1, 214, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (388, 3, 'element_icon', 'Coffee', 'Coffee', 1, 215, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (389, 3, 'element_icon', 'Chicken', 'Chicken', 1, 216, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (390, 3, 'element_icon', 'Dish', 'Dish', 1, 217, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (391, 3, 'element_icon', 'IceTea', 'IceTea', 1, 218, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (392, 3, 'element_icon', 'ColdDrink', 'ColdDrink', 1, 219, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (393, 3, 'element_icon', 'CoffeeCup', 'CoffeeCup', 1, 220, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (394, 3, 'element_icon', 'DishDot', 'DishDot', 1, 221, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (395, 3, 'element_icon', 'IceDrink', 'IceDrink', 1, 222, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (396, 3, 'element_icon', 'IceCream', 'IceCream', 1, 223, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (397, 3, 'element_icon', 'Dessert', 'Dessert', 1, 224, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (398, 3, 'element_icon', 'IceCreamSquare', 'IceCreamSquare', 1, 225, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (399, 3, 'element_icon', 'ForkSpoon', 'ForkSpoon', 1, 226, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (400, 3, 'element_icon', 'IceCreamRound', 'IceCreamRound', 1, 227, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (401, 3, 'element_icon', 'Food', 'Food', 1, 228, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (402, 3, 'element_icon', 'HotWater', 'HotWater', 1, 229, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (403, 3, 'element_icon', 'Grape', 'Grape', 1, 230, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (404, 3, 'element_icon', 'Fries', 'Fries', 1, 231, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (405, 3, 'element_icon', 'Apple', 'Apple', 1, 232, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (406, 3, 'element_icon', 'Burger', 'Burger', 1, 233, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (407, 3, 'element_icon', 'Goblet', 'Goblet', 1, 234, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (408, 3, 'element_icon', 'GobletSquare', 'GobletSquare', 1, 235, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (409, 3, 'element_icon', 'Orange', 'Orange', 1, 236, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (410, 3, 'element_icon', 'Cherry', 'Cherry', 1, 237, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (411, 3, 'element_icon', 'Printer', 'Printer', 1, 238, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (412, 3, 'element_icon', 'Calendar', 'Calendar', 1, 239, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (413, 3, 'element_icon', 'CreditCard', 'CreditCard', 1, 240, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (414, 3, 'element_icon', 'Box', 'Box', 1, 241, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (415, 3, 'element_icon', 'Money', 'Money', 1, 242, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (416, 3, 'element_icon', 'Refrigerator', 'Refrigerator', 1, 243, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (417, 3, 'element_icon', 'Cpu', 'Cpu', 1, 244, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (418, 3, 'element_icon', 'Football', 'Football', 1, 245, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (419, 3, 'element_icon', 'Brush', 'Brush', 1, 246, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (420, 3, 'element_icon', 'Suitcase', 'Suitcase', 1, 247, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (421, 3, 'element_icon', 'SuitcaseLine', 'SuitcaseLine', 1, 248, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (422, 3, 'element_icon', 'Umbrella', 'Umbrella', 1, 249, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (423, 3, 'element_icon', 'AlarmClock', 'AlarmClock', 1, 250, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (424, 3, 'element_icon', 'Medal', 'Medal', 1, 251, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (425, 3, 'element_icon', 'GoldMedal', 'GoldMedal', 1, 252, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (426, 3, 'element_icon', 'Present', 'Present', 1, 253, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (427, 3, 'element_icon', 'Mouse', 'Mouse', 1, 254, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (428, 3, 'element_icon', 'Watch', 'Watch', 1, 255, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (429, 3, 'element_icon', 'QuartzWatch', 'QuartzWatch', 1, 256, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (430, 3, 'element_icon', 'Magnet', 'Magnet', 1, 257, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (431, 3, 'element_icon', 'Help', 'Help', 1, 258, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (432, 3, 'element_icon', 'Soccer', 'Soccer', 1, 259, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (433, 3, 'element_icon', 'ToiletPaper', 'ToiletPaper', 1, 260, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (434, 3, 'element_icon', 'ReadingLamp', 'ReadingLamp', 1, 261, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (435, 3, 'element_icon', 'Paperclip', 'Paperclip', 1, 262, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (436, 3, 'element_icon', 'MagicStick', 'MagicStick', 1, 263, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (437, 3, 'element_icon', 'Basketball', 'Basketball', 1, 264, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (438, 3, 'element_icon', 'Baseball', 'Baseball', 1, 265, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (439, 3, 'element_icon', 'Coin', 'Coin', 1, 266, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (440, 3, 'element_icon', 'Goods', 'Goods', 1, 267, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (441, 3, 'element_icon', 'Sell', 'Sell', 1, 268, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (442, 3, 'element_icon', 'SoldOut', 'SoldOut', 1, 269, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (443, 3, 'element_icon', 'Key', 'Key', 1, 270, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (444, 3, 'element_icon', 'ShoppingCart', 'ShoppingCart', 1, 271, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (445, 3, 'element_icon', 'ShoppingCartFull', 'ShoppingCartFull', 1, 272, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (446, 3, 'element_icon', 'ShoppingTrolley', 'ShoppingTrolley', 1, 273, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (447, 3, 'element_icon', 'Phone', 'Phone', 1, 274, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (448, 3, 'element_icon', 'Scissor', 'Scissor', 1, 275, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (449, 3, 'element_icon', 'Handbag', 'Handbag', 1, 276, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (450, 3, 'element_icon', 'ShoppingBag', 'ShoppingBag', 1, 277, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (451, 3, 'element_icon', 'Trophy', 'Trophy', 1, 278, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (452, 3, 'element_icon', 'TrophyBase', 'TrophyBase', 1, 279, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (453, 3, 'element_icon', 'Stopwatch', 'Stopwatch', 1, 280, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (454, 3, 'element_icon', 'Timer', 'Timer', 1, 281, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (455, 3, 'element_icon', 'CollectionTag', 'CollectionTag', 1, 282, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (456, 3, 'element_icon', 'TakeawayBox', 'TakeawayBox', 1, 283, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (457, 3, 'element_icon', 'PriceTag', 'PriceTag', 1, 284, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (458, 3, 'element_icon', 'Wallet', 'Wallet', 1, 285, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (459, 3, 'element_icon', 'Opportunity', 'Opportunity', 1, 286, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (460, 3, 'element_icon', 'PhoneFilled', 'PhoneFilled', 1, 287, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (461, 3, 'element_icon', 'WalletFilled', 'WalletFilled', 1, 288, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (462, 3, 'element_icon', 'GoodsFilled', 'GoodsFilled', 1, 289, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (463, 3, 'element_icon', 'Flag', 'Flag', 1, 290, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (464, 3, 'element_icon', 'BrushFilled', 'BrushFilled', 1, 291, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (465, 3, 'element_icon', 'Briefcase', 'Briefcase', 1, 292, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (466, 3, 'element_icon', 'Stamp', 'Stamp', 1, 293, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (467, 3, 'element_icon', 'Sunrise', 'Sunrise', 1, 294, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (468, 3, 'element_icon', 'Sunny', 'Sunny', 1, 295, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (469, 3, 'element_icon', 'Ship', 'Ship', 1, 296, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (470, 3, 'element_icon', 'MostlyCloudy', 'MostlyCloudy', 1, 297, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (471, 3, 'element_icon', 'PartlyCloudy', 'PartlyCloudy', 1, 298, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (472, 3, 'element_icon', 'Sunset', 'Sunset', 1, 299, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (473, 3, 'element_icon', 'Drizzling', 'Drizzling', 1, 300, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (474, 3, 'element_icon', 'Pouring', 'Pouring', 1, 301, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (475, 3, 'element_icon', 'Cloudy', 'Cloudy', 1, 302, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (476, 3, 'element_icon', 'Moon', 'Moon', 1, 303, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (477, 3, 'element_icon', 'MoonNight', 'MoonNight', 1, 304, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (478, 3, 'element_icon', 'Lightning', 'Lightning', 1, 305, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (479, 3, 'element_icon', 'ChromeFilled', 'ChromeFilled', 1, 306, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (480, 3, 'element_icon', 'Eleme', 'Eleme', 1, 307, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (481, 3, 'element_icon', 'ElemeFilled', 'ElemeFilled', 1, 308, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (482, 3, 'element_icon', 'ElementPlus', 'ElementPlus', 1, 309, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (483, 3, 'element_icon', 'Shop', 'Shop', 1, 310, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (484, 3, 'element_icon', 'SwitchFilled', 'SwitchFilled', 1, 311, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (485, 3, 'element_icon', 'WindPower', 'WindPower', 1, 312, NULL, 0, '2023-08-28 19:49:22', 0, '2023-08-28 19:49:22', 0);
INSERT INTO `t_sys_dict` VALUES (486, 8, 'mall_spu_service', '7天无理由退货', '7天无理由退货', 1, 1, '符合相应条件时，消费者可申请7天无理由退货', 1, '2023-10-07 15:18:58', 1, '2023-10-07 15:24:25', 0);
INSERT INTO `t_sys_dict` VALUES (487, 8, 'mall_spu_service', '运费险', '运费险', 1, 2, '退货或换货，均可享受一定金额的运费补贴', 1, '2023-10-07 15:25:18', 1, '2023-10-07 15:25:18', 0);
INSERT INTO `t_sys_dict` VALUES (488, 10, 'mall_spu_explain', '商品退换', '商品退换', 1, 1, '如商品有破损或错漏请第一时间拍照并联系客服，如需要退换货，请在收货日起7日内提出，否则不予退换货', 1, '2023-10-07 15:27:53', 1, '2023-10-07 15:27:53', 0);

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
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基础模块-数据字典类型' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_dict_type
-- ----------------------------
INSERT INTO `t_sys_dict_type` VALUES (3, 'element_icon', 'Element-Icon图标', 1, 0, 0, 1, '2020-08-30 02:52:36', 1, '2020-08-30 02:52:38', 0);
INSERT INTO `t_sys_dict_type` VALUES (6, 'oauth_type', '第三方帐号授权类型', 1, 2, 0, 1, '2020-12-06 13:11:27', 1, '2020-12-06 13:21:45', 0);
INSERT INTO `t_sys_dict_type` VALUES (8, 'mall_spu_service', '商城-商品-服务', 1, 3, 0, 1, '2023-10-07 15:05:53', 1, '2023-10-07 15:13:35', 0);
INSERT INTO `t_sys_dict_type` VALUES (10, 'mall_spu_explain', '商城-商品-说明', 1, 4, 0, 1, '2023-10-07 15:13:49', 1, '2023-10-07 15:13:49', 0);

-- ----------------------------
-- Table structure for t_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_file`;
CREATE TABLE `t_sys_file`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件地址',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
  `md5` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件唯一标识（md5）',
  `size` bigint(20) NOT NULL COMMENT '文件大小（byte）',
  `env` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务器环境',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-文件上传记录' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_file
-- ----------------------------
INSERT INTO `t_sys_file` VALUES (21, 1, '美图13.png', '/2023-09-28/1707240834895855616-美图13.png', 'image/png', 'a846e6a993854b34f46e909b2e3971d7', 124751, 'dev', '2023-09-28 11:48:29', '2023-10-23 09:29:05', 1, 1, 0);
INSERT INTO `t_sys_file` VALUES (22, 1, '美图14.jpg', '/2023-09-28/1707276494134525952-美图14.jpg', 'image/jpeg', '7612baeeb4e1f22b28c92400b1a727a6', 805694, 'dev', '2023-09-28 14:10:11', '2023-10-23 09:29:05', 1, 1, 0);
INSERT INTO `t_sys_file` VALUES (23, 1, '美图35.jpg', '/2023-09-28/1707276699923857408-美图35.jpg', 'image/jpeg', 'bafca25d99f49411f09c76985654f78c', 115888, 'dev', '2023-09-28 14:11:00', '2023-10-23 09:29:05', 1, 1, 0);
INSERT INTO `t_sys_file` VALUES (24, 1, '美图28.jpg', '/2023-09-28/1707280259789041664-美图28.jpg', 'image/jpeg', '4336d41fcd5a3076e54ed6f9d39e82d5', 874939, 'dev', '2023-09-28 14:25:08', '2023-10-23 09:29:05', 1, 1, 0);
INSERT INTO `t_sys_file` VALUES (25, 1, '小知识.jpg', '/2023-10-08/1710975930417258496-小知识.jpg', 'image/jpeg', '5d57274ba11b7a30b2716d2149a27fc9', 11619, 'dev', '2023-10-08 19:10:25', '2023-10-23 09:29:05', 1, 1, 0);

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '类型(1:操作日志 2:登录日志)',
  `api_method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `api_method_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法名',
  `api_header` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求头参数',
  `operation_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人名称',
  `request_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求IP',
  `request_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `request_http_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `env` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器环境',
  `time` int(11) NULL DEFAULT 1 COMMENT '执行时间(单位：毫秒)',
  `status` tinyint(2) NULL DEFAULT 1 COMMENT '状态(0:异常 1:正常)',
  `response_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应结果',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 587 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-操作日志' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) NULL DEFAULT 0 COMMENT '父ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `btn_perm` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `component` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件',
  `redirect` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '重定向路径',
  `is_show` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示(1:显示 0:隐藏)',
  `is_show_breadcrumb` tinyint(1) NULL DEFAULT 1 COMMENT '是否显示面包屑(1:显示 0:隐藏)',
  `type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '类型(1:菜单 2:按钮)',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-菜单表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES (1, 0, '首页', 'Odometer', '/', '', 1, 'dashboard', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-10-21 14:23:50', 0);
INSERT INTO `t_sys_menu` VALUES (2, 0, '租户管理', 'Star', '/tenant', NULL, 2, '', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-10-10 09:58:37', 0);
INSERT INTO `t_sys_menu` VALUES (3, 0, '系统管理', 'Setting', '/system', NULL, 4, NULL, NULL, 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2020-08-30 03:17:44', 0);
INSERT INTO `t_sys_menu` VALUES (4, 3, '个人中心', 'Avatar', 'personal-center', '', 2, 'system/personal-center', '', 0, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-10-17 16:46:47', 0);
INSERT INTO `t_sys_menu` VALUES (5, 3, '用户管理', 'User', 'user-sys', NULL, 11, 'system/user-sys', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:52:01', 0);
INSERT INTO `t_sys_menu` VALUES (6, 3, '角色管理', 'StarFilled', 'role', NULL, 12, 'system/role', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:53:51', 0);
INSERT INTO `t_sys_menu` VALUES (7, 3, '角色权限', '', 'role-edit', NULL, 13, 'system/role-edit', '', 0, 0, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-29 17:01:32', 0);
INSERT INTO `t_sys_menu` VALUES (9, 3, '菜单管理', 'Menu', 'menu', NULL, 14, 'system/menu', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 18:57:19', 0);
INSERT INTO `t_sys_menu` VALUES (10, 3, '数据字典', 'Grid', 'dict', NULL, 19, 'system/dict', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:54:10', 0);
INSERT INTO `t_sys_menu` VALUES (11, 3, '系统属性', 'List', 'property', NULL, 20, 'system/property', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:54:31', 0);
INSERT INTO `t_sys_menu` VALUES (12, 0, '微信公众号管理', 'GoldMedal', '/wx/mp', NULL, 5, NULL, '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 18:56:41', 0);
INSERT INTO `t_sys_menu` VALUES (13, 12, '公众号列表', 'List', 'account', NULL, 1, 'wxmp/account', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:54:43', 0);
INSERT INTO `t_sys_menu` VALUES (14, 12, '自定义菜单', 'Menu', 'custom-menu', NULL, 2, 'wxmp/custom-menu', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:55:07', 0);
INSERT INTO `t_sys_menu` VALUES (15, 12, '模板消息', 'Message', 'template-msg', NULL, 3, 'wxmp/template-msg', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:55:17', 0);
INSERT INTO `t_sys_menu` VALUES (16, 12, '公众号用户', 'User', 'mp-user', NULL, 4, 'wxmp/mp-user', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:55:25', 0);
INSERT INTO `t_sys_menu` VALUES (17, 12, '素材管理', 'Management', 'material', NULL, 5, 'wxmp/material', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:56:12', 0);
INSERT INTO `t_sys_menu` VALUES (18, 12, '消息自动回复', 'MessageBox', 'msg-auto-reply', NULL, 6, 'wxmp/msg-auto-reply', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:55:57', 0);
INSERT INTO `t_sys_menu` VALUES (19, 0, '商城管理', 'ColdDrink', '/mall', NULL, 6, NULL, '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 20:01:14', 0);
INSERT INTO `t_sys_menu` VALUES (20, 19, '分类', 'Guide', 'category', NULL, 2, 'mall/category', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 19:56:48', 0);
INSERT INTO `t_sys_menu` VALUES (21, 19, '商品', 'Lollipop', 'product', NULL, 4, 'mall/product', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-08-28 20:01:32', 0);
INSERT INTO `t_sys_menu` VALUES (22, 19, '规格', 'KnifeFork', 'attr', NULL, 3, 'mall/attr', '', 1, 1, 1, 1, '2023-05-25 11:14:14', 1, '2023-08-28 19:57:54', 0);
INSERT INTO `t_sys_menu` VALUES (23, 19, '订单', 'List', 'order', NULL, 5, 'mall/order', '', 1, 1, 1, 1, '2023-05-25 11:14:14', 1, '2023-08-28 19:57:38', 0);
INSERT INTO `t_sys_menu` VALUES (24, 0, '小程序用户', 'User', '/mini-user', NULL, 6, 'system/user-mini', '', 1, 1, 1, 1, '2023-05-25 11:14:14', 1, '2023-08-28 19:57:25', 0);
INSERT INTO `t_sys_menu` VALUES (25, 19, '编辑商品', '', 'product-edit', NULL, 1, 'mall/product-edit', '', 0, 0, 1, 1, '2023-08-28 11:51:07', 1, '2023-08-29 15:37:14', 0);
INSERT INTO `t_sys_menu` VALUES (27, 19, '店铺', 'Shop', 'shop', NULL, 1, 'mall/shop', '', 1, 1, 1, 1, '2023-09-13 11:15:18', 1, '2023-09-13 11:15:38', 0);
INSERT INTO `t_sys_menu` VALUES (28, 19, '店铺-编辑', '', 'shop-edit', NULL, 1, 'mall/shop-edit', '', 0, 0, 1, 1, '2023-09-13 17:55:23', 1, '2023-09-14 09:21:46', 0);
INSERT INTO `t_sys_menu` VALUES (29, 3, '系统配置', 'SetUp', 'config', NULL, 21, 'system/config', NULL, 1, 1, 1, 1, '2023-09-15 16:44:56', 1, '2023-09-15 16:44:56', 0);
INSERT INTO `t_sys_menu` VALUES (30, 3, '文件上传记录', 'Files', 'file', NULL, 22, 'system/file', '', 1, 1, 1, 1, '2023-09-28 11:50:14', 1, '2023-09-28 11:50:22', 0);
INSERT INTO `t_sys_menu` VALUES (31, 2, '租户套餐', 'Management', 'tenant-package', NULL, 2, 'system/tenant-package', '', 1, 1, 1, 1, '2023-10-08 10:58:32', 1, '2023-10-08 17:04:43', 0);
INSERT INTO `t_sys_menu` VALUES (32, 2, '租户列表', 'List', 'tenant', NULL, 1, 'system/tenant', '', 1, 1, 1, 1, '2023-10-08 10:58:32', 1, '2023-10-15 14:05:10', 0);
INSERT INTO `t_sys_menu` VALUES (33, 3, '企业管理', 'List', 'dept', NULL, 16, 'system/dept', '', 1, 1, 1, 1, '2023-10-08 10:58:32', 1, '2023-10-08 17:04:22', 0);
INSERT INTO `t_sys_menu` VALUES (34, 3, '岗位管理', 'List', 'post', NULL, 18, 'system/post', '', 1, 1, 1, 1, '2023-10-08 10:58:32', 1, '2023-10-08 17:04:22', 0);
INSERT INTO `t_sys_menu` VALUES (40, 3, '商户管理', 'Star', 'merchant', NULL, 30, 'system/merchant', '', 1, 1, 1, 1, '2023-10-08 10:58:32', 1, '2023-10-13 14:57:08', 0);
INSERT INTO `t_sys_menu` VALUES (44, 5, '查看用户分页列表', '', 'GET:/web/api/system/user/page', 'sys:user:page', 1, '', '', 0, 0, 2, 1, '2023-10-17 15:59:25', 1, '2023-10-17 16:41:43', 0);
INSERT INTO `t_sys_menu` VALUES (45, 5, '编辑用户', '', 'PUT:/web/api/system/user', 'sys:user:edit', 2, '', '', 0, 0, 2, 1, '2023-10-17 16:01:22', 1, '2023-10-17 16:46:50', 0);
INSERT INTO `t_sys_menu` VALUES (46, 5, '新增用户', '', 'POST:/web/api/system/user', 'sys:user:add', 3, '', '', 0, 0, 2, 1, '2023-10-17 16:01:49', 1, '2023-10-17 16:41:53', 0);
INSERT INTO `t_sys_menu` VALUES (47, 5, '删除用户', '', 'DELETE:/web/api/system/user', 'sys:user:delete', 4, '', '', 0, 0, 2, 1, '2023-10-17 16:02:09', 1, '2023-10-17 16:41:56', 0);
INSERT INTO `t_sys_menu` VALUES (48, 3, '数据权限', 'Star', 'scope-data', NULL, 15, 'system/scope-data', '', 1, 1, 1, 1, '2023-10-08 10:58:32', 1, '2023-10-13 14:57:08', 0);
INSERT INTO `t_sys_menu` VALUES (50, 3, '操作日志', 'ChatLineSquare', 'log', '', 100, 'system/log', '', 1, 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-10-19 17:00:29', 0);
INSERT INTO `t_sys_menu` VALUES (51, 0, '工作流', 'MagicStick', '/workflow', '', 8, '', '', 1, 1, 1, 1, '2023-12-01 17:39:13', 1, '2023-12-01 17:46:51', 0);
INSERT INTO `t_sys_menu` VALUES (52, 51, '表单管理', 'Flag', 'form', NULL, 1, 'workflow/form', NULL, 1, 1, 1, 1, '2023-12-01 17:43:57', 1, '2023-12-01 17:43:57', 0);
INSERT INTO `t_sys_menu` VALUES (53, 3, '代码生成器', 'SwitchFilled', 'code-generate', NULL, 100, 'system/code-generate', NULL, 1, 1, 1, 1, '2023-12-05 15:10:33', 1, '2023-12-05 15:10:33', 0);

-- ----------------------------
-- Table structure for t_sys_merchant
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_merchant`;
CREATE TABLE `t_sys_merchant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `admin_user_id` int(11) NULL DEFAULT NULL COMMENT '管理员用户id',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(0:停用 1:正常)',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '商户类型（1：共享小程序  2：独立小程序）',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `user_num` int(11) NULL DEFAULT NULL COMMENT '最大用户数',
  `job_num` int(11) NULL DEFAULT NULL COMMENT '最大职位发布数',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AppID',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AppSecret',
  `app_status` int(11) NULL DEFAULT NULL COMMENT '小程序状态',
  `app_index_title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '小程序首页标题',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-商户管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_merchant
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_post
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_post`;
CREATE TABLE `t_sys_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(0:停用 1:正常)',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-岗位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_post
-- ----------------------------
INSERT INTO `t_sys_post` VALUES (1, 1, NULL, '总经理', 'zjl', 1, 1, '', 1, '2023-10-09 18:53:08', 1, '2023-10-10 10:46:50');
INSERT INTO `t_sys_post` VALUES (2, 1, NULL, '董事长', 'ceo', 1, 2, '', 1, '2023-10-09 18:55:07', 1, '2023-10-09 18:55:15');
INSERT INTO `t_sys_post` VALUES (3, 1, NULL, '项目经理', 'se', 1, 3, NULL, 1, '2023-10-09 18:55:30', 1, '2023-10-09 18:55:30');
INSERT INTO `t_sys_post` VALUES (4, 1, NULL, '人力资源', 'hr', 1, 4, NULL, 1, '2023-10-09 18:55:39', 1, '2023-10-09 18:55:39');
INSERT INTO `t_sys_post` VALUES (5, 1, NULL, '普通员工', 'user', 1, 5, NULL, 1, '2023-10-09 18:55:50', 1, '2023-10-09 18:55:50');
INSERT INTO `t_sys_post` VALUES (6, 1, 2, 'Java开发工程师', 'java', 1, 100, '', 1, '2023-10-10 10:47:14', 1, '2023-10-17 18:22:21');
INSERT INTO `t_sys_post` VALUES (7, 1, 2, '前端开发工程师', 'web', 1, 100, NULL, 1, '2023-10-10 10:47:30', 1, '2023-10-10 10:47:30');

-- ----------------------------
-- Table structure for t_sys_province_city_area
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_province_city_area`;
CREATE TABLE `t_sys_province_city_area`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父ID',
  `parent_code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父代码',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '行政区代码',
  `type` tinyint(2) NOT NULL COMMENT '类型（1:省 2:市 3:区）',
  `is_shop` tinyint(1) NULL DEFAULT 0 COMMENT '是否存在门店（1:是 0:否）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13211 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-省市区' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_province_city_area
-- ----------------------------
INSERT INTO `t_sys_province_city_area` VALUES (1, 0, '110000', '北京市', '110000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2, 1, '110000', '东城区', '110101', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3, 1, '110000', '西城区', '110102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (4, 1, '110000', '朝阳区', '110105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (5, 1, '110000', '丰台区', '110106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (6, 1, '110000', '石景山区', '110107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (7, 1, '110000', '海淀区', '110108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (8, 1, '110000', '门头沟区', '110109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (9, 1, '110000', '房山区', '110111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (10, 1, '110000', '通州区', '110112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (11, 1, '110000', '顺义区', '110113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (12, 1, '110000', '昌平区', '110114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (13, 1, '110000', '大兴区', '110115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (14, 1, '110000', '怀柔区', '110116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (15, 1, '110000', '平谷区', '110117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (16, 1, '110000', '密云区', '110118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (17, 1, '110000', '延庆区', '110119', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (18, 0, '120000', '天津市', '120000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (19, 18, '120000', '和平区', '120101', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (20, 18, '120000', '河东区', '120102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (21, 18, '120000', '河西区', '120103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (22, 18, '120000', '南开区', '120104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (23, 18, '120000', '河北区', '120105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (24, 18, '120000', '红桥区', '120106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (25, 18, '120000', '东丽区', '120110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (26, 18, '120000', '西青区', '120111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (27, 18, '120000', '津南区', '120112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (28, 18, '120000', '北辰区', '120113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (29, 18, '120000', '武清区', '120114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (30, 18, '120000', '宝坻区', '120115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (31, 18, '120000', '滨海新区', '120116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (32, 18, '120000', '宁河区', '120117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (33, 18, '120000', '静海区', '120118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (34, 18, '120000', '蓟州区', '120119', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (35, 0, '130000', '河北省', '130000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (36, 35, '130000', '石家庄市', '130100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (37, 36, '130100', '长安区', '130102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (38, 36, '130100', '桥西区', '130104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (39, 36, '130100', '新华区', '130105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (40, 36, '130100', '井陉矿区', '130107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (41, 36, '130100', '裕华区', '130108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (42, 36, '130100', '藁城区', '130109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (43, 36, '130100', '鹿泉区', '130110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (44, 36, '130100', '栾城区', '130111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (45, 36, '130100', '井陉县', '130121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (46, 36, '130100', '正定县', '130123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (47, 36, '130100', '行唐县', '130125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (48, 36, '130100', '灵寿县', '130126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (49, 36, '130100', '高邑县', '130127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (50, 36, '130100', '深泽县', '130128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (51, 36, '130100', '赞皇县', '130129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (52, 36, '130100', '无极县', '130130', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (53, 36, '130100', '平山县', '130131', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (54, 36, '130100', '元氏县', '130132', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (55, 36, '130100', '赵县', '130133', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (56, 36, '130100', '辛集市', '130181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (57, 36, '130100', '晋州市', '130183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (58, 36, '130100', '新乐市', '130184', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (59, 35, '130000', '唐山市', '130200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (60, 59, '130200', '路南区', '130202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (61, 59, '130200', '路北区', '130203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (62, 59, '130200', '古冶区', '130204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (63, 59, '130200', '开平区', '130205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (64, 59, '130200', '丰南区', '130207', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (65, 59, '130200', '丰润区', '130208', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (66, 59, '130200', '曹妃甸区', '130209', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (67, 59, '130200', '滦南县', '130224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (68, 59, '130200', '乐亭县', '130225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (69, 59, '130200', '迁西县', '130227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (70, 59, '130200', '玉田县', '130229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (71, 59, '130200', '遵化市', '130281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (72, 59, '130200', '迁安市', '130283', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (73, 59, '130200', '滦州市', '130284', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (74, 35, '130000', '秦皇岛市', '130300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (75, 74, '130300', '海港区', '130302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (76, 74, '130300', '山海关区', '130303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (77, 74, '130300', '北戴河区', '130304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (78, 74, '130300', '抚宁区', '130306', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (79, 74, '130300', '青龙满族自治县', '130321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (80, 74, '130300', '昌黎县', '130322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (81, 74, '130300', '卢龙县', '130324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (82, 35, '130000', '邯郸市', '130400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (83, 82, '130400', '邯山区', '130402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (84, 82, '130400', '丛台区', '130403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (85, 82, '130400', '复兴区', '130404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (86, 82, '130400', '峰峰矿区', '130406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (87, 82, '130400', '肥乡区', '130407', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (88, 82, '130400', '永年区', '130408', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (89, 82, '130400', '临漳县', '130423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (90, 82, '130400', '成安县', '130424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (91, 82, '130400', '大名县', '130425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (92, 82, '130400', '涉县', '130426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (93, 82, '130400', '磁县', '130427', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (94, 82, '130400', '邱县', '130430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (95, 82, '130400', '鸡泽县', '130431', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (96, 82, '130400', '广平县', '130432', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (97, 82, '130400', '馆陶县', '130433', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (98, 82, '130400', '魏县', '130434', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (99, 82, '130400', '曲周县', '130435', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (100, 82, '130400', '武安市', '130481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (101, 35, '130000', '邢台市', '130500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (102, 101, '130500', '襄都区', '130502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (103, 101, '130500', '信都区', '130503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (104, 101, '130500', '任泽区', '130505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (105, 101, '130500', '南和区', '130506', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (106, 101, '130500', '临城县', '130522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (107, 101, '130500', '内丘县', '130523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (108, 101, '130500', '柏乡县', '130524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (109, 101, '130500', '隆尧县', '130525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (110, 101, '130500', '宁晋县', '130528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (111, 101, '130500', '巨鹿县', '130529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (112, 101, '130500', '新河县', '130530', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (113, 101, '130500', '广宗县', '130531', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (114, 101, '130500', '平乡县', '130532', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (115, 101, '130500', '威县', '130533', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (116, 101, '130500', '清河县', '130534', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (117, 101, '130500', '临西县', '130535', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (118, 101, '130500', '南宫市', '130581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (119, 101, '130500', '沙河市', '130582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (120, 35, '130000', '保定市', '130600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (121, 120, '130600', '竞秀区', '130602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (122, 120, '130600', '莲池区', '130606', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (123, 120, '130600', '满城区', '130607', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (124, 120, '130600', '清苑区', '130608', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (125, 120, '130600', '徐水区', '130609', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (126, 120, '130600', '涞水县', '130623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (127, 120, '130600', '阜平县', '130624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (128, 120, '130600', '定兴县', '130626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (129, 120, '130600', '唐县', '130627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (130, 120, '130600', '高阳县', '130628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (131, 120, '130600', '容城县', '130629', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (132, 120, '130600', '涞源县', '130630', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (133, 120, '130600', '望都县', '130631', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (134, 120, '130600', '安新县', '130632', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (135, 120, '130600', '易县', '130633', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (136, 120, '130600', '曲阳县', '130634', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (137, 120, '130600', '蠡县', '130635', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (138, 120, '130600', '顺平县', '130636', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (139, 120, '130600', '博野县', '130637', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (140, 120, '130600', '雄县', '130638', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (141, 120, '130600', '涿州市', '130681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (142, 120, '130600', '定州市', '130682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (143, 120, '130600', '安国市', '130683', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (144, 120, '130600', '高碑店市', '130684', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (145, 35, '130000', '张家口市', '130700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (146, 145, '130700', '桥东区', '130702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (147, 145, '130700', '桥西区', '130703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (148, 145, '130700', '宣化区', '130705', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (149, 145, '130700', '下花园区', '130706', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (150, 145, '130700', '万全区', '130708', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (151, 145, '130700', '崇礼区', '130709', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (152, 145, '130700', '张北县', '130722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (153, 145, '130700', '康保县', '130723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (154, 145, '130700', '沽源县', '130724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (155, 145, '130700', '尚义县', '130725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (156, 145, '130700', '蔚县', '130726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (157, 145, '130700', '阳原县', '130727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (158, 145, '130700', '怀安县', '130728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (159, 145, '130700', '怀来县', '130730', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (160, 145, '130700', '涿鹿县', '130731', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (161, 145, '130700', '赤城县', '130732', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (162, 35, '130000', '承德市', '130800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (163, 162, '130800', '双桥区', '130802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (164, 162, '130800', '双滦区', '130803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (165, 162, '130800', '鹰手营子矿区', '130804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (166, 162, '130800', '承德县', '130821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (167, 162, '130800', '兴隆县', '130822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (168, 162, '130800', '滦平县', '130824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (169, 162, '130800', '隆化县', '130825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (170, 162, '130800', '丰宁满族自治县', '130826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (171, 162, '130800', '宽城满族自治县', '130827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (172, 162, '130800', '围场满族蒙古族自治县', '130828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (173, 162, '130800', '平泉市', '130881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (174, 35, '130000', '沧州市', '130900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (175, 174, '130900', '新华区', '130902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (176, 174, '130900', '运河区', '130903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (177, 174, '130900', '沧县', '130921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (178, 174, '130900', '青县', '130922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (179, 174, '130900', '东光县', '130923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (180, 174, '130900', '海兴县', '130924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (181, 174, '130900', '盐山县', '130925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (182, 174, '130900', '肃宁县', '130926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (183, 174, '130900', '南皮县', '130927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (184, 174, '130900', '吴桥县', '130928', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (185, 174, '130900', '献县', '130929', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (186, 174, '130900', '孟村回族自治县', '130930', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (187, 174, '130900', '泊头市', '130981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (188, 174, '130900', '任丘市', '130982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (189, 174, '130900', '黄骅市', '130983', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (190, 174, '130900', '河间市', '130984', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (191, 35, '130000', '廊坊市', '131000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (192, 191, '131000', '安次区', '131002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (193, 191, '131000', '广阳区', '131003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (194, 191, '131000', '固安县', '131022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (195, 191, '131000', '永清县', '131023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (196, 191, '131000', '香河县', '131024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (197, 191, '131000', '大城县', '131025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (198, 191, '131000', '文安县', '131026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (199, 191, '131000', '大厂回族自治县', '131028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (200, 191, '131000', '霸州市', '131081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (201, 191, '131000', '三河市', '131082', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (202, 35, '130000', '衡水市', '131100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (203, 202, '131100', '桃城区', '131102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (204, 202, '131100', '冀州区', '131103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (205, 202, '131100', '枣强县', '131121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (206, 202, '131100', '武邑县', '131122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (207, 202, '131100', '武强县', '131123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (208, 202, '131100', '饶阳县', '131124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (209, 202, '131100', '安平县', '131125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (210, 202, '131100', '故城县', '131126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (211, 202, '131100', '景县', '131127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (212, 202, '131100', '阜城县', '131128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (213, 202, '131100', '深州市', '131182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (214, 0, '140000', '山西省', '140000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (215, 214, '140000', '太原市', '140100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (216, 215, '140100', '小店区', '140105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (217, 215, '140100', '迎泽区', '140106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (218, 215, '140100', '杏花岭区', '140107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (219, 215, '140100', '尖草坪区', '140108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (220, 215, '140100', '万柏林区', '140109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (221, 215, '140100', '晋源区', '140110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (222, 215, '140100', '清徐县', '140121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (223, 215, '140100', '阳曲县', '140122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (224, 215, '140100', '娄烦县', '140123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (225, 215, '140100', '古交市', '140181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (226, 214, '140000', '大同市', '140200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (227, 226, '140200', '新荣区', '140212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (228, 226, '140200', '平城区', '140213', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (229, 226, '140200', '云冈区', '140214', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (230, 226, '140200', '云州区', '140215', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (231, 226, '140200', '阳高县', '140221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (232, 226, '140200', '天镇县', '140222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (233, 226, '140200', '广灵县', '140223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (234, 226, '140200', '灵丘县', '140224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (235, 226, '140200', '浑源县', '140225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (236, 226, '140200', '左云县', '140226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (237, 214, '140000', '阳泉市', '140300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (238, 237, '140300', '城区', '140302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (239, 237, '140300', '矿区', '140303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (240, 237, '140300', '郊区', '140311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (241, 237, '140300', '平定县', '140321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (242, 237, '140300', '盂县', '140322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (243, 214, '140000', '长治市', '140400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (244, 243, '140400', '潞州区', '140403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (245, 243, '140400', '上党区', '140404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (246, 243, '140400', '屯留区', '140405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (247, 243, '140400', '潞城区', '140406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (248, 243, '140400', '襄垣县', '140423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (249, 243, '140400', '平顺县', '140425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (250, 243, '140400', '黎城县', '140426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (251, 243, '140400', '壶关县', '140427', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (252, 243, '140400', '长子县', '140428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (253, 243, '140400', '武乡县', '140429', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (254, 243, '140400', '沁县', '140430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (255, 243, '140400', '沁源县', '140431', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (256, 214, '140000', '晋城市', '140500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (257, 256, '140500', '城区', '140502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (258, 256, '140500', '沁水县', '140521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (259, 256, '140500', '阳城县', '140522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (260, 256, '140500', '陵川县', '140524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (261, 256, '140500', '泽州县', '140525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (262, 256, '140500', '高平市', '140581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (263, 214, '140000', '朔州市', '140600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (264, 263, '140600', '朔城区', '140602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (265, 263, '140600', '平鲁区', '140603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (266, 263, '140600', '山阴县', '140621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (267, 263, '140600', '应县', '140622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (268, 263, '140600', '右玉县', '140623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (269, 263, '140600', '怀仁市', '140681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (270, 214, '140000', '晋中市', '140700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (271, 270, '140700', '榆次区', '140702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (272, 270, '140700', '太谷区', '140703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (273, 270, '140700', '榆社县', '140721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (274, 270, '140700', '左权县', '140722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (275, 270, '140700', '和顺县', '140723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (276, 270, '140700', '昔阳县', '140724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (277, 270, '140700', '寿阳县', '140725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (278, 270, '140700', '祁县', '140727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (279, 270, '140700', '平遥县', '140728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (280, 270, '140700', '灵石县', '140729', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (281, 270, '140700', '介休市', '140781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (282, 214, '140000', '运城市', '140800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (283, 282, '140800', '盐湖区', '140802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (284, 282, '140800', '临猗县', '140821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (285, 282, '140800', '万荣县', '140822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (286, 282, '140800', '闻喜县', '140823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (287, 282, '140800', '稷山县', '140824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (288, 282, '140800', '新绛县', '140825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (289, 282, '140800', '绛县', '140826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (290, 282, '140800', '垣曲县', '140827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (291, 282, '140800', '夏县', '140828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (292, 282, '140800', '平陆县', '140829', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (293, 282, '140800', '芮城县', '140830', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (294, 282, '140800', '永济市', '140881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (295, 282, '140800', '河津市', '140882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (296, 214, '140000', '忻州市', '140900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (297, 296, '140900', '忻府区', '140902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (298, 296, '140900', '定襄县', '140921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (299, 296, '140900', '五台县', '140922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (300, 296, '140900', '代县', '140923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (301, 296, '140900', '繁峙县', '140924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (302, 296, '140900', '宁武县', '140925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (303, 296, '140900', '静乐县', '140926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (304, 296, '140900', '神池县', '140927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (305, 296, '140900', '五寨县', '140928', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (306, 296, '140900', '岢岚县', '140929', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (307, 296, '140900', '河曲县', '140930', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (308, 296, '140900', '保德县', '140931', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (309, 296, '140900', '偏关县', '140932', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (310, 296, '140900', '原平市', '140981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (311, 214, '140000', '临汾市', '141000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (312, 311, '141000', '尧都区', '141002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (313, 311, '141000', '曲沃县', '141021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (314, 311, '141000', '翼城县', '141022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (315, 311, '141000', '襄汾县', '141023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (316, 311, '141000', '洪洞县', '141024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (317, 311, '141000', '古县', '141025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (318, 311, '141000', '安泽县', '141026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (319, 311, '141000', '浮山县', '141027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (320, 311, '141000', '吉县', '141028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (321, 311, '141000', '乡宁县', '141029', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (322, 311, '141000', '大宁县', '141030', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (323, 311, '141000', '隰县', '141031', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (324, 311, '141000', '永和县', '141032', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (325, 311, '141000', '蒲县', '141033', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (326, 311, '141000', '汾西县', '141034', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (327, 311, '141000', '侯马市', '141081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (328, 311, '141000', '霍州市', '141082', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (329, 214, '140000', '吕梁市', '141100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (330, 329, '141100', '离石区', '141102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (331, 329, '141100', '文水县', '141121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (332, 329, '141100', '交城县', '141122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (333, 329, '141100', '兴县', '141123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (334, 329, '141100', '临县', '141124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (335, 329, '141100', '柳林县', '141125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (336, 329, '141100', '石楼县', '141126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (337, 329, '141100', '岚县', '141127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (338, 329, '141100', '方山县', '141128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (339, 329, '141100', '中阳县', '141129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (340, 329, '141100', '交口县', '141130', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (341, 329, '141100', '孝义市', '141181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (342, 329, '141100', '汾阳市', '141182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (343, 0, '150000', '内蒙古自治区', '150000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (344, 343, '150000', '呼和浩特市', '150100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (345, 344, '150100', '新城区', '150102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (346, 344, '150100', '回民区', '150103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (347, 344, '150100', '玉泉区', '150104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (348, 344, '150100', '赛罕区', '150105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (349, 344, '150100', '土默特左旗', '150121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (350, 344, '150100', '托克托县', '150122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (351, 344, '150100', '和林格尔县', '150123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (352, 344, '150100', '清水河县', '150124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (353, 344, '150100', '武川县', '150125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (354, 343, '150000', '包头市', '150200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (355, 354, '150200', '东河区', '150202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (356, 354, '150200', '昆都仑区', '150203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (357, 354, '150200', '青山区', '150204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (358, 354, '150200', '石拐区', '150205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (359, 354, '150200', '白云鄂博矿区', '150206', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (360, 354, '150200', '九原区', '150207', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (361, 354, '150200', '土默特右旗', '150221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (362, 354, '150200', '固阳县', '150222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (363, 354, '150200', '达尔罕茂明安联合旗', '150223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (364, 343, '150000', '乌海市', '150300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (365, 364, '150300', '海勃湾区', '150302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (366, 364, '150300', '海南区', '150303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (367, 364, '150300', '乌达区', '150304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (368, 343, '150000', '赤峰市', '150400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (369, 368, '150400', '红山区', '150402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (370, 368, '150400', '元宝山区', '150403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (371, 368, '150400', '松山区', '150404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (372, 368, '150400', '阿鲁科尔沁旗', '150421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (373, 368, '150400', '巴林左旗', '150422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (374, 368, '150400', '巴林右旗', '150423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (375, 368, '150400', '林西县', '150424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (376, 368, '150400', '克什克腾旗', '150425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (377, 368, '150400', '翁牛特旗', '150426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (378, 368, '150400', '喀喇沁旗', '150428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (379, 368, '150400', '宁城县', '150429', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (380, 368, '150400', '敖汉旗', '150430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (381, 343, '150000', '通辽市', '150500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (382, 381, '150500', '科尔沁区', '150502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (383, 381, '150500', '科尔沁左翼中旗', '150521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (384, 381, '150500', '科尔沁左翼后旗', '150522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (385, 381, '150500', '开鲁县', '150523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (386, 381, '150500', '库伦旗', '150524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (387, 381, '150500', '奈曼旗', '150525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (388, 381, '150500', '扎鲁特旗', '150526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (389, 381, '150500', '霍林郭勒市', '150581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (390, 343, '150000', '鄂尔多斯市', '150600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (391, 390, '150600', '东胜区', '150602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (392, 390, '150600', '康巴什区', '150603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (393, 390, '150600', '达拉特旗', '150621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (394, 390, '150600', '准格尔旗', '150622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (395, 390, '150600', '鄂托克前旗', '150623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (396, 390, '150600', '鄂托克旗', '150624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (397, 390, '150600', '杭锦旗', '150625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (398, 390, '150600', '乌审旗', '150626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (399, 390, '150600', '伊金霍洛旗', '150627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (400, 343, '150000', '呼伦贝尔市', '150700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (401, 400, '150700', '海拉尔区', '150702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (402, 400, '150700', '扎赉诺尔区', '150703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (403, 400, '150700', '阿荣旗', '150721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (404, 400, '150700', '莫力达瓦达斡尔族自治旗', '150722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (405, 400, '150700', '鄂伦春自治旗', '150723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (406, 400, '150700', '鄂温克族自治旗', '150724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (407, 400, '150700', '陈巴尔虎旗', '150725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (408, 400, '150700', '新巴尔虎左旗', '150726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (409, 400, '150700', '新巴尔虎右旗', '150727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (410, 400, '150700', '满洲里市', '150781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (411, 400, '150700', '牙克石市', '150782', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (412, 400, '150700', '扎兰屯市', '150783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (413, 400, '150700', '额尔古纳市', '150784', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (414, 400, '150700', '根河市', '150785', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (415, 343, '150000', '巴彦淖尔市', '150800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (416, 415, '150800', '临河区', '150802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (417, 415, '150800', '五原县', '150821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (418, 415, '150800', '磴口县', '150822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (419, 415, '150800', '乌拉特前旗', '150823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (420, 415, '150800', '乌拉特中旗', '150824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (421, 415, '150800', '乌拉特后旗', '150825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (422, 415, '150800', '杭锦后旗', '150826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (423, 343, '150000', '乌兰察布市', '150900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (424, 423, '150900', '集宁区', '150902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (425, 423, '150900', '卓资县', '150921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (426, 423, '150900', '化德县', '150922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (427, 423, '150900', '商都县', '150923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (428, 423, '150900', '兴和县', '150924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (429, 423, '150900', '凉城县', '150925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (430, 423, '150900', '察哈尔右翼前旗', '150926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (431, 423, '150900', '察哈尔右翼中旗', '150927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (432, 423, '150900', '察哈尔右翼后旗', '150928', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (433, 423, '150900', '四子王旗', '150929', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (434, 423, '150900', '丰镇市', '150981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (435, 343, '150000', '兴安盟', '152200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (436, 435, '152200', '乌兰浩特市', '152201', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (437, 435, '152200', '阿尔山市', '152202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (438, 435, '152200', '科尔沁右翼前旗', '152221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (439, 435, '152200', '科尔沁右翼中旗', '152222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (440, 435, '152200', '扎赉特旗', '152223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (441, 435, '152200', '突泉县', '152224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (442, 343, '150000', '锡林郭勒盟', '152500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (443, 442, '152500', '二连浩特市', '152501', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (444, 442, '152500', '锡林浩特市', '152502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (445, 442, '152500', '阿巴嘎旗', '152522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (446, 442, '152500', '苏尼特左旗', '152523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (447, 442, '152500', '苏尼特右旗', '152524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (448, 442, '152500', '东乌珠穆沁旗', '152525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (449, 442, '152500', '西乌珠穆沁旗', '152526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (450, 442, '152500', '太仆寺旗', '152527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (451, 442, '152500', '镶黄旗', '152528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (452, 442, '152500', '正镶白旗', '152529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (453, 442, '152500', '正蓝旗', '152530', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (454, 442, '152500', '多伦县', '152531', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (455, 343, '150000', '阿拉善盟', '152900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (456, 455, '152900', '阿拉善左旗', '152921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (457, 455, '152900', '阿拉善右旗', '152922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (458, 455, '152900', '额济纳旗', '152923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (459, 0, '210000', '辽宁省', '210000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (460, 459, '210000', '沈阳市', '210100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (461, 460, '210100', '和平区', '210102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (462, 460, '210100', '沈河区', '210103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (463, 460, '210100', '大东区', '210104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (464, 460, '210100', '皇姑区', '210105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (465, 460, '210100', '铁西区', '210106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (466, 460, '210100', '苏家屯区', '210111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (467, 460, '210100', '浑南区', '210112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (468, 460, '210100', '沈北新区', '210113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (469, 460, '210100', '于洪区', '210114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (470, 460, '210100', '辽中区', '210115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (471, 460, '210100', '康平县', '210123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (472, 460, '210100', '法库县', '210124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (473, 460, '210100', '新民市', '210181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (474, 459, '210000', '大连市', '210200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (475, 474, '210200', '中山区', '210202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (476, 474, '210200', '西岗区', '210203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (477, 474, '210200', '沙河口区', '210204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (478, 474, '210200', '甘井子区', '210211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (479, 474, '210200', '旅顺口区', '210212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (480, 474, '210200', '金州区', '210213', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (481, 474, '210200', '普兰店区', '210214', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (482, 474, '210200', '长海县', '210224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (483, 474, '210200', '瓦房店市', '210281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (484, 474, '210200', '庄河市', '210283', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (485, 459, '210000', '鞍山市', '210300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (486, 485, '210300', '铁东区', '210302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (487, 485, '210300', '铁西区', '210303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (488, 485, '210300', '立山区', '210304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (489, 485, '210300', '千山区', '210311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (490, 485, '210300', '台安县', '210321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (491, 485, '210300', '岫岩满族自治县', '210323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (492, 485, '210300', '海城市', '210381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (493, 459, '210000', '抚顺市', '210400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (494, 493, '210400', '新抚区', '210402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (495, 493, '210400', '东洲区', '210403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (496, 493, '210400', '望花区', '210404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (497, 493, '210400', '顺城区', '210411', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (498, 493, '210400', '抚顺县', '210421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (499, 493, '210400', '新宾满族自治县', '210422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (500, 493, '210400', '清原满族自治县', '210423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (501, 459, '210000', '本溪市', '210500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (502, 501, '210500', '平山区', '210502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (503, 501, '210500', '溪湖区', '210503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (504, 501, '210500', '明山区', '210504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (505, 501, '210500', '南芬区', '210505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (506, 501, '210500', '本溪满族自治县', '210521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (507, 501, '210500', '桓仁满族自治县', '210522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (508, 459, '210000', '丹东市', '210600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (509, 508, '210600', '元宝区', '210602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (510, 508, '210600', '振兴区', '210603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (511, 508, '210600', '振安区', '210604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (512, 508, '210600', '宽甸满族自治县', '210624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (513, 508, '210600', '东港市', '210681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (514, 508, '210600', '凤城市', '210682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (515, 459, '210000', '锦州市', '210700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (516, 515, '210700', '古塔区', '210702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (517, 515, '210700', '凌河区', '210703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (518, 515, '210700', '太和区', '210711', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (519, 515, '210700', '黑山县', '210726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (520, 515, '210700', '义县', '210727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (521, 515, '210700', '凌海市', '210781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (522, 515, '210700', '北镇市', '210782', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (523, 459, '210000', '营口市', '210800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (524, 523, '210800', '站前区', '210802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (525, 523, '210800', '西市区', '210803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (526, 523, '210800', '鲅鱼圈区', '210804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (527, 523, '210800', '老边区', '210811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (528, 523, '210800', '盖州市', '210881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (529, 523, '210800', '大石桥市', '210882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (530, 459, '210000', '阜新市', '210900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (531, 530, '210900', '海州区', '210902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (532, 530, '210900', '新邱区', '210903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (533, 530, '210900', '太平区', '210904', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (534, 530, '210900', '清河门区', '210905', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (535, 530, '210900', '细河区', '210911', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (536, 530, '210900', '阜新蒙古族自治县', '210921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (537, 530, '210900', '彰武县', '210922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (538, 459, '210000', '辽阳市', '211000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (539, 538, '211000', '白塔区', '211002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (540, 538, '211000', '文圣区', '211003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (541, 538, '211000', '宏伟区', '211004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (542, 538, '211000', '弓长岭区', '211005', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (543, 538, '211000', '太子河区', '211011', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (544, 538, '211000', '辽阳县', '211021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (545, 538, '211000', '灯塔市', '211081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (546, 459, '210000', '盘锦市', '211100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (547, 546, '211100', '双台子区', '211102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (548, 546, '211100', '兴隆台区', '211103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (549, 546, '211100', '大洼区', '211104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (550, 546, '211100', '盘山县', '211122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (551, 459, '210000', '铁岭市', '211200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (552, 551, '211200', '银州区', '211202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (553, 551, '211200', '清河区', '211204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (554, 551, '211200', '铁岭县', '211221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (555, 551, '211200', '西丰县', '211223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (556, 551, '211200', '昌图县', '211224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (557, 551, '211200', '调兵山市', '211281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (558, 551, '211200', '开原市', '211282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (559, 459, '210000', '朝阳市', '211300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (560, 559, '211300', '双塔区', '211302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (561, 559, '211300', '龙城区', '211303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (562, 559, '211300', '朝阳县', '211321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (563, 559, '211300', '建平县', '211322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (564, 559, '211300', '喀喇沁左翼蒙古族自治县', '211324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (565, 559, '211300', '北票市', '211381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (566, 559, '211300', '凌源市', '211382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (567, 459, '210000', '葫芦岛市', '211400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (568, 567, '211400', '连山区', '211402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (569, 567, '211400', '龙港区', '211403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (570, 567, '211400', '南票区', '211404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (571, 567, '211400', '绥中县', '211421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (572, 567, '211400', '建昌县', '211422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (573, 567, '211400', '兴城市', '211481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (574, 0, '220000', '吉林省', '220000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (575, 574, '220000', '长春市', '220100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (576, 575, '220100', '南关区', '220102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (577, 575, '220100', '宽城区', '220103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (578, 575, '220100', '朝阳区', '220104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (579, 575, '220100', '二道区', '220105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (580, 575, '220100', '绿园区', '220106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (581, 575, '220100', '双阳区', '220112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (582, 575, '220100', '九台区', '220113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (583, 575, '220100', '农安县', '220122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (584, 575, '220100', '榆树市', '220182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (585, 575, '220100', '德惠市', '220183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (586, 575, '220100', '公主岭市', '220184', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (587, 574, '220000', '吉林市', '220200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (588, 587, '220200', '昌邑区', '220202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (589, 587, '220200', '龙潭区', '220203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (590, 587, '220200', '船营区', '220204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (591, 587, '220200', '丰满区', '220211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (592, 587, '220200', '永吉县', '220221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (593, 587, '220200', '蛟河市', '220281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (594, 587, '220200', '桦甸市', '220282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (595, 587, '220200', '舒兰市', '220283', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (596, 587, '220200', '磐石市', '220284', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (597, 574, '220000', '四平市', '220300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (598, 597, '220300', '铁西区', '220302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (599, 597, '220300', '铁东区', '220303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (600, 597, '220300', '梨树县', '220322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (601, 597, '220300', '伊通满族自治县', '220323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (602, 597, '220300', '双辽市', '220382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (603, 574, '220000', '辽源市', '220400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (604, 603, '220400', '龙山区', '220402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (605, 603, '220400', '西安区', '220403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (606, 603, '220400', '东丰县', '220421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (607, 603, '220400', '东辽县', '220422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (608, 574, '220000', '通化市', '220500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (609, 608, '220500', '东昌区', '220502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (610, 608, '220500', '二道江区', '220503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (611, 608, '220500', '通化县', '220521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (612, 608, '220500', '辉南县', '220523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (613, 608, '220500', '柳河县', '220524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (614, 608, '220500', '梅河口市', '220581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (615, 608, '220500', '集安市', '220582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (616, 574, '220000', '白山市', '220600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (617, 616, '220600', '浑江区', '220602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (618, 616, '220600', '江源区', '220605', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (619, 616, '220600', '抚松县', '220621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (620, 616, '220600', '靖宇县', '220622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (621, 616, '220600', '长白朝鲜族自治县', '220623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (622, 616, '220600', '临江市', '220681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (623, 574, '220000', '松原市', '220700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (624, 623, '220700', '宁江区', '220702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (625, 623, '220700', '前郭尔罗斯蒙古族自治县', '220721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (626, 623, '220700', '长岭县', '220722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (627, 623, '220700', '乾安县', '220723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (628, 623, '220700', '扶余市', '220781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (629, 574, '220000', '白城市', '220800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (630, 629, '220800', '洮北区', '220802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (631, 629, '220800', '镇赉县', '220821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (632, 629, '220800', '通榆县', '220822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (633, 629, '220800', '洮南市', '220881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (634, 629, '220800', '大安市', '220882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (635, 574, '220000', '延边朝鲜族自治州', '222400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (636, 635, '222400', '延吉市', '222401', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (637, 635, '222400', '图们市', '222402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (638, 635, '222400', '敦化市', '222403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (639, 635, '222400', '珲春市', '222404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (640, 635, '222400', '龙井市', '222405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (641, 635, '222400', '和龙市', '222406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (642, 635, '222400', '汪清县', '222424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (643, 635, '222400', '安图县', '222426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (644, 0, '230000', '黑龙江省', '230000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (645, 644, '230000', '哈尔滨市', '230100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (646, 645, '230100', '道里区', '230102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (647, 645, '230100', '南岗区', '230103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (648, 645, '230100', '道外区', '230104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (649, 645, '230100', '平房区', '230108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (650, 645, '230100', '松北区', '230109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (651, 645, '230100', '香坊区', '230110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (652, 645, '230100', '呼兰区', '230111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (653, 645, '230100', '阿城区', '230112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (654, 645, '230100', '双城区', '230113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (655, 645, '230100', '依兰县', '230123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (656, 645, '230100', '方正县', '230124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (657, 645, '230100', '宾县', '230125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (658, 645, '230100', '巴彦县', '230126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (659, 645, '230100', '木兰县', '230127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (660, 645, '230100', '通河县', '230128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (661, 645, '230100', '延寿县', '230129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (662, 645, '230100', '尚志市', '230183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (663, 645, '230100', '五常市', '230184', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (664, 644, '230000', '齐齐哈尔市', '230200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (665, 664, '230200', '龙沙区', '230202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (666, 664, '230200', '建华区', '230203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (667, 664, '230200', '铁锋区', '230204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (668, 664, '230200', '昂昂溪区', '230205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (669, 664, '230200', '富拉尔基区', '230206', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (670, 664, '230200', '碾子山区', '230207', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (671, 664, '230200', '梅里斯达斡尔族区', '230208', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (672, 664, '230200', '龙江县', '230221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (673, 664, '230200', '依安县', '230223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (674, 664, '230200', '泰来县', '230224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (675, 664, '230200', '甘南县', '230225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (676, 664, '230200', '富裕县', '230227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (677, 664, '230200', '克山县', '230229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (678, 664, '230200', '克东县', '230230', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (679, 664, '230200', '拜泉县', '230231', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (680, 664, '230200', '讷河市', '230281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (681, 644, '230000', '鸡西市', '230300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (682, 681, '230300', '鸡冠区', '230302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (683, 681, '230300', '恒山区', '230303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (684, 681, '230300', '滴道区', '230304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (685, 681, '230300', '梨树区', '230305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (686, 681, '230300', '城子河区', '230306', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (687, 681, '230300', '麻山区', '230307', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (688, 681, '230300', '鸡东县', '230321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (689, 681, '230300', '虎林市', '230381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (690, 681, '230300', '密山市', '230382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (691, 644, '230000', '鹤岗市', '230400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (692, 691, '230400', '向阳区', '230402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (693, 691, '230400', '工农区', '230403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (694, 691, '230400', '南山区', '230404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (695, 691, '230400', '兴安区', '230405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (696, 691, '230400', '东山区', '230406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (697, 691, '230400', '兴山区', '230407', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (698, 691, '230400', '萝北县', '230421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (699, 691, '230400', '绥滨县', '230422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (700, 644, '230000', '双鸭山市', '230500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (701, 700, '230500', '尖山区', '230502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (702, 700, '230500', '岭东区', '230503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (703, 700, '230500', '四方台区', '230505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (704, 700, '230500', '宝山区', '230506', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (705, 700, '230500', '集贤县', '230521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (706, 700, '230500', '友谊县', '230522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (707, 700, '230500', '宝清县', '230523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (708, 700, '230500', '饶河县', '230524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (709, 644, '230000', '大庆市', '230600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (710, 709, '230600', '萨尔图区', '230602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (711, 709, '230600', '龙凤区', '230603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (712, 709, '230600', '让胡路区', '230604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (713, 709, '230600', '红岗区', '230605', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (714, 709, '230600', '大同区', '230606', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (715, 709, '230600', '肇州县', '230621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (716, 709, '230600', '肇源县', '230622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (717, 709, '230600', '林甸县', '230623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (718, 709, '230600', '杜尔伯特蒙古族自治县', '230624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (719, 644, '230000', '伊春市', '230700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (720, 719, '230700', '伊美区', '230717', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (721, 719, '230700', '乌翠区', '230718', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (722, 719, '230700', '友好区', '230719', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (723, 719, '230700', '嘉荫县', '230722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (724, 719, '230700', '汤旺县', '230723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (725, 719, '230700', '丰林县', '230724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (726, 719, '230700', '大箐山县', '230725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (727, 719, '230700', '南岔县', '230726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (728, 719, '230700', '金林区', '230751', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (729, 719, '230700', '铁力市', '230781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (730, 644, '230000', '佳木斯市', '230800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (731, 730, '230800', '向阳区', '230803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (732, 730, '230800', '前进区', '230804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (733, 730, '230800', '东风区', '230805', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (734, 730, '230800', '郊区', '230811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (735, 730, '230800', '桦南县', '230822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (736, 730, '230800', '桦川县', '230826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (737, 730, '230800', '汤原县', '230828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (738, 730, '230800', '同江市', '230881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (739, 730, '230800', '富锦市', '230882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (740, 730, '230800', '抚远市', '230883', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (741, 644, '230000', '七台河市', '230900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (742, 741, '230900', '新兴区', '230902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (743, 741, '230900', '桃山区', '230903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (744, 741, '230900', '茄子河区', '230904', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (745, 741, '230900', '勃利县', '230921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (746, 644, '230000', '牡丹江市', '231000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (747, 746, '231000', '东安区', '231002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (748, 746, '231000', '阳明区', '231003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (749, 746, '231000', '爱民区', '231004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (750, 746, '231000', '西安区', '231005', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (751, 746, '231000', '林口县', '231025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (752, 746, '231000', '绥芬河市', '231081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (753, 746, '231000', '海林市', '231083', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (754, 746, '231000', '宁安市', '231084', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (755, 746, '231000', '穆棱市', '231085', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (756, 746, '231000', '东宁市', '231086', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (757, 644, '230000', '黑河市', '231100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (758, 757, '231100', '爱辉区', '231102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (759, 757, '231100', '逊克县', '231123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (760, 757, '231100', '孙吴县', '231124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (761, 757, '231100', '北安市', '231181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (762, 757, '231100', '五大连池市', '231182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (763, 757, '231100', '嫩江市', '231183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (764, 644, '230000', '绥化市', '231200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (765, 764, '231200', '北林区', '231202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (766, 764, '231200', '望奎县', '231221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (767, 764, '231200', '兰西县', '231222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (768, 764, '231200', '青冈县', '231223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (769, 764, '231200', '庆安县', '231224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (770, 764, '231200', '明水县', '231225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (771, 764, '231200', '绥棱县', '231226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (772, 764, '231200', '安达市', '231281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (773, 764, '231200', '肇东市', '231282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (774, 764, '231200', '海伦市', '231283', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (775, 644, '230000', '大兴安岭地区', '232700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (776, 775, '232700', '漠河市', '232701', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (777, 775, '232700', '呼玛县', '232721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (778, 775, '232700', '塔河县', '232722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (779, 0, '310000', '上海市', '310000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (780, 779, '310000', '黄浦区', '310101', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (781, 779, '310000', '徐汇区', '310104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (782, 779, '310000', '长宁区', '310105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (783, 779, '310000', '静安区', '310106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (784, 779, '310000', '普陀区', '310107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (785, 779, '310000', '虹口区', '310109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (786, 779, '310000', '杨浦区', '310110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (787, 779, '310000', '闵行区', '310112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (788, 779, '310000', '宝山区', '310113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (789, 779, '310000', '嘉定区', '310114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (790, 779, '310000', '浦东新区', '310115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (791, 779, '310000', '金山区', '310116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (792, 779, '310000', '松江区', '310117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (793, 779, '310000', '青浦区', '310118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (794, 779, '310000', '奉贤区', '310120', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (795, 779, '310000', '崇明区', '310151', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (796, 0, '320000', '江苏省', '320000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (797, 796, '320000', '南京市', '320100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (798, 797, '320100', '玄武区', '320102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (799, 797, '320100', '秦淮区', '320104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (800, 797, '320100', '建邺区', '320105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (801, 797, '320100', '鼓楼区', '320106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (802, 797, '320100', '浦口区', '320111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (803, 797, '320100', '栖霞区', '320113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (804, 797, '320100', '雨花台区', '320114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (805, 797, '320100', '江宁区', '320115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (806, 797, '320100', '六合区', '320116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (807, 797, '320100', '溧水区', '320117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (808, 797, '320100', '高淳区', '320118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (809, 796, '320000', '无锡市', '320200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (810, 809, '320200', '锡山区', '320205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (811, 809, '320200', '惠山区', '320206', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (812, 809, '320200', '滨湖区', '320211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (813, 809, '320200', '梁溪区', '320213', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (814, 809, '320200', '新吴区', '320214', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (815, 809, '320200', '江阴市', '320281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (816, 809, '320200', '宜兴市', '320282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (817, 796, '320000', '徐州市', '320300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (818, 817, '320300', '鼓楼区', '320302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (819, 817, '320300', '云龙区', '320303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (820, 817, '320300', '贾汪区', '320305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (821, 817, '320300', '泉山区', '320311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (822, 817, '320300', '铜山区', '320312', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (823, 817, '320300', '丰县', '320321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (824, 817, '320300', '沛县', '320322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (825, 817, '320300', '睢宁县', '320324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (826, 817, '320300', '新沂市', '320381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (827, 817, '320300', '邳州市', '320382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (828, 796, '320000', '常州市', '320400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (829, 828, '320400', '天宁区', '320402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (830, 828, '320400', '钟楼区', '320404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (831, 828, '320400', '新北区', '320411', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (832, 828, '320400', '武进区', '320412', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (833, 828, '320400', '金坛区', '320413', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (834, 828, '320400', '溧阳市', '320481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (835, 796, '320000', '苏州市', '320500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (836, 835, '320500', '虎丘区', '320505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (837, 835, '320500', '吴中区', '320506', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (838, 835, '320500', '相城区', '320507', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (839, 835, '320500', '姑苏区', '320508', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (840, 835, '320500', '吴江区', '320509', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (841, 835, '320500', '常熟市', '320581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (842, 835, '320500', '张家港市', '320582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (843, 835, '320500', '昆山市', '320583', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (844, 835, '320500', '太仓市', '320585', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (845, 796, '320000', '南通市', '320600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (846, 845, '320600', '通州区', '320612', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (847, 845, '320600', '崇川区', '320613', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (848, 845, '320600', '海门区', '320614', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (849, 845, '320600', '如东县', '320623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (850, 845, '320600', '启东市', '320681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (851, 845, '320600', '如皋市', '320682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (852, 845, '320600', '海安市', '320685', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (853, 796, '320000', '连云港市', '320700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (854, 853, '320700', '连云区', '320703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (855, 853, '320700', '海州区', '320706', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (856, 853, '320700', '赣榆区', '320707', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (857, 853, '320700', '东海县', '320722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (858, 853, '320700', '灌云县', '320723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (859, 853, '320700', '灌南县', '320724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (860, 796, '320000', '淮安市', '320800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (861, 860, '320800', '淮安区', '320803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (862, 860, '320800', '淮阴区', '320804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (863, 860, '320800', '清江浦区', '320812', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (864, 860, '320800', '洪泽区', '320813', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (865, 860, '320800', '涟水县', '320826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (866, 860, '320800', '盱眙县', '320830', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (867, 860, '320800', '金湖县', '320831', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (868, 796, '320000', '盐城市', '320900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (869, 868, '320900', '亭湖区', '320902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (870, 868, '320900', '盐都区', '320903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (871, 868, '320900', '大丰区', '320904', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (872, 868, '320900', '响水县', '320921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (873, 868, '320900', '滨海县', '320922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (874, 868, '320900', '阜宁县', '320923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (875, 868, '320900', '射阳县', '320924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (876, 868, '320900', '建湖县', '320925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (877, 868, '320900', '东台市', '320981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (878, 796, '320000', '扬州市', '321000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (879, 878, '321000', '广陵区', '321002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (880, 878, '321000', '邗江区', '321003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (881, 878, '321000', '江都区', '321012', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (882, 878, '321000', '宝应县', '321023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (883, 878, '321000', '仪征市', '321081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (884, 878, '321000', '高邮市', '321084', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (885, 796, '320000', '镇江市', '321100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (886, 885, '321100', '京口区', '321102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (887, 885, '321100', '润州区', '321111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (888, 885, '321100', '丹徒区', '321112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (889, 885, '321100', '丹阳市', '321181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (890, 885, '321100', '扬中市', '321182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (891, 885, '321100', '句容市', '321183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (892, 796, '320000', '泰州市', '321200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (893, 892, '321200', '海陵区', '321202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (894, 892, '321200', '高港区', '321203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (895, 892, '321200', '姜堰区', '321204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (896, 892, '321200', '兴化市', '321281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (897, 892, '321200', '靖江市', '321282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (898, 892, '321200', '泰兴市', '321283', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (899, 796, '320000', '宿迁市', '321300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (900, 899, '321300', '宿城区', '321302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (901, 899, '321300', '宿豫区', '321311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (902, 899, '321300', '沭阳县', '321322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (903, 899, '321300', '泗阳县', '321323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (904, 899, '321300', '泗洪县', '321324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (905, 0, '330000', '浙江省', '330000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (906, 905, '330000', '杭州市', '330100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (907, 906, '330100', '上城区', '330102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (908, 906, '330100', '拱墅区', '330105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (909, 906, '330100', '西湖区', '330106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (910, 906, '330100', '滨江区', '330108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (911, 906, '330100', '萧山区', '330109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (912, 906, '330100', '余杭区', '330110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (913, 906, '330100', '富阳区', '330111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (914, 906, '330100', '临安区', '330112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (915, 906, '330100', '临平区', '330113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (916, 906, '330100', '钱塘区', '330114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (917, 906, '330100', '桐庐县', '330122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (918, 906, '330100', '淳安县', '330127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (919, 906, '330100', '建德市', '330182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (920, 905, '330000', '宁波市', '330200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (921, 920, '330200', '海曙区', '330203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (922, 920, '330200', '江北区', '330205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (923, 920, '330200', '北仑区', '330206', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (924, 920, '330200', '镇海区', '330211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (925, 920, '330200', '鄞州区', '330212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (926, 920, '330200', '奉化区', '330213', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (927, 920, '330200', '象山县', '330225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (928, 920, '330200', '宁海县', '330226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (929, 920, '330200', '余姚市', '330281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (930, 920, '330200', '慈溪市', '330282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (931, 905, '330000', '温州市', '330300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (932, 931, '330300', '鹿城区', '330302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (933, 931, '330300', '龙湾区', '330303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (934, 931, '330300', '瓯海区', '330304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (935, 931, '330300', '洞头区', '330305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (936, 931, '330300', '永嘉县', '330324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (937, 931, '330300', '平阳县', '330326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (938, 931, '330300', '苍南县', '330327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (939, 931, '330300', '文成县', '330328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (940, 931, '330300', '泰顺县', '330329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (941, 931, '330300', '瑞安市', '330381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (942, 931, '330300', '乐清市', '330382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (943, 931, '330300', '龙港市', '330383', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (944, 905, '330000', '嘉兴市', '330400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (945, 944, '330400', '南湖区', '330402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (946, 944, '330400', '秀洲区', '330411', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (947, 944, '330400', '嘉善县', '330421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (948, 944, '330400', '海盐县', '330424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (949, 944, '330400', '海宁市', '330481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (950, 944, '330400', '平湖市', '330482', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (951, 944, '330400', '桐乡市', '330483', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (952, 905, '330000', '湖州市', '330500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (953, 952, '330500', '吴兴区', '330502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (954, 952, '330500', '南浔区', '330503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (955, 952, '330500', '德清县', '330521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (956, 952, '330500', '长兴县', '330522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (957, 952, '330500', '安吉县', '330523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (958, 905, '330000', '绍兴市', '330600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (959, 958, '330600', '越城区', '330602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (960, 958, '330600', '柯桥区', '330603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (961, 958, '330600', '上虞区', '330604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (962, 958, '330600', '新昌县', '330624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (963, 958, '330600', '诸暨市', '330681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (964, 958, '330600', '嵊州市', '330683', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (965, 905, '330000', '金华市', '330700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (966, 965, '330700', '婺城区', '330702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (967, 965, '330700', '金东区', '330703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (968, 965, '330700', '武义县', '330723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (969, 965, '330700', '浦江县', '330726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (970, 965, '330700', '磐安县', '330727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (971, 965, '330700', '兰溪市', '330781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (972, 965, '330700', '义乌市', '330782', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (973, 965, '330700', '东阳市', '330783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (974, 965, '330700', '永康市', '330784', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (975, 905, '330000', '衢州市', '330800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (976, 975, '330800', '柯城区', '330802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (977, 975, '330800', '衢江区', '330803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (978, 975, '330800', '常山县', '330822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (979, 975, '330800', '开化县', '330824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (980, 975, '330800', '龙游县', '330825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (981, 975, '330800', '江山市', '330881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (982, 905, '330000', '舟山市', '330900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (983, 982, '330900', '定海区', '330902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (984, 982, '330900', '普陀区', '330903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (985, 982, '330900', '岱山县', '330921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (986, 982, '330900', '嵊泗县', '330922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (987, 905, '330000', '台州市', '331000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (988, 987, '331000', '椒江区', '331002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (989, 987, '331000', '黄岩区', '331003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (990, 987, '331000', '路桥区', '331004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (991, 987, '331000', '三门县', '331022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (992, 987, '331000', '天台县', '331023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (993, 987, '331000', '仙居县', '331024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (994, 987, '331000', '温岭市', '331081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (995, 987, '331000', '临海市', '331082', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (996, 987, '331000', '玉环市', '331083', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (997, 905, '330000', '丽水市', '331100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (998, 997, '331100', '莲都区', '331102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (999, 997, '331100', '青田县', '331121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1000, 997, '331100', '缙云县', '331122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1001, 997, '331100', '遂昌县', '331123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1002, 997, '331100', '松阳县', '331124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1003, 997, '331100', '云和县', '331125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1004, 997, '331100', '庆元县', '331126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1005, 997, '331100', '景宁畲族自治县', '331127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1006, 997, '331100', '龙泉市', '331181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1007, 0, '340000', '安徽省', '340000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1008, 1007, '340000', '合肥市', '340100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1009, 1008, '340100', '瑶海区', '340102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1010, 1008, '340100', '庐阳区', '340103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1011, 1008, '340100', '蜀山区', '340104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1012, 1008, '340100', '包河区', '340111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1013, 1008, '340100', '长丰县', '340121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1014, 1008, '340100', '肥东县', '340122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1015, 1008, '340100', '肥西县', '340123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1016, 1008, '340100', '庐江县', '340124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1017, 1008, '340100', '巢湖市', '340181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1018, 1007, '340000', '芜湖市', '340200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1019, 1018, '340200', '镜湖区', '340202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1020, 1018, '340200', '鸠江区', '340207', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1021, 1018, '340200', '弋江区', '340209', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1022, 1018, '340200', '湾沚区', '340210', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1023, 1018, '340200', '繁昌区', '340212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1024, 1018, '340200', '南陵县', '340223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1025, 1018, '340200', '无为市', '340281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1026, 1007, '340000', '蚌埠市', '340300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1027, 1026, '340300', '龙子湖区', '340302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1028, 1026, '340300', '蚌山区', '340303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1029, 1026, '340300', '禹会区', '340304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1030, 1026, '340300', '淮上区', '340311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1031, 1026, '340300', '怀远县', '340321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1032, 1026, '340300', '五河县', '340322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1033, 1026, '340300', '固镇县', '340323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1034, 1007, '340000', '淮南市', '340400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1035, 1034, '340400', '大通区', '340402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1036, 1034, '340400', '田家庵区', '340403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1037, 1034, '340400', '谢家集区', '340404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1038, 1034, '340400', '八公山区', '340405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1039, 1034, '340400', '潘集区', '340406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1040, 1034, '340400', '凤台县', '340421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1041, 1034, '340400', '寿县', '340422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1042, 1007, '340000', '马鞍山市', '340500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1043, 1042, '340500', '花山区', '340503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1044, 1042, '340500', '雨山区', '340504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1045, 1042, '340500', '博望区', '340506', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1046, 1042, '340500', '当涂县', '340521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1047, 1042, '340500', '含山县', '340522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1048, 1042, '340500', '和县', '340523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1049, 1007, '340000', '淮北市', '340600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1050, 1049, '340600', '杜集区', '340602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1051, 1049, '340600', '相山区', '340603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1052, 1049, '340600', '烈山区', '340604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1053, 1049, '340600', '濉溪县', '340621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1054, 1007, '340000', '铜陵市', '340700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1055, 1054, '340700', '铜官区', '340705', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1056, 1054, '340700', '义安区', '340706', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1057, 1054, '340700', '郊区', '340711', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1058, 1054, '340700', '枞阳县', '340722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1059, 1007, '340000', '安庆市', '340800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1060, 1059, '340800', '迎江区', '340802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1061, 1059, '340800', '大观区', '340803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1062, 1059, '340800', '宜秀区', '340811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1063, 1059, '340800', '怀宁县', '340822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1064, 1059, '340800', '太湖县', '340825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1065, 1059, '340800', '宿松县', '340826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1066, 1059, '340800', '望江县', '340827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1067, 1059, '340800', '岳西县', '340828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1068, 1059, '340800', '桐城市', '340881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1069, 1059, '340800', '潜山市', '340882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1070, 1007, '340000', '黄山市', '341000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1071, 1070, '341000', '屯溪区', '341002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1072, 1070, '341000', '黄山区', '341003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1073, 1070, '341000', '徽州区', '341004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1074, 1070, '341000', '歙县', '341021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1075, 1070, '341000', '休宁县', '341022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1076, 1070, '341000', '黟县', '341023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1077, 1070, '341000', '祁门县', '341024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1078, 1007, '340000', '滁州市', '341100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1079, 1078, '341100', '琅琊区', '341102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1080, 1078, '341100', '南谯区', '341103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1081, 1078, '341100', '来安县', '341122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1082, 1078, '341100', '全椒县', '341124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1083, 1078, '341100', '定远县', '341125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1084, 1078, '341100', '凤阳县', '341126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1085, 1078, '341100', '天长市', '341181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1086, 1078, '341100', '明光市', '341182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1087, 1007, '340000', '阜阳市', '341200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1088, 1087, '341200', '颍州区', '341202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1089, 1087, '341200', '颍东区', '341203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1090, 1087, '341200', '颍泉区', '341204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1091, 1087, '341200', '临泉县', '341221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1092, 1087, '341200', '太和县', '341222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1093, 1087, '341200', '阜南县', '341225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1094, 1087, '341200', '颍上县', '341226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1095, 1087, '341200', '界首市', '341282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1096, 1007, '340000', '宿州市', '341300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1097, 1096, '341300', '埇桥区', '341302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1098, 1096, '341300', '砀山县', '341321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1099, 1096, '341300', '萧县', '341322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1100, 1096, '341300', '灵璧县', '341323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1101, 1096, '341300', '泗县', '341324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1102, 1007, '340000', '六安市', '341500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1103, 1102, '341500', '金安区', '341502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1104, 1102, '341500', '裕安区', '341503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1105, 1102, '341500', '叶集区', '341504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1106, 1102, '341500', '霍邱县', '341522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1107, 1102, '341500', '舒城县', '341523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1108, 1102, '341500', '金寨县', '341524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1109, 1102, '341500', '霍山县', '341525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1110, 1007, '340000', '亳州市', '341600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1111, 1110, '341600', '谯城区', '341602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1112, 1110, '341600', '涡阳县', '341621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1113, 1110, '341600', '蒙城县', '341622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1114, 1110, '341600', '利辛县', '341623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1115, 1007, '340000', '池州市', '341700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1116, 1115, '341700', '贵池区', '341702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1117, 1115, '341700', '东至县', '341721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1118, 1115, '341700', '石台县', '341722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1119, 1115, '341700', '青阳县', '341723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1120, 1007, '340000', '宣城市', '341800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1121, 1120, '341800', '宣州区', '341802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1122, 1120, '341800', '郎溪县', '341821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1123, 1120, '341800', '泾县', '341823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1124, 1120, '341800', '绩溪县', '341824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1125, 1120, '341800', '旌德县', '341825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1126, 1120, '341800', '宁国市', '341881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1127, 1120, '341800', '广德市', '341882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1128, 0, '350000', '福建省', '350000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1129, 1128, '350000', '福州市', '350100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1130, 1129, '350100', '鼓楼区', '350102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1131, 1129, '350100', '台江区', '350103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1132, 1129, '350100', '仓山区', '350104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1133, 1129, '350100', '马尾区', '350105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1134, 1129, '350100', '晋安区', '350111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1135, 1129, '350100', '长乐区', '350112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1136, 1129, '350100', '闽侯县', '350121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1137, 1129, '350100', '连江县', '350122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1138, 1129, '350100', '罗源县', '350123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1139, 1129, '350100', '闽清县', '350124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1140, 1129, '350100', '永泰县', '350125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1141, 1129, '350100', '平潭县', '350128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1142, 1129, '350100', '福清市', '350181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1143, 1128, '350000', '厦门市', '350200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1144, 1143, '350200', '思明区', '350203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1145, 1143, '350200', '海沧区', '350205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1146, 1143, '350200', '湖里区', '350206', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1147, 1143, '350200', '集美区', '350211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1148, 1143, '350200', '同安区', '350212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1149, 1143, '350200', '翔安区', '350213', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1150, 1128, '350000', '莆田市', '350300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1151, 1150, '350300', '城厢区', '350302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1152, 1150, '350300', '涵江区', '350303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1153, 1150, '350300', '荔城区', '350304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1154, 1150, '350300', '秀屿区', '350305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1155, 1150, '350300', '仙游县', '350322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1156, 1128, '350000', '三明市', '350400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1157, 1156, '350400', '三元区', '350404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1158, 1156, '350400', '沙县区', '350405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1159, 1156, '350400', '明溪县', '350421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1160, 1156, '350400', '清流县', '350423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1161, 1156, '350400', '宁化县', '350424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1162, 1156, '350400', '大田县', '350425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1163, 1156, '350400', '尤溪县', '350426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1164, 1156, '350400', '将乐县', '350428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1165, 1156, '350400', '泰宁县', '350429', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1166, 1156, '350400', '建宁县', '350430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1167, 1156, '350400', '永安市', '350481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1168, 1128, '350000', '泉州市', '350500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1169, 1168, '350500', '鲤城区', '350502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1170, 1168, '350500', '丰泽区', '350503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1171, 1168, '350500', '洛江区', '350504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1172, 1168, '350500', '泉港区', '350505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1173, 1168, '350500', '惠安县', '350521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1174, 1168, '350500', '安溪县', '350524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1175, 1168, '350500', '永春县', '350525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1176, 1168, '350500', '德化县', '350526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1177, 1168, '350500', '金门县', '350527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1178, 1168, '350500', '石狮市', '350581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1179, 1168, '350500', '晋江市', '350582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1180, 1168, '350500', '南安市', '350583', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1181, 1128, '350000', '漳州市', '350600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1182, 1181, '350600', '芗城区', '350602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1183, 1181, '350600', '龙文区', '350603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1184, 1181, '350600', '龙海区', '350604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1185, 1181, '350600', '长泰区', '350605', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1186, 1181, '350600', '云霄县', '350622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1187, 1181, '350600', '漳浦县', '350623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1188, 1181, '350600', '诏安县', '350624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1189, 1181, '350600', '东山县', '350626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1190, 1181, '350600', '南靖县', '350627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1191, 1181, '350600', '平和县', '350628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1192, 1181, '350600', '华安县', '350629', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1193, 1128, '350000', '南平市', '350700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1194, 1193, '350700', '延平区', '350702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1195, 1193, '350700', '建阳区', '350703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1196, 1193, '350700', '顺昌县', '350721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1197, 1193, '350700', '浦城县', '350722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1198, 1193, '350700', '光泽县', '350723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1199, 1193, '350700', '松溪县', '350724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1200, 1193, '350700', '政和县', '350725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1201, 1193, '350700', '邵武市', '350781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1202, 1193, '350700', '武夷山市', '350782', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1203, 1193, '350700', '建瓯市', '350783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1204, 1128, '350000', '龙岩市', '350800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1205, 1204, '350800', '新罗区', '350802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1206, 1204, '350800', '永定区', '350803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1207, 1204, '350800', '长汀县', '350821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1208, 1204, '350800', '上杭县', '350823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1209, 1204, '350800', '武平县', '350824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1210, 1204, '350800', '连城县', '350825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1211, 1204, '350800', '漳平市', '350881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1212, 1128, '350000', '宁德市', '350900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1213, 1212, '350900', '蕉城区', '350902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1214, 1212, '350900', '霞浦县', '350921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1215, 1212, '350900', '古田县', '350922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1216, 1212, '350900', '屏南县', '350923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1217, 1212, '350900', '寿宁县', '350924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1218, 1212, '350900', '周宁县', '350925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1219, 1212, '350900', '柘荣县', '350926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1220, 1212, '350900', '福安市', '350981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1221, 1212, '350900', '福鼎市', '350982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1222, 0, '360000', '江西省', '360000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1223, 1222, '360000', '南昌市', '360100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1224, 1223, '360100', '东湖区', '360102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1225, 1223, '360100', '西湖区', '360103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1226, 1223, '360100', '青云谱区', '360104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1227, 1223, '360100', '青山湖区', '360111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1228, 1223, '360100', '新建区', '360112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1229, 1223, '360100', '红谷滩区', '360113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1230, 1223, '360100', '南昌县', '360121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1231, 1223, '360100', '安义县', '360123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1232, 1223, '360100', '进贤县', '360124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1233, 1222, '360000', '景德镇市', '360200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1234, 1233, '360200', '昌江区', '360202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1235, 1233, '360200', '珠山区', '360203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1236, 1233, '360200', '浮梁县', '360222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1237, 1233, '360200', '乐平市', '360281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1238, 1222, '360000', '萍乡市', '360300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1239, 1238, '360300', '安源区', '360302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1240, 1238, '360300', '湘东区', '360313', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1241, 1238, '360300', '莲花县', '360321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1242, 1238, '360300', '上栗县', '360322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1243, 1238, '360300', '芦溪县', '360323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1244, 1222, '360000', '九江市', '360400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1245, 1244, '360400', '濂溪区', '360402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1246, 1244, '360400', '浔阳区', '360403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1247, 1244, '360400', '柴桑区', '360404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1248, 1244, '360400', '武宁县', '360423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1249, 1244, '360400', '修水县', '360424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1250, 1244, '360400', '永修县', '360425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1251, 1244, '360400', '德安县', '360426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1252, 1244, '360400', '都昌县', '360428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1253, 1244, '360400', '湖口县', '360429', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1254, 1244, '360400', '彭泽县', '360430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1255, 1244, '360400', '瑞昌市', '360481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1256, 1244, '360400', '共青城市', '360482', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1257, 1244, '360400', '庐山市', '360483', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1258, 1222, '360000', '新余市', '360500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1259, 1258, '360500', '渝水区', '360502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1260, 1258, '360500', '分宜县', '360521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1261, 1222, '360000', '鹰潭市', '360600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1262, 1261, '360600', '月湖区', '360602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1263, 1261, '360600', '余江区', '360603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1264, 1261, '360600', '贵溪市', '360681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1265, 1222, '360000', '赣州市', '360700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1266, 1265, '360700', '章贡区', '360702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1267, 1265, '360700', '南康区', '360703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1268, 1265, '360700', '赣县区', '360704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1269, 1265, '360700', '信丰县', '360722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1270, 1265, '360700', '大余县', '360723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1271, 1265, '360700', '上犹县', '360724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1272, 1265, '360700', '崇义县', '360725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1273, 1265, '360700', '安远县', '360726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1274, 1265, '360700', '定南县', '360728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1275, 1265, '360700', '全南县', '360729', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1276, 1265, '360700', '宁都县', '360730', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1277, 1265, '360700', '于都县', '360731', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1278, 1265, '360700', '兴国县', '360732', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1279, 1265, '360700', '会昌县', '360733', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1280, 1265, '360700', '寻乌县', '360734', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1281, 1265, '360700', '石城县', '360735', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1282, 1265, '360700', '瑞金市', '360781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1283, 1265, '360700', '龙南市', '360783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1284, 1222, '360000', '吉安市', '360800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1285, 1284, '360800', '吉州区', '360802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1286, 1284, '360800', '青原区', '360803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1287, 1284, '360800', '吉安县', '360821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1288, 1284, '360800', '吉水县', '360822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1289, 1284, '360800', '峡江县', '360823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1290, 1284, '360800', '新干县', '360824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1291, 1284, '360800', '永丰县', '360825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1292, 1284, '360800', '泰和县', '360826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1293, 1284, '360800', '遂川县', '360827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1294, 1284, '360800', '万安县', '360828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1295, 1284, '360800', '安福县', '360829', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1296, 1284, '360800', '永新县', '360830', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1297, 1284, '360800', '井冈山市', '360881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1298, 1222, '360000', '宜春市', '360900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1299, 1298, '360900', '袁州区', '360902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1300, 1298, '360900', '奉新县', '360921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1301, 1298, '360900', '万载县', '360922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1302, 1298, '360900', '上高县', '360923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1303, 1298, '360900', '宜丰县', '360924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1304, 1298, '360900', '靖安县', '360925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1305, 1298, '360900', '铜鼓县', '360926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1306, 1298, '360900', '丰城市', '360981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1307, 1298, '360900', '樟树市', '360982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1308, 1298, '360900', '高安市', '360983', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1309, 1222, '360000', '抚州市', '361000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1310, 1309, '361000', '临川区', '361002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1311, 1309, '361000', '东乡区', '361003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1312, 1309, '361000', '南城县', '361021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1313, 1309, '361000', '黎川县', '361022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1314, 1309, '361000', '南丰县', '361023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1315, 1309, '361000', '崇仁县', '361024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1316, 1309, '361000', '乐安县', '361025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1317, 1309, '361000', '宜黄县', '361026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1318, 1309, '361000', '金溪县', '361027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1319, 1309, '361000', '资溪县', '361028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1320, 1309, '361000', '广昌县', '361030', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1321, 1222, '360000', '上饶市', '361100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1322, 1321, '361100', '信州区', '361102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1323, 1321, '361100', '广丰区', '361103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1324, 1321, '361100', '广信区', '361104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1325, 1321, '361100', '玉山县', '361123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1326, 1321, '361100', '铅山县', '361124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1327, 1321, '361100', '横峰县', '361125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1328, 1321, '361100', '弋阳县', '361126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1329, 1321, '361100', '余干县', '361127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1330, 1321, '361100', '鄱阳县', '361128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1331, 1321, '361100', '万年县', '361129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1332, 1321, '361100', '婺源县', '361130', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1333, 1321, '361100', '德兴市', '361181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1334, 0, '370000', '山东省', '370000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1335, 1334, '370000', '济南市', '370100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1336, 1335, '370100', '历下区', '370102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1337, 1335, '370100', '市中区', '370103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1338, 1335, '370100', '槐荫区', '370104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1339, 1335, '370100', '天桥区', '370105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1340, 1335, '370100', '历城区', '370112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1341, 1335, '370100', '长清区', '370113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1342, 1335, '370100', '章丘区', '370114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1343, 1335, '370100', '济阳区', '370115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1344, 1335, '370100', '莱芜区', '370116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1345, 1335, '370100', '钢城区', '370117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1346, 1335, '370100', '平阴县', '370124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1347, 1335, '370100', '商河县', '370126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1348, 1334, '370000', '青岛市', '370200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1349, 1348, '370200', '市南区', '370202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1350, 1348, '370200', '市北区', '370203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1351, 1348, '370200', '黄岛区', '370211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1352, 1348, '370200', '崂山区', '370212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1353, 1348, '370200', '李沧区', '370213', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1354, 1348, '370200', '城阳区', '370214', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1355, 1348, '370200', '即墨区', '370215', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1356, 1348, '370200', '胶州市', '370281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1357, 1348, '370200', '平度市', '370283', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1358, 1348, '370200', '莱西市', '370285', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1359, 1334, '370000', '淄博市', '370300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1360, 1359, '370300', '淄川区', '370302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1361, 1359, '370300', '张店区', '370303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1362, 1359, '370300', '博山区', '370304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1363, 1359, '370300', '临淄区', '370305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1364, 1359, '370300', '周村区', '370306', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1365, 1359, '370300', '桓台县', '370321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1366, 1359, '370300', '高青县', '370322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1367, 1359, '370300', '沂源县', '370323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1368, 1334, '370000', '枣庄市', '370400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1369, 1368, '370400', '市中区', '370402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1370, 1368, '370400', '薛城区', '370403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1371, 1368, '370400', '峄城区', '370404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1372, 1368, '370400', '台儿庄区', '370405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1373, 1368, '370400', '山亭区', '370406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1374, 1368, '370400', '滕州市', '370481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1375, 1334, '370000', '东营市', '370500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1376, 1375, '370500', '东营区', '370502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1377, 1375, '370500', '河口区', '370503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1378, 1375, '370500', '垦利区', '370505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1379, 1375, '370500', '利津县', '370522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1380, 1375, '370500', '广饶县', '370523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1381, 1334, '370000', '烟台市', '370600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1382, 1381, '370600', '芝罘区', '370602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1383, 1381, '370600', '福山区', '370611', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1384, 1381, '370600', '牟平区', '370612', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1385, 1381, '370600', '莱山区', '370613', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1386, 1381, '370600', '蓬莱区', '370614', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1387, 1381, '370600', '龙口市', '370681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1388, 1381, '370600', '莱阳市', '370682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1389, 1381, '370600', '莱州市', '370683', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1390, 1381, '370600', '招远市', '370685', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1391, 1381, '370600', '栖霞市', '370686', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1392, 1381, '370600', '海阳市', '370687', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1393, 1334, '370000', '潍坊市', '370700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1394, 1393, '370700', '潍城区', '370702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1395, 1393, '370700', '寒亭区', '370703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1396, 1393, '370700', '坊子区', '370704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1397, 1393, '370700', '奎文区', '370705', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1398, 1393, '370700', '临朐县', '370724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1399, 1393, '370700', '昌乐县', '370725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1400, 1393, '370700', '青州市', '370781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1401, 1393, '370700', '诸城市', '370782', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1402, 1393, '370700', '寿光市', '370783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1403, 1393, '370700', '安丘市', '370784', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1404, 1393, '370700', '高密市', '370785', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1405, 1393, '370700', '昌邑市', '370786', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1406, 1334, '370000', '济宁市', '370800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1407, 1406, '370800', '任城区', '370811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1408, 1406, '370800', '兖州区', '370812', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1409, 1406, '370800', '微山县', '370826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1410, 1406, '370800', '鱼台县', '370827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1411, 1406, '370800', '金乡县', '370828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1412, 1406, '370800', '嘉祥县', '370829', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1413, 1406, '370800', '汶上县', '370830', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1414, 1406, '370800', '泗水县', '370831', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1415, 1406, '370800', '梁山县', '370832', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1416, 1406, '370800', '曲阜市', '370881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1417, 1406, '370800', '邹城市', '370883', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1418, 1334, '370000', '泰安市', '370900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1419, 1418, '370900', '泰山区', '370902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1420, 1418, '370900', '岱岳区', '370911', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1421, 1418, '370900', '宁阳县', '370921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1422, 1418, '370900', '东平县', '370923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1423, 1418, '370900', '新泰市', '370982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1424, 1418, '370900', '肥城市', '370983', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1425, 1334, '370000', '威海市', '371000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1426, 1425, '371000', '环翠区', '371002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1427, 1425, '371000', '文登区', '371003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1428, 1425, '371000', '荣成市', '371082', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1429, 1425, '371000', '乳山市', '371083', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1430, 1334, '370000', '日照市', '371100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1431, 1430, '371100', '东港区', '371102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1432, 1430, '371100', '岚山区', '371103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1433, 1430, '371100', '五莲县', '371121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1434, 1430, '371100', '莒县', '371122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1435, 1334, '370000', '临沂市', '371300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1436, 1435, '371300', '兰山区', '371302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1437, 1435, '371300', '罗庄区', '371311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1438, 1435, '371300', '河东区', '371312', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1439, 1435, '371300', '沂南县', '371321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1440, 1435, '371300', '郯城县', '371322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1441, 1435, '371300', '沂水县', '371323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1442, 1435, '371300', '兰陵县', '371324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1443, 1435, '371300', '费县', '371325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1444, 1435, '371300', '平邑县', '371326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1445, 1435, '371300', '莒南县', '371327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1446, 1435, '371300', '蒙阴县', '371328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1447, 1435, '371300', '临沭县', '371329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1448, 1334, '370000', '德州市', '371400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1449, 1448, '371400', '德城区', '371402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1450, 1448, '371400', '陵城区', '371403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1451, 1448, '371400', '宁津县', '371422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1452, 1448, '371400', '庆云县', '371423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1453, 1448, '371400', '临邑县', '371424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1454, 1448, '371400', '齐河县', '371425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1455, 1448, '371400', '平原县', '371426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1456, 1448, '371400', '夏津县', '371427', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1457, 1448, '371400', '武城县', '371428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1458, 1448, '371400', '乐陵市', '371481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1459, 1448, '371400', '禹城市', '371482', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1460, 1334, '370000', '聊城市', '371500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1461, 1460, '371500', '东昌府区', '371502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1462, 1460, '371500', '茌平区', '371503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1463, 1460, '371500', '阳谷县', '371521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1464, 1460, '371500', '莘县', '371522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1465, 1460, '371500', '东阿县', '371524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1466, 1460, '371500', '冠县', '371525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1467, 1460, '371500', '高唐县', '371526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1468, 1460, '371500', '临清市', '371581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1469, 1334, '370000', '滨州市', '371600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1470, 1469, '371600', '滨城区', '371602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1471, 1469, '371600', '沾化区', '371603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1472, 1469, '371600', '惠民县', '371621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1473, 1469, '371600', '阳信县', '371622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1474, 1469, '371600', '无棣县', '371623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1475, 1469, '371600', '博兴县', '371625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1476, 1469, '371600', '邹平市', '371681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1477, 1334, '370000', '菏泽市', '371700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1478, 1477, '371700', '牡丹区', '371702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1479, 1477, '371700', '定陶区', '371703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1480, 1477, '371700', '曹县', '371721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1481, 1477, '371700', '单县', '371722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1482, 1477, '371700', '成武县', '371723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1483, 1477, '371700', '巨野县', '371724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1484, 1477, '371700', '郓城县', '371725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1485, 1477, '371700', '鄄城县', '371726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1486, 1477, '371700', '东明县', '371728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1487, 0, '410000', '河南省', '410000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1488, 1487, '410000', '郑州市', '410100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1489, 1488, '410100', '中原区', '410102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1490, 1488, '410100', '二七区', '410103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1491, 1488, '410100', '管城回族区', '410104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1492, 1488, '410100', '金水区', '410105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1493, 1488, '410100', '上街区', '410106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1494, 1488, '410100', '惠济区', '410108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1495, 1488, '410100', '中牟县', '410122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1496, 1488, '410100', '巩义市', '410181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1497, 1488, '410100', '荥阳市', '410182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1498, 1488, '410100', '新密市', '410183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1499, 1488, '410100', '新郑市', '410184', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1500, 1488, '410100', '登封市', '410185', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1501, 1487, '410000', '开封市', '410200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1502, 1501, '410200', '龙亭区', '410202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1503, 1501, '410200', '顺河回族区', '410203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1504, 1501, '410200', '鼓楼区', '410204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1505, 1501, '410200', '禹王台区', '410205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1506, 1501, '410200', '祥符区', '410212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1507, 1501, '410200', '杞县', '410221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1508, 1501, '410200', '通许县', '410222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1509, 1501, '410200', '尉氏县', '410223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1510, 1501, '410200', '兰考县', '410225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1511, 1487, '410000', '洛阳市', '410300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1512, 1511, '410300', '老城区', '410302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1513, 1511, '410300', '西工区', '410303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1514, 1511, '410300', '瀍河回族区', '410304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1515, 1511, '410300', '涧西区', '410305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1516, 1511, '410300', '偃师区', '410307', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1517, 1511, '410300', '孟津区', '410308', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1518, 1511, '410300', '洛龙区', '410311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1519, 1511, '410300', '新安县', '410323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1520, 1511, '410300', '栾川县', '410324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1521, 1511, '410300', '嵩县', '410325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1522, 1511, '410300', '汝阳县', '410326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1523, 1511, '410300', '宜阳县', '410327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1524, 1511, '410300', '洛宁县', '410328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1525, 1511, '410300', '伊川县', '410329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1526, 1487, '410000', '平顶山市', '410400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1527, 1526, '410400', '新华区', '410402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1528, 1526, '410400', '卫东区', '410403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1529, 1526, '410400', '石龙区', '410404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1530, 1526, '410400', '湛河区', '410411', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1531, 1526, '410400', '宝丰县', '410421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1532, 1526, '410400', '叶县', '410422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1533, 1526, '410400', '鲁山县', '410423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1534, 1526, '410400', '郏县', '410425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1535, 1526, '410400', '舞钢市', '410481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1536, 1526, '410400', '汝州市', '410482', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1537, 1487, '410000', '安阳市', '410500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1538, 1537, '410500', '文峰区', '410502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1539, 1537, '410500', '北关区', '410503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1540, 1537, '410500', '殷都区', '410505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1541, 1537, '410500', '龙安区', '410506', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1542, 1537, '410500', '安阳县', '410522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1543, 1537, '410500', '汤阴县', '410523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1544, 1537, '410500', '滑县', '410526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1545, 1537, '410500', '内黄县', '410527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1546, 1537, '410500', '林州市', '410581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1547, 1487, '410000', '鹤壁市', '410600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1548, 1547, '410600', '鹤山区', '410602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1549, 1547, '410600', '山城区', '410603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1550, 1547, '410600', '淇滨区', '410611', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1551, 1547, '410600', '浚县', '410621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1552, 1547, '410600', '淇县', '410622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1553, 1487, '410000', '新乡市', '410700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1554, 1553, '410700', '红旗区', '410702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1555, 1553, '410700', '卫滨区', '410703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1556, 1553, '410700', '凤泉区', '410704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1557, 1553, '410700', '牧野区', '410711', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1558, 1553, '410700', '新乡县', '410721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1559, 1553, '410700', '获嘉县', '410724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1560, 1553, '410700', '原阳县', '410725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1561, 1553, '410700', '延津县', '410726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1562, 1553, '410700', '封丘县', '410727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1563, 1553, '410700', '卫辉市', '410781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1564, 1553, '410700', '辉县市', '410782', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1565, 1553, '410700', '长垣市', '410783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1566, 1487, '410000', '焦作市', '410800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1567, 1566, '410800', '解放区', '410802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1568, 1566, '410800', '中站区', '410803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1569, 1566, '410800', '马村区', '410804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1570, 1566, '410800', '山阳区', '410811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1571, 1566, '410800', '修武县', '410821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1572, 1566, '410800', '博爱县', '410822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1573, 1566, '410800', '武陟县', '410823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1574, 1566, '410800', '温县', '410825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1575, 1566, '410800', '沁阳市', '410882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1576, 1566, '410800', '孟州市', '410883', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1577, 1487, '410000', '濮阳市', '410900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1578, 1577, '410900', '华龙区', '410902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1579, 1577, '410900', '清丰县', '410922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1580, 1577, '410900', '南乐县', '410923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1581, 1577, '410900', '范县', '410926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1582, 1577, '410900', '台前县', '410927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1583, 1577, '410900', '濮阳县', '410928', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1584, 1487, '410000', '许昌市', '411000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1585, 1584, '411000', '魏都区', '411002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1586, 1584, '411000', '建安区', '411003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1587, 1584, '411000', '鄢陵县', '411024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1588, 1584, '411000', '襄城县', '411025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1589, 1584, '411000', '禹州市', '411081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1590, 1584, '411000', '长葛市', '411082', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1591, 1487, '410000', '漯河市', '411100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1592, 1591, '411100', '源汇区', '411102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1593, 1591, '411100', '郾城区', '411103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1594, 1591, '411100', '召陵区', '411104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1595, 1591, '411100', '舞阳县', '411121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1596, 1591, '411100', '临颍县', '411122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1597, 1487, '410000', '三门峡市', '411200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1598, 1597, '411200', '湖滨区', '411202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1599, 1597, '411200', '陕州区', '411203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1600, 1597, '411200', '渑池县', '411221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1601, 1597, '411200', '卢氏县', '411224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1602, 1597, '411200', '义马市', '411281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1603, 1597, '411200', '灵宝市', '411282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1604, 1487, '410000', '南阳市', '411300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1605, 1604, '411300', '宛城区', '411302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1606, 1604, '411300', '卧龙区', '411303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1607, 1604, '411300', '南召县', '411321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1608, 1604, '411300', '方城县', '411322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1609, 1604, '411300', '西峡县', '411323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1610, 1604, '411300', '镇平县', '411324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1611, 1604, '411300', '内乡县', '411325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1612, 1604, '411300', '淅川县', '411326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1613, 1604, '411300', '社旗县', '411327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1614, 1604, '411300', '唐河县', '411328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1615, 1604, '411300', '新野县', '411329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1616, 1604, '411300', '桐柏县', '411330', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1617, 1604, '411300', '邓州市', '411381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1618, 1487, '410000', '商丘市', '411400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1619, 1618, '411400', '梁园区', '411402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1620, 1618, '411400', '睢阳区', '411403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1621, 1618, '411400', '民权县', '411421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1622, 1618, '411400', '睢县', '411422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1623, 1618, '411400', '宁陵县', '411423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1624, 1618, '411400', '柘城县', '411424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1625, 1618, '411400', '虞城县', '411425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1626, 1618, '411400', '夏邑县', '411426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1627, 1618, '411400', '永城市', '411481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1628, 1487, '410000', '信阳市', '411500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1629, 1628, '411500', '浉河区', '411502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1630, 1628, '411500', '平桥区', '411503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1631, 1628, '411500', '罗山县', '411521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1632, 1628, '411500', '光山县', '411522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1633, 1628, '411500', '新县', '411523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1634, 1628, '411500', '商城县', '411524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1635, 1628, '411500', '固始县', '411525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1636, 1628, '411500', '潢川县', '411526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1637, 1628, '411500', '淮滨县', '411527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1638, 1628, '411500', '息县', '411528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1639, 1487, '410000', '周口市', '411600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1640, 1639, '411600', '川汇区', '411602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1641, 1639, '411600', '淮阳区', '411603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1642, 1639, '411600', '扶沟县', '411621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1643, 1639, '411600', '西华县', '411622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1644, 1639, '411600', '商水县', '411623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1645, 1639, '411600', '沈丘县', '411624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1646, 1639, '411600', '郸城县', '411625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1647, 1639, '411600', '太康县', '411627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1648, 1639, '411600', '鹿邑县', '411628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1649, 1639, '411600', '项城市', '411681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1650, 1487, '410000', '驻马店市', '411700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1651, 1650, '411700', '驿城区', '411702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1652, 1650, '411700', '西平县', '411721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1653, 1650, '411700', '上蔡县', '411722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1654, 1650, '411700', '平舆县', '411723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1655, 1650, '411700', '正阳县', '411724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1656, 1650, '411700', '确山县', '411725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1657, 1650, '411700', '泌阳县', '411726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1658, 1650, '411700', '汝南县', '411727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1659, 1650, '411700', '遂平县', '411728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1660, 1650, '411700', '新蔡县', '411729', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1661, 1487, '410000', '济源市', '419001', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1662, 0, '420000', '湖北省', '420000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1663, 1662, '420000', '武汉市', '420100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1664, 1663, '420100', '江岸区', '420102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1665, 1663, '420100', '江汉区', '420103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1666, 1663, '420100', '硚口区', '420104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1667, 1663, '420100', '汉阳区', '420105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1668, 1663, '420100', '武昌区', '420106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1669, 1663, '420100', '青山区', '420107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1670, 1663, '420100', '洪山区', '420111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1671, 1663, '420100', '东西湖区', '420112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1672, 1663, '420100', '汉南区', '420113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1673, 1663, '420100', '蔡甸区', '420114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1674, 1663, '420100', '江夏区', '420115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1675, 1663, '420100', '黄陂区', '420116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1676, 1663, '420100', '新洲区', '420117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1677, 1662, '420000', '黄石市', '420200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1678, 1677, '420200', '黄石港区', '420202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1679, 1677, '420200', '西塞山区', '420203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1680, 1677, '420200', '下陆区', '420204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1681, 1677, '420200', '铁山区', '420205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1682, 1677, '420200', '阳新县', '420222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1683, 1677, '420200', '大冶市', '420281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1684, 1662, '420000', '十堰市', '420300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1685, 1684, '420300', '茅箭区', '420302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1686, 1684, '420300', '张湾区', '420303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1687, 1684, '420300', '郧阳区', '420304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1688, 1684, '420300', '郧西县', '420322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1689, 1684, '420300', '竹山县', '420323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1690, 1684, '420300', '竹溪县', '420324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1691, 1684, '420300', '房县', '420325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1692, 1684, '420300', '丹江口市', '420381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1693, 1662, '420000', '宜昌市', '420500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1694, 1693, '420500', '西陵区', '420502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1695, 1693, '420500', '伍家岗区', '420503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1696, 1693, '420500', '点军区', '420504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1697, 1693, '420500', '猇亭区', '420505', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1698, 1693, '420500', '夷陵区', '420506', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1699, 1693, '420500', '远安县', '420525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1700, 1693, '420500', '兴山县', '420526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1701, 1693, '420500', '秭归县', '420527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1702, 1693, '420500', '长阳土家族自治县', '420528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1703, 1693, '420500', '五峰土家族自治县', '420529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1704, 1693, '420500', '宜都市', '420581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1705, 1693, '420500', '当阳市', '420582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1706, 1693, '420500', '枝江市', '420583', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1707, 1662, '420000', '襄阳市', '420600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1708, 1707, '420600', '襄城区', '420602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1709, 1707, '420600', '樊城区', '420606', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1710, 1707, '420600', '襄州区', '420607', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1711, 1707, '420600', '南漳县', '420624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1712, 1707, '420600', '谷城县', '420625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1713, 1707, '420600', '保康县', '420626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1714, 1707, '420600', '老河口市', '420682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1715, 1707, '420600', '枣阳市', '420683', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1716, 1707, '420600', '宜城市', '420684', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1717, 1662, '420000', '鄂州市', '420700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1718, 1717, '420700', '梁子湖区', '420702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1719, 1717, '420700', '华容区', '420703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1720, 1717, '420700', '鄂城区', '420704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1721, 1662, '420000', '荆门市', '420800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1722, 1721, '420800', '东宝区', '420802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1723, 1721, '420800', '掇刀区', '420804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1724, 1721, '420800', '沙洋县', '420822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1725, 1721, '420800', '钟祥市', '420881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1726, 1721, '420800', '京山市', '420882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1727, 1662, '420000', '孝感市', '420900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1728, 1727, '420900', '孝南区', '420902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1729, 1727, '420900', '孝昌县', '420921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1730, 1727, '420900', '大悟县', '420922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1731, 1727, '420900', '云梦县', '420923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1732, 1727, '420900', '应城市', '420981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1733, 1727, '420900', '安陆市', '420982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1734, 1727, '420900', '汉川市', '420984', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1735, 1662, '420000', '荆州市', '421000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1736, 1735, '421000', '沙市区', '421002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1737, 1735, '421000', '荆州区', '421003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1738, 1735, '421000', '公安县', '421022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1739, 1735, '421000', '江陵县', '421024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1740, 1735, '421000', '石首市', '421081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1741, 1735, '421000', '洪湖市', '421083', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1742, 1735, '421000', '松滋市', '421087', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1743, 1735, '421000', '监利市', '421088', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1744, 1662, '420000', '黄冈市', '421100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1745, 1744, '421100', '黄州区', '421102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1746, 1744, '421100', '团风县', '421121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1747, 1744, '421100', '红安县', '421122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1748, 1744, '421100', '罗田县', '421123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1749, 1744, '421100', '英山县', '421124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1750, 1744, '421100', '浠水县', '421125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1751, 1744, '421100', '蕲春县', '421126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1752, 1744, '421100', '黄梅县', '421127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1753, 1744, '421100', '麻城市', '421181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1754, 1744, '421100', '武穴市', '421182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1755, 1662, '420000', '咸宁市', '421200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1756, 1755, '421200', '咸安区', '421202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1757, 1755, '421200', '嘉鱼县', '421221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1758, 1755, '421200', '通城县', '421222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1759, 1755, '421200', '崇阳县', '421223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1760, 1755, '421200', '通山县', '421224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1761, 1755, '421200', '赤壁市', '421281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1762, 1662, '420000', '随州市', '421300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1763, 1762, '421300', '曾都区', '421303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1764, 1762, '421300', '随县', '421321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1765, 1762, '421300', '广水市', '421381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1766, 1662, '420000', '恩施土家族苗族自治州', '422800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1767, 1766, '422800', '恩施市', '422801', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1768, 1766, '422800', '利川市', '422802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1769, 1766, '422800', '建始县', '422822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1770, 1766, '422800', '巴东县', '422823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1771, 1766, '422800', '宣恩县', '422825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1772, 1766, '422800', '咸丰县', '422826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1773, 1766, '422800', '来凤县', '422827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1774, 1766, '422800', '鹤峰县', '422828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1775, 1662, '420000', '仙桃市', '429004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1776, 1662, '420000', '潜江市', '429005', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1777, 1662, '420000', '天门市', '429006', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1778, 1662, '420000', '神农架林区', '429021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1779, 0, '430000', '湖南省', '430000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1780, 1779, '430000', '长沙市', '430100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1781, 1780, '430100', '芙蓉区', '430102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1782, 1780, '430100', '天心区', '430103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1783, 1780, '430100', '岳麓区', '430104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1784, 1780, '430100', '开福区', '430105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1785, 1780, '430100', '雨花区', '430111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1786, 1780, '430100', '望城区', '430112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1787, 1780, '430100', '长沙县', '430121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1788, 1780, '430100', '浏阳市', '430181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1789, 1780, '430100', '宁乡市', '430182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1790, 1779, '430000', '株洲市', '430200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1791, 1790, '430200', '荷塘区', '430202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1792, 1790, '430200', '芦淞区', '430203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1793, 1790, '430200', '石峰区', '430204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1794, 1790, '430200', '天元区', '430211', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1795, 1790, '430200', '渌口区', '430212', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1796, 1790, '430200', '攸县', '430223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1797, 1790, '430200', '茶陵县', '430224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1798, 1790, '430200', '炎陵县', '430225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1799, 1790, '430200', '醴陵市', '430281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1800, 1779, '430000', '湘潭市', '430300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1801, 1800, '430300', '雨湖区', '430302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1802, 1800, '430300', '岳塘区', '430304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1803, 1800, '430300', '湘潭县', '430321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1804, 1800, '430300', '湘乡市', '430381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1805, 1800, '430300', '韶山市', '430382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1806, 1779, '430000', '衡阳市', '430400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1807, 1806, '430400', '珠晖区', '430405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1808, 1806, '430400', '雁峰区', '430406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1809, 1806, '430400', '石鼓区', '430407', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1810, 1806, '430400', '蒸湘区', '430408', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1811, 1806, '430400', '南岳区', '430412', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1812, 1806, '430400', '衡阳县', '430421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1813, 1806, '430400', '衡南县', '430422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1814, 1806, '430400', '衡山县', '430423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1815, 1806, '430400', '衡东县', '430424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1816, 1806, '430400', '祁东县', '430426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1817, 1806, '430400', '耒阳市', '430481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1818, 1806, '430400', '常宁市', '430482', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1819, 1779, '430000', '邵阳市', '430500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1820, 1819, '430500', '双清区', '430502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1821, 1819, '430500', '大祥区', '430503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1822, 1819, '430500', '北塔区', '430511', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1823, 1819, '430500', '新邵县', '430522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1824, 1819, '430500', '邵阳县', '430523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1825, 1819, '430500', '隆回县', '430524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1826, 1819, '430500', '洞口县', '430525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1827, 1819, '430500', '绥宁县', '430527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1828, 1819, '430500', '新宁县', '430528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1829, 1819, '430500', '城步苗族自治县', '430529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1830, 1819, '430500', '武冈市', '430581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1831, 1819, '430500', '邵东市', '430582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1832, 1779, '430000', '岳阳市', '430600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1833, 1832, '430600', '岳阳楼区', '430602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1834, 1832, '430600', '云溪区', '430603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1835, 1832, '430600', '君山区', '430611', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1836, 1832, '430600', '岳阳县', '430621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1837, 1832, '430600', '华容县', '430623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1838, 1832, '430600', '湘阴县', '430624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1839, 1832, '430600', '平江县', '430626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1840, 1832, '430600', '汨罗市', '430681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1841, 1832, '430600', '临湘市', '430682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1842, 1779, '430000', '常德市', '430700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1843, 1842, '430700', '武陵区', '430702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1844, 1842, '430700', '鼎城区', '430703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1845, 1842, '430700', '安乡县', '430721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1846, 1842, '430700', '汉寿县', '430722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1847, 1842, '430700', '澧县', '430723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1848, 1842, '430700', '临澧县', '430724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1849, 1842, '430700', '桃源县', '430725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1850, 1842, '430700', '石门县', '430726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1851, 1842, '430700', '津市市', '430781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1852, 1779, '430000', '张家界市', '430800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1853, 1852, '430800', '永定区', '430802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1854, 1852, '430800', '武陵源区', '430811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1855, 1852, '430800', '慈利县', '430821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1856, 1852, '430800', '桑植县', '430822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1857, 1779, '430000', '益阳市', '430900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1858, 1857, '430900', '资阳区', '430902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1859, 1857, '430900', '赫山区', '430903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1860, 1857, '430900', '南县', '430921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1861, 1857, '430900', '桃江县', '430922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1862, 1857, '430900', '安化县', '430923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1863, 1857, '430900', '沅江市', '430981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1864, 1779, '430000', '郴州市', '431000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1865, 1864, '431000', '北湖区', '431002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1866, 1864, '431000', '苏仙区', '431003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1867, 1864, '431000', '桂阳县', '431021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1868, 1864, '431000', '宜章县', '431022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1869, 1864, '431000', '永兴县', '431023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1870, 1864, '431000', '嘉禾县', '431024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1871, 1864, '431000', '临武县', '431025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1872, 1864, '431000', '汝城县', '431026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1873, 1864, '431000', '桂东县', '431027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1874, 1864, '431000', '安仁县', '431028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1875, 1864, '431000', '资兴市', '431081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1876, 1779, '430000', '永州市', '431100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1877, 1876, '431100', '零陵区', '431102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1878, 1876, '431100', '冷水滩区', '431103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1879, 1876, '431100', '东安县', '431122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1880, 1876, '431100', '双牌县', '431123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1881, 1876, '431100', '道县', '431124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1882, 1876, '431100', '江永县', '431125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1883, 1876, '431100', '宁远县', '431126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1884, 1876, '431100', '蓝山县', '431127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1885, 1876, '431100', '新田县', '431128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1886, 1876, '431100', '江华瑶族自治县', '431129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1887, 1876, '431100', '祁阳市', '431181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1888, 1779, '430000', '怀化市', '431200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1889, 1888, '431200', '鹤城区', '431202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1890, 1888, '431200', '中方县', '431221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1891, 1888, '431200', '沅陵县', '431222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1892, 1888, '431200', '辰溪县', '431223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1893, 1888, '431200', '溆浦县', '431224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1894, 1888, '431200', '会同县', '431225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1895, 1888, '431200', '麻阳苗族自治县', '431226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1896, 1888, '431200', '新晃侗族自治县', '431227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1897, 1888, '431200', '芷江侗族自治县', '431228', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1898, 1888, '431200', '靖州苗族侗族自治县', '431229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1899, 1888, '431200', '通道侗族自治县', '431230', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1900, 1888, '431200', '洪江市', '431281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1901, 1779, '430000', '娄底市', '431300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1902, 1901, '431300', '娄星区', '431302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1903, 1901, '431300', '双峰县', '431321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1904, 1901, '431300', '新化县', '431322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1905, 1901, '431300', '冷水江市', '431381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1906, 1901, '431300', '涟源市', '431382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1907, 1779, '430000', '湘西土家族苗族自治州', '433100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1908, 1907, '433100', '吉首市', '433101', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1909, 1907, '433100', '泸溪县', '433122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1910, 1907, '433100', '凤凰县', '433123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1911, 1907, '433100', '花垣县', '433124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1912, 1907, '433100', '保靖县', '433125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1913, 1907, '433100', '古丈县', '433126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1914, 1907, '433100', '永顺县', '433127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1915, 1907, '433100', '龙山县', '433130', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1916, 0, '440000', '广东省', '440000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1917, 1916, '440000', '广州市', '440100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1918, 1917, '440100', '荔湾区', '440103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1919, 1917, '440100', '越秀区', '440104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1920, 1917, '440100', '海珠区', '440105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1921, 1917, '440100', '天河区', '440106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1922, 1917, '440100', '白云区', '440111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1923, 1917, '440100', '黄埔区', '440112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1924, 1917, '440100', '番禺区', '440113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1925, 1917, '440100', '花都区', '440114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1926, 1917, '440100', '南沙区', '440115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1927, 1917, '440100', '从化区', '440117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1928, 1917, '440100', '增城区', '440118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1929, 1916, '440000', '韶关市', '440200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1930, 1929, '440200', '武江区', '440203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1931, 1929, '440200', '浈江区', '440204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1932, 1929, '440200', '曲江区', '440205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1933, 1929, '440200', '始兴县', '440222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1934, 1929, '440200', '仁化县', '440224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1935, 1929, '440200', '翁源县', '440229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1936, 1929, '440200', '乳源瑶族自治县', '440232', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1937, 1929, '440200', '新丰县', '440233', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1938, 1929, '440200', '乐昌市', '440281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1939, 1929, '440200', '南雄市', '440282', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1940, 1916, '440000', '深圳市', '440300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1941, 1940, '440300', '罗湖区', '440303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1942, 1940, '440300', '福田区', '440304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1943, 1940, '440300', '南山区', '440305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1944, 1940, '440300', '宝安区', '440306', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1945, 1940, '440300', '龙岗区', '440307', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1946, 1940, '440300', '盐田区', '440308', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1947, 1940, '440300', '龙华区', '440309', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1948, 1940, '440300', '坪山区', '440310', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1949, 1940, '440300', '光明区', '440311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1950, 1916, '440000', '珠海市', '440400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1951, 1950, '440400', '香洲区', '440402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1952, 1950, '440400', '斗门区', '440403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1953, 1950, '440400', '金湾区', '440404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1954, 1916, '440000', '汕头市', '440500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1955, 1954, '440500', '龙湖区', '440507', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1956, 1954, '440500', '金平区', '440511', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1957, 1954, '440500', '濠江区', '440512', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1958, 1954, '440500', '潮阳区', '440513', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1959, 1954, '440500', '潮南区', '440514', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1960, 1954, '440500', '澄海区', '440515', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1961, 1954, '440500', '南澳县', '440523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1962, 1916, '440000', '佛山市', '440600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1963, 1962, '440600', '禅城区', '440604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1964, 1962, '440600', '南海区', '440605', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1965, 1962, '440600', '顺德区', '440606', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1966, 1962, '440600', '三水区', '440607', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1967, 1962, '440600', '高明区', '440608', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1968, 1916, '440000', '江门市', '440700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1969, 1968, '440700', '蓬江区', '440703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1970, 1968, '440700', '江海区', '440704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1971, 1968, '440700', '新会区', '440705', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1972, 1968, '440700', '台山市', '440781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1973, 1968, '440700', '开平市', '440783', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1974, 1968, '440700', '鹤山市', '440784', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1975, 1968, '440700', '恩平市', '440785', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1976, 1916, '440000', '湛江市', '440800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1977, 1976, '440800', '赤坎区', '440802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1978, 1976, '440800', '霞山区', '440803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1979, 1976, '440800', '坡头区', '440804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1980, 1976, '440800', '麻章区', '440811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1981, 1976, '440800', '遂溪县', '440823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1982, 1976, '440800', '徐闻县', '440825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1983, 1976, '440800', '廉江市', '440881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1984, 1976, '440800', '雷州市', '440882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1985, 1976, '440800', '吴川市', '440883', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1986, 1916, '440000', '茂名市', '440900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1987, 1986, '440900', '茂南区', '440902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1988, 1986, '440900', '电白区', '440904', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1989, 1986, '440900', '高州市', '440981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1990, 1986, '440900', '化州市', '440982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1991, 1986, '440900', '信宜市', '440983', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1992, 1916, '440000', '肇庆市', '441200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1993, 1992, '441200', '端州区', '441202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1994, 1992, '441200', '鼎湖区', '441203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1995, 1992, '441200', '高要区', '441204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1996, 1992, '441200', '广宁县', '441223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1997, 1992, '441200', '怀集县', '441224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1998, 1992, '441200', '封开县', '441225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (1999, 1992, '441200', '德庆县', '441226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2000, 1992, '441200', '四会市', '441284', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2001, 1916, '440000', '惠州市', '441300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2002, 2001, '441300', '惠城区', '441302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2003, 2001, '441300', '惠阳区', '441303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2004, 2001, '441300', '博罗县', '441322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2005, 2001, '441300', '惠东县', '441323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2006, 2001, '441300', '龙门县', '441324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2007, 1916, '440000', '梅州市', '441400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2008, 2007, '441400', '梅江区', '441402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2009, 2007, '441400', '梅县区', '441403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2010, 2007, '441400', '大埔县', '441422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2011, 2007, '441400', '丰顺县', '441423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2012, 2007, '441400', '五华县', '441424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2013, 2007, '441400', '平远县', '441426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2014, 2007, '441400', '蕉岭县', '441427', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2015, 2007, '441400', '兴宁市', '441481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2016, 1916, '440000', '汕尾市', '441500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2017, 2016, '441500', '城区', '441502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2018, 2016, '441500', '海丰县', '441521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2019, 2016, '441500', '陆河县', '441523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2020, 2016, '441500', '陆丰市', '441581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2021, 1916, '440000', '河源市', '441600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2022, 2021, '441600', '源城区', '441602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2023, 2021, '441600', '紫金县', '441621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2024, 2021, '441600', '龙川县', '441622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2025, 2021, '441600', '连平县', '441623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2026, 2021, '441600', '和平县', '441624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2027, 2021, '441600', '东源县', '441625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2028, 1916, '440000', '阳江市', '441700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2029, 2028, '441700', '江城区', '441702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2030, 2028, '441700', '阳东区', '441704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2031, 2028, '441700', '阳西县', '441721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2032, 2028, '441700', '阳春市', '441781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2033, 1916, '440000', '清远市', '441800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2034, 2033, '441800', '清城区', '441802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2035, 2033, '441800', '清新区', '441803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2036, 2033, '441800', '佛冈县', '441821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2037, 2033, '441800', '阳山县', '441823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2038, 2033, '441800', '连山壮族瑶族自治县', '441825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2039, 2033, '441800', '连南瑶族自治县', '441826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2040, 2033, '441800', '英德市', '441881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2041, 2033, '441800', '连州市', '441882', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2042, 1916, '440000', '东莞市', '441900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2043, 1916, '440000', '中山市', '442000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2044, 1916, '440000', '潮州市', '445100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2045, 2044, '445100', '湘桥区', '445102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2046, 2044, '445100', '潮安区', '445103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2047, 2044, '445100', '饶平县', '445122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2048, 1916, '440000', '揭阳市', '445200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2049, 2048, '445200', '榕城区', '445202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2050, 2048, '445200', '揭东区', '445203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2051, 2048, '445200', '揭西县', '445222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2052, 2048, '445200', '惠来县', '445224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2053, 2048, '445200', '普宁市', '445281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2054, 1916, '440000', '云浮市', '445300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2055, 2054, '445300', '云城区', '445302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2056, 2054, '445300', '云安区', '445303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2057, 2054, '445300', '新兴县', '445321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2058, 2054, '445300', '郁南县', '445322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2059, 2054, '445300', '罗定市', '445381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2060, 0, '450000', '广西壮族自治区', '450000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2061, 2060, '450000', '南宁市', '450100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2062, 2061, '450100', '兴宁区', '450102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2063, 2061, '450100', '青秀区', '450103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2064, 2061, '450100', '江南区', '450105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2065, 2061, '450100', '西乡塘区', '450107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2066, 2061, '450100', '良庆区', '450108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2067, 2061, '450100', '邕宁区', '450109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2068, 2061, '450100', '武鸣区', '450110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2069, 2061, '450100', '隆安县', '450123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2070, 2061, '450100', '马山县', '450124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2071, 2061, '450100', '上林县', '450125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2072, 2061, '450100', '宾阳县', '450126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2073, 2061, '450100', '横州市', '450181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2074, 2060, '450000', '柳州市', '450200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2075, 2074, '450200', '城中区', '450202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2076, 2074, '450200', '鱼峰区', '450203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2077, 2074, '450200', '柳南区', '450204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2078, 2074, '450200', '柳北区', '450205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2079, 2074, '450200', '柳江区', '450206', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2080, 2074, '450200', '柳城县', '450222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2081, 2074, '450200', '鹿寨县', '450223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2082, 2074, '450200', '融安县', '450224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2083, 2074, '450200', '融水苗族自治县', '450225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2084, 2074, '450200', '三江侗族自治县', '450226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2085, 2060, '450000', '桂林市', '450300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2086, 2085, '450300', '秀峰区', '450302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2087, 2085, '450300', '叠彩区', '450303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2088, 2085, '450300', '象山区', '450304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2089, 2085, '450300', '七星区', '450305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2090, 2085, '450300', '雁山区', '450311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2091, 2085, '450300', '临桂区', '450312', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2092, 2085, '450300', '阳朔县', '450321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2093, 2085, '450300', '灵川县', '450323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2094, 2085, '450300', '全州县', '450324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2095, 2085, '450300', '兴安县', '450325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2096, 2085, '450300', '永福县', '450326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2097, 2085, '450300', '灌阳县', '450327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2098, 2085, '450300', '龙胜各族自治县', '450328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2099, 2085, '450300', '资源县', '450329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2100, 2085, '450300', '平乐县', '450330', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2101, 2085, '450300', '恭城瑶族自治县', '450332', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2102, 2085, '450300', '荔浦市', '450381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2103, 2060, '450000', '梧州市', '450400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2104, 2103, '450400', '万秀区', '450403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2105, 2103, '450400', '长洲区', '450405', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2106, 2103, '450400', '龙圩区', '450406', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2107, 2103, '450400', '苍梧县', '450421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2108, 2103, '450400', '藤县', '450422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2109, 2103, '450400', '蒙山县', '450423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2110, 2103, '450400', '岑溪市', '450481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2111, 2060, '450000', '北海市', '450500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2112, 2111, '450500', '海城区', '450502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2113, 2111, '450500', '银海区', '450503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2114, 2111, '450500', '铁山港区', '450512', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2115, 2111, '450500', '合浦县', '450521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2116, 2060, '450000', '防城港市', '450600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2117, 2116, '450600', '港口区', '450602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2118, 2116, '450600', '防城区', '450603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2119, 2116, '450600', '上思县', '450621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2120, 2116, '450600', '东兴市', '450681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2121, 2060, '450000', '钦州市', '450700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2122, 2121, '450700', '钦南区', '450702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2123, 2121, '450700', '钦北区', '450703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2124, 2121, '450700', '灵山县', '450721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2125, 2121, '450700', '浦北县', '450722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2126, 2060, '450000', '贵港市', '450800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2127, 2126, '450800', '港北区', '450802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2128, 2126, '450800', '港南区', '450803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2129, 2126, '450800', '覃塘区', '450804', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2130, 2126, '450800', '平南县', '450821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2131, 2126, '450800', '桂平市', '450881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2132, 2060, '450000', '玉林市', '450900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2133, 2132, '450900', '玉州区', '450902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2134, 2132, '450900', '福绵区', '450903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2135, 2132, '450900', '容县', '450921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2136, 2132, '450900', '陆川县', '450922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2137, 2132, '450900', '博白县', '450923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2138, 2132, '450900', '兴业县', '450924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2139, 2132, '450900', '北流市', '450981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2140, 2060, '450000', '百色市', '451000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2141, 2140, '451000', '右江区', '451002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2142, 2140, '451000', '田阳区', '451003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2143, 2140, '451000', '田东县', '451022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2144, 2140, '451000', '德保县', '451024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2145, 2140, '451000', '那坡县', '451026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2146, 2140, '451000', '凌云县', '451027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2147, 2140, '451000', '乐业县', '451028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2148, 2140, '451000', '田林县', '451029', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2149, 2140, '451000', '西林县', '451030', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2150, 2140, '451000', '隆林各族自治县', '451031', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2151, 2140, '451000', '靖西市', '451081', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2152, 2140, '451000', '平果市', '451082', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2153, 2060, '450000', '贺州市', '451100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2154, 2153, '451100', '八步区', '451102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2155, 2153, '451100', '平桂区', '451103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2156, 2153, '451100', '昭平县', '451121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2157, 2153, '451100', '钟山县', '451122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2158, 2153, '451100', '富川瑶族自治县', '451123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2159, 2060, '450000', '河池市', '451200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2160, 2159, '451200', '金城江区', '451202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2161, 2159, '451200', '宜州区', '451203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2162, 2159, '451200', '南丹县', '451221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2163, 2159, '451200', '天峨县', '451222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2164, 2159, '451200', '凤山县', '451223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2165, 2159, '451200', '东兰县', '451224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2166, 2159, '451200', '罗城仫佬族自治县', '451225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2167, 2159, '451200', '环江毛南族自治县', '451226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2168, 2159, '451200', '巴马瑶族自治县', '451227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2169, 2159, '451200', '都安瑶族自治县', '451228', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2170, 2159, '451200', '大化瑶族自治县', '451229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2171, 2060, '450000', '来宾市', '451300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2172, 2171, '451300', '兴宾区', '451302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2173, 2171, '451300', '忻城县', '451321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2174, 2171, '451300', '象州县', '451322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2175, 2171, '451300', '武宣县', '451323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2176, 2171, '451300', '金秀瑶族自治县', '451324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2177, 2171, '451300', '合山市', '451381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2178, 2060, '450000', '崇左市', '451400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2179, 2178, '451400', '江州区', '451402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2180, 2178, '451400', '扶绥县', '451421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2181, 2178, '451400', '宁明县', '451422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2182, 2178, '451400', '龙州县', '451423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2183, 2178, '451400', '大新县', '451424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2184, 2178, '451400', '天等县', '451425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2185, 2178, '451400', '凭祥市', '451481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2186, 0, '460000', '海南省', '460000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2187, 2186, '460000', '海口市', '460100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2188, 2187, '460100', '秀英区', '460105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2189, 2187, '460100', '龙华区', '460106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2190, 2187, '460100', '琼山区', '460107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2191, 2187, '460100', '美兰区', '460108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2192, 2186, '460000', '三亚市', '460200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2193, 2192, '460200', '海棠区', '460202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2194, 2192, '460200', '吉阳区', '460203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2195, 2192, '460200', '天涯区', '460204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2196, 2192, '460200', '崖州区', '460205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2197, 2186, '460000', '三沙市', '460300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2200, 2186, '460000', '儋州市', '460400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2201, 2186, '460000', '五指山市', '469001', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2202, 2186, '460000', '琼海市', '469002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2203, 2186, '460000', '文昌市', '469005', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2204, 2186, '460000', '万宁市', '469006', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2205, 2186, '460000', '东方市', '469007', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2206, 2186, '460000', '定安县', '469021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2207, 2186, '460000', '屯昌县', '469022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2208, 2186, '460000', '澄迈县', '469023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2209, 2186, '460000', '临高县', '469024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2210, 2186, '460000', '白沙黎族自治县', '469025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2211, 2186, '460000', '昌江黎族自治县', '469026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2212, 2186, '460000', '乐东黎族自治县', '469027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2213, 2186, '460000', '陵水黎族自治县', '469028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2214, 2186, '460000', '保亭黎族苗族自治县', '469029', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2215, 2186, '460000', '琼中黎族苗族自治县', '469030', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2216, 0, '500000', '重庆市', '500000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2217, 2216, '500000', '万州区', '500101', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2218, 2216, '500000', '涪陵区', '500102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2219, 2216, '500000', '渝中区', '500103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2220, 2216, '500000', '大渡口区', '500104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2221, 2216, '500000', '江北区', '500105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2222, 2216, '500000', '沙坪坝区', '500106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2223, 2216, '500000', '九龙坡区', '500107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2224, 2216, '500000', '南岸区', '500108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2225, 2216, '500000', '北碚区', '500109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2226, 2216, '500000', '綦江区', '500110', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2227, 2216, '500000', '大足区', '500111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2228, 2216, '500000', '渝北区', '500112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2229, 2216, '500000', '巴南区', '500113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2230, 2216, '500000', '黔江区', '500114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2231, 2216, '500000', '长寿区', '500115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2232, 2216, '500000', '江津区', '500116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2233, 2216, '500000', '合川区', '500117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2234, 2216, '500000', '永川区', '500118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2235, 2216, '500000', '南川区', '500119', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2236, 2216, '500000', '璧山区', '500120', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2237, 2216, '500000', '铜梁区', '500151', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2238, 2216, '500000', '潼南区', '500152', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2239, 2216, '500000', '荣昌区', '500153', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2240, 2216, '500000', '开州区', '500154', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2241, 2216, '500000', '梁平区', '500155', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2242, 2216, '500000', '武隆区', '500156', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2243, 2216, '500000', '城口县', '500229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2244, 2216, '500000', '丰都县', '500230', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2245, 2216, '500000', '垫江县', '500231', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2246, 2216, '500000', '忠县', '500233', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2247, 2216, '500000', '云阳县', '500235', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2248, 2216, '500000', '奉节县', '500236', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2249, 2216, '500000', '巫山县', '500237', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2250, 2216, '500000', '巫溪县', '500238', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2251, 2216, '500000', '石柱土家族自治县', '500240', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2252, 2216, '500000', '秀山土家族苗族自治县', '500241', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2253, 2216, '500000', '酉阳土家族苗族自治县', '500242', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2254, 2216, '500000', '彭水苗族土家族自治县', '500243', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2255, 0, '510000', '四川省', '510000', 1, 1);
INSERT INTO `t_sys_province_city_area` VALUES (2256, 2255, '510000', '成都市', '510100', 2, 1);
INSERT INTO `t_sys_province_city_area` VALUES (2257, 2256, '510100', '锦江区', '510104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2258, 2256, '510100', '青羊区', '510105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2259, 2256, '510100', '金牛区', '510106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2260, 2256, '510100', '武侯区', '510107', 3, 1);
INSERT INTO `t_sys_province_city_area` VALUES (2261, 2256, '510100', '成华区', '510108', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2262, 2256, '510100', '龙泉驿区', '510112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2263, 2256, '510100', '青白江区', '510113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2264, 2256, '510100', '新都区', '510114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2265, 2256, '510100', '温江区', '510115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2266, 2256, '510100', '双流区', '510116', 3, 1);
INSERT INTO `t_sys_province_city_area` VALUES (2267, 2256, '510100', '郫都区', '510117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2268, 2256, '510100', '新津区', '510118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2269, 2256, '510100', '金堂县', '510121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2270, 2256, '510100', '大邑县', '510129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2271, 2256, '510100', '蒲江县', '510131', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2272, 2256, '510100', '都江堰市', '510181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2273, 2256, '510100', '彭州市', '510182', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2274, 2256, '510100', '邛崃市', '510183', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2275, 2256, '510100', '崇州市', '510184', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2276, 2256, '510100', '简阳市', '510185', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2277, 2255, '510000', '自贡市', '510300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2278, 2277, '510300', '自流井区', '510302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2279, 2277, '510300', '贡井区', '510303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2280, 2277, '510300', '大安区', '510304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2281, 2277, '510300', '沿滩区', '510311', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2282, 2277, '510300', '荣县', '510321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2283, 2277, '510300', '富顺县', '510322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2284, 2255, '510000', '攀枝花市', '510400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2285, 2284, '510400', '东区', '510402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2286, 2284, '510400', '西区', '510403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2287, 2284, '510400', '仁和区', '510411', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2288, 2284, '510400', '米易县', '510421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2289, 2284, '510400', '盐边县', '510422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2290, 2255, '510000', '泸州市', '510500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2291, 2290, '510500', '江阳区', '510502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2292, 2290, '510500', '纳溪区', '510503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2293, 2290, '510500', '龙马潭区', '510504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2294, 2290, '510500', '泸县', '510521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2295, 2290, '510500', '合江县', '510522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2296, 2290, '510500', '叙永县', '510524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2297, 2290, '510500', '古蔺县', '510525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2298, 2255, '510000', '德阳市', '510600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2299, 2298, '510600', '旌阳区', '510603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2300, 2298, '510600', '罗江区', '510604', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2301, 2298, '510600', '中江县', '510623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2302, 2298, '510600', '广汉市', '510681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2303, 2298, '510600', '什邡市', '510682', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2304, 2298, '510600', '绵竹市', '510683', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2305, 2255, '510000', '绵阳市', '510700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2306, 2305, '510700', '涪城区', '510703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2307, 2305, '510700', '游仙区', '510704', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2308, 2305, '510700', '安州区', '510705', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2309, 2305, '510700', '三台县', '510722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2310, 2305, '510700', '盐亭县', '510723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2311, 2305, '510700', '梓潼县', '510725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2312, 2305, '510700', '北川羌族自治县', '510726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2313, 2305, '510700', '平武县', '510727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2314, 2305, '510700', '江油市', '510781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2315, 2255, '510000', '广元市', '510800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2316, 2315, '510800', '利州区', '510802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2317, 2315, '510800', '昭化区', '510811', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2318, 2315, '510800', '朝天区', '510812', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2319, 2315, '510800', '旺苍县', '510821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2320, 2315, '510800', '青川县', '510822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2321, 2315, '510800', '剑阁县', '510823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2322, 2315, '510800', '苍溪县', '510824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2323, 2255, '510000', '遂宁市', '510900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2324, 2323, '510900', '船山区', '510903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2325, 2323, '510900', '安居区', '510904', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2326, 2323, '510900', '蓬溪县', '510921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2327, 2323, '510900', '大英县', '510923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2328, 2323, '510900', '射洪市', '510981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2329, 2255, '510000', '内江市', '511000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2330, 2329, '511000', '市中区', '511002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2331, 2329, '511000', '东兴区', '511011', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2332, 2329, '511000', '威远县', '511024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2333, 2329, '511000', '资中县', '511025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2334, 2329, '511000', '隆昌市', '511083', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2335, 2255, '510000', '乐山市', '511100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2336, 2335, '511100', '市中区', '511102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2337, 2335, '511100', '沙湾区', '511111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2338, 2335, '511100', '五通桥区', '511112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2339, 2335, '511100', '金口河区', '511113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2340, 2335, '511100', '犍为县', '511123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2341, 2335, '511100', '井研县', '511124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2342, 2335, '511100', '夹江县', '511126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2343, 2335, '511100', '沐川县', '511129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2344, 2335, '511100', '峨边彝族自治县', '511132', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2345, 2335, '511100', '马边彝族自治县', '511133', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2346, 2335, '511100', '峨眉山市', '511181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2347, 2255, '510000', '南充市', '511300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2348, 2347, '511300', '顺庆区', '511302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2349, 2347, '511300', '高坪区', '511303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2350, 2347, '511300', '嘉陵区', '511304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2351, 2347, '511300', '南部县', '511321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2352, 2347, '511300', '营山县', '511322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2353, 2347, '511300', '蓬安县', '511323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2354, 2347, '511300', '仪陇县', '511324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2355, 2347, '511300', '西充县', '511325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2356, 2347, '511300', '阆中市', '511381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2357, 2255, '510000', '眉山市', '511400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2358, 2357, '511400', '东坡区', '511402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2359, 2357, '511400', '彭山区', '511403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2360, 2357, '511400', '仁寿县', '511421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2361, 2357, '511400', '洪雅县', '511423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2362, 2357, '511400', '丹棱县', '511424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2363, 2357, '511400', '青神县', '511425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2364, 2255, '510000', '宜宾市', '511500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2365, 2364, '511500', '翠屏区', '511502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2366, 2364, '511500', '南溪区', '511503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2367, 2364, '511500', '叙州区', '511504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2368, 2364, '511500', '江安县', '511523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2369, 2364, '511500', '长宁县', '511524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2370, 2364, '511500', '高县', '511525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2371, 2364, '511500', '珙县', '511526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2372, 2364, '511500', '筠连县', '511527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2373, 2364, '511500', '兴文县', '511528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2374, 2364, '511500', '屏山县', '511529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2375, 2255, '510000', '广安市', '511600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2376, 2375, '511600', '广安区', '511602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2377, 2375, '511600', '前锋区', '511603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2378, 2375, '511600', '岳池县', '511621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2379, 2375, '511600', '武胜县', '511622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2380, 2375, '511600', '邻水县', '511623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2381, 2375, '511600', '华蓥市', '511681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2382, 2255, '510000', '达州市', '511700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2383, 2382, '511700', '通川区', '511702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2384, 2382, '511700', '达川区', '511703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2385, 2382, '511700', '宣汉县', '511722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2386, 2382, '511700', '开江县', '511723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2387, 2382, '511700', '大竹县', '511724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2388, 2382, '511700', '渠县', '511725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2389, 2382, '511700', '万源市', '511781', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2390, 2255, '510000', '雅安市', '511800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2391, 2390, '511800', '雨城区', '511802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2392, 2390, '511800', '名山区', '511803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2393, 2390, '511800', '荥经县', '511822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2394, 2390, '511800', '汉源县', '511823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2395, 2390, '511800', '石棉县', '511824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2396, 2390, '511800', '天全县', '511825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2397, 2390, '511800', '芦山县', '511826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2398, 2390, '511800', '宝兴县', '511827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2399, 2255, '510000', '巴中市', '511900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2400, 2399, '511900', '巴州区', '511902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2401, 2399, '511900', '恩阳区', '511903', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2402, 2399, '511900', '通江县', '511921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2403, 2399, '511900', '南江县', '511922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2404, 2399, '511900', '平昌县', '511923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2405, 2255, '510000', '资阳市', '512000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2406, 2405, '512000', '雁江区', '512002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2407, 2405, '512000', '安岳县', '512021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2408, 2405, '512000', '乐至县', '512022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2409, 2255, '510000', '阿坝藏族羌族自治州', '513200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2410, 2409, '513200', '马尔康市', '513201', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2411, 2409, '513200', '汶川县', '513221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2412, 2409, '513200', '理县', '513222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2413, 2409, '513200', '茂县', '513223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2414, 2409, '513200', '松潘县', '513224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2415, 2409, '513200', '九寨沟县', '513225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2416, 2409, '513200', '金川县', '513226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2417, 2409, '513200', '小金县', '513227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2418, 2409, '513200', '黑水县', '513228', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2419, 2409, '513200', '壤塘县', '513230', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2420, 2409, '513200', '阿坝县', '513231', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2421, 2409, '513200', '若尔盖县', '513232', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2422, 2409, '513200', '红原县', '513233', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2423, 2255, '510000', '甘孜藏族自治州', '513300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2424, 2423, '513300', '康定市', '513301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2425, 2423, '513300', '泸定县', '513322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2426, 2423, '513300', '丹巴县', '513323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2427, 2423, '513300', '九龙县', '513324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2428, 2423, '513300', '雅江县', '513325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2429, 2423, '513300', '道孚县', '513326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2430, 2423, '513300', '炉霍县', '513327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2431, 2423, '513300', '甘孜县', '513328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2432, 2423, '513300', '新龙县', '513329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2433, 2423, '513300', '德格县', '513330', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2434, 2423, '513300', '白玉县', '513331', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2435, 2423, '513300', '石渠县', '513332', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2436, 2423, '513300', '色达县', '513333', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2437, 2423, '513300', '理塘县', '513334', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2438, 2423, '513300', '巴塘县', '513335', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2439, 2423, '513300', '乡城县', '513336', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2440, 2423, '513300', '稻城县', '513337', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2441, 2423, '513300', '得荣县', '513338', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2442, 2255, '510000', '凉山彝族自治州', '513400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2443, 2442, '513400', '西昌市', '513401', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2444, 2442, '513400', '会理市', '513402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2445, 2442, '513400', '木里藏族自治县', '513422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2446, 2442, '513400', '盐源县', '513423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2447, 2442, '513400', '德昌县', '513424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2448, 2442, '513400', '会东县', '513426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2449, 2442, '513400', '宁南县', '513427', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2450, 2442, '513400', '普格县', '513428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2451, 2442, '513400', '布拖县', '513429', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2452, 2442, '513400', '金阳县', '513430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2453, 2442, '513400', '昭觉县', '513431', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2454, 2442, '513400', '喜德县', '513432', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2455, 2442, '513400', '冕宁县', '513433', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2456, 2442, '513400', '越西县', '513434', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2457, 2442, '513400', '甘洛县', '513435', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2458, 2442, '513400', '美姑县', '513436', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2459, 2442, '513400', '雷波县', '513437', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2460, 0, '520000', '贵州省', '520000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2461, 2460, '520000', '贵阳市', '520100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2462, 2461, '520100', '南明区', '520102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2463, 2461, '520100', '云岩区', '520103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2464, 2461, '520100', '花溪区', '520111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2465, 2461, '520100', '乌当区', '520112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2466, 2461, '520100', '白云区', '520113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2467, 2461, '520100', '观山湖区', '520115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2468, 2461, '520100', '开阳县', '520121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2469, 2461, '520100', '息烽县', '520122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2470, 2461, '520100', '修文县', '520123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2471, 2461, '520100', '清镇市', '520181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2472, 2460, '520000', '六盘水市', '520200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2473, 2472, '520200', '钟山区', '520201', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2474, 2472, '520200', '六枝特区', '520203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2475, 2472, '520200', '水城区', '520204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2476, 2472, '520200', '盘州市', '520281', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2477, 2460, '520000', '遵义市', '520300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2478, 2477, '520300', '红花岗区', '520302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2479, 2477, '520300', '汇川区', '520303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2480, 2477, '520300', '播州区', '520304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2481, 2477, '520300', '桐梓县', '520322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2482, 2477, '520300', '绥阳县', '520323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2483, 2477, '520300', '正安县', '520324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2484, 2477, '520300', '道真仡佬族苗族自治县', '520325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2485, 2477, '520300', '务川仡佬族苗族自治县', '520326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2486, 2477, '520300', '凤冈县', '520327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2487, 2477, '520300', '湄潭县', '520328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2488, 2477, '520300', '余庆县', '520329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2489, 2477, '520300', '习水县', '520330', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2490, 2477, '520300', '赤水市', '520381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2491, 2477, '520300', '仁怀市', '520382', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2492, 2460, '520000', '安顺市', '520400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2493, 2492, '520400', '西秀区', '520402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2494, 2492, '520400', '平坝区', '520403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2495, 2492, '520400', '普定县', '520422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2496, 2492, '520400', '镇宁布依族苗族自治县', '520423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2497, 2492, '520400', '关岭布依族苗族自治县', '520424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2498, 2492, '520400', '紫云苗族布依族自治县', '520425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2499, 2460, '520000', '毕节市', '520500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2500, 2499, '520500', '七星关区', '520502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2501, 2499, '520500', '大方县', '520521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2502, 2499, '520500', '金沙县', '520523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2503, 2499, '520500', '织金县', '520524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2504, 2499, '520500', '纳雍县', '520525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2505, 2499, '520500', '威宁彝族回族苗族自治县', '520526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2506, 2499, '520500', '赫章县', '520527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2507, 2499, '520500', '黔西市', '520581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2508, 2460, '520000', '铜仁市', '520600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2509, 2508, '520600', '碧江区', '520602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2510, 2508, '520600', '万山区', '520603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2511, 2508, '520600', '江口县', '520621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2512, 2508, '520600', '玉屏侗族自治县', '520622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2513, 2508, '520600', '石阡县', '520623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2514, 2508, '520600', '思南县', '520624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2515, 2508, '520600', '印江土家族苗族自治县', '520625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2516, 2508, '520600', '德江县', '520626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2517, 2508, '520600', '沿河土家族自治县', '520627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2518, 2508, '520600', '松桃苗族自治县', '520628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2519, 2460, '520000', '黔西南布依族苗族自治州', '522300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2520, 2519, '522300', '兴义市', '522301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2521, 2519, '522300', '兴仁市', '522302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2522, 2519, '522300', '普安县', '522323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2523, 2519, '522300', '晴隆县', '522324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2524, 2519, '522300', '贞丰县', '522325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2525, 2519, '522300', '望谟县', '522326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2526, 2519, '522300', '册亨县', '522327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2527, 2519, '522300', '安龙县', '522328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2528, 2460, '520000', '黔东南苗族侗族自治州', '522600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2529, 2528, '522600', '凯里市', '522601', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2530, 2528, '522600', '黄平县', '522622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2531, 2528, '522600', '施秉县', '522623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2532, 2528, '522600', '三穗县', '522624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2533, 2528, '522600', '镇远县', '522625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2534, 2528, '522600', '岑巩县', '522626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2535, 2528, '522600', '天柱县', '522627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2536, 2528, '522600', '锦屏县', '522628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2537, 2528, '522600', '剑河县', '522629', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2538, 2528, '522600', '台江县', '522630', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2539, 2528, '522600', '黎平县', '522631', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2540, 2528, '522600', '榕江县', '522632', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2541, 2528, '522600', '从江县', '522633', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2542, 2528, '522600', '雷山县', '522634', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2543, 2528, '522600', '麻江县', '522635', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2544, 2528, '522600', '丹寨县', '522636', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2545, 2460, '520000', '黔南布依族苗族自治州', '522700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2546, 2545, '522700', '都匀市', '522701', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2547, 2545, '522700', '福泉市', '522702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2548, 2545, '522700', '荔波县', '522722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2549, 2545, '522700', '贵定县', '522723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2550, 2545, '522700', '瓮安县', '522725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2551, 2545, '522700', '独山县', '522726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2552, 2545, '522700', '平塘县', '522727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2553, 2545, '522700', '罗甸县', '522728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2554, 2545, '522700', '长顺县', '522729', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2555, 2545, '522700', '龙里县', '522730', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2556, 2545, '522700', '惠水县', '522731', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2557, 2545, '522700', '三都水族自治县', '522732', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2558, 0, '530000', '云南省', '530000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2559, 2558, '530000', '昆明市', '530100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2560, 2559, '530100', '五华区', '530102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2561, 2559, '530100', '盘龙区', '530103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2562, 2559, '530100', '官渡区', '530111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2563, 2559, '530100', '西山区', '530112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2564, 2559, '530100', '东川区', '530113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2565, 2559, '530100', '呈贡区', '530114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2566, 2559, '530100', '晋宁区', '530115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2567, 2559, '530100', '富民县', '530124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2568, 2559, '530100', '宜良县', '530125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2569, 2559, '530100', '石林彝族自治县', '530126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2570, 2559, '530100', '嵩明县', '530127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2571, 2559, '530100', '禄劝彝族苗族自治县', '530128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2572, 2559, '530100', '寻甸回族彝族自治县', '530129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2573, 2559, '530100', '安宁市', '530181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2574, 2558, '530000', '曲靖市', '530300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2575, 2574, '530300', '麒麟区', '530302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2576, 2574, '530300', '沾益区', '530303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2577, 2574, '530300', '马龙区', '530304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2578, 2574, '530300', '陆良县', '530322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2579, 2574, '530300', '师宗县', '530323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2580, 2574, '530300', '罗平县', '530324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2581, 2574, '530300', '富源县', '530325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2582, 2574, '530300', '会泽县', '530326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2583, 2574, '530300', '宣威市', '530381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2584, 2558, '530000', '玉溪市', '530400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2585, 2584, '530400', '红塔区', '530402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2586, 2584, '530400', '江川区', '530403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2587, 2584, '530400', '通海县', '530423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2588, 2584, '530400', '华宁县', '530424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2589, 2584, '530400', '易门县', '530425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2590, 2584, '530400', '峨山彝族自治县', '530426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2591, 2584, '530400', '新平彝族傣族自治县', '530427', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2592, 2584, '530400', '元江哈尼族彝族傣族自治县', '530428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2593, 2584, '530400', '澄江市', '530481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2594, 2558, '530000', '保山市', '530500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2595, 2594, '530500', '隆阳区', '530502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2596, 2594, '530500', '施甸县', '530521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2597, 2594, '530500', '龙陵县', '530523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2598, 2594, '530500', '昌宁县', '530524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2599, 2594, '530500', '腾冲市', '530581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2600, 2558, '530000', '昭通市', '530600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2601, 2600, '530600', '昭阳区', '530602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2602, 2600, '530600', '鲁甸县', '530621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2603, 2600, '530600', '巧家县', '530622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2604, 2600, '530600', '盐津县', '530623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2605, 2600, '530600', '大关县', '530624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2606, 2600, '530600', '永善县', '530625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2607, 2600, '530600', '绥江县', '530626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2608, 2600, '530600', '镇雄县', '530627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2609, 2600, '530600', '彝良县', '530628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2610, 2600, '530600', '威信县', '530629', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2611, 2600, '530600', '水富市', '530681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2612, 2558, '530000', '丽江市', '530700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2613, 2612, '530700', '古城区', '530702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2614, 2612, '530700', '玉龙纳西族自治县', '530721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2615, 2612, '530700', '永胜县', '530722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2616, 2612, '530700', '华坪县', '530723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2617, 2612, '530700', '宁蒗彝族自治县', '530724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2618, 2558, '530000', '普洱市', '530800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2619, 2618, '530800', '思茅区', '530802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2620, 2618, '530800', '宁洱哈尼族彝族自治县', '530821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2621, 2618, '530800', '墨江哈尼族自治县', '530822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2622, 2618, '530800', '景东彝族自治县', '530823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2623, 2618, '530800', '景谷傣族彝族自治县', '530824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2624, 2618, '530800', '镇沅彝族哈尼族拉祜族自治县', '530825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2625, 2618, '530800', '江城哈尼族彝族自治县', '530826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2626, 2618, '530800', '孟连傣族拉祜族佤族自治县', '530827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2627, 2618, '530800', '澜沧拉祜族自治县', '530828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2628, 2618, '530800', '西盟佤族自治县', '530829', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2629, 2558, '530000', '临沧市', '530900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2630, 2629, '530900', '临翔区', '530902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2631, 2629, '530900', '凤庆县', '530921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2632, 2629, '530900', '云县', '530922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2633, 2629, '530900', '永德县', '530923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2634, 2629, '530900', '镇康县', '530924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2635, 2629, '530900', '双江拉祜族佤族布朗族傣族自治县', '530925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2636, 2629, '530900', '耿马傣族佤族自治县', '530926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2637, 2629, '530900', '沧源佤族自治县', '530927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2638, 2558, '530000', '楚雄彝族自治州', '532300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2639, 2638, '532300', '楚雄市', '532301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2640, 2638, '532300', '禄丰市', '532302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2641, 2638, '532300', '双柏县', '532322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2642, 2638, '532300', '牟定县', '532323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2643, 2638, '532300', '南华县', '532324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2644, 2638, '532300', '姚安县', '532325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2645, 2638, '532300', '大姚县', '532326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2646, 2638, '532300', '永仁县', '532327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2647, 2638, '532300', '元谋县', '532328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2648, 2638, '532300', '武定县', '532329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2649, 2558, '530000', '红河哈尼族彝族自治州', '532500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2650, 2649, '532500', '个旧市', '532501', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2651, 2649, '532500', '开远市', '532502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2652, 2649, '532500', '蒙自市', '532503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2653, 2649, '532500', '弥勒市', '532504', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2654, 2649, '532500', '屏边苗族自治县', '532523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2655, 2649, '532500', '建水县', '532524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2656, 2649, '532500', '石屏县', '532525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2657, 2649, '532500', '泸西县', '532527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2658, 2649, '532500', '元阳县', '532528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2659, 2649, '532500', '红河县', '532529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2660, 2649, '532500', '金平苗族瑶族傣族自治县', '532530', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2661, 2649, '532500', '绿春县', '532531', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2662, 2649, '532500', '河口瑶族自治县', '532532', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2663, 2558, '530000', '文山壮族苗族自治州', '532600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2664, 2663, '532600', '文山市', '532601', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2665, 2663, '532600', '砚山县', '532622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2666, 2663, '532600', '西畴县', '532623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2667, 2663, '532600', '麻栗坡县', '532624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2668, 2663, '532600', '马关县', '532625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2669, 2663, '532600', '丘北县', '532626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2670, 2663, '532600', '广南县', '532627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2671, 2663, '532600', '富宁县', '532628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2672, 2558, '530000', '西双版纳傣族自治州', '532800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2673, 2672, '532800', '景洪市', '532801', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2674, 2672, '532800', '勐海县', '532822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2675, 2672, '532800', '勐腊县', '532823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2676, 2558, '530000', '大理白族自治州', '532900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2677, 2676, '532900', '大理市', '532901', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2678, 2676, '532900', '漾濞彝族自治县', '532922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2679, 2676, '532900', '祥云县', '532923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2680, 2676, '532900', '宾川县', '532924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2681, 2676, '532900', '弥渡县', '532925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2682, 2676, '532900', '南涧彝族自治县', '532926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2683, 2676, '532900', '巍山彝族回族自治县', '532927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2684, 2676, '532900', '永平县', '532928', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2685, 2676, '532900', '云龙县', '532929', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2686, 2676, '532900', '洱源县', '532930', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2687, 2676, '532900', '剑川县', '532931', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2688, 2676, '532900', '鹤庆县', '532932', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2689, 2558, '530000', '德宏傣族景颇族自治州', '533100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2690, 2689, '533100', '瑞丽市', '533102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2691, 2689, '533100', '芒市', '533103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2692, 2689, '533100', '梁河县', '533122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2693, 2689, '533100', '盈江县', '533123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2694, 2689, '533100', '陇川县', '533124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2695, 2558, '530000', '怒江傈僳族自治州', '533300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2696, 2695, '533300', '泸水市', '533301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2697, 2695, '533300', '福贡县', '533323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2698, 2695, '533300', '贡山独龙族怒族自治县', '533324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2699, 2695, '533300', '兰坪白族普米族自治县', '533325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2700, 2558, '530000', '迪庆藏族自治州', '533400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2701, 2700, '533400', '香格里拉市', '533401', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2702, 2700, '533400', '德钦县', '533422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2703, 2700, '533400', '维西傈僳族自治县', '533423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2704, 0, '540000', '西藏自治区', '540000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2705, 2704, '540000', '拉萨市', '540100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2706, 2705, '540100', '城关区', '540102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2707, 2705, '540100', '堆龙德庆区', '540103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2708, 2705, '540100', '达孜区', '540104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2709, 2705, '540100', '林周县', '540121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2710, 2705, '540100', '当雄县', '540122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2711, 2705, '540100', '尼木县', '540123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2712, 2705, '540100', '曲水县', '540124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2713, 2705, '540100', '墨竹工卡县', '540127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2714, 2704, '540000', '日喀则市', '540200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2715, 2714, '540200', '桑珠孜区', '540202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2716, 2714, '540200', '南木林县', '540221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2717, 2714, '540200', '江孜县', '540222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2718, 2714, '540200', '定日县', '540223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2719, 2714, '540200', '萨迦县', '540224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2720, 2714, '540200', '拉孜县', '540225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2721, 2714, '540200', '昂仁县', '540226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2722, 2714, '540200', '谢通门县', '540227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2723, 2714, '540200', '白朗县', '540228', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2724, 2714, '540200', '仁布县', '540229', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2725, 2714, '540200', '康马县', '540230', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2726, 2714, '540200', '定结县', '540231', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2727, 2714, '540200', '仲巴县', '540232', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2728, 2714, '540200', '亚东县', '540233', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2729, 2714, '540200', '吉隆县', '540234', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2730, 2714, '540200', '聂拉木县', '540235', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2731, 2714, '540200', '萨嘎县', '540236', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2732, 2714, '540200', '岗巴县', '540237', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2733, 2704, '540000', '昌都市', '540300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2734, 2733, '540300', '卡若区', '540302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2735, 2733, '540300', '江达县', '540321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2736, 2733, '540300', '贡觉县', '540322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2737, 2733, '540300', '类乌齐县', '540323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2738, 2733, '540300', '丁青县', '540324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2739, 2733, '540300', '察雅县', '540325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2740, 2733, '540300', '八宿县', '540326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2741, 2733, '540300', '左贡县', '540327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2742, 2733, '540300', '芒康县', '540328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2743, 2733, '540300', '洛隆县', '540329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2744, 2733, '540300', '边坝县', '540330', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2745, 2704, '540000', '林芝市', '540400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2746, 2745, '540400', '巴宜区', '540402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2747, 2745, '540400', '工布江达县', '540421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2748, 2745, '540400', '米林县', '540422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2749, 2745, '540400', '墨脱县', '540423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2750, 2745, '540400', '波密县', '540424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2751, 2745, '540400', '察隅县', '540425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2752, 2745, '540400', '朗县', '540426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2753, 2704, '540000', '山南市', '540500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2754, 2753, '540500', '乃东区', '540502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2755, 2753, '540500', '扎囊县', '540521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2756, 2753, '540500', '贡嘎县', '540522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2757, 2753, '540500', '桑日县', '540523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2758, 2753, '540500', '琼结县', '540524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2759, 2753, '540500', '曲松县', '540525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2760, 2753, '540500', '措美县', '540526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2761, 2753, '540500', '洛扎县', '540527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2762, 2753, '540500', '加查县', '540528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2763, 2753, '540500', '隆子县', '540529', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2764, 2753, '540500', '错那县', '540530', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2765, 2753, '540500', '浪卡子县', '540531', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2766, 2704, '540000', '那曲市', '540600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2767, 2766, '540600', '色尼区', '540602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2768, 2766, '540600', '嘉黎县', '540621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2769, 2766, '540600', '比如县', '540622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2770, 2766, '540600', '聂荣县', '540623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2771, 2766, '540600', '安多县', '540624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2772, 2766, '540600', '申扎县', '540625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2773, 2766, '540600', '索县', '540626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2774, 2766, '540600', '班戈县', '540627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2775, 2766, '540600', '巴青县', '540628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2776, 2766, '540600', '尼玛县', '540629', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2777, 2766, '540600', '双湖县', '540630', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2778, 2704, '540000', '阿里地区', '542500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2779, 2778, '542500', '普兰县', '542521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2780, 2778, '542500', '札达县', '542522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2781, 2778, '542500', '噶尔县', '542523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2782, 2778, '542500', '日土县', '542524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2783, 2778, '542500', '革吉县', '542525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2784, 2778, '542500', '改则县', '542526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2785, 2778, '542500', '措勤县', '542527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2786, 0, '610000', '陕西省', '610000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2787, 2786, '610000', '西安市', '610100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2788, 2787, '610100', '新城区', '610102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2789, 2787, '610100', '碑林区', '610103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2790, 2787, '610100', '莲湖区', '610104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2791, 2787, '610100', '灞桥区', '610111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2792, 2787, '610100', '未央区', '610112', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2793, 2787, '610100', '雁塔区', '610113', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2794, 2787, '610100', '阎良区', '610114', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2795, 2787, '610100', '临潼区', '610115', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2796, 2787, '610100', '长安区', '610116', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2797, 2787, '610100', '高陵区', '610117', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2798, 2787, '610100', '鄠邑区', '610118', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2799, 2787, '610100', '蓝田县', '610122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2800, 2787, '610100', '周至县', '610124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2801, 2786, '610000', '铜川市', '610200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2802, 2801, '610200', '王益区', '610202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2803, 2801, '610200', '印台区', '610203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2804, 2801, '610200', '耀州区', '610204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2805, 2801, '610200', '宜君县', '610222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2806, 2786, '610000', '宝鸡市', '610300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2807, 2806, '610300', '渭滨区', '610302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2808, 2806, '610300', '金台区', '610303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2809, 2806, '610300', '陈仓区', '610304', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2810, 2806, '610300', '凤翔区', '610305', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2811, 2806, '610300', '岐山县', '610323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2812, 2806, '610300', '扶风县', '610324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2813, 2806, '610300', '眉县', '610326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2814, 2806, '610300', '陇县', '610327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2815, 2806, '610300', '千阳县', '610328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2816, 2806, '610300', '麟游县', '610329', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2817, 2806, '610300', '凤县', '610330', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2818, 2806, '610300', '太白县', '610331', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2819, 2786, '610000', '咸阳市', '610400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2820, 2819, '610400', '秦都区', '610402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2821, 2819, '610400', '杨陵区', '610403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2822, 2819, '610400', '渭城区', '610404', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2823, 2819, '610400', '三原县', '610422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2824, 2819, '610400', '泾阳县', '610423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2825, 2819, '610400', '乾县', '610424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2826, 2819, '610400', '礼泉县', '610425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2827, 2819, '610400', '永寿县', '610426', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2828, 2819, '610400', '长武县', '610428', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2829, 2819, '610400', '旬邑县', '610429', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2830, 2819, '610400', '淳化县', '610430', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2831, 2819, '610400', '武功县', '610431', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2832, 2819, '610400', '兴平市', '610481', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2833, 2819, '610400', '彬州市', '610482', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2834, 2786, '610000', '渭南市', '610500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2835, 2834, '610500', '临渭区', '610502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2836, 2834, '610500', '华州区', '610503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2837, 2834, '610500', '潼关县', '610522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2838, 2834, '610500', '大荔县', '610523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2839, 2834, '610500', '合阳县', '610524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2840, 2834, '610500', '澄城县', '610525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2841, 2834, '610500', '蒲城县', '610526', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2842, 2834, '610500', '白水县', '610527', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2843, 2834, '610500', '富平县', '610528', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2844, 2834, '610500', '韩城市', '610581', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2845, 2834, '610500', '华阴市', '610582', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2846, 2786, '610000', '延安市', '610600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2847, 2846, '610600', '宝塔区', '610602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2848, 2846, '610600', '安塞区', '610603', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2849, 2846, '610600', '延长县', '610621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2850, 2846, '610600', '延川县', '610622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2851, 2846, '610600', '志丹县', '610625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2852, 2846, '610600', '吴起县', '610626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2853, 2846, '610600', '甘泉县', '610627', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2854, 2846, '610600', '富县', '610628', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2855, 2846, '610600', '洛川县', '610629', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2856, 2846, '610600', '宜川县', '610630', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2857, 2846, '610600', '黄龙县', '610631', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2858, 2846, '610600', '黄陵县', '610632', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2859, 2846, '610600', '子长市', '610681', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2860, 2786, '610000', '汉中市', '610700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2861, 2860, '610700', '汉台区', '610702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2862, 2860, '610700', '南郑区', '610703', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2863, 2860, '610700', '城固县', '610722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2864, 2860, '610700', '洋县', '610723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2865, 2860, '610700', '西乡县', '610724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2866, 2860, '610700', '勉县', '610725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2867, 2860, '610700', '宁强县', '610726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2868, 2860, '610700', '略阳县', '610727', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2869, 2860, '610700', '镇巴县', '610728', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2870, 2860, '610700', '留坝县', '610729', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2871, 2860, '610700', '佛坪县', '610730', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2872, 2786, '610000', '榆林市', '610800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2873, 2872, '610800', '榆阳区', '610802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2874, 2872, '610800', '横山区', '610803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2875, 2872, '610800', '府谷县', '610822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2876, 2872, '610800', '靖边县', '610824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2877, 2872, '610800', '定边县', '610825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2878, 2872, '610800', '绥德县', '610826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2879, 2872, '610800', '米脂县', '610827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2880, 2872, '610800', '佳县', '610828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2881, 2872, '610800', '吴堡县', '610829', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2882, 2872, '610800', '清涧县', '610830', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2883, 2872, '610800', '子洲县', '610831', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2884, 2872, '610800', '神木市', '610881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2885, 2786, '610000', '安康市', '610900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2886, 2885, '610900', '汉滨区', '610902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2887, 2885, '610900', '汉阴县', '610921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2888, 2885, '610900', '石泉县', '610922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2889, 2885, '610900', '宁陕县', '610923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2890, 2885, '610900', '紫阳县', '610924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2891, 2885, '610900', '岚皋县', '610925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2892, 2885, '610900', '平利县', '610926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2893, 2885, '610900', '镇坪县', '610927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2894, 2885, '610900', '白河县', '610929', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2895, 2885, '610900', '旬阳市', '610981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2896, 2786, '610000', '商洛市', '611000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2897, 2896, '611000', '商州区', '611002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2898, 2896, '611000', '洛南县', '611021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2899, 2896, '611000', '丹凤县', '611022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2900, 2896, '611000', '商南县', '611023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2901, 2896, '611000', '山阳县', '611024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2902, 2896, '611000', '镇安县', '611025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2903, 2896, '611000', '柞水县', '611026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2904, 0, '620000', '甘肃省', '620000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2905, 2904, '620000', '兰州市', '620100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2906, 2905, '620100', '城关区', '620102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2907, 2905, '620100', '七里河区', '620103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2908, 2905, '620100', '西固区', '620104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2909, 2905, '620100', '安宁区', '620105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2910, 2905, '620100', '红古区', '620111', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2911, 2905, '620100', '永登县', '620121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2912, 2905, '620100', '皋兰县', '620122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2913, 2905, '620100', '榆中县', '620123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2914, 2904, '620000', '嘉峪关市', '620200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2915, 2904, '620000', '金昌市', '620300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2916, 2915, '620300', '金川区', '620302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2917, 2915, '620300', '永昌县', '620321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2918, 2904, '620000', '白银市', '620400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2919, 2918, '620400', '白银区', '620402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2920, 2918, '620400', '平川区', '620403', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2921, 2918, '620400', '靖远县', '620421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2922, 2918, '620400', '会宁县', '620422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2923, 2918, '620400', '景泰县', '620423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2924, 2904, '620000', '天水市', '620500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2925, 2924, '620500', '秦州区', '620502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2926, 2924, '620500', '麦积区', '620503', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2927, 2924, '620500', '清水县', '620521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2928, 2924, '620500', '秦安县', '620522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2929, 2924, '620500', '甘谷县', '620523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2930, 2924, '620500', '武山县', '620524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2931, 2924, '620500', '张家川回族自治县', '620525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2932, 2904, '620000', '武威市', '620600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2933, 2932, '620600', '凉州区', '620602', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2934, 2932, '620600', '民勤县', '620621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2935, 2932, '620600', '古浪县', '620622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2936, 2932, '620600', '天祝藏族自治县', '620623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2937, 2904, '620000', '张掖市', '620700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2938, 2937, '620700', '甘州区', '620702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2939, 2937, '620700', '肃南裕固族自治县', '620721', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2940, 2937, '620700', '民乐县', '620722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2941, 2937, '620700', '临泽县', '620723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2942, 2937, '620700', '高台县', '620724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2943, 2937, '620700', '山丹县', '620725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2944, 2904, '620000', '平凉市', '620800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2945, 2944, '620800', '崆峒区', '620802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2946, 2944, '620800', '泾川县', '620821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2947, 2944, '620800', '灵台县', '620822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2948, 2944, '620800', '崇信县', '620823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2949, 2944, '620800', '庄浪县', '620825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2950, 2944, '620800', '静宁县', '620826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2951, 2944, '620800', '华亭市', '620881', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2952, 2904, '620000', '酒泉市', '620900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2953, 2952, '620900', '肃州区', '620902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2954, 2952, '620900', '金塔县', '620921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2955, 2952, '620900', '瓜州县', '620922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2956, 2952, '620900', '肃北蒙古族自治县', '620923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2957, 2952, '620900', '阿克塞哈萨克族自治县', '620924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2958, 2952, '620900', '玉门市', '620981', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2959, 2952, '620900', '敦煌市', '620982', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2960, 2904, '620000', '庆阳市', '621000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2961, 2960, '621000', '西峰区', '621002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2962, 2960, '621000', '庆城县', '621021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2963, 2960, '621000', '环县', '621022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2964, 2960, '621000', '华池县', '621023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2965, 2960, '621000', '合水县', '621024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2966, 2960, '621000', '正宁县', '621025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2967, 2960, '621000', '宁县', '621026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2968, 2960, '621000', '镇原县', '621027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2969, 2904, '620000', '定西市', '621100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2970, 2969, '621100', '安定区', '621102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2971, 2969, '621100', '通渭县', '621121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2972, 2969, '621100', '陇西县', '621122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2973, 2969, '621100', '渭源县', '621123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2974, 2969, '621100', '临洮县', '621124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2975, 2969, '621100', '漳县', '621125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2976, 2969, '621100', '岷县', '621126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2977, 2904, '620000', '陇南市', '621200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2978, 2977, '621200', '武都区', '621202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2979, 2977, '621200', '成县', '621221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2980, 2977, '621200', '文县', '621222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2981, 2977, '621200', '宕昌县', '621223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2982, 2977, '621200', '康县', '621224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2983, 2977, '621200', '西和县', '621225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2984, 2977, '621200', '礼县', '621226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2985, 2977, '621200', '徽县', '621227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2986, 2977, '621200', '两当县', '621228', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2987, 2904, '620000', '临夏回族自治州', '622900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2988, 2987, '622900', '临夏市', '622901', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2989, 2987, '622900', '临夏县', '622921', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2990, 2987, '622900', '康乐县', '622922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2991, 2987, '622900', '永靖县', '622923', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2992, 2987, '622900', '广河县', '622924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2993, 2987, '622900', '和政县', '622925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2994, 2987, '622900', '东乡族自治县', '622926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2995, 2987, '622900', '积石山保安族东乡族撒拉族自治县', '622927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2996, 2904, '620000', '甘南藏族自治州', '623000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2997, 2996, '623000', '合作市', '623001', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2998, 2996, '623000', '临潭县', '623021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (2999, 2996, '623000', '卓尼县', '623022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3000, 2996, '623000', '舟曲县', '623023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3001, 2996, '623000', '迭部县', '623024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3002, 2996, '623000', '玛曲县', '623025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3003, 2996, '623000', '碌曲县', '623026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3004, 2996, '623000', '夏河县', '623027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3005, 0, '630000', '青海省', '630000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3006, 3005, '630000', '西宁市', '630100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3007, 3006, '630100', '城东区', '630102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3008, 3006, '630100', '城中区', '630103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3009, 3006, '630100', '城西区', '630104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3010, 3006, '630100', '城北区', '630105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3011, 3006, '630100', '湟中区', '630106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3012, 3006, '630100', '大通回族土族自治县', '630121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3013, 3006, '630100', '湟源县', '630123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3014, 3005, '630000', '海东市', '630200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3015, 3014, '630200', '乐都区', '630202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3016, 3014, '630200', '平安区', '630203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3017, 3014, '630200', '民和回族土族自治县', '630222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3018, 3014, '630200', '互助土族自治县', '630223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3019, 3014, '630200', '化隆回族自治县', '630224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3020, 3014, '630200', '循化撒拉族自治县', '630225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3021, 3005, '630000', '海北藏族自治州', '632200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3022, 3021, '632200', '门源回族自治县', '632221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3023, 3021, '632200', '祁连县', '632222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3024, 3021, '632200', '海晏县', '632223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3025, 3021, '632200', '刚察县', '632224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3026, 3005, '630000', '黄南藏族自治州', '632300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3027, 3026, '632300', '同仁市', '632301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3028, 3026, '632300', '尖扎县', '632322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3029, 3026, '632300', '泽库县', '632323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3030, 3026, '632300', '河南蒙古族自治县', '632324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3031, 3005, '630000', '海南藏族自治州', '632500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3032, 3031, '632500', '共和县', '632521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3033, 3031, '632500', '同德县', '632522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3034, 3031, '632500', '贵德县', '632523', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3035, 3031, '632500', '兴海县', '632524', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3036, 3031, '632500', '贵南县', '632525', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3037, 3005, '630000', '果洛藏族自治州', '632600', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3038, 3037, '632600', '玛沁县', '632621', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3039, 3037, '632600', '班玛县', '632622', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3040, 3037, '632600', '甘德县', '632623', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3041, 3037, '632600', '达日县', '632624', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3042, 3037, '632600', '久治县', '632625', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3043, 3037, '632600', '玛多县', '632626', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3044, 3005, '630000', '玉树藏族自治州', '632700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3045, 3044, '632700', '玉树市', '632701', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3046, 3044, '632700', '杂多县', '632722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3047, 3044, '632700', '称多县', '632723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3048, 3044, '632700', '治多县', '632724', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3049, 3044, '632700', '囊谦县', '632725', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3050, 3044, '632700', '曲麻莱县', '632726', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3051, 3005, '630000', '海西蒙古族藏族自治州', '632800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3052, 3051, '632800', '格尔木市', '632801', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3053, 3051, '632800', '德令哈市', '632802', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3054, 3051, '632800', '茫崖市', '632803', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3055, 3051, '632800', '乌兰县', '632821', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3056, 3051, '632800', '都兰县', '632822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3057, 3051, '632800', '天峻县', '632823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3058, 0, '640000', '宁夏回族自治区', '640000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3059, 3058, '640000', '银川市', '640100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3060, 3059, '640100', '兴庆区', '640104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3061, 3059, '640100', '西夏区', '640105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3062, 3059, '640100', '金凤区', '640106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3063, 3059, '640100', '永宁县', '640121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3064, 3059, '640100', '贺兰县', '640122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3065, 3059, '640100', '灵武市', '640181', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3066, 3058, '640000', '石嘴山市', '640200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3067, 3066, '640200', '大武口区', '640202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3068, 3066, '640200', '惠农区', '640205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3069, 3066, '640200', '平罗县', '640221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3070, 3058, '640000', '吴忠市', '640300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3071, 3070, '640300', '利通区', '640302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3072, 3070, '640300', '红寺堡区', '640303', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3073, 3070, '640300', '盐池县', '640323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3074, 3070, '640300', '同心县', '640324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3075, 3070, '640300', '青铜峡市', '640381', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3076, 3058, '640000', '固原市', '640400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3077, 3076, '640400', '原州区', '640402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3078, 3076, '640400', '西吉县', '640422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3079, 3076, '640400', '隆德县', '640423', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3080, 3076, '640400', '泾源县', '640424', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3081, 3076, '640400', '彭阳县', '640425', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3082, 3058, '640000', '中卫市', '640500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3083, 3082, '640500', '沙坡头区', '640502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3084, 3082, '640500', '中宁县', '640521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3085, 3082, '640500', '海原县', '640522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3086, 0, '650000', '新疆维吾尔自治区', '650000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3087, 3086, '650000', '乌鲁木齐市', '650100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3088, 3087, '650100', '天山区', '650102', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3089, 3087, '650100', '沙依巴克区', '650103', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3090, 3087, '650100', '新市区', '650104', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3091, 3087, '650100', '水磨沟区', '650105', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3092, 3087, '650100', '头屯河区', '650106', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3093, 3087, '650100', '达坂城区', '650107', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3094, 3087, '650100', '米东区', '650109', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3095, 3087, '650100', '乌鲁木齐县', '650121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3096, 3086, '650000', '克拉玛依市', '650200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3097, 3096, '650200', '独山子区', '650202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3098, 3096, '650200', '克拉玛依区', '650203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3099, 3096, '650200', '白碱滩区', '650204', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3100, 3096, '650200', '乌尔禾区', '650205', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3101, 3086, '650000', '吐鲁番市', '650400', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3102, 3101, '650400', '高昌区', '650402', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3103, 3101, '650400', '鄯善县', '650421', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3104, 3101, '650400', '托克逊县', '650422', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3105, 3086, '650000', '哈密市', '650500', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3106, 3105, '650500', '伊州区', '650502', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3107, 3105, '650500', '巴里坤哈萨克自治县', '650521', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3108, 3105, '650500', '伊吾县', '650522', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3109, 3086, '650000', '昌吉回族自治州', '652300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3110, 3109, '652300', '昌吉市', '652301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3111, 3109, '652300', '阜康市', '652302', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3112, 3109, '652300', '呼图壁县', '652323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3113, 3109, '652300', '玛纳斯县', '652324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3114, 3109, '652300', '奇台县', '652325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3115, 3109, '652300', '吉木萨尔县', '652327', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3116, 3109, '652300', '木垒哈萨克自治县', '652328', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3117, 3086, '650000', '博尔塔拉蒙古自治州', '652700', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3118, 3117, '652700', '博乐市', '652701', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3119, 3117, '652700', '阿拉山口市', '652702', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3120, 3117, '652700', '精河县', '652722', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3121, 3117, '652700', '温泉县', '652723', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3122, 3086, '650000', '巴音郭楞蒙古自治州', '652800', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3123, 3122, '652800', '库尔勒市', '652801', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3124, 3122, '652800', '轮台县', '652822', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3125, 3122, '652800', '尉犁县', '652823', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3126, 3122, '652800', '若羌县', '652824', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3127, 3122, '652800', '且末县', '652825', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3128, 3122, '652800', '焉耆回族自治县', '652826', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3129, 3122, '652800', '和静县', '652827', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3130, 3122, '652800', '和硕县', '652828', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3131, 3122, '652800', '博湖县', '652829', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3132, 3086, '650000', '阿克苏地区', '652900', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3133, 3132, '652900', '阿克苏市', '652901', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3134, 3132, '652900', '库车市', '652902', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3135, 3132, '652900', '温宿县', '652922', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3136, 3132, '652900', '沙雅县', '652924', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3137, 3132, '652900', '新和县', '652925', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3138, 3132, '652900', '拜城县', '652926', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3139, 3132, '652900', '乌什县', '652927', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3140, 3132, '652900', '阿瓦提县', '652928', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3141, 3132, '652900', '柯坪县', '652929', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3142, 3086, '650000', '克孜勒苏柯尔克孜自治州', '653000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3143, 3142, '653000', '阿图什市', '653001', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3144, 3142, '653000', '阿克陶县', '653022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3145, 3142, '653000', '阿合奇县', '653023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3146, 3142, '653000', '乌恰县', '653024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3147, 3086, '650000', '喀什地区', '653100', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3148, 3147, '653100', '喀什市', '653101', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3149, 3147, '653100', '疏附县', '653121', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3150, 3147, '653100', '疏勒县', '653122', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3151, 3147, '653100', '英吉沙县', '653123', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3152, 3147, '653100', '泽普县', '653124', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3153, 3147, '653100', '莎车县', '653125', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3154, 3147, '653100', '叶城县', '653126', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3155, 3147, '653100', '麦盖提县', '653127', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3156, 3147, '653100', '岳普湖县', '653128', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3157, 3147, '653100', '伽师县', '653129', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3158, 3147, '653100', '巴楚县', '653130', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3159, 3147, '653100', '塔什库尔干塔吉克自治县', '653131', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3160, 3086, '650000', '和田地区', '653200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3161, 3160, '653200', '和田市', '653201', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3162, 3160, '653200', '和田县', '653221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3163, 3160, '653200', '墨玉县', '653222', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3164, 3160, '653200', '皮山县', '653223', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3165, 3160, '653200', '洛浦县', '653224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3166, 3160, '653200', '策勒县', '653225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3167, 3160, '653200', '于田县', '653226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3168, 3160, '653200', '民丰县', '653227', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3169, 3086, '650000', '伊犁哈萨克自治州', '654000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3170, 3169, '654000', '伊宁市', '654002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3171, 3169, '654000', '奎屯市', '654003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3172, 3169, '654000', '霍尔果斯市', '654004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3173, 3169, '654000', '伊宁县', '654021', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3174, 3169, '654000', '察布查尔锡伯自治县', '654022', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3175, 3169, '654000', '霍城县', '654023', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3176, 3169, '654000', '巩留县', '654024', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3177, 3169, '654000', '新源县', '654025', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3178, 3169, '654000', '昭苏县', '654026', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3179, 3169, '654000', '特克斯县', '654027', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3180, 3169, '654000', '尼勒克县', '654028', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3181, 3086, '650000', '塔城地区', '654200', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3182, 3181, '654200', '塔城市', '654201', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3183, 3181, '654200', '乌苏市', '654202', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3184, 3181, '654200', '沙湾市', '654203', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3185, 3181, '654200', '额敏县', '654221', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3186, 3181, '654200', '托里县', '654224', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3187, 3181, '654200', '裕民县', '654225', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3188, 3181, '654200', '和布克赛尔蒙古自治县', '654226', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3189, 3086, '650000', '阿勒泰地区', '654300', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3190, 3189, '654300', '阿勒泰市', '654301', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3191, 3189, '654300', '布尔津县', '654321', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3192, 3189, '654300', '富蕴县', '654322', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3193, 3189, '654300', '福海县', '654323', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3194, 3189, '654300', '哈巴河县', '654324', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3195, 3189, '654300', '青河县', '654325', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3196, 3189, '654300', '吉木乃县', '654326', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3197, 3086, '650000', '石河子市', '659001', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3198, 3086, '650000', '阿拉尔市', '659002', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3199, 3086, '650000', '图木舒克市', '659003', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3200, 3086, '650000', '五家渠市', '659004', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3201, 3086, '650000', '北屯市', '659005', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3202, 3086, '650000', '铁门关市', '659006', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3203, 3086, '650000', '双河市', '659007', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3204, 3086, '650000', '可克达拉市', '659008', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3205, 3086, '650000', '昆玉市', '659009', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3206, 3086, '650000', '胡杨河市', '659010', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3207, 3086, '650000', '新星市', '659011', 3, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3208, 0, '710000', '台湾省', '710000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3209, 0, '810000', '香港特别行政区', '810000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (3210, 0, '820000', '澳门特别行政区', '820000', 1, 0);
INSERT INTO `t_sys_province_city_area` VALUES (10001, 1, '110000', '北京市', '110000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (10018, 18, '120000', '天津市', '120000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (10779, 779, '310000', '上海市', '310000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (12216, 2216, '500000', '重庆市', '500000', 2, 0);
INSERT INTO `t_sys_province_city_area` VALUES (13210, 3210, '820000', '澳门特别行政区', '820000', 2, 0);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `parent_id` int(11) NOT NULL COMMENT '父ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态(1:开启 0:禁用)',
  `is_fixed` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否固定(0->否 1->是)',
  `sort` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '排序',
  `is_refresh_all_tenant` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否刷新所有租户权限数据(0->否 1->是)',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色管理表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES (1, 1, 0, '超级管理员', 'super_admin', 1, 1, 1, b'0', 1, '2020-08-22 15:01:51', 1, '2023-10-21 17:24:42');
INSERT INTO `t_sys_role` VALUES (2, 1, 0, '系统管理员', 'system_admin', 1, 1, 2, b'0', 1, '2023-10-20 18:03:26', 1, '2023-10-21 17:24:42');
INSERT INTO `t_sys_role` VALUES (11, 1, 0, '租户管理员', 'tenant_admin', 1, 1, 3, b'1', 1, '2023-10-13 15:09:04', 2, '2023-10-23 10:20:23');
INSERT INTO `t_sys_role` VALUES (28, 1, 0, '测试同步角色', 'sync_role_test', 1, 1, 100, b'1', 2, '2023-10-23 16:28:29', 2, '2023-10-23 16:31:46');
INSERT INTO `t_sys_role` VALUES (30, 3, 0, '租户管理员', 'tenant_admin', 1, 1, 3, b'1', 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');
INSERT INTO `t_sys_role` VALUES (31, 3, 0, '系统管理员', 'system_admin', 1, 1, 2, b'0', 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');
INSERT INTO `t_sys_role` VALUES (32, 3, 0, '测试同步角色', 'sync_role_test', 1, 1, 100, b'1', 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1515 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色菜单关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_menu
-- ----------------------------
INSERT INTO `t_sys_role_menu` VALUES (1, 1, 1, 1, 1, '2023-08-31 16:36:56', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (2, 1, 1, 3, 1, '2023-08-31 16:36:56', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (3, 1, 1, 4, 1, '2023-08-31 16:36:56', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (4, 1, 1, 5, 1, '2023-08-31 16:36:56', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (5, 1, 1, 6, 1, '2023-08-31 16:36:56', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (6, 1, 1, 7, 1, '2023-08-31 16:36:56', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (28, 1, 1, 30, 1, '2023-09-28 11:50:35', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (50, 1, 1, 33, 1, '2023-10-09 18:39:53', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (81, 1, 10, 1, 1, '2023-10-13 15:14:50', 1, '2023-10-18 20:18:56');
INSERT INTO `t_sys_role_menu` VALUES (101, 1, 10, 3, 1, '2023-10-13 16:53:09', 1, '2023-10-18 20:18:56');
INSERT INTO `t_sys_role_menu` VALUES (102, 1, 10, 4, 1, '2023-10-13 16:53:09', 1, '2023-10-18 20:18:56');
INSERT INTO `t_sys_role_menu` VALUES (141, 1, 1, 45, 1, '2023-10-17 16:01:23', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (142, 1, 1, 46, 1, '2023-10-17 16:01:50', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (143, 1, 1, 47, 1, '2023-10-17 16:02:10', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (144, 1, 1, 44, 1, '2023-10-17 16:22:04', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (190, 1, 11, 1, 1, '2023-10-20 17:49:44', 1, '2023-10-21 17:05:32');
INSERT INTO `t_sys_role_menu` VALUES (192, 1, 11, 3, 1, '2023-10-20 17:49:44', 1, '2023-10-21 17:05:32');
INSERT INTO `t_sys_role_menu` VALUES (193, 1, 11, 4, 1, '2023-10-20 17:49:44', 1, '2023-10-21 17:05:32');
INSERT INTO `t_sys_role_menu` VALUES (208, 1, 1, 2, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (209, 1, 1, 9, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (210, 1, 1, 10, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (211, 1, 1, 11, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (212, 1, 1, 12, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (213, 1, 1, 13, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (214, 1, 1, 14, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:24');
INSERT INTO `t_sys_role_menu` VALUES (215, 1, 1, 15, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (216, 1, 1, 16, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (217, 1, 1, 17, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (218, 1, 1, 18, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (219, 1, 1, 19, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (220, 1, 1, 20, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (221, 1, 1, 21, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (222, 1, 1, 22, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (223, 1, 1, 23, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (224, 1, 1, 24, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (225, 1, 1, 25, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (226, 1, 1, 27, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (227, 1, 1, 28, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (228, 1, 1, 29, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (229, 1, 1, 31, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (230, 1, 1, 32, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (231, 1, 1, 34, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (232, 1, 1, 40, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (233, 1, 1, 48, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (234, 1, 1, 50, 0, '2023-10-20 17:54:47', 0, '2023-10-23 09:28:25');
INSERT INTO `t_sys_role_menu` VALUES (235, 1, 2, 1, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:51');
INSERT INTO `t_sys_role_menu` VALUES (240, 1, 2, 3, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (241, 1, 2, 4, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (242, 1, 2, 5, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (243, 1, 2, 44, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (244, 1, 2, 45, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (245, 1, 2, 46, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (246, 1, 2, 47, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (247, 1, 2, 6, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (248, 1, 2, 7, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (249, 1, 2, 33, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (252, 1, 2, 35, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (253, 1, 2, 36, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (254, 1, 2, 37, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (255, 1, 2, 38, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (256, 1, 2, 39, 1, '2023-10-20 18:12:51', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (261, 1, 2, 2, 1, '2023-10-20 19:08:20', 1, '2023-10-21 17:29:51');
INSERT INTO `t_sys_role_menu` VALUES (262, 1, 2, 32, 1, '2023-10-20 19:08:20', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (263, 1, 2, 31, 1, '2023-10-20 19:08:20', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (264, 1, 2, 41, 1, '2023-10-20 19:08:20', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (265, 1, 2, 30, 1, '2023-10-20 19:08:20', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (266, 1, 2, 50, 1, '2023-10-20 19:08:20', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (269, 1, 2, 24, 1, '2023-10-21 13:28:41', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (273, 1, 2, 51, 1, '2023-10-21 14:33:45', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (277, 1, 2, 52, 1, '2023-10-21 17:05:19', 1, '2023-10-21 17:29:52');
INSERT INTO `t_sys_role_menu` VALUES (1509, 3, 30, 1, 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');
INSERT INTO `t_sys_role_menu` VALUES (1510, 3, 30, 3, 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');
INSERT INTO `t_sys_role_menu` VALUES (1511, 3, 30, 4, 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');
INSERT INTO `t_sys_role_menu` VALUES (1512, 1, 1, 51, 1, '2023-12-01 17:39:14', 1, '2023-12-01 17:39:14');
INSERT INTO `t_sys_role_menu` VALUES (1513, 1, 1, 52, 1, '2023-12-01 17:43:58', 1, '2023-12-01 17:43:58');
INSERT INTO `t_sys_role_menu` VALUES (1514, 1, 1, 53, 1, '2023-12-05 15:10:33', 1, '2023-12-05 15:10:33');

-- ----------------------------
-- Table structure for t_sys_role_scope
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_scope`;
CREATE TABLE `t_sys_role_scope`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `scope_id` int(11) NOT NULL COMMENT '数据权限ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-角色&数据权限关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_role_scope
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_scope_data
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_scope_data`;
CREATE TABLE `t_sys_scope_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  `scope_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `scope_column` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限字段',
  `scope_visible_field` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '可见字段',
  `scope_class` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全权限类名',
  `scope_type` tinyint(4) NOT NULL COMMENT '规则类型(1:全部可见 2:本人可见 3:所在部门可见 4:所在部门及子级可见 5:自定义)',
  `scope_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规则值',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-数据权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_scope_data
-- ----------------------------
INSERT INTO `t_sys_scope_data` VALUES (2, 6, '角色仅自己可见', 'create_by', '*', 'com.zhengqing.system.mapper.SysRoleMapper#selectDataList', 2, '1', '1', 1, '2023-10-18 14:46:54', 1, '2023-10-19 14:49:07', 0);
INSERT INTO `t_sys_scope_data` VALUES (4, 33, '部门数据仅所在部门可见', 'dept_id', '*', 'com.zhengqing.system.mapper.SysDeptMapper.selectDataList', 3, '', '', 1, '2023-10-18 16:33:19', 1, '2023-10-18 19:52:50', 0);
INSERT INTO `t_sys_scope_data` VALUES (8, 5, '【用户管理】仅部门可见', 'dept_id', '*', 'com.zhengqing.system.mapper.SysUserMapper#selectDataList', 3, '', '', 1, '2023-10-18 16:33:18', 1, '2023-10-19 18:50:12', 0);
INSERT INTO `t_sys_scope_data` VALUES (10, 6, '【角色】所在角色以及下级角色可见', 'role_id', '*', 'com.zhengqing.system.mapper.SysRoleMapper#selectDataList', 6, NULL, NULL, 1, '2023-10-19 19:05:59', 1, '2023-10-19 19:05:59', 0);

-- ----------------------------
-- Table structure for t_sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_tenant`;
CREATE TABLE `t_sys_tenant`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名',
  `admin_user_id` int(11) NULL DEFAULT NULL COMMENT '管理员用户id',
  `admin_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员名称',
  `admin_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员手机号',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户状态(0->停用 1->开启)',
  `package_id` int(11) NOT NULL COMMENT '租户套餐ID',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `account_count` int(11) NOT NULL COMMENT '账号数量',
  `sort` int(11) NOT NULL COMMENT '排序',
  `create_by` bigint(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(11) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-租户信息' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_tenant
-- ----------------------------
INSERT INTO `t_sys_tenant` VALUES (1, 'SmallBoot', 1, '郑清', '15183308888', 1, 1, '3023-10-08 05:02:06', 999, 1, 1, '2023-10-08 17:02:17', 1, '2023-11-02 19:05:29', b'0');
INSERT INTO `t_sys_tenant` VALUES (3, '测试租户', 25, '管理员', '15186668888', 1, 4, '2023-10-28 12:00:00', 10, 100, 2, '2023-10-23 16:31:46', 1, '2023-12-01 17:39:25', b'0');

-- ----------------------------
-- Table structure for t_sys_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_tenant_package`;
CREATE TABLE `t_sys_tenant_package`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '套餐名',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态(1:开启 0:禁用)',
  `menu_id_list` json NOT NULL COMMENT '关联的菜单ids',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(11) NOT NULL COMMENT '排序',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-租户套餐' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_tenant_package
-- ----------------------------
INSERT INTO `t_sys_tenant_package` VALUES (1, '超级套餐', 1, '[1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 44, 45, 46, 47, 48, 50]', '包含系统所有操作权限', 1, 1, '2023-10-08 14:45:41', 0, '2023-10-20 17:30:37', b'0');
INSERT INTO `t_sys_tenant_package` VALUES (4, '普通套餐', 1, '[1, 3, 4]', '普通套餐', 4, 1, '2023-10-08 15:33:45', 1, '2023-11-02 18:43:46', b'0');

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `sex` tinyint(2) NULL DEFAULT 0 COMMENT '性别(0:未知 1:男  2:女)',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `post_id_list` json NULL COMMENT '岗位ids',
  `is_fixed` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否固定(0->否 1->是)',
  `mini_user_id` bigint(20) NULL DEFAULT NULL COMMENT '小程序用户id',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户基础信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES (1, 1, 'superadmin', '3014dcb9ee3639535d5d9301b32c840c', '超级管理员', 1, '15188888888', 'zhengqingya@it.com', 'http://127.0.0.1:886/2023-09-28/1707276699923857408-美图35.jpg', NULL, '[]', 1, 1, 1, '2020-08-22 15:01:51', 1, '2023-10-21 14:47:52', 0);
INSERT INTO `t_sys_user` VALUES (2, 1, 'admin', '3014dcb9ee3639535d5d9301b32c840c', '系统管理员', 0, '', '', 'http://127.0.0.1:886/2023-10-08/1710975930417258496-小知识.jpg', NULL, '[1]', 1, NULL, 1, '2020-08-22 15:01:51', 2, '2023-10-23 10:30:19', 0);
INSERT INTO `t_sys_user` VALUES (25, 3, 'admin', '3014dcb9ee3639535d5d9301b32c840c', '管理员', 0, '15186668888', NULL, NULL, 14, NULL, 1, NULL, 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46', 0);

-- ----------------------------
-- Table structure for t_sys_user_re_oauth
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_re_oauth`;
CREATE TABLE `t_sys_user_re_oauth`  (
  `user_re_oauth_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `user_id` int(11) NOT NULL COMMENT '用户id（关联表`t_sys_user`字段`user_id`）',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '三方id',
  `oauth_type` tinyint(4) NOT NULL COMMENT '第三方授权类型',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_re_oauth_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户三方授权表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user_re_oauth
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_by` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-用户角色关联表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES (1, 1, 1, 1, 0, '2023-08-31 15:54:57', 0, '2023-10-23 10:12:01');
INSERT INTO `t_sys_user_role` VALUES (10, 1, 8, 10, 1, '2023-10-13 15:16:12', 1, '2023-10-13 15:16:12');
INSERT INTO `t_sys_user_role` VALUES (11, 1, 9, 10, 1, '2023-10-13 15:20:22', 1, '2023-10-13 15:20:22');
INSERT INTO `t_sys_user_role` VALUES (12, 1, 10, 10, 1, '2023-10-13 15:37:31', 1, '2023-10-13 15:37:31');
INSERT INTO `t_sys_user_role` VALUES (13, 1, 11, 10, 1, '2023-10-13 15:54:44', 1, '2023-10-13 15:54:44');
INSERT INTO `t_sys_user_role` VALUES (28, 1, 2, 2, 1, '2023-10-20 19:36:21', 1, '2023-10-20 19:37:39');
INSERT INTO `t_sys_user_role` VALUES (47, 3, 25, 30, 2, '2023-10-23 16:31:46', 2, '2023-10-23 16:31:46');

-- ----------------------------
-- Table structure for t_sys_version
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_version`;
CREATE TABLE `t_sys_version`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态',
  `app_audit_result_list` json NULL COMMENT '小程序审核结果',
  `remark` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '类型(1:抖音小程序)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统管理-版本记录' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of t_sys_version
-- ----------------------------

-- ----------------------------
-- Table structure for t_wx_mp_account
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_account`;
CREATE TABLE `t_wx_mp_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
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
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-账号' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_account
-- ----------------------------
INSERT INTO `t_wx_mp_account` VALUES (1, 1, '郑清的测试账号', 1, 'gh_42e1dbad014e', 'wxe01d9bde2cc81b89', 'f292d6cb69755a7105863d97910a9579', '/wx/mp/portal/wxe01d9bde2cc81b89', 'test', '4J9GOBZ4VTElUFm0EvRDV6aJu5spvYXGkn1RJela56U', 'http://mmbiz.qpic.cn/mmbiz_jpg/8ytq3xBRtdjkBkb2JfibmdJCkT4t2ZSxo8PBwzZ6cgic64mMVicxibpqAyQY0kuiaHhMc3Yjh5J2ATy8kkQgfzbIFJA/0', '2023-03-16 15:39:45', '2023-10-08 18:27:30', 1, 1);

-- ----------------------------
-- Table structure for t_wx_mp_msg_auto_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_msg_auto_reply`;
CREATE TABLE `t_wx_mp_msg_auto_reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AppID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `type` tinyint(1) NOT NULL COMMENT '类型（1：关注时回复；2：关键词回复）',
  `match_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关键词',
  `is_exact_match` tinyint(1) NULL DEFAULT 1 COMMENT '是否精确匹配（0：否；1：是）',
  `reply_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）',
  `reply_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复消息内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-消息自动回复' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_msg_auto_reply
-- ----------------------------
INSERT INTO `t_wx_mp_msg_auto_reply` VALUES (1, 1, 'wxe01d9bde2cc81b89', '关注', 1, '', 1, 'text', '谢谢关注！', '2023-03-20 17:54:30', '2023-10-08 18:27:53', 1, 1);
INSERT INTO `t_wx_mp_msg_auto_reply` VALUES (2, 1, 'wxe01d9bde2cc81b89', '文本消息', 2, 'hello', 1, 'text', '自动回复：你好', '2023-03-20 19:30:47', '2023-10-08 18:27:53', 1, 1);
INSERT INTO `t_wx_mp_msg_auto_reply` VALUES (3, 1, 'wxe01d9bde2cc81b89', '图片消息', 2, '图片', 1, 'image', 'hm_a1Quvy6P39bspNEXRaIGBhVqBzDfHKxRCKhAZEtK7pyYDG2Hjc4B2L7Yg-p57', '2023-03-21 15:12:44', '2023-10-08 18:27:53', 1, 1);

-- ----------------------------
-- Table structure for t_wx_mp_template_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_template_msg`;
CREATE TABLE `t_wx_mp_template_msg`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AppID',
  `template_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板标题',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板内容',
  `data_list` json NOT NULL COMMENT '模板数据',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-模板消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_template_msg
-- ----------------------------
INSERT INTO `t_wx_mp_template_msg` VALUES (15, 1, 'wxe01d9bde2cc81b89', 'alASmcPvowZ2ZgaK2BWMWHMvsnXyz_tP_vQH5EEY8Kc', '早上好，单身狗☀', '{{txt1.DATA}}\n\n单身狗所在地区：{{city.DATA}}\n日期：{{date.DATA}} {{week.DATA}}\n\n{{txt2.DATA}}\n\n天气：{{wea.DATA}}\n最高气温：{{tem1.DATA}}\n最低气温：{{tem2.DATA}}\n风向：{{win.DATA}}\n风力：{{win_speed.DATA}}\n风速：{{win_meter.DATA}}\n湿度：{{humidity.DATA}}\n能见度：{{visibility.DATA}}\n气压：{{pressure.DATA}}\n空气质量：{{air.DATA}}\npm2.5含量：{{air_pm25.DATA}}\n空气等级：{{air_level.DATA}}\n温馨小提示：{{air_tips.DATA}}\n\n{{end1.DATA}}\n{{end2.DATA}}\n\n{{author.DATA}}', '[{\"name\": \"txt1\", \"color\": \"#20B2AA\", \"value\": \"今天心情不好，我问小狗怎么办，小狗说：汪汪汪☀☀☀☀☀☀☀☀☀☀☀☀☀\"}, {\"name\": \"city\", \"color\": \"#F093FB\", \"value\": \"四川成都\"}, {\"name\": \"date\", \"color\": \"#F093FB\", \"value\": \"2023-03-22\"}, {\"name\": \"week\", \"color\": \"#F093FB\", \"value\": \"星期三\"}, {\"name\": \"txt2\", \"color\": \"#1E90FF\", \"value\": \"单身狗出门也要看下今天的天气状况哦╰(*°▽°*)╯\"}, {\"name\": \"wea\", \"color\": \"#000\", \"value\": \"晴\"}, {\"name\": \"tem1\", \"color\": \"#000\", \"value\": \"25°\"}, {\"name\": \"tem2\", \"color\": \"#000\", \"value\": \"20°\"}, {\"name\": \"win\", \"color\": \"#000\", \"value\": \"南风\"}, {\"name\": \"win_speed\", \"color\": \"#000\", \"value\": \"2级\"}, {\"name\": \"win_meter\", \"color\": \"#000\", \"value\": \"6km/h\"}, {\"name\": \"humidity\", \"color\": \"#000\", \"value\": \"44%\"}, {\"name\": \"visibility\", \"color\": \"#000\", \"value\": \"10km\"}, {\"name\": \"pressure\", \"color\": \"#000\", \"value\": \"1000\"}, {\"name\": \"air\", \"color\": \"#000\", \"value\": \"60\"}, {\"name\": \"air_pm25\", \"color\": \"#000\", \"value\": \"31\"}, {\"name\": \"air_level\", \"color\": \"#000\", \"value\": \"良\"}, {\"name\": \"air_tips\", \"color\": \"#000\", \"value\": \"空气好，可以外出活动!\"}, {\"name\": \"end1\", \"color\": \"#FF4500\", \"value\": \"How are you still single? \"}, {\"name\": \"end2\", \"color\": \"#FF4500\", \"value\": \"你怎么还单着？\"}, {\"name\": \"author\", \"color\": \"#DDA0DD\", \"value\": \"zhengqingya\"}]', '2023-03-21 18:52:15', '2023-10-08 18:28:22', 1, 1, 0);
INSERT INTO `t_wx_mp_template_msg` VALUES (16, 1, 'wxe01d9bde2cc81b89', 'xEUCJF0EhbiHCIttO17lyXXmCxJHX6TeI9Sq7m6WV_I', 'test', '{{txt1.DATA}}\n\n所在地区：{{city.DATA}}', '[{\"name\": \"txt1\", \"color\": \"#E60E0E\", \"value\": \"测试\"}, {\"name\": \"city\", \"color\": \"#15F4D6\", \"value\": \"四川成都\"}]', '2023-03-21 18:52:15', '2023-10-08 18:28:22', 1, 1, 0);

-- ----------------------------
-- Table structure for t_wx_mp_user
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_mp_user`;
CREATE TABLE `t_wx_mp_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `app_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'AppID',
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信openid',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `head_img_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像',
  `subscribe_scene` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户关注的渠道来源：ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_OTHERS 其他',
  `subscribe_time` datetime NOT NULL COMMENT '关注时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信公众号-用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_wx_mp_user
-- ----------------------------
INSERT INTO `t_wx_mp_user` VALUES (8, 1, 'wxe01d9bde2cc81b89', 'ojplN5nVM2H4c2yXFGKVrOVPrclM', '', '', 'ADD_SCENE_QR_CODE', '2023-08-16 09:44:41', '2023-08-28 18:27:16', '2023-10-08 18:28:34', 1, 1);
INSERT INTO `t_wx_mp_user` VALUES (9, 1, 'wxe01d9bde2cc81b89', 'ojplN5krzEif5V-Lv1iklr4_Re-s', '', '', 'ADD_SCENE_QR_CODE', '2023-06-15 16:18:21', '2023-08-28 18:27:16', '2023-10-08 18:28:34', 1, 1);
INSERT INTO `t_wx_mp_user` VALUES (10, 1, 'wxe01d9bde2cc81b89', 'ojplN5tMax4tNacU3tKeWCnL7qEU', '', '', 'ADD_SCENE_QR_CODE', '2023-03-20 19:34:19', '2023-08-28 18:27:16', '2023-10-08 18:28:34', 1, 1);

-- ----------------------------
-- Table structure for ums_user
-- ----------------------------
DROP TABLE IF EXISTS `ums_user`;
CREATE TABLE `ums_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) NOT NULL COMMENT '租户ID',
  `openid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'openid',
  `unionid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'unionid',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(0:未知 1:男  2:女)',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `type` tinyint(4) NOT NULL COMMENT '小程序类型（1:微信 2:抖音）',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AppID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint(20) UNSIGNED NOT NULL COMMENT '创建人',
  `update_by` bigint(20) UNSIGNED NOT NULL COMMENT '修改人',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_user
-- ----------------------------
INSERT INTO `ums_user` VALUES (1, 1, 'oT_ym5AicCHtMBq_yeo0JLZ8GDEY', NULL, '郑清', '15183388881', 0, '2003-12-16', 'http://127.0.0.1:886/2023-10-08/1710975930417258496-小知识.jpg', 1, NULL, '2023-04-10 15:13:33', '2023-10-21 13:42:05', 0, 0, 0);

-- ----------------------------
-- Table structure for wf_category
-- ----------------------------
DROP TABLE IF EXISTS `wf_category`;
CREATE TABLE `wf_category`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '编码',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `create_by` bigint(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工作流-分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wf_category
-- ----------------------------

-- ----------------------------
-- Table structure for wf_copy
-- ----------------------------
DROP TABLE IF EXISTS `wf_copy`;
CREATE TABLE `wf_copy`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '抄送标题',
  `process_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '流程主键',
  `process_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '流程名称',
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '流程分类主键',
  `deployment_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部署主键',
  `instance_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '流程实例主键',
  `task_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '任务主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户主键',
  `originator_id` bigint(20) NULL DEFAULT NULL COMMENT '发起人主键',
  `originator_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '发起人名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` bigint(20) UNSIGNED NOT NULL DEFAULT '' COMMENT '创建人id',
  `update_by` bigint(20) UNSIGNED NOT NULL DEFAULT '' COMMENT '更新人id',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(0->否,1->是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工作流-抄送' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wf_copy
-- ----------------------------

-- ----------------------------
-- Table structure for wf_deploy_form
-- ----------------------------
DROP TABLE IF EXISTS `wf_deploy_form`;
CREATE TABLE `wf_deploy_form`  (
  `deploy_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程实例主键',
  `form_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表单Key',
  `node_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '节点Key',
  `form_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '表单名称',
  `node_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '节点名称',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表单内容',
  PRIMARY KEY (`deploy_id`, `form_key`, `node_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工作流-流程关联表单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wf_deploy_form
-- ----------------------------

-- ----------------------------
-- Table structure for wf_form
-- ----------------------------
DROP TABLE IF EXISTS `wf_form`;
CREATE TABLE `wf_form`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` int(11) UNSIGNED NOT NULL COMMENT '租户ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '名称',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` bigint(20) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(20) NOT NULL DEFAULT '' COMMENT '修改人',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1->是，0->否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工作流-表单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wf_form
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
