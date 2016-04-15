CREATE DATABASE  IF NOT EXISTS `mmrepo` /*!40100 DEFAULT CHARACTER SET latin1 */;
grant all privileges on mmrepo.* to 'superadmin'@'%' identified by 'password';
USE `mmrepo`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 95.211.172.242    Database: mmrepo
-- ------------------------------------------------------
-- Server version	5.6.21-1+deb.sury.org~precise+1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `desc1` varchar(256) DEFAULT NULL,
  `url` varchar(128) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `tags` varchar(128) DEFAULT NULL,
  `stored` varchar(16) DEFAULT NULL,
  `created_at` varchar(128) DEFAULT NULL,
  `updated_at` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'test','test','https://www.youtube.com/embed/vsjohNujiXU','video','test','false','2016/04/04 14:07:24',NULL),(2,'test1','','http://res.cloudinary.com/dgznzkkkw/video/upload/v1459772158/test1.mp4','video','test1','true','2016/04/04 14:15:59','2016/04/04 14:15:59');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `rol` varchar(65) DEFAULT NULL,
  `location` varchar(64) DEFAULT NULL,
  `created_at` varchar(128) DEFAULT NULL,
  `updated_at` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'superadmin','softcare','$2a$11$JP.kCocclbT54juAWa7A1OUASOaQXRkO4yJL5VGGOLY0GnyfdmUce','admin','Spain','2016/04/04 14:07:24',NULL),(2,'rsucasas','Roi Sucasas','$2a$11$G8MXeWX3B8dYf/QJqGpvrOkOoMOiW3hC66CNlUNTvwiS3.RPHD052','admin','Spain','2016/04/04 14:07:24',NULL),(3,'admin','admin','$2a$11$Gh2vb/fTBlsrpzQL63nRYORFghAdsx2E0vXh5WYy.vxdlG7GL2HVK','admin','Spain','2016/04/04 14:07:24',NULL),(4,'papito','pepito2','$2a$11$s/Em1hEt1r98TXCjg39T6O/gjHh5IBRUTSTowOvSiSSBVP8aoI3Ei','admin','Germany','2016/04/04 14:14:32','2016/04/04 14:17:17');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-04 14:28:20
