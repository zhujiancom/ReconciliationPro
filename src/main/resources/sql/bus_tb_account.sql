/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.17 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('1','收银机账户','CASH_MACHINE','CASH','358898.00','RMB','收银机现金入账部分','358898.00',NULL,'Y',NULL,'1644','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('4','饿了么总账户','ELE','VIRTUAL','538444.00','RMB','饿了么总账户金额','538444.00',NULL,'Y',NULL,'2273','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('5','饿了么活动补贴账户','ALLOWANCE_ELE','VIRTUAL','28923.00','RMB','饿了么平台返还给商家的补贴部分','28923.00',NULL,NULL,'4','1705','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('6','美团账户','MT','VIRTUAL','28949.68','RMB','美团总账户','28949.68',NULL,'Y',NULL,'483','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('7','大众点评总账户','DZDP','VIRTUAL','86734.40','RMB','大众点评团购支付部分','86734.40',NULL,'Y',NULL,'786','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('8','美团外卖账户','MTWM','VIRTUAL','19247.95','RMB','美团外卖总账户','19247.95',NULL,'Y',NULL,'539','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('9','美团外卖免单账户','FREE_MTWM','VIRTUAL','4200.50','RMB','美团外卖商家免单部分','4200.50',NULL,NULL,'8','497','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('10','支付宝账户','ALIPAY','VIRTUAL','32355.00','RMB','支付宝总账户','32355.00',NULL,'Y',NULL,'468','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('11','商家免单总账户','FREE','CASH','2096.00','RMB','商家线上线下免单总金额','2096.00',NULL,NULL,NULL,'508','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('12','美团超券账户','MT_SUPER','VIRTUAL','16305.02','RMB','美团超券在线支付部分','16305.02',NULL,NULL,'6','259','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('13','美团超券免单账户','FREE_MT_SUPER','VIRTUAL','180.00','RMB','美团超券商家免单部分','180.00',NULL,NULL,'6','29','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('14','大众点评闪慧账户','DPSH','VIRTUAL','55042.00','RMB','大众点评闪惠支付部分','55042.00',NULL,NULL,'7','494','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('15','POS机账户','POS','VIRTUAL','57720.31','RMB','pos机总账户','57720.31',NULL,'Y',NULL,'615','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('16','商家在线免单账户','FREE_ONLINE','VIRTUAL','66394.45','RMB','商家线上免单总金额','66394.45',NULL,NULL,'11','1157','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('17','外卖超人账户','WMCR','VIRTUAL','2535.00','RMB','外卖超人在线支付部分','2535.00',NULL,'Y',NULL,'120','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('18','外卖超人免单账户','FREE_WMCR','VIRTUAL','461.00','RMB','外卖超人商家免单部分','461.00',NULL,NULL,'17','98','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('20','百度糯米账户','BDNM','VIRTUAL','1656.96','RMB','百度糯米总账户','1656.96',NULL,'Y',NULL,'34','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('21','派乐趣账户','PLQ','VIRTUAL','2793.00','RMB','派乐趣总账户','2793.00',NULL,'Y',NULL,'99','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('22','派乐趣免单账户','FREE_PLQ','VIRTUAL','1217.00','RMB','派乐趣商家免单部分','1217.00',NULL,NULL,'21','85','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('23','淘点点总账户','TDD','VIRTUAL',NULL,'RMB','淘点点总账户',NULL,NULL,'Y',NULL,'0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('24','饿了么免单账户','FREE_ELE','VIRTUAL',NULL,'RMB','饿了么平台商家免单部分',NULL,NULL,NULL,'4','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('25','美团免单账户','FREE_MT','VIRTUAL',NULL,'RMB','美团团购商家免单部分',NULL,NULL,NULL,'6','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('26','美团外卖活动补贴账户','ALLOWANCE_MTWM','VIRTUAL',NULL,'RMB','美团外卖平台返还给商家的补贴部分',NULL,NULL,NULL,'8','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('27','商家线下免单账户','FREE_OFFLINE','VIRTUAL',NULL,'RMB','商家线下免单总金额',NULL,NULL,NULL,'11','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('28','百度糯米免单账户','FREE_BDNM','VIRTUAL',NULL,'RMB','百度糯米商家免单部分',NULL,NULL,NULL,'20','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('29','百度糯米到店付账户','DDF_BDNM','VIRTUAL',NULL,'RMB','百度糯米到店付部分',NULL,NULL,NULL,'20','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('30','百度糯米到店付免单账户','FREE_DDF_BDNM','VIRTUAL',NULL,'RMB','百度糯米到店付免单部分',NULL,NULL,NULL,'20','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('31','派乐趣补贴账户','ALLOWANCE_PLQ','VIRTUAL',NULL,'RMB','派乐趣平台返还给商家的补贴部分',NULL,NULL,NULL,'21','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('32','现金钱包账户','WALLET','CASH',NULL,'RMB','现金钱包账户',NULL,NULL,'Y',NULL,'0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('33','支付宝免单账户','FREE_ALIPAY','VIRTUAL',NULL,'RMB','支付宝在线免单部分',NULL,NULL,NULL,'10','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('34','大众点评闪惠免单账户','FREE_DPSH','VIRTUAL',NULL,'RMB','大众点评闪惠商家免单部分',NULL,NULL,NULL,'7','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('35','大众点评团购免单账户','FREE_DPTG','VIRTUAL',NULL,'RMB','大众点评团购商家免单部分',NULL,NULL,NULL,'7','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('36','外卖超人补贴账户','ALLOWNACE_WMCR','VIRTUAL',NULL,'RMB','外卖超人平台返还商家补贴部分',NULL,NULL,NULL,'17','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('37','饿了么在线支付账户','ONLINE_ELE','VIRTUAL',NULL,'RMB','饿了么在线支付金额入账',NULL,NULL,NULL,'4','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('38','美团团购账户','ONLINE_MT','VIRTUAL',NULL,'RMB','美团团购在线支付付账金额',NULL,NULL,NULL,'6','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('39','美团外卖在线支付账户','ONLINE_MTWM','VIRTUAL',NULL,'RMB','美团外卖在线支付金额入账',NULL,NULL,NULL,'8','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('40','百度糯米团购账户','ONLINE_BDNM','VIRTUAL',NULL,'RMB','百度糯米团购在线支付金额入账',NULL,NULL,NULL,'20','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('41','派乐趣在线支付账户','ONLINE_PLQ','VIRTUAL',NULL,'RMB','派乐趣在线支付金额入账',NULL,NULL,NULL,'21','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('42','淘点点免单账户','FREE_TDD','VIRTUAL',NULL,'RMB','淘点点商家免单金额',NULL,NULL,NULL,'23','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('43','淘点点在线支付账户','ONLINE_TDD','VIRTUAL',NULL,'RMB','淘点点在线支付金额',NULL,NULL,NULL,'23','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('44','大众点评团购账户','ONLINE_DPTG','VIRTUAL',NULL,'RMB','大众点评团购支付部分',NULL,NULL,NULL,'7','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('45','支付宝在线支付账户','ONLINE_ALIPAY','VIRTUAL',NULL,'RMB','支付宝在线支付金额',NULL,NULL,NULL,'10','0','P');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('46','POS机免单账户','FREE_POS','VIRTUAL',NULL,'RMB','POS机商家免单金额',NULL,NULL,NULL,'15','0','N');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`, `symbol`) values('47','POS机在线支付账户','ONLINE_POS','VIRTUAL',NULL,'RMB','pos刷卡部分',NULL,NULL,NULL,'15','0','P');
