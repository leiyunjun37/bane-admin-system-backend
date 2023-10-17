/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : sys_backend

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 17/10/2023 17:47:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_group
-- ----------------------------
DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_group
-- ----------------------------

-- ----------------------------
-- Table structure for auth_group_permissions
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_permissions`;
CREATE TABLE `auth_group_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auth_group_permissions_group_id_permission_id_0cd325b0_uniq`(`group_id`, `permission_id`) USING BTREE,
  INDEX `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm`(`permission_id`) USING BTREE,
  CONSTRAINT `auth_group_permissio_permission_id_84c5c92e_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `auth_group_permissions_group_id_b120cbf9_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_group_permissions
-- ----------------------------

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content_type_id` int(11) NOT NULL,
  `codename` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auth_permission_content_type_id_codename_01ab375a_uniq`(`content_type_id`, `codename`) USING BTREE,
  CONSTRAINT `auth_permission_content_type_id_2f476e4b_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
INSERT INTO `auth_permission` VALUES (1, 'Can add log entry', 1, 'add_logentry');
INSERT INTO `auth_permission` VALUES (2, 'Can change log entry', 1, 'change_logentry');
INSERT INTO `auth_permission` VALUES (3, 'Can delete log entry', 1, 'delete_logentry');
INSERT INTO `auth_permission` VALUES (4, 'Can view log entry', 1, 'view_logentry');
INSERT INTO `auth_permission` VALUES (5, 'Can add permission', 2, 'add_permission');
INSERT INTO `auth_permission` VALUES (6, 'Can change permission', 2, 'change_permission');
INSERT INTO `auth_permission` VALUES (7, 'Can delete permission', 2, 'delete_permission');
INSERT INTO `auth_permission` VALUES (8, 'Can view permission', 2, 'view_permission');
INSERT INTO `auth_permission` VALUES (9, 'Can add group', 3, 'add_group');
INSERT INTO `auth_permission` VALUES (10, 'Can change group', 3, 'change_group');
INSERT INTO `auth_permission` VALUES (11, 'Can delete group', 3, 'delete_group');
INSERT INTO `auth_permission` VALUES (12, 'Can view group', 3, 'view_group');
INSERT INTO `auth_permission` VALUES (13, 'Can add user', 4, 'add_user');
INSERT INTO `auth_permission` VALUES (14, 'Can change user', 4, 'change_user');
INSERT INTO `auth_permission` VALUES (15, 'Can delete user', 4, 'delete_user');
INSERT INTO `auth_permission` VALUES (16, 'Can view user', 4, 'view_user');
INSERT INTO `auth_permission` VALUES (17, 'Can add content type', 5, 'add_contenttype');
INSERT INTO `auth_permission` VALUES (18, 'Can change content type', 5, 'change_contenttype');
INSERT INTO `auth_permission` VALUES (19, 'Can delete content type', 5, 'delete_contenttype');
INSERT INTO `auth_permission` VALUES (20, 'Can view content type', 5, 'view_contenttype');
INSERT INTO `auth_permission` VALUES (21, 'Can add session', 6, 'add_session');
INSERT INTO `auth_permission` VALUES (22, 'Can change session', 6, 'change_session');
INSERT INTO `auth_permission` VALUES (23, 'Can delete session', 6, 'delete_session');
INSERT INTO `auth_permission` VALUES (24, 'Can view session', 6, 'view_session');
INSERT INTO `auth_permission` VALUES (25, 'Can add 商品表', 7, 'add_productinfo');
INSERT INTO `auth_permission` VALUES (26, 'Can change 商品表', 7, 'change_productinfo');
INSERT INTO `auth_permission` VALUES (27, 'Can delete 商品表', 7, 'delete_productinfo');
INSERT INTO `auth_permission` VALUES (28, 'Can view 商品表', 7, 'view_productinfo');
INSERT INTO `auth_permission` VALUES (29, 'Can add 用户表', 8, 'add_userinfo');
INSERT INTO `auth_permission` VALUES (30, 'Can change 用户表', 8, 'change_userinfo');
INSERT INTO `auth_permission` VALUES (31, 'Can delete 用户表', 8, 'delete_userinfo');
INSERT INTO `auth_permission` VALUES (32, 'Can view 用户表', 8, 'view_userinfo');
INSERT INTO `auth_permission` VALUES (33, 'Can add 宠物表', 9, 'add_petsinfo');
INSERT INTO `auth_permission` VALUES (34, 'Can change 宠物表', 9, 'change_petsinfo');
INSERT INTO `auth_permission` VALUES (35, 'Can delete 宠物表', 9, 'delete_petsinfo');
INSERT INTO `auth_permission` VALUES (36, 'Can view 宠物表', 9, 'view_petsinfo');
INSERT INTO `auth_permission` VALUES (37, 'Can add vip用户表', 10, 'add_vipguestsinfo');
INSERT INTO `auth_permission` VALUES (38, 'Can change vip用户表', 10, 'change_vipguestsinfo');
INSERT INTO `auth_permission` VALUES (39, 'Can delete vip用户表', 10, 'delete_vipguestsinfo');
INSERT INTO `auth_permission` VALUES (40, 'Can view vip用户表', 10, 'view_vipguestsinfo');
INSERT INTO `auth_permission` VALUES (41, 'Can add AI测试结果表', 11, 'add_aicheckresultinfo');
INSERT INTO `auth_permission` VALUES (42, 'Can change AI测试结果表', 11, 'change_aicheckresultinfo');
INSERT INTO `auth_permission` VALUES (43, 'Can delete AI测试结果表', 11, 'delete_aicheckresultinfo');
INSERT INTO `auth_permission` VALUES (44, 'Can view AI测试结果表', 11, 'view_aicheckresultinfo');

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_login` datetime(6) NULL DEFAULT NULL,
  `is_superuser` tinyint(1) NOT NULL,
  `username` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `first_name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `last_name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(254) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_staff` tinyint(1) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `date_joined` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user
