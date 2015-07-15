/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.6.20 : Database - reconciliation2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`reconciliation2` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `reconciliation2`;

/*Table structure for table `bus_tb_dish` */

DROP TABLE IF EXISTS `bus_tb_dish`;

CREATE TABLE `bus_tb_dish` (
  `did` bigint(20) NOT NULL AUTO_INCREMENT,
  `dish_name` varchar(255) DEFAULT NULL,
  `dish_no` varchar(255) DEFAULT NULL,
  `dish_price` decimal(19,2) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `dish_type_id` bigint(20) DEFAULT NULL,
  `stop_flag` varchar(255) DEFAULT NULL,
  `stock_flag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`did`),
  KEY `FK_gxgaxyqmoff4e1yu2xvg030if` (`dish_type_id`),
  CONSTRAINT `FK_gxgaxyqmoff4e1yu2xvg030if` FOREIGN KEY (`dish_type_id`) REFERENCES `bus_tb_dish_type` (`dtid`)
) ENGINE=InnoDB AUTO_INCREMENT=447 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
