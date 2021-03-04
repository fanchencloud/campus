-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: campusshop
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES UTF8MB4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

create database if not exists campusShop;
use campusShop;
--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
                        `area_id` int NOT NULL AUTO_INCREMENT,
                        `area_name` varchar(200) NOT NULL,
                        `area_description` varchar(1000) DEFAULT NULL,
                        `priority` int NOT NULL DEFAULT '0',
                        `create_time` datetime DEFAULT NULL,
                        `last_edit_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`area_id`),
                        UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (3,'东苑','东苑',12,'2017-06-04 19:12:58','2017-06-04 19:12:58'),(4,'南苑','南苑',10,'2017-06-04 19:13:09','2017-06-04 19:13:09'),(5,'西苑','西苑',9,'2017-06-04 19:13:18','2017-06-04 19:13:18'),(6,'北苑','北苑',7,'2017-06-04 19:13:29','2017-06-04 19:13:29');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `headline`
--

DROP TABLE IF EXISTS `headline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `headline` (
                            `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `name` varchar(1000) DEFAULT NULL COMMENT '名称',
                            `linked` varchar(2000) NOT NULL COMMENT '链接',
                            `picture` varchar(2000) NOT NULL COMMENT '图片',
                            `priority` int DEFAULT NULL COMMENT '优先级',
                            `status` int NOT NULL DEFAULT '0' COMMENT '状态 0 不可用 1可用',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                            `uuid` varchar(64) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `headline`
--

LOCK TABLES `headline` WRITE;
/*!40000 ALTER TABLE `headline` DISABLE KEYS */;
INSERT INTO `headline` VALUES (16,'test1','headtitle/2017061320315746624.jpg','headtitle/2017061320315746624.jpg',1,1,'2021-03-04 19:18:33','2021-03-04 19:18:35','42627666b7454e96bdf7c1c5737770a7'),(17,'test2','headtitle/2017061320371786788.jpg','headtitle/2017061320371786788.jpg',2,1,'2021-03-04 19:43:58','2021-03-04 19:44:00','8546c66f42094c33866c47d77de51e7a'),(18,NULL,'headtitle/2017061320393452772.jpg','headtitle/2017061320393452772.jpg',3,1,'2021-03-04 19:45:02','2021-03-04 19:45:00','78807e7c4a71434da3cfbdb73edc32be'),(19,NULL,'headtitle/2017061320400198256.jpg','headtitle/2017061320400198256.jpg',4,1,'2021-03-04 19:45:03','2021-03-04 19:45:02','240c4db091c14b499f6e53462aa6048c');
/*!40000 ALTER TABLE `headline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `local_account`
--

DROP TABLE IF EXISTS `local_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `local_account` (
                                 `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                 `user_id` int DEFAULT NULL COMMENT '用户表id',
                                 `username` varchar(128) NOT NULL COMMENT '用户名',
                                 `password` varchar(128) NOT NULL COMMENT '密码',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_local_profile` (`username`),
                                 KEY `fk_local_profile` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `local_account`
--

LOCK TABLES `local_account` WRITE;
/*!40000 ALTER TABLE `local_account` DISABLE KEYS */;
INSERT INTO `local_account` VALUES (1,1,'fanchen','$apr1$123456$R5EKxc23spIGsnVUhCN0A1','2021-03-04 17:54:26','2021-03-04 17:54:33'),(4,12,'fanchenshop','$apr1$123456$Oayh418mfUbuTUGloOnT50','2021-03-04 09:51:14','2021-03-04 09:51:14');
/*!40000 ALTER TABLE `local_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_info`
--

DROP TABLE IF EXISTS `person_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person_info` (
                               `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
                               `gender` varchar(2) DEFAULT NULL COMMENT '用户性别',
                               `phone` varchar(32) DEFAULT NULL COMMENT '电话号码',
                               `email` varchar(128) DEFAULT NULL COMMENT '邮件',
                               `head_portrait` varchar(1024) DEFAULT NULL COMMENT '用户头像',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                               `enable_status` int NOT NULL DEFAULT '0' COMMENT '用户状态 0 禁止使用本商城 1允许使用本商城',
                               `user_type` int NOT NULL DEFAULT '1' COMMENT '用户身份标识1 顾客 2 店家 3 超级管理员',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_info`
--

LOCK TABLES `person_info` WRITE;
/*!40000 ALTER TABLE `person_info` DISABLE KEYS */;
INSERT INTO `person_info` VALUES (1,NULL,NULL,NULL,NULL,NULL,'2021-03-04 19:05:32','2021-03-04 19:05:35',1,3),(12,NULL,NULL,NULL,NULL,NULL,'2021-03-04 09:51:14','2021-03-04 09:51:14',0,2);
/*!40000 ALTER TABLE `person_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
                           `product_id` int NOT NULL AUTO_INCREMENT COMMENT '商品id',
                           `product_name` varchar(100) NOT NULL COMMENT '商品名称',
                           `product_desc` varchar(2000) DEFAULT NULL COMMENT '商品描述',
                           `img_path` varchar(2000) DEFAULT '' COMMENT '商品缩略图',
                           `normal_price` varchar(100) DEFAULT NULL COMMENT '原价',
                           `promotion_price` varchar(100) DEFAULT NULL COMMENT '折扣价',
                           `priority` int NOT NULL DEFAULT '0' COMMENT '权重',
                           `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                           `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                           `enable_status` int NOT NULL DEFAULT '0' COMMENT '商品状态',
                           `product_category_id` int DEFAULT NULL COMMENT '商品类别id',
                           `shop_id` int NOT NULL DEFAULT '0' COMMENT '商店店铺id',
                           PRIMARY KEY (`product_id`),
                           KEY `fk_product_shop` (`shop_id`),
                           KEY `fk_product_probate` (`product_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
                                    `product_category_id` int NOT NULL AUTO_INCREMENT COMMENT '类别id',
                                    `product_category_name` varchar(100) NOT NULL COMMENT '类别名称',
                                    `product_category_description` varchar(500) DEFAULT NULL COMMENT '类别描述',
                                    `priority` int DEFAULT '0' COMMENT '权重',
                                    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                    `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                                    `shop_id` int NOT NULL DEFAULT '0' COMMENT '店铺id',
                                    PRIMARY KEY (`product_category_id`),
                                    KEY `fk_probate_shop` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_img`
--

DROP TABLE IF EXISTS `product_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_img` (
                               `product_img_id` int NOT NULL AUTO_INCREMENT COMMENT '商品图片id',
                               `img_path` varchar(2000) NOT NULL COMMENT '商品图片路径',
                               `img_desc` varchar(2000) DEFAULT NULL COMMENT '图片描述',
                               `priority` int DEFAULT '0' COMMENT '图片描述',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `product_id` int DEFAULT NULL COMMENT '商品id',
                               PRIMARY KEY (`product_img_id`),
                               KEY `fk_img_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_img`
--

LOCK TABLES `product_img` WRITE;
/*!40000 ALTER TABLE `product_img` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_img` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
                        `shop_id` int NOT NULL AUTO_INCREMENT COMMENT '店铺id',
                        `owner_id` int NOT NULL COMMENT '店铺创建人',
                        `area_id` int DEFAULT NULL COMMENT '店铺区域id',
                        `shop_category_id` int DEFAULT NULL COMMENT '店铺类别id',
                        `shop_name` varchar(256) NOT NULL COMMENT '店铺名字',
                        `shop_description` varchar(1024) DEFAULT NULL COMMENT '店铺描述',
                        `shop_address` varchar(200) DEFAULT NULL COMMENT '店铺地址',
                        `phone` varchar(128) DEFAULT NULL COMMENT '店铺联系方式',
                        `shop_img` varchar(1024) DEFAULT NULL COMMENT '店铺门面照片',
                        `priority` int DEFAULT '0' COMMENT '优先级',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                        `enable_status` int NOT NULL DEFAULT '0' COMMENT '店铺状态',
                        `advice` varchar(255) DEFAULT NULL COMMENT '店铺建议，超级管理员给店铺的建议',
                        PRIMARY KEY (`shop_id`),
                        KEY `fk_shop_profile` (`owner_id`),
                        KEY `fk_shop_area` (`area_id`),
                        KEY `fk_shop_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_category`
--

DROP TABLE IF EXISTS `shop_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_category` (
                                 `shop_category_id` int NOT NULL AUTO_INCREMENT COMMENT '店铺类别id',
                                 `shop_category_name` varchar(100) NOT NULL DEFAULT '' COMMENT '店铺类别名称',
                                 `shop_category_desc` varchar(1000) DEFAULT '' COMMENT '店铺类别描述',
                                 `shop_category_img` varchar(2000) DEFAULT NULL COMMENT '图片',
                                 `priority` int NOT NULL DEFAULT '0' COMMENT '优先级',
                                 `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                                 `parent_id` int DEFAULT NULL COMMENT '上级ID',
                                 `uuid` varchar(32) DEFAULT NULL,
                                 PRIMARY KEY (`shop_category_id`),
                                 KEY `fk_shop_category_self` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_category`
--

LOCK TABLES `shop_category` WRITE;
/*!40000 ALTER TABLE `shop_category` DISABLE KEYS */;
INSERT INTO `shop_category` VALUES (1,'二手市场','二手商品交易','shopcategory/2017061223272255687.png',99,'2021-03-04 19:09:47','2021-03-04 19:09:49',NULL,'b592999e594f4ef68d5fd5b343a84c3e'),(2,'美容美发','美容美发','shopcategory/2017061223273314635.png',99,'2021-03-04 19:46:46','2021-03-04 19:46:43',NULL,'774f84ae6df64f23b33e7a75ee1d3b8f'),(3,'美食饮品','美食饮品','shopcategory/2017061223274213433.png',99,'2021-03-04 19:46:47','2021-03-04 19:46:44',NULL,'d558aa8671bc4d0289729653e00bcfb0'),(4,'休闲娱乐','休闲娱乐','shopcategory/2017061223275121460.png',99,'2021-03-04 19:46:45','2021-03-04 19:46:45',NULL,'c37187f82925409ea9039ddebcffd9d6'),(5,'培训教育','培训教育','shopcategory/2017061223280082147.png',99,'2021-03-04 19:47:33','2021-03-04 19:47:31',NULL,'6aafdce6692e45f48c9c6139c3e59a52'),(6,'租赁市场','租赁市场','shopcategory/2017061223281361578.png',99,'2021-03-04 19:47:34','2021-03-04 19:47:32',NULL,'982ce5a4cd174405986042b92282db6e');
/*!40000 ALTER TABLE `shop_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_account`
--

DROP TABLE IF EXISTS `wechat_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wechat_account` (
                                  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `user_id` int NOT NULL COMMENT '用户id',
                                  `open_id` varchar(512) NOT NULL COMMENT '微信的openId',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
                                  PRIMARY KEY (`id`),
                                  KEY `fk_oauth_profile` (`user_id`),
                                  KEY `uk_oauth` (`open_id`(255))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_account`
--

LOCK TABLES `wechat_account` WRITE;
/*!40000 ALTER TABLE `wechat_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-04 20:06:59