-- ----------------------------

-- ----------------------------
-- Table structure for auth_user_groups
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_groups`;
CREATE TABLE `auth_user_groups`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auth_user_groups_user_id_group_id_94350c0c_uniq`(`user_id`, `group_id`) USING BTREE,
  INDEX `auth_user_groups_group_id_97559544_fk_auth_group_id`(`group_id`) USING BTREE,
  CONSTRAINT `auth_user_groups_group_id_97559544_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `auth_user_groups_user_id_6a12ed8b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user_groups
-- ----------------------------

-- ----------------------------
-- Table structure for auth_user_user_permissions
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_user_permissions`;
CREATE TABLE `auth_user_user_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `auth_user_user_permissions_user_id_permission_id_14a6b632_uniq`(`user_id`, `permission_id`) USING BTREE,
  INDEX `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm`(`permission_id`) USING BTREE,
  CONSTRAINT `auth_user_user_permi_permission_id_1fbb5f2c_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `auth_user_user_permissions_user_id_a95ead1b_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_user_user_permissions
-- ----------------------------

-- ----------------------------
-- Table structure for django_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `django_admin_log`;
CREATE TABLE `django_admin_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_time` datetime(6) NOT NULL,
  `object_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `object_repr` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `action_flag` smallint(5) UNSIGNED NOT NULL,
  `change_message` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content_type_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `django_admin_log_content_type_id_c4bce8eb_fk_django_co`(`content_type_id`) USING BTREE,
  INDEX `django_admin_log_user_id_c564eba6_fk_auth_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `django_admin_log_content_type_id_c4bce8eb_fk_django_co` FOREIGN KEY (`content_type_id`) REFERENCES `django_content_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `django_admin_log_user_id_c564eba6_fk_auth_user_id` FOREIGN KEY (`user_id`) REFERENCES `auth_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_admin_log
-- ----------------------------

