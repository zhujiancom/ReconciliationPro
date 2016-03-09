/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.17 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `bus_tb_account` (
	`h�i` bigint (20),
	`��i` varchar (765),
	`��i` varchar (765),
	`@�i` varchar (765),
	`��i` Decimal (21),
	`Хi` varchar (765),
	`(�i` varchar (765),
	`��i` Decimal (21),
	`ئi` Decimal (21),
	`0�i` varchar (765),
	`��i` bigint (20),
	`Чi` int (11)
); 
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('1','收银机账户','CASH_MACHINE','CASH','11105.00','RMB','收银机现金入账部分','11105.00','0.00','Y',NULL,'3088');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('4','饿了么总账户','ELE','VIRTUAL','8909.00','RMB','饿了么总账户金额','8909.00','-1191.00','Y',NULL,'5161');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('5','饿了么活动补贴账户','ALLOWANCE_ELE','VIRTUAL','0.00','RMB','饿了么平台返还给商家的补贴部分','0.00','0.00',NULL,'4','1705');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('6','美团账户','MT','VIRTUAL','9813.00','RMB','美团总账户','9813.00','-340.00','Y',NULL,'1584');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('7','大众点评总账户','DZDP','VIRTUAL','795.60','RMB','大众点评团购支付部分','795.60','-65.40','Y',NULL,'859');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('8','美团外卖账户','MTWM','VIRTUAL','3239.60','RMB','美团外卖总账户','3239.60','-1310.70','Y',NULL,'1586');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('9','美团外卖免单账户','FREE_MTWM','VIRTUAL','-1298.70','RMB','美团外卖商家免单部分','0.00','-1298.70',NULL,'8','960');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('10','支付宝账户','ALIPAY','VIRTUAL','4543.00','RMB','支付宝总账户','4543.00','-363.00','Y',NULL,'989');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('11','商家免单总账户','FREE','CASH','112.00','RMB','商家线上线下免单总金额','112.00','-3340.29',NULL,NULL,'3354');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('12','美团超券账户','MT_SUPER','VIRTUAL','4475.00','RMB','美团超券在线支付部分','4475.00','0.00',NULL,'6','755');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('13','美团超券免单账户','FREE_MT_SUPER','VIRTUAL','-300.00','RMB','美团超券商家免单部分','0.00','-300.00',NULL,'6','474');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('14','大众点评闪慧账户','DPSH','VIRTUAL','795.60','RMB','大众点评闪惠支付部分','795.60','0.00',NULL,'7','535');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('15','POS机账户','POS','VIRTUAL','2605.55','RMB','pos机总账户','2605.55','-10.45','Y',NULL,'858');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('16','商家在线免单账户','FREE_ONLINE','VIRTUAL','-3190.29','RMB','商家线上免单总金额','112.00','-3302.29',NULL,'11','3991');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('17','外卖超人账户','WMCR','VIRTUAL','0.00','RMB','外卖超人在线支付部分','0.00','0.00','Y',NULL,'120');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('18','外卖超人免单账户','FREE_WMCR','VIRTUAL','0.00','RMB','外卖超人商家免单部分','0.00','0.00',NULL,'17','98');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('20','百度糯米账户','BDNM','VIRTUAL','513.26','RMB','百度糯米总账户','513.26','-30.74','Y',NULL,'96');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('21','派乐趣账户','PLQ','VIRTUAL','49.00','RMB','派乐趣总账户','49.00','-3.00','Y',NULL,'118');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('22','派乐趣免单账户','FREE_PLQ','VIRTUAL','-3.00','RMB','派乐趣商家免单部分','0.00','-3.00',NULL,'21','94');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('23','淘点点总账户','TDD','VIRTUAL','0.00','RMB','淘点点总账户','0.00','0.00','Y',NULL,'0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('24','饿了么免单账户','FREE_ELE','VIRTUAL','-1191.00','RMB','饿了么平台商家免单部分','0.00','-1191.00',NULL,'4','2066');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('25','美团免单账户','FREE_MT','VIRTUAL','-40.00','RMB','美团团购商家免单部分','0.00','-40.00',NULL,'6','45');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('26','美团外卖活动补贴账户','ALLOWANCE_MTWM','VIRTUAL','908.00','RMB','美团外卖平台返还给商家的补贴部分','920.00','-12.00',NULL,'8','445');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('27','商家线下免单账户','FREE_OFFLINE','VIRTUAL','-38.00','RMB','商家线下免单总金额','0.00','-38.00',NULL,'11','22');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('28','百度糯米免单账户','FREE_BDNM','VIRTUAL','-30.74','RMB','百度糯米商家免单部分','0.00','-30.74',NULL,'20','48');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('29','百度糯米到店付账户','DDF_BDNM','VIRTUAL','0.00','RMB','百度糯米到店付部分','0.00','0.00',NULL,'20','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('30','百度糯米到店付免单账户','FREE_DDF_BDNM','VIRTUAL','0.00','RMB','百度糯米到店付免单部分','0.00','0.00',NULL,'20','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('31','派乐趣补贴账户','ALLOWANCE_PLQ','VIRTUAL','5.00','RMB','派乐趣平台返还给商家的补贴部分','5.00','0.00',NULL,'21','9');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('32','现金钱包账户','WALLET','CASH','0.00','RMB','现金钱包账户','0.00','0.00','Y',NULL,'0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('33','支付宝免单账户','FREE_ALIPAY','VIRTUAL','-251.00','RMB','支付宝在线免单部分','112.00','-363.00',NULL,'10','43');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('34','大众点评闪惠免单账户','FREE_DPSH','VIRTUAL','-65.40','RMB','大众点评闪惠商家免单部分','0.00','-65.40',NULL,'7','41');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('35','大众点评团购免单账户','FREE_DPTG','VIRTUAL','0.00','RMB','大众点评团购商家免单部分','0.00','0.00',NULL,'7','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('36','外卖超人补贴账户','ALLOWNACE_WMCR','VIRTUAL','0.00','RMB','外卖超人平台返还商家补贴部分','0.00','0.00',NULL,'17','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('37','饿了么在线支付账户','ONLINE_ELE','VIRTUAL','8909.00','RMB','饿了么在线支付金额入账','8909.00','0.00',NULL,'4','2089');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('38','美团团购账户','ONLINE_MT','VIRTUAL','5338.00','RMB','美团团购在线支付付账金额','5338.00','0.00',NULL,'6','574');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('39','美团外卖在线支付账户','ONLINE_MTWM','VIRTUAL','2319.60','RMB','美团外卖在线支付金额入账','2319.60','0.00',NULL,'8','466');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('40','百度糯米团购账户','ONLINE_BDNM','VIRTUAL','513.26','RMB','百度糯米团购在线支付金额入账','513.26','0.00',NULL,'20','48');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('41','派乐趣在线支付账户','ONLINE_PLQ','VIRTUAL','44.00','RMB','派乐趣在线支付金额入账','44.00','0.00',NULL,'21','9');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('42','淘点点免单账户','FREE_TDD','VIRTUAL','0.00','RMB','淘点点商家免单金额','0.00','0.00',NULL,'23','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('43','淘点点在线支付账户','ONLINE_TDD','VIRTUAL','0.00','RMB','淘点点在线支付金额','0.00','0.00',NULL,'23','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('44','大众点评团购账户','ONLINE_DPTG','VIRTUAL','0.00','RMB','大众点评团购支付部分','0.00','0.00',NULL,'7','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('45','支付宝在线支付账户','ONLINE_ALIPAY','VIRTUAL','4431.00','RMB','支付宝在线支付金额','4431.00','0.00',NULL,'10','513');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('46','POS机免单账户','FREE_POS','VIRTUAL','-10.45','RMB','POS机商家免单金额','0.00','-10.45',NULL,'15','96');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('47','POS机在线支付账户','ONLINE_POS','VIRTUAL','2605.55','RMB','pos刷卡部分','2605.55','0.00',NULL,'15','210');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('48','百度外卖账户','BDWM','VIRTUAL','128.08','RMB','百度外卖主账户','128.08','0.00','Y',NULL,'74');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('49','百度外卖在线支付账户','ONLINE_BDWM','VIRTUAL','128.08','RMB','百度外卖在线支付金额','128.08','0.00',NULL,'48','74');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('50','百度外卖免单账户','FREE_BDWM','VIRTUAL','0.00','RMB','百度外卖商家免单金额','0.00','0.00',NULL,'48','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('51','百度外卖补贴账户','ALLOWANCE_BDWM','VIRTUAL','0.00','RMB','百度外卖平台给商家的补贴金额','0.00','0.00',NULL,'48','0');
insert into `bus_tb_account` (`accId`, `name`, `acc_no`, `type`, `balance`, `currency`, `description`, `earning_amount`, `expense_amount`, `is_parent`, `pid`, `version`) values('52','线上代金券使用账户','ONLINE_VOUCHER','VIRTUAL','0.00','RMB','商家自己在线上平台发放的代金券消费金额','0.00','0.00','Y',NULL,'0');
