/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.17 : Database - reconciliation2
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

/*Table structure for table `bus_tb_scheme` */

DROP TABLE IF EXISTS `bus_tb_scheme`;

CREATE TABLE `bus_tb_scheme` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `commission` decimal(19,2) DEFAULT NULL,
  `scheme_name` varchar(255) DEFAULT NULL,
  `paymode_no` varchar(255) DEFAULT NULL,
  `post_price` decimal(19,2) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `spread` decimal(19,2) DEFAULT NULL,
  `s_type` varchar(255) DEFAULT NULL,
  `unitCode` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `activity_status` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `vendor` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `bus_tb_scheme` */

insert  into `bus_tb_scheme`(`sid`,`commission`,`scheme_name`,`paymode_no`,`post_price`,`price`,`spread`,`s_type`,`unitCode`,`version`,`activity_status`,`start_date`,`end_date`,`vendor`) values (1,NULL,'美团50元代金券','99',43.56,50.00,NULL,'CHIT_50','PC',0,NULL,NULL,NULL,'MT'),(2,NULL,'美团100元代金券','99',87.12,100.00,NULL,'CHIT_100','PC',0,NULL,NULL,NULL,'MT'),(3,NULL,'美团套餐A','99',31.68,32.00,NULL,'SUIT_32','PKG',0,NULL,NULL,NULL,'MT'),(4,NULL,'美团套餐B','99',67.32,68.00,NULL,'SUIT_68','PKG',0,NULL,NULL,NULL,'MT'),(5,NULL,'美团套餐C','99',97.02,98.00,NULL,'SUIT_98','PKG',0,NULL,NULL,NULL,'MT'),(6,NULL,'大众点评50元代金券','98',43.56,50.00,NULL,'CHIT_50','PC',0,NULL,NULL,NULL,'DZDP'),(7,NULL,'大众点评100元代金券','98',87.12,100.00,NULL,'CHIT_100','PC',0,NULL,NULL,NULL,'DZDP'),(8,NULL,'大众点评套餐A','98',31.68,32.00,NULL,'SUIT_32','PKG',0,NULL,NULL,NULL,'DZDP'),(9,NULL,'大众点评套餐B','98',67.32,68.00,NULL,'SUIT_68','PKG',0,NULL,NULL,NULL,'DZDP'),(10,NULL,'大众点评套餐C','98',97.12,98.00,NULL,'SUIT_98','PKG',0,NULL,NULL,NULL,'DZDP'),(11,NULL,'美团外卖新用户首次下单立减15','12',13.00,15.00,2.00,NULL,'RMB',0,'RUNNING','2015-05-05','2015-05-31','MTWM'),(12,NULL,'美团外卖满15减8','12',6.00,8.00,2.00,NULL,'RMB',0,'RUNNING','2015-05-05','2015-05-31','MTWM'),(13,NULL,'美团外卖满50减20','12',18.00,20.00,2.00,NULL,'RMB',0,'FINISHED','2015-05-05','2015-05-26','MTWM'),(14,NULL,'美团外卖满100减30','12',28.00,30.00,2.00,NULL,'RMB',0,'FINISHED','2015-05-05','2015-05-26','MTWM'),(15,NULL,'饿了么新用户优惠15（不叠加）','11',15.00,15.00,0.00,NULL,'RMB',0,'FINISHED','2015-03-02','2015-04-10','ELE'),(16,NULL,'饿了么满20优惠10','11',10.00,10.00,0.00,NULL,'RMB',0,'FINISHED','2015-03-02','2015-04-10','ELE'),(17,NULL,'饿了么满20优惠10','11',9.00,10.00,1.00,NULL,'RMB',0,'FINISHED','2015-04-20','2015-05-04','ELE'),(18,NULL,'饿了么满40优惠17','11',17.00,17.00,0.00,NULL,'RMB',0,'FINISHED','2015-04-20','2015-05-04','ELE'),(19,NULL,'饿了么满20减12','11',9.00,12.00,3.00,NULL,'RMB',0,'FINISHED','2015-05-06','2015-05-20','ELE'),(20,NULL,'饿了么新用户优惠15（不叠加）','11',13.00,15.00,2.00,NULL,'RMB',0,'FINISHED','2015-03-27','2015-05-18','ELE'),(21,NULL,'美团外卖满50减20','12',15.00,20.00,5.00,NULL,'RMB',0,'RUNNING','2015-05-27','2015-05-31','MTWM'),(22,NULL,'美团外卖满100减30','12',25.00,30.00,5.00,NULL,'RMB',0,'RUNNING','2015-05-27','2015-05-31','MTWM'),(23,NULL,'饿了么新用户优惠15（不叠加）','11',12.00,15.00,3.00,NULL,'RMB',0,'RUNNING','2015-04-30','2015-05-31','ELE');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
