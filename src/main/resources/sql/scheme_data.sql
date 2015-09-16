/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.17 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('1',NULL,'美团50元代金券','99','43.56','50.00',NULL,'CHIT_50','PC','0',NULL,NULL,NULL,'MT',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('2',NULL,'美团100元代金券','99','87.12','100.00',NULL,'CHIT_100','PC','0',NULL,NULL,NULL,'MT',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('3',NULL,'美团套餐A','99','31.68','32.00',NULL,'SUIT_32','PKG','0',NULL,NULL,NULL,'MT',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('4',NULL,'美团套餐B','99','67.32','68.00',NULL,'SUIT_68','PKG','0',NULL,NULL,NULL,'MT',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('5',NULL,'美团套餐C','99','97.02','98.00',NULL,'SUIT_98','PKG','0',NULL,NULL,NULL,'MT',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('6',NULL,'大众点评50元代金券','98','43.56','50.00',NULL,'CHIT_50','PC','0',NULL,NULL,NULL,'DZDP',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('7',NULL,'大众点评100元代金券','98','87.12','100.00',NULL,'CHIT_100','PC','0',NULL,NULL,NULL,'DZDP',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('8',NULL,'大众点评套餐A','98','31.68','32.00',NULL,'SUIT_32','PKG','0',NULL,NULL,NULL,'DZDP',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('9',NULL,'大众点评套餐B','98','67.32','68.00',NULL,'SUIT_68','PKG','0',NULL,NULL,NULL,'DZDP',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('10',NULL,'大众点评套餐C','98','97.12','98.00',NULL,'SUIT_98','PKG','0',NULL,NULL,NULL,'DZDP',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('11',NULL,'美团外卖新用户首次下单立减15','12','13.00','15.00','2.00',NULL,'RMB','0','ACTIVE','2015-05-05','2015-05-31','MTWM','15.00','15.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('12',NULL,'美团外卖满15减8','12','6.00','8.00','2.00',NULL,'RMB','0','ACTIVE','2015-05-05','2015-05-31','MTWM','15.00','50.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('13',NULL,'美团外卖满50减20','12','18.00','20.00','2.00',NULL,'RMB','0','ACTIVE','2015-05-05','2015-05-26','MTWM','50.00','100.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('14',NULL,'美团外卖满100减30','12','28.00','30.00','2.00',NULL,'RMB','0','ACTIVE','2015-05-05','2015-05-26','MTWM','100.00','10000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('15',NULL,'饿了么新用户优惠15（不叠加）','11','15.00','15.00','0.00',NULL,'RMB','0','INACTIVE','2015-03-02','2015-04-10','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('16',NULL,'饿了么满20优惠10','11','10.00','10.00','0.00',NULL,'RMB','0','INACTIVE','2015-03-02','2015-04-10','ELE','20.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('17',NULL,'饿了么满20优惠10','11','9.00','10.00','1.00',NULL,'RMB','0','INACTIVE','2015-04-20','2015-05-04','ELE','20.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('18',NULL,'饿了么满40优惠17','11','17.00','17.00','0.00',NULL,'RMB','0','INACTIVE','2015-04-20','2015-05-04','ELE','40.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('19',NULL,'饿了么满20减12','11','9.00','12.00','3.00',NULL,'RMB','0','INACTIVE','2015-05-06','2015-05-20','ELE','20.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('20',NULL,'饿了么新用户优惠15（不叠加）','11','13.00','15.00','2.00',NULL,'RMB','0','INACTIVE','2015-03-27','2015-05-18','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('21',NULL,'美团外卖满50减20','12','15.00','20.00','5.00',NULL,'RMB','0','ACTIVE','2015-05-27','2015-05-31','MTWM','50.00','100.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('22',NULL,'美团外卖满100减30','12','25.00','30.00','5.00',NULL,'RMB','0','ACTIVE','2015-05-27','2015-05-31','MTWM','100.00','1000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('23',NULL,'饿了么新用户优惠15（不叠加）','11','12.00','15.00','3.00',NULL,'RMB','0','INACTIVE','2015-04-30','2015-05-31','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('24',NULL,'饿了么新用户优惠15（不叠加）','11','12.00','15.00','3.00',NULL,'RMB','0','ACTIVE','2015-05-23','2015-06-30','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('25',NULL,'饿了么满20减8','11','6.00','8.00','2.00',NULL,'RMB','0','INACTIVE','2015-05-07','2015-06-20','ELE','20.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('26',NULL,'饿了么满25减9','11','6.00','9.00','3.00',NULL,'RMB','0','INACTIVE','2015-05-21','2015-06-30','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('27',NULL,'饿了么满25减8','11','5.00','8.00','3.00',NULL,'RMB','0','ACTIVE','2015-06-01','2015-06-30','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('28',NULL,'美团外卖新用户首次下单立减15','12','12.00','15.00','3.00',NULL,'RMB','0','ACTIVE','2015-05-30','2015-06-16','MTWM','15.00','15.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('29',NULL,'美团外卖满15减8','12','5.00','8.00','3.00',NULL,'RMB','0','ACTIVE','2015-05-31','2015-06-16','MTWM','15.00','50.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('30',NULL,'美团外卖满50减18','12','13.00','18.00','5.00',NULL,'RMB','0','ACTIVE','2015-05-31','2015-06-16','MTWM','50.00','1000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('31',NULL,'美团外卖新用户首次下单立减10','12','7.00','10.00','3.00',NULL,'RMB','0','ACTIVE','2015-06-17','2015-07-06','MTWM','10.00','10.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('32',NULL,'美团外卖满15减7','12','4.00','7.00','3.00',NULL,'RMB','0','ACTIVE','2015-06-17','2015-07-06','MTWM','15.00','50.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('33',NULL,'美团外卖满50减15','12','10.00','15.00','5.00',NULL,'RMB','0','ACTIVE','2015-06-17','2015-07-06','MTWM','50.00','1000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('34',NULL,'饿了么满25减11','11','8.00','11.00','3.00',NULL,'RMB','0','ACTIVE','2015-07-01','2015-07-19','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('35',NULL,'Frypan单人休闲餐B','13','31.60','32.00',NULL,'SUIT_32','PKG','0','ACTIVE',NULL,NULL,'LS',NULL,NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('36',NULL,'饿了么新用户优惠15（不叠加）','11','12.00','15.00','3.00',NULL,'RMB','0','ACTIVE','2015-07-01','2015-07-31','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('37',NULL,'饿了么满25减9','11','6.00','9.00','3.00',NULL,'RMB','0','ACTIVE','2015-07-20','2015-07-23','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('38',NULL,'饿了么满25减8','11','5.00','8.00','3.00',NULL,'RMB','0','ACTIVE','2015-07-24','2015-07-31','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('39',NULL,'饿了么满25减11','11','8.00','11.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-01','2015-08-03','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('40',NULL,'饿了么新用户优惠15（不叠加）','11','12.00','15.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-01','2015-08-20','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('41',NULL,'饿了么满25减9','11','6.00','9.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-04','2015-08-07','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('42',NULL,'饿了么满25减8','11','5.00','8.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-08','2015-08-27','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('43',NULL,'饿了么满25减8','11','5.00','8.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-07','2015-08-07','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('44',NULL,'饿了么满25减11','11','8.00','11.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-08','2015-08-08','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('45',NULL,'饿了么新用户优惠15（不叠加）','11','15.00','15.00','0.00',NULL,'RMB','0','ACTIVE','2015-08-21','2015-09-07','ELE','15.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('46',NULL,'饿了么满25减7','11','4.00','7.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-28','2015-09-09','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('47',NULL,'淘点点满15减4','96','1.00','4.00','3.00',NULL,'RMB','0','ACTIVE','2015-08-31','2015-09-06','TDD','15.00','25.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('48',NULL,'淘点点满25减8','96','4.96','8.00','3.04',NULL,'RMB','0','ACTIVE','2015-08-31','2015-09-06','TDD','25.00','1000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('49',NULL,'淘点点满10减3','96','1.02','3.00','1.98',NULL,'RMB','0','ACTIVE','2015-09-07','2015-09-13','TDD','10.00','1000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('50',NULL,'饿了么满25减5','11','2.00','5.00','3.00',NULL,'RMB','0','ACTIVE','2015-09-10','2015-09-15','ELE','25.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('51',NULL,'饿了么新用户优惠20（不叠加）','11','15.00','20.00','5.00',NULL,'RMB','0','ACTIVE','2015-09-08','2015-09-15','ELE','20.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('52',NULL,'美团外卖满50减13','12','8.00','13.00','5.00',NULL,'RMB','0','ACTIVE','2015-09-15','2015-10-12','MTWM','50.00','1000.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('53',NULL,'美团外卖满15减7','12','4.00','7.00','3.00',NULL,'RMB','0','ACTIVE','2015-09-15','2015-10-12','MTWM','15.00','50.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('54',NULL,'美团外卖新用户首次下单立减15','12','10.00','15.00','5.00',NULL,'RMB','0','ACTIVE','2015-09-15','2015-10-12','MTWM','15.00','15.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('55',NULL,'饿了么新用户优惠20（不叠加）','11','0.00','20.00','20.00',NULL,'RMB','0','ACTIVE','2015-09-16','2015-10-20','ELE','20.00',NULL);
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('56',NULL,'饿了么满25减8','11','3.00','8.00','5.00',NULL,'RMB','0','ACTIVE','2015-09-16','2015-10-20','ELE','25.00','50.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('57',NULL,'饿了么满50减13','11','3.00','13.00','10.00',NULL,'RMB','0','ACTIVE','2015-09-16','2015-10-20','ELE','50.00','75.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('58',NULL,'饿了么满75减18','11','3.00','18.00','15.00',NULL,'RMB','0','ACTIVE','2015-09-16','2015-10-20','ELE','75.00','100.00');
insert into `bus_tb_scheme` (`sid`, `commission`, `scheme_name`, `paymode_no`, `post_price`, `price`, `spread`, `s_type`, `unitCode`, `version`, `activity_status`, `start_date`, `end_date`, `vendor`, `floor_amount`, `ceil_amount`) values('59',NULL,'饿了么满100减23','11','3.00','23.00','20.00',NULL,'RMB','0','ACTIVE','2015-09-16','2015-10-20','ELE','100.00','1000.00');
