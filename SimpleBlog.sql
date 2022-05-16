-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: simpleblog
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE SCHEMA `simpleblog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
use simpleblog;
--
-- Table structure for table `t_blog`
--

DROP TABLE IF EXISTS `t_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `appreciation` bit(1) NOT NULL,
                          `commentable` bit(1) NOT NULL,
                          `content` longtext,
                          `create_time` datetime(6) DEFAULT NULL,
                          `description` varchar(255) DEFAULT NULL,
                          `first_picture` varchar(255) DEFAULT NULL,
                          `flag` varchar(255) DEFAULT NULL,
                          `published` bit(1) NOT NULL,
                          `recommend` bit(1) NOT NULL,
                          `share_statement` bit(1) NOT NULL,
                          `title` varchar(255) DEFAULT '无',
                          `update_time` datetime(6) DEFAULT NULL,
                          `views` int DEFAULT '0',
                          `type_id` bigint DEFAULT NULL,
                          `user_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`user_id`),
                          CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_blog_tags`
--

DROP TABLE IF EXISTS `t_blog_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog_tags` (
                               `blogs_id` bigint NOT NULL,
                               `tags_id` bigint NOT NULL,
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_comment` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `admin_comment` bit(1) NOT NULL,
                             `avatar` varchar(255) DEFAULT NULL,
                             `content` varchar(255) DEFAULT NULL,
                             `create_time` datetime(6) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `nickname` varchar(255) DEFAULT NULL,
                             `blog_id` bigint DEFAULT NULL,
                             `parent_comment_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FKke3uogd04j4jx316m1p51e05u` (`blog_id`),
                             KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`),

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_tag`
--

DROP TABLE IF EXISTS `t_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_tag` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_type`
--

DROP TABLE IF EXISTS `t_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_type` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `avatar` varchar(255) DEFAULT NULL,
                          `create_time` datetime(6) DEFAULT NULL,
                          `email` varchar(255) DEFAULT NULL,
                          `nickname` varchar(255) NOT NULL,
                          `password` varchar(255) NOT NULL,
                          `type` int DEFAULT NULL,
                          `update_time` datetime(6) DEFAULT NULL,
                          `username` varchar(255) NOT NULL,
                          `salt` varchar(10) NOT NULL,
                          `introduction` varchar(255) DEFAULT '你好懒呀',
                          `qq_email_smtp` varchar(45) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- 初始用户
--

INSERT INTO simpleblog.t_user (id, avatar, create_time, email, nickname, password, type, update_time, username, salt, introduction, qq_email_smtp) VALUES (1, '/images/avatar/zry.jpg', '2022-03-23 18:59:55', '1447051936@qq.com', 'root', 'b5fb686c5752edd1c337ac7231c6cea5', 1, '2022-03-23 18:59:55', 'root', '1a2b3c4d', '这厮我的个人博客，会分享关于编程，思考，随笔相关的内容，欢迎大家访问，希望可以给到这的人有所帮助...', null);

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-09 22:53:17