-- ----------------------------
-- Table structure for django_content_type
-- ----------------------------
DROP TABLE IF EXISTS `django_content_type`;
CREATE TABLE `django_content_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `model` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `django_content_type_app_label_model_76bd3d3b_uniq`(`app_label`, `model`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_content_type
-- ----------------------------
INSERT INTO `django_content_type` VALUES (1, 'admin', 'logentry');
INSERT INTO `django_content_type` VALUES (3, 'auth', 'group');
INSERT INTO `django_content_type` VALUES (2, 'auth', 'permission');
INSERT INTO `django_content_type` VALUES (4, 'auth', 'user');
INSERT INTO `django_content_type` VALUES (5, 'contenttypes', 'contenttype');
INSERT INTO `django_content_type` VALUES (11, 'myapp', 'aicheckresultinfo');
INSERT INTO `django_content_type` VALUES (9, 'mybackend', 'petsinfo');
INSERT INTO `django_content_type` VALUES (7, 'mybackend', 'productinfo');
INSERT INTO `django_content_type` VALUES (8, 'mybackend', 'userinfo');
INSERT INTO `django_content_type` VALUES (10, 'mybackend', 'vipguestsinfo');
INSERT INTO `django_content_type` VALUES (6, 'sessions', 'session');

-- ----------------------------
-- Table structure for django_migrations
-- ----------------------------
DROP TABLE IF EXISTS `django_migrations`;
CREATE TABLE `django_migrations`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `applied` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_migrations
-- ----------------------------
INSERT INTO `django_migrations` VALUES (1, 'contenttypes', '0001_initial', '2023-08-29 07:16:49.026918');
INSERT INTO `django_migrations` VALUES (2, 'auth', '0001_initial', '2023-08-29 07:16:50.034226');
INSERT INTO `django_migrations` VALUES (3, 'admin', '0001_initial', '2023-08-29 07:16:50.221749');
INSERT INTO `django_migrations` VALUES (4, 'admin', '0002_logentry_remove_auto_add', '2023-08-29 07:16:50.230700');
INSERT INTO `django_migrations` VALUES (5, 'admin', '0003_logentry_add_action_flag_choices', '2023-08-29 07:16:50.242701');
INSERT INTO `django_migrations` VALUES (6, 'contenttypes', '0002_remove_content_type_name', '2023-08-29 07:16:50.383293');
INSERT INTO `django_migrations` VALUES (7, 'auth', '0002_alter_permission_name_max_length', '2023-08-29 07:16:50.468100');
INSERT INTO `django_migrations` VALUES (8, 'auth', '0003_alter_user_email_max_length', '2023-08-29 07:16:50.587746');
INSERT INTO `django_migrations` VALUES (9, 'auth', '0004_alter_user_username_opts', '2023-08-29 07:16:50.598750');
INSERT INTO `django_migrations` VALUES (10, 'auth', '0005_alter_user_last_login_null', '2023-08-29 07:16:50.689502');
INSERT INTO `django_migrations` VALUES (11, 'auth', '0006_require_contenttypes_0002', '2023-08-29 07:16:50.695458');
INSERT INTO `django_migrations` VALUES (12, 'auth', '0007_alter_validators_add_error_messages', '2023-08-29 07:16:50.706429');
INSERT INTO `django_migrations` VALUES (13, 'auth', '0008_alter_user_username_max_length', '2023-08-29 07:16:50.805208');
INSERT INTO `django_migrations` VALUES (14, 'auth', '0009_alter_user_last_name_max_length', '2023-08-29 07:16:50.906929');
INSERT INTO `django_migrations` VALUES (15, 'auth', '0010_alter_group_name_max_length', '2023-08-29 07:16:51.014604');
INSERT INTO `django_migrations` VALUES (16, 'auth', '0011_update_proxy_permissions', '2023-08-29 07:16:51.026614');
INSERT INTO `django_migrations` VALUES (17, 'auth', '0012_alter_user_first_name_max_length', '2023-08-29 07:16:51.108386');
INSERT INTO `django_migrations` VALUES (18, 'sessions', '0001_initial', '2023-08-29 07:16:51.156250');
INSERT INTO `django_migrations` VALUES (19, 'mybackend', '0001_initial', '2023-08-29 08:14:38.028494');
INSERT INTO `django_migrations` VALUES (20, 'mybackend', '0002_petsinfo_vipguestsinfo_alter_productinfo_comment', '2023-08-30 03:46:22.851349');
INSERT INTO `django_migrations` VALUES (21, 'mybackend', '0003_petsinfo_is_delete_vipguestsinfo_is_delete_and_more', '2023-08-30 06:06:42.835252');
INSERT INTO `django_migrations` VALUES (22, 'myapp', '0001_initial', '2023-08-31 08:47:09.549777');
INSERT INTO `django_migrations` VALUES (23, 'mybackend', '0004_alter_userinfo_is_delete_alter_userinfo_management', '2023-09-23 09:54:50.386537');
INSERT INTO `django_migrations` VALUES (24, 'mybackend', '0005_remove_vipguestsinfo_account_vipguestsinfo_pets_and_more', '2023-09-29 13:21:24.108943');
INSERT INTO `django_migrations` VALUES (25, 'mybackend', '0006_alter_vipguestsinfo_table', '2023-09-29 13:21:24.123938');
INSERT INTO `django_migrations` VALUES (26, 'mybackend', '0007_alter_vipguestsinfo_table', '2023-09-29 13:21:24.138593');
INSERT INTO `django_migrations` VALUES (27, 'mybackend', '0008_alter_petsinfo_variety', '2023-09-29 13:38:56.713151');
INSERT INTO `django_migrations` VALUES (28, 'mybackend', '0009_alter_vipguestsinfo_name', '2023-09-29 13:47:31.407271');
INSERT INTO `django_migrations` VALUES (29, 'mybackend', '0010_alter_petsinfo_owner_alter_petsinfo_petname_and_more', '2023-09-29 13:48:30.730098');
INSERT INTO `django_migrations` VALUES (30, 'mybackend', '0011_remove_vipguestsinfo_pets', '2023-10-01 15:26:37.409847');
INSERT INTO `django_migrations` VALUES (31, 'mybackend', '0012_alter_userinfo_managers_userinfo_date_joined_and_more', '2023-10-07 03:57:06.020172');
INSERT INTO `django_migrations` VALUES (32, 'mybackend', '0013_alter_userinfo_username', '2023-10-07 04:14:17.640158');

-- ----------------------------
-- Table structure for django_session
-- ----------------------------
DROP TABLE IF EXISTS `django_session`;
CREATE TABLE `django_session`  (
  `session_key` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `session_data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expire_date` datetime(6) NOT NULL,
  PRIMARY KEY (`session_key`) USING BTREE,
  INDEX `django_session_expire_date_a5c62663`(`expire_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of django_session
