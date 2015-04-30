CREATE DATABASE  IF NOT EXISTS `tik` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `tik`;
-- MySQL dump 10.13  Distrib 5.5.43, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tik
-- ------------------------------------------------------
-- Server version	5.5.43-0+deb8u1

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
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `is_admin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'CATEGORIA POR DEFECTO');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eproducts`
--

DROP TABLE IF EXISTS `eproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `removed` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eproducts`
--

LOCK TABLES `eproducts` WRITE;
/*!40000 ALTER TABLE `eproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `eproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eproducts_pproducts`
--

DROP TABLE IF EXISTS `eproducts_pproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eproducts_pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eproduct_id` int(11) DEFAULT NULL,
  `pproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eproducts_pproducts`
--

LOCK TABLES `eproducts_pproducts` WRITE;
/*!40000 ALTER TABLE `eproducts_pproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `eproducts_pproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fproducts`
--

DROP TABLE IF EXISTS `fproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `subcategory_id` int(11) DEFAULT NULL,
  `removed` int(1) DEFAULT '0',
  `sell_price` float DEFAULT NULL,
  `belong` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fproducts`
--

LOCK TABLES `fproducts` WRITE;
/*!40000 ALTER TABLE `fproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `fproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fproducts_eproducts`
--

DROP TABLE IF EXISTS `fproducts_eproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fproducts_eproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fproduct_id` int(11) DEFAULT NULL,
  `eproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fproducts_eproducts`
--

LOCK TABLES `fproducts_eproducts` WRITE;
/*!40000 ALTER TABLE `fproducts_eproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `fproducts_eproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fproducts_pproducts`
--

DROP TABLE IF EXISTS `fproducts_pproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fproducts_pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fproduct_id` int(11) DEFAULT NULL,
  `pproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fproducts_pproducts`
--

LOCK TABLES `fproducts_pproducts` WRITE;
/*!40000 ALTER TABLE `fproducts_pproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `fproducts_pproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pproducts`
--

DROP TABLE IF EXISTS `pproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT 'empty',
  `stock` float DEFAULT '0',
  `measure_unit` varchar(45) DEFAULT NULL,
  `unit_price` float DEFAULT '0',
  `removed` int(1) DEFAULT '0',
  `provider_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pproducts`
--

LOCK TABLES `pproducts` WRITE;
/*!40000 ALTER TABLE `pproducts` DISABLE KEYS */;
/*!40000 ALTER TABLE `pproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pproducts_purchases`
--

DROP TABLE IF EXISTS `pproducts_purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pproducts_purchases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pproduct_id` varchar(45) NOT NULL,
  `purchase_id` varchar(45) NOT NULL,
  `amount` varchar(45) NOT NULL,
  `final_price` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pproducts_purchases`
--

LOCK TABLES `pproducts_purchases` WRITE;
/*!40000 ALTER TABLE `pproducts_purchases` DISABLE KEYS */;
/*!40000 ALTER TABLE `pproducts_purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providercategories`
--

DROP TABLE IF EXISTS `providercategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `providercategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providercategories`
--

LOCK TABLES `providercategories` WRITE;
/*!40000 ALTER TABLE `providercategories` DISABLE KEYS */;
/*!40000 ALTER TABLE `providercategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providers`
--

DROP TABLE IF EXISTS `providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `providers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `cuit` varchar(45) DEFAULT '-1',
  `address` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `phones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providers`
--

LOCK TABLES `providers` WRITE;
/*!40000 ALTER TABLE `providers` DISABLE KEYS */;
/*!40000 ALTER TABLE `providers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providers_products`
--

DROP TABLE IF EXISTS `providers_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `providers_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providers_products`
--

LOCK TABLES `providers_products` WRITE;
/*!40000 ALTER TABLE `providers_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `providers_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providers_providercategories`
--

DROP TABLE IF EXISTS `providers_providercategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `providers_providercategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `providercategory_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providers_providercategories`
--

LOCK TABLES `providers_providercategories` WRITE;
/*!40000 ALTER TABLE `providers_providercategories` DISABLE KEYS */;
/*!40000 ALTER TABLE `providers_providercategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost` float DEFAULT NULL,
  `paid` float DEFAULT NULL,
  `date` date DEFAULT NULL,
  `provider_id` int(11) DEFAULT NULL,
  `date_paid` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subcategories`
--

DROP TABLE IF EXISTS `subcategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subcategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `category_id` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subcategories`
--

LOCK TABLES `subcategories` WRITE;
/*!40000 ALTER TABLE `subcategories` DISABLE KEYS */;
INSERT INTO `subcategories` VALUES (1,'SUBCATEGORIA POR DEFECTO',1);
/*!40000 ALTER TABLE `subcategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `date_hired` date DEFAULT NULL,
  `date_discharged` date DEFAULT NULL,
  `turn` varchar(45) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `place_of_birth` varchar(45) DEFAULT NULL,
  `id_type` varchar(45) DEFAULT NULL,
  `id_number` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `home_phone` varchar(45) DEFAULT NULL,
  `emergency_phone` varchar(45) DEFAULT NULL,
  `mobile_phone` varchar(45) DEFAULT NULL,
  `marital_status` varchar(45) DEFAULT NULL,
  `blood_type` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  `photo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
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

-- Dump completed on 2015-04-30 15:12:59
