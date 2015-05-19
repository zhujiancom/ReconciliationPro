/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.1.44-community 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bus_tb_scheme` (
	`sid` double ,
	`commission` Decimal (21),
	`scheme_name` varchar (765),
	`paymode_no` varchar (765),
	`post_price` Decimal (21),
	`price` Decimal (21),
	`spread` Decimal (21),
	`s_type` varchar (765),
	`unitCode` varchar (765),
	`version` double ,
	`activity_status` varchar (765),
	`end_date` date ,
	`start_date` date ,
	`vendor` varchar (765)
); 
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('1',NULL,'美团50元代金券','99','43.56','50.00',NULL,'CHIT_50','PC','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('2',NULL,'美团100元代金券','99','87.12','100.00',NULL,'CHIT_100','PC','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('3',NULL,'美团套餐A','99','31.68','32.00',NULL,'SUIT_32','PKG','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('4',NULL,'美团套餐B','99','67.32','68.00',NULL,'SUIT_68','PKG','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('5',NULL,'美团套餐C','99','97.02','98.00',NULL,'SUIT_98','PKG','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('6',NULL,'大众点评50元代金券','98','43.56','50.00',NULL,'CHIT_50','PC','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('7',NULL,'大众点评100元代金券','98','87.12','100.00',NULL,'CHIT_100','PC','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('8',NULL,'大众点评套餐A','98','31.68','32.00',NULL,'SUIT_32','PKG','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('9',NULL,'大众点评套餐B','98','67.32','68.00',NULL,'SUIT_68','PKG','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('10',NULL,'大众点评套餐C','98','97.12','98.00',NULL,'SUIT_98','PKG','0',NULL,NULL,NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('11',NULL,'美团外卖新用户首次下单立减15','12','13.00','15.00','2.00',NULL,'RMB','0',NULL,NULL,NULL,'MTWM');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('12',NULL,'美团外卖满15减8','12','6.00','8.00','2.00',NULL,'RMB','0',NULL,NULL,NULL,'MTWM');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('13',NULL,'美团外卖满50减20','12','18.00','20.00','2.00',NULL,'RMB','0',NULL,NULL,NULL,'MTWM');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('14',NULL,'美团外卖满100减30','12','28.00','30.00','2.00',NULL,'RMB','0',NULL,NULL,NULL,'MTWM');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('15',NULL,'饿了么新用户优惠15（不叠加）','11','15.00','15.00','0.00',NULL,NULL,'0','FINISHED','2015-04-10','2015-03-02','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('16',NULL,'饿了么满20优惠10','11','10.00','10.00','0.00',NULL,NULL,'0','FINISHED','2015-04-10','2015-03-02','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('17',NULL,'饿了么满20优惠10','11','9.00','10.00','1.00',NULL,NULL,'0','FINISHED','2015-05-04','2015-04-20','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('18',NULL,'饿了么满40优惠17','11','17.00','17.00','0.00',NULL,NULL,'0','FINISHED','2015-05-04','2015-04-20','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('19',NULL,'饿了么满20减12','11','9.00','12.00','3.00',NULL,NULL,'0','RUNNING','2015-05-20','2015-05-06','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('20',NULL,'饿了么新用户优惠15（不叠加）','11','13.00','15.00','2.00',NULL,NULL,'0','FINISHED','2015-05-18','2015-03-27','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('21',NULL,'饿了么满20减8','11','6.00','8.00','2.00',NULL,NULL,'0','RUNNING','2015-05-26','2015-04-30','ELE');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `end_date`, `start_date`, `vendor`) values('22',NULL,'饿了么新用户优惠15（不叠加）','11','12.00','15.00','3.00',NULL,NULL,'0','RUNNING','2015-05-31','2015-04-30','ELE');