-- ----------------------------

-- ----------------------------
-- Table structure for loginlog
-- ----------------------------
DROP TABLE IF EXISTS `loginlog`;
CREATE TABLE `loginlog`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `logintime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of loginlog
-- ----------------------------
INSERT INTO `loginlog` VALUES (1, 'lyj', '2023-10-17 17:35:50', 0);
INSERT INTO `loginlog` VALUES (2, 'lyj', '2023-10-17 17:40:18', 0);

-- ----------------------------
-- Table structure for orderecord
-- ----------------------------
DROP TABLE IF EXISTS `orderecord`;
CREATE TABLE `orderecord`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_vipguest` int(1) NOT NULL,
  `comment` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `datetime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderecord
-- ----------------------------

-- ----------------------------
-- Table structure for pets
-- ----------------------------
DROP TABLE IF EXISTS `pets`;
CREATE TABLE `pets`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `petname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `owner` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `age` int(11) NOT NULL,
  `variety` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pets
-- ----------------------------
INSERT INTO `pets` VALUES (1, '张三', 'vip777', 2, '藏獒', 0);
INSERT INTO `pets` VALUES (2, '王五', 'vip888', 1, '-', 0);
INSERT INTO `pets` VALUES (3, '老六', 'vip888', 1, '-', 0);
INSERT INTO `pets` VALUES (4, '111', 'cece', 1, '-', 1);
INSERT INTO `pets` VALUES (5, '123', 'vip2', 5, '金毛', 0);
INSERT INTO `pets` VALUES (6, 'csdelete', 'vip888', 2, '123', 0);
INSERT INTO `pets` VALUES (7, '123', 'vip666', 1, '123333', 0);
INSERT INTO `pets` VALUES (8, 'eeeeee', 'vip666', 4, '1222222', 1);
INSERT INTO `pets` VALUES (9, 'fffffff', 'vip777', 4, '1232233333', 1);
INSERT INTO `pets` VALUES (10, '1231', 'cscscsc', 1, '-', 1);
INSERT INTO `pets` VALUES (11, 'jdkww', 'vip9', 6, '1231312', 0);

-- ----------------------------
-- Table structure for petserve
-- ----------------------------
DROP TABLE IF EXISTS `petserve`;
CREATE TABLE `petserve`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `serveName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(255) NOT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of petserve
-- ----------------------------
INSERT INTO `petserve` VALUES (1, 'sssoosos', 12, '3333', '----', 1);
INSERT INTO `petserve` VALUES (2, 'cscs', 40, '元/斤', '1313123123', 0);

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` int(11) NOT NULL,
  `inventory` int(11) NOT NULL,
  `comment` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of products
