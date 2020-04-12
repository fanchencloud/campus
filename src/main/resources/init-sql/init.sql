-- MySQL dump 10.13  Distrib 5.5.34, for Linux (x86_64)
--
-- Host: localhost    Database: o2odb
-- ------------------------------------------------------
-- Server version	5.5.34-log

create database if not exists campusShop;
use campusShop;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `tb_area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `area`
(
    `area_id`          int(5)       NOT NULL AUTO_INCREMENT,
    `area_name`        varchar(200) NOT NULL,
    `area_description` varchar(1000)         DEFAULT NULL,
    `priority`         int(2)       NOT NULL DEFAULT '0',
    `create_time`      datetime              DEFAULT NULL,
    `last_edit_time`   datetime              DEFAULT NULL,
    PRIMARY KEY (`area_id`),
    UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area`
    DISABLE KEYS */;
INSERT INTO `area`
VALUES (3, '东苑', '东苑', 12, '2017-06-04 19:12:58', '2017-06-04 19:12:58'),
       (4, '南苑', '南苑', 10, '2017-06-04 19:13:09', '2017-06-04 19:13:09'),
       (5, '西苑', '西苑', 9, '2017-06-04 19:13:18', '2017-06-04 19:13:18'),
       (6, '北苑', '北苑', 7, '2017-06-04 19:13:29', '2017-06-04 19:13:29');
/*!40000 ALTER TABLE `area`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_info`
--

DROP TABLE IF EXISTS `person_info`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_info`
(
    `id`             int(10) NOT NULL AUTO_INCREMENT comment 'id',
    `name`           varchar(32)      DEFAULT NULL comment '用户姓名',
    `gender`         varchar(2)       DEFAULT NULL comment '用户性别',
    `phone`          varchar(32)      DEFAULT NULL comment '电话号码',
    `email`          varchar(128)     DEFAULT NULL comment '邮件',
    `head_portrait`  varchar(1024)    DEFAULT NULL comment '用户头像',
    `create_time`    datetime         DEFAULT NULL comment '创建时间',
    `last_edit_time` datetime         DEFAULT NULL comment '最后修改时间',
    `enable_status`  int(2)  NOT NULL DEFAULT 0 comment '用户状态 0 禁止使用本商城 1允许使用本商城',
    `user_type`      int(2)  NOT NULL DEFAULT 1 comment '用户身份标识1 顾客 2 店家 3 超级管理员',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weChat_account`
-- 微信账号
--

DROP TABLE IF EXISTS `weChat_account`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `weChat_account`
(
    `id`             int(10)      NOT NULL AUTO_INCREMENT comment 'id',
    `user_id`        int(10)      NOT NULL comment '用户id',
    `open_id`        varchar(512) NOT NULL comment '微信的openId',
    `create_time`    datetime DEFAULT NULL comment '创建时间',
    `last_edit_time` datetime DEFAULT NULL comment '最后修改时间',
    PRIMARY KEY (`id`),
    KEY `fk_oauth_profile` (`user_id`),
    KEY `uk_oauth` (`open_id`(255))
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `local_account`
-- 本地账号信息
--

DROP TABLE IF EXISTS `local_account`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `local_account`
(
    `id`             int(10)      NOT NULL AUTO_INCREMENT comment 'id',
    `user_id`        int(10)  DEFAULT NULL comment '用户表id',
    `username`       varchar(128) NOT NULL comment '用户名',
    `password`       varchar(128) NOT NULL comment '密码',
    `create_time`    datetime DEFAULT NULL comment '创建时间',
    `last_edit_time` datetime DEFAULT NULL comment '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_local_profile` (`username`),
    KEY `fk_local_profile` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `headline`
-- 头条信息
--

DROP TABLE IF EXISTS `headline`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `headline`
(
    `id`             int(100)      NOT NULL AUTO_INCREMENT comment 'id',
    `name`           varchar(1000)          DEFAULT NULL comment '名称',
    `linked`         varchar(2000) NOT NULL comment '链接',
    `picture`        varchar(2000) NOT NULL comment '图片',
    `priority`       int(2)                 DEFAULT NULL comment '优先级',
    `status`         int(2)        NOT NULL DEFAULT '0' comment '状态 0 不可用 1可用',
    `create_time`    datetime               DEFAULT NULL comment '创建时间',
    `last_edit_time` datetime               DEFAULT NULL comment '最后修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tb_shop_category`
--

DROP TABLE IF EXISTS `shop_category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_category`
(
    `shop_category_id`   int(11)      NOT NULL AUTO_INCREMENT COMMENT '店铺类别id',
    `shop_category_name` varchar(100) NOT NULL DEFAULT '' COMMENT '店铺类别名称',
    `shop_category_desc` varchar(1000)         DEFAULT '' COMMENT '店铺类别描述',
    `shop_category_img`  varchar(2000)         DEFAULT NULL COMMENT '图片',
    `priority`           int(11)      NOT NULL DEFAULT '0' COMMENT '优先级',
    `create_time`        datetime              DEFAULT NULL COMMENT '创建时间',
    `last_edit_time`     datetime              DEFAULT NULL COMMENT '最后修改时间',
    `parent_id`          int(11)               DEFAULT NULL COMMENT '上级ID',
    `uuid`               varchar(32)           DEFAULT NULL,
    PRIMARY KEY (`shop_category_id`),
    KEY `fk_shop_category_self` (`parent_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop`
(
    `shop_id`          int(10)      NOT NULL AUTO_INCREMENT comment '店铺id',
    `owner_id`         int(10)      NOT NULL COMMENT '店铺创建人',
    `area_id`          int(5)                DEFAULT NULL comment '店铺区域id',
    `shop_category_id` int(11)               DEFAULT NULL comment '店铺类别id',
    `shop_name`        varchar(256) NOT NULL comment '店铺名字',
    `shop_description` varchar(1024)         DEFAULT NULL comment '店铺描述',
    `shop_address`     varchar(200)          DEFAULT NULL comment '店铺地址',
    `phone`            varchar(128)          DEFAULT NULL comment '店铺联系方式',
    `shop_img`         varchar(1024)         DEFAULT NULL comment '店铺门面照片',
    `priority`         int(3)                DEFAULT '0' comment '优先级',
    `create_time`      datetime              DEFAULT NULL comment '创建时间',
    `last_edit_time`   datetime              DEFAULT NULL comment '最后修改时间',
    `enable_status`    int(2)       NOT NULL DEFAULT '0' comment '店铺状态',
    `advice`           varchar(255)          DEFAULT NULL comment '店铺建议，超级管理员给店铺的建议',
    PRIMARY KEY (`shop_id`),
    KEY `fk_shop_profile` (`owner_id`),
    KEY `fk_shop_area` (`area_id`),
    KEY `fk_shop_shop_category` (`shop_category_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 29
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_category`
-- 商品类别表
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category`
(
    `product_category_id`          int(11)      NOT NULL AUTO_INCREMENT comment '类别id',
    `product_category_name`        varchar(100) NOT NULL comment '类别名称',
    `product_category_description` varchar(500)          DEFAULT NULL comment '类别描述',
    `priority`                     int(2)                DEFAULT '0' comment '权重',
    `create_time`                  datetime              DEFAULT NULL comment '创建时间',
    `last_edit_time`               datetime              DEFAULT NULL comment '最后修改时间',
    `shop_id`                      int(20)      NOT NULL DEFAULT '0' comment '店铺id',
    PRIMARY KEY (`product_category_id`),
    KEY `fk_probate_shop` (`shop_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product_img`
--

DROP TABLE IF EXISTS `product_img`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_img`
(
    `product_img_id` int(20)       NOT NULL AUTO_INCREMENT comment '商品图片id',
    `img_path`       varchar(2000) NOT NULL comment '商品图片路径',
    `img_desc`       varchar(2000) DEFAULT NULL comment '图片描述',
    `priority`       int(2)        DEFAULT '0' comment '图片描述',
    `create_time`    datetime      DEFAULT NULL comment '创建时间',
    `product_id`     int(20)       DEFAULT NULL comment '商品id',
    PRIMARY KEY (`product_img_id`),
    KEY `fk_img_product` (`product_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 38
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `product`
-- 商品表
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `product`
(
    `product_id`          int(100)     NOT NULL AUTO_INCREMENT comment '商品id',
    `product_name`        varchar(100) NOT NULL comment '商品名称',
    `product_desc`        varchar(2000)         DEFAULT NULL comment '商品描述',
    `img_path`            varchar(2000)         DEFAULT '' comment '商品缩略图',
    `normal_price`        varchar(100)          DEFAULT NULL comment '原价',
    `promotion_price`     varchar(100)          DEFAULT NULL comment '折扣价',
    `priority`            int(2)       NOT NULL DEFAULT '0' comment '权重',
    `create_time`         datetime              DEFAULT NULL comment '创建时间',
    `last_edit_time`      datetime              DEFAULT NULL comment '最后修改时间',
    `enable_status`       int(2)       NOT NULL DEFAULT '0' comment '商品状态',
    `product_category_id` int(11)               DEFAULT NULL comment '商品类别id',
    `shop_id`             int(20)      NOT NULL DEFAULT '0' comment '商店店铺id',
    PRIMARY KEY (`product_id`),
    KEY `fk_product_shop` (`shop_id`),
    KEY `fk_product_probate` (`product_category_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;