-- ----------------------------
INSERT INTO `products` VALUES (1, 'cscscscs', 111, 31, '121121', 0);
INSERT INTO `products` VALUES (2, 'cscscs', 123, 10, '12312312312', 1);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `management` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lastlogin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(11) NOT NULL,
  `encryptedpassword` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `users_username_e8658fc8_uniq`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (3, 'admin', '123456', '管理员', '2023-08-29 08:24:34.399744', 0, 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `users` VALUES (4, 'lyj', '123456', '普通用户', '2023-08-29 09:33:27.412310', 0, 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `users` VALUES (5, 'yjlei', 'admin@111222', '管理员', '2023-08-29 09:35:50.682418', 1, '13c71fb7a09090401b5c26d8ebc7d676');
INSERT INTO `users` VALUES (6, 'springcs', 'admin@123', '管理员', '-', 0, 'e6e061838856bf47e1de730719fb2609');
INSERT INTO `users` VALUES (7, 'plus', 'adminmmmm@111', '管理员', '-', 0, '144335f69a1712d3c25c6134f549dbcf');

-- ----------------------------
-- Table structure for users_groups
-- ----------------------------
DROP TABLE IF EXISTS `users_groups`;
CREATE TABLE `users_groups`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userinfo_id` bigint(20) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `users_groups_userinfo_id_group_id_68b303ac_uniq`(`userinfo_id`, `group_id`) USING BTREE,
  INDEX `users_groups_group_id_2f3517aa_fk_auth_group_id`(`group_id`) USING BTREE,
  CONSTRAINT `users_groups_group_id_2f3517aa_fk_auth_group_id` FOREIGN KEY (`group_id`) REFERENCES `auth_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_groups_userinfo_id_1a6144c7_fk_users_id` FOREIGN KEY (`userinfo_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_groups
-- ----------------------------

-- ----------------------------
-- Table structure for users_user_permissions
-- ----------------------------
DROP TABLE IF EXISTS `users_user_permissions`;
CREATE TABLE `users_user_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userinfo_id` bigint(20) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `users_user_permissions_userinfo_id_permission_id_a1b0ef65_uniq`(`userinfo_id`, `permission_id`) USING BTREE,
  INDEX `users_user_permissio_permission_id_6d08dcd2_fk_auth_perm`(`permission_id`) USING BTREE,
  CONSTRAINT `users_user_permissio_permission_id_6d08dcd2_fk_auth_perm` FOREIGN KEY (`permission_id`) REFERENCES `auth_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_user_permissions_userinfo_id_0ad2cdda_fk_users_id` FOREIGN KEY (`userinfo_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users_user_permissions
-- ----------------------------

-- ----------------------------
-- Table structure for vipguests
-- ----------------------------
DROP TABLE IF EXISTS `vipguests`;
CREATE TABLE `vipguests`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `balance` int(11) NOT NULL,
  `conway` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lastshop` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registertime` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `is_delete` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vipguests
-- ----------------------------
INSERT INTO `vipguests` VALUES (1, 'vip1', 230, '微信:1234567', '洗浴', '2023-08-09', 0);
INSERT INTO `vipguests` VALUES (2, 'vip2', 111, '电话:123456', '6786', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (3, 'vip3', 333, '微信:123123131', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (4, 'vip4', 333, '3333333', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (5, 'vip5', 333, '3333333', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (6, 'vip6', 333, '3333333', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (7, 'vip7', 333, '3333333', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (8, 'vip8', 333, '3333333', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (9, 'vip9', 333, '3333333', '3333', '2023-09-09', 0);
INSERT INTO `vipguests` VALUES (10, 'vip10', 333, '3333333', '3333', '2023-09-09', 1);
INSERT INTO `vipguests` VALUES (11, 'vip11', 333, '3333333', '3333', '2023-09-09', 1);
INSERT INTO `vipguests` VALUES (12, 'vip12', 123, '微信:12331', '-', '2023-09-28', 1);
INSERT INTO `vipguests` VALUES (13, 'vip666', 150, '微信:12321312', '-', '2023-09-29', 0);
INSERT INTO `vipguests` VALUES (14, 'vip777', 150, '微信:23131', '-', '2023-09-29', 0);
INSERT INTO `vipguests` VALUES (15, 'vip888', 150, '微信:dsam89823', '-', '2023-09-29', 0);
INSERT INTO `vipguests` VALUES (16, '777', 0, '微信:234234', '-', '2023-10-02', 1);
INSERT INTO `vipguests` VALUES (17, 'cece', 999, '微信:1231231231', '-', '-', 1);
INSERT INTO `vipguests` VALUES (18, 'cscscsc', 123, '微信:1312312312', '-', '2023-10-08', 0);
INSERT INTO `vipguests` VALUES (19, 'springcs', 150, '微信:666666', '-', '2023-10-11', 0);
INSERT INTO `vipguests` VALUES (20, 'newcersioncs', 150, '微信：123456', '-', '-', 1);

SET FOREIGN_KEY_CHECKS = 1;
