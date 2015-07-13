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

/*Data for the table `bus_tb_dish` */

insert  into `bus_tb_dish`(`did`,`dish_name`,`dish_no`,`dish_price`,`version`,`dish_type_id`,`stop_flag`,`stock_flag`) values (232,'原味无骨(小)','01',35.00,0,25,'N',NULL),(233,'原味无骨(大)','02',40.00,0,25,'N',NULL),(234,'原味无骨(小-外送)','110',30.00,0,25,'Y',NULL),(235,'原味无骨(大-外送)','112',38.00,0,25,'Y',NULL),(236,'香辣无骨(小-外送)','113',30.00,0,25,'Y',NULL),(237,'香辣无骨(大-外送)','115',38.00,0,25,'Y',NULL),(238,'外带','146',0.00,0,25,'Y',NULL),(239,'甜辣酱','174',0.00,0,25,'Y',NULL),(240,'照烧酱','175',0.00,0,25,'Y',NULL),(241,'补差价','350',1.00,0,25,'N',NULL),(242,'补差价','351',5.00,0,25,'N',NULL),(243,'无骨双拼小份','400',40.00,0,25,'N',NULL),(244,'无骨双拼大份','401',45.00,0,25,'N',NULL),(245,'香辣无骨 (小)','47',35.00,0,25,'N',NULL),(246,'香辣无骨(大)','48',40.00,0,25,'N',NULL),(247,'无骨鸡腿（小）','593',40.00,0,25,'N',NULL),(248,'无骨鸡腿（大）','594',45.00,0,25,'N',NULL),(249,'香辣无骨(小)','03',30.00,0,26,'N',NULL),(250,'香辣无骨(大)','04',38.00,0,26,'N',NULL),(251,'香辣带骨(小)','05',40.00,0,27,'N',NULL),(252,'香辣带骨(大)','06',45.00,0,27,'N',NULL),(253,'香辣带骨(小-外送)','116',35.00,0,27,'Y',NULL),(254,'香辣带骨(大-外送)','117',40.00,0,27,'Y',NULL),(255,'甜辣带骨(小-外送)','118',35.00,0,27,'Y',NULL),(256,'甜辣带骨(大-外送)','119',40.00,0,27,'Y',NULL),(257,'不刷酱','171',0.00,0,27,'Y',NULL),(258,'带骨双拼（大份）','185',50.00,0,27,'N',NULL),(259,'带骨双拼（小份）','186',45.00,0,27,'N',NULL),(260,'原味带骨(小)','303',40.00,0,27,'N',NULL),(261,'原味带骨(大)','304',45.00,0,27,'N',NULL),(262,'甜辣带骨(小)','49',40.00,0,27,'N',NULL),(263,'甜辣带骨(大)','50',45.00,0,27,'N',NULL),(264,'蒜香带骨（大）','598',45.00,0,27,'N',NULL),(265,'甜辣带骨(小)','07',35.00,0,28,'Y',NULL),(266,'甜辣带骨(大)','08',40.00,0,28,'Y',NULL),(267,'杰克丹尼(小)','09',40.00,0,29,'N',NULL),(268,'杰克丹尼(大)','10',45.00,0,29,'N',NULL),(269,'杰克丹尼(小-外送)','120',40.00,0,29,'Y',NULL),(270,'杰克丹尼(大-外送)','121',45.00,0,29,'Y',NULL),(271,'香辣碳烤(小-外送)','122',40.00,0,29,'Y',NULL),(272,'香辣碳烤(大-外送)','123',45.00,0,29,'Y',NULL),(273,'蒜香碳烤(小-外送)','124',40.00,0,29,'Y',NULL),(274,'蒜香碳烤(大-外送)','125',45.00,0,29,'Y',NULL),(275,'外送','160',0.00,0,29,'Y',NULL),(276,'香辣碳烤(小)','51',40.00,0,29,'N',NULL),(277,'香辣碳烤(大)','52',45.00,0,29,'N',NULL),(278,'蒜香碳烤(小)','53',40.00,0,29,'N',NULL),(279,'蒜香碳烤(大)','54',45.00,0,29,'N',NULL),(280,'香辣碳烤(小)','12',40.00,0,30,'N',NULL),(281,'香辣碳烤(大)','13',45.00,0,30,'N',NULL),(282,'蒜香碳烤(小)','14',40.00,0,31,'N',NULL),(283,'蒜香碳烤(大)','15',45.00,0,31,'N',NULL),(284,'全鸡香辣(外送)','126',80.00,0,32,'Y',NULL),(285,'全鸡照烧香辣(外送)','127',85.00,0,32,'Y',NULL),(286,'全鸡蒜香甜辣(外送)','128',85.00,0,32,'Y',NULL),(287,'全鸡香辣','16',85.00,0,32,'N',NULL),(288,'香辣酱','162',3.00,0,32,'Y',NULL),(289,'甜辣酱','163',3.00,0,32,'Y',NULL),(290,'照烧酱','164',3.00,0,32,'Y',NULL),(291,'黄芥酱','165',3.00,0,32,'Y',NULL),(292,'蒜香酱','166',3.00,0,32,'Y',NULL),(293,'全鸡（香辣-照烧）','178',90.00,0,32,'N',NULL),(294,'全鸡照烧香辣','18',85.00,0,32,'Y',NULL),(295,'全鸡','180',85.00,0,32,'Y',NULL),(296,'全鸡蒜香甜辣','19',85.00,0,32,'Y',NULL),(297,'你的一半','308',80.00,0,32,'N',NULL),(298,'全鸡（甜辣-蒜香）','310',90.00,0,32,'N',NULL),(299,'半鸡香辣','317',50.00,0,32,'N',NULL),(300,'半鸡照烧','507',50.00,0,32,'N',NULL),(301,'半鸡甜辣','508',50.00,0,32,'N',NULL),(302,'半鸡蒜香','509',50.00,0,32,'N',NULL),(303,'全鸡（甜辣-照烧）','527',90.00,0,32,'N',NULL),(304,'全鸡（香辣-甜辣）','528',90.00,0,32,'N',NULL),(305,'全鸡（香辣-蒜香）','529',90.00,0,32,'N',NULL),(306,'番茄酱','530',3.00,0,32,'Y',NULL),(307,'全鸡（蒜香-照烧）','531',90.00,0,32,'N',NULL),(308,'半鸡原味','580',50.00,0,32,'N',NULL),(309,'甜辣全鸡','591',85.00,0,32,'N',NULL),(310,'部队年糕火锅','21',68.00,0,33,'N',NULL),(311,'鸡肉年糕火锅','22',78.00,0,33,'N',NULL),(312,'五花肉年糕火锅','23',78.00,0,33,'N',NULL),(313,'火锅清汤','320',0.00,0,33,'N',NULL),(314,'排骨年糕火锅','360',88.00,0,33,'N',NULL),(315,'肥牛年糕火锅','362',88.00,0,33,'N',NULL),(316,'frypan薯片(外送)','129',15.00,0,34,'Y',NULL),(317,'无骨鸡肉卷(原味)','24',15.00,0,34,'Y',NULL),(318,'无骨鸡肉卷(香辣)','25',15.00,0,34,'Y',NULL),(319,'鸡肉沙拉','26',25.00,0,34,'Y',NULL),(320,'frypan水果沙拉','27',35.00,0,34,'Y',NULL),(321,'frypan薯片','28',15.00,0,34,'Y',NULL),(322,'香爆三剑客（中份）','309',65.00,0,34,'Y',NULL),(323,'香爆三剑客（大份）','313',75.00,0,34,'Y',NULL),(324,'无骨鸡肉卷（甜辣）','316',15.00,0,34,'Y',NULL),(325,'香酥棒棒腿原味','505',10.00,0,34,'Y',NULL),(326,'脆皮棒棒腿原味','506',10.00,0,34,'Y',NULL),(327,'风味薯片','516',15.00,0,34,'Y',NULL),(328,'脆皮棒棒腿甜辣','518',10.00,0,34,'Y',NULL),(329,'脆皮棒棒腿香辣','519',10.00,0,34,'Y',NULL),(330,'脆皮棒棒腿照烧','520',10.00,0,34,'Y',NULL),(331,'脆皮棒棒腿蒜香','521',10.00,0,34,'Y',NULL),(332,'香酥棒棒腿甜辣','522',10.00,0,34,'Y',NULL),(333,'香酥棒棒腿香辣','523',10.00,0,34,'Y',NULL),(334,'香酥棒棒腿照烧','524',10.00,0,34,'Y',NULL),(335,'香酥棒棒腿蒜香','525',10.00,0,34,'Y',NULL),(336,'海鲜炒饭(外送)','130',20.00,0,35,'Y',NULL),(337,'咸鱼鸡粒炒饭(外送)','131',18.00,0,35,'Y',NULL),(338,'香辣翅中年糕(外送)','132',25.00,0,35,'Y',NULL),(339,'鸡肉红薯年糕(外送)','133',20.00,0,35,'Y',NULL),(340,'外送','168',0.00,0,35,'Y',NULL),(341,'海鲜炒饭','29',20.00,0,35,'N','Y'),(342,'咸鱼鸡粒炒饭','30',18.00,0,35,'N','Y'),(343,'香辣翅中年糕','31',25.00,0,35,'N',NULL),(344,'鸡肉红薯年糕','32',25.00,0,35,'N',NULL),(345,'香辣鸡肉卷','541',15.00,0,35,'N','Y'),(346,'甜辣鸡肉卷','542',15.00,0,35,'N','Y'),(347,'原味鸡肉卷','543',15.00,0,35,'N','Y'),(348,'韩式泡菜炒饭','592',23.00,0,35,'N','Y'),(349,'牛奶苏打','145',8.00,0,36,'N','Y'),(350,'百事可乐','190',7.00,0,36,'N','Y'),(351,'宾格瑞牛奶','305',20.00,0,36,'Y',NULL),(352,'青岛冰纯','307',7.00,0,36,'N','Y'),(353,'扎啤','33',18.00,0,36,'N',NULL),(354,'排骨','330',18.00,0,36,'Y',NULL),(355,'柚子汽水','34',18.00,0,36,'Y',NULL),(356,'韩国柚子茶','35',20.00,0,36,'Y',NULL),(357,'葡萄汁','36',8.00,0,36,'N','Y'),(358,'芒果汁','37',8.00,0,36,'N','Y'),(359,'橙汁','38',8.00,0,36,'N','Y'),(360,'石榴汁','39',8.00,0,36,'N','Y'),(361,'草莓汁','40',8.00,0,36,'N','Y'),(362,'cass啤酒','41',10.00,0,36,'N','Y'),(363,'真露烧酒','42',30.00,0,36,'N','Y'),(364,'玄米汁','43',8.00,0,36,'N','Y'),(365,'乐天雪碧','44',8.00,0,36,'N','Y'),(366,'青岛纯生','511',12.00,0,36,'N','Y'),(367,'玛克丽生米酒','513',45.00,0,36,'N','Y'),(368,'玛克丽碳酸米酒','514',45.00,0,36,'N','Y'),(369,'百事可乐1.2L','540',15.00,0,36,'N','Y'),(370,'雪花啤酒','599',8.00,0,36,'N','Y'),(371,'柚子汽水','60',18.00,0,36,'N',NULL),(372,'韩国柚子茶','69',20.00,0,36,'N',NULL),(373,'白米饭','134',3.00,0,37,'N',NULL),(374,'鸡蛋','135',2.00,0,37,'N',NULL),(375,'韩式泡菜','150',3.00,0,37,'N',NULL),(376,'芝士年糕','151',5.00,0,37,'Y',NULL),(377,'金针菇','200',8.00,0,37,'N',NULL),(378,'白菜','201',3.00,0,37,'Y',NULL),(379,'鸡肉','300',10.00,0,37,'N',NULL),(380,'火腿','306',8.00,0,37,'N',NULL),(381,'鱼饼','318',6.00,0,37,'N',NULL),(382,'排骨','340',18.00,0,37,'N',NULL),(383,'肥牛','341',18.00,0,37,'N',NULL),(384,'芝士','361',5.00,0,37,'N',NULL),(385,'农心拉面','45',6.00,0,37,'N','Y'),(386,'芝士年糕','46',10.00,0,37,'N',NULL),(387,'海藻-堂食','512',8.00,0,37,'N',NULL),(388,'蜂蜜黄芥酱','515',5.00,0,37,'N',NULL),(389,'情迷海藻（外）','517',6.00,0,37,'Y',NULL),(390,'韩式萝卜','526',3.00,0,37,'N',NULL),(391,'甜辣酱','532',3.00,0,37,'N',NULL),(392,'香辣酱','533',3.00,0,37,'N',NULL),(393,'照烧酱','534',3.00,0,37,'N',NULL),(394,'蒜香酱','535',3.00,0,37,'N',NULL),(395,'黄芥酱','536',5.00,0,37,'N',NULL),(396,'番茄酱','537',3.00,0,37,'N',NULL),(397,'黑蒜酱油酱','538',5.00,0,37,'N',NULL),(398,'海藻-外送','539',6.00,0,37,'N',NULL),(399,'白米饭','56',2.00,0,37,'Y',NULL),(400,'小酱','57',3.00,0,37,'Y',NULL),(401,'白菜','590',8.00,0,37,'N',NULL),(402,'五花肉','77',12.00,0,37,'N',NULL),(403,'不刷酱','172',0.00,0,38,'Y',NULL),(404,'套菜小份','58',50.00,0,38,'Y',NULL),(405,'套餐大份（香辣带骨）','59',98.00,0,38,'Y',NULL),(406,'套餐大份（甜辣）','70',98.00,0,38,'Y',NULL),(407,'超值套餐A（加百事一罐）','71',25.00,0,38,'N',NULL),(408,'超值套餐B（加果汁2罐）','72',52.00,0,38,'N',NULL),(409,'超值套餐C（原味）','73',98.00,0,38,'N',NULL),(410,'超值套餐C（香辣）','74',98.00,0,38,'N',NULL),(411,'欢乐全家餐（2冰纯，1葡萄）','500',118.00,0,39,'N',NULL),(412,'甜蜜情侣套餐（玄米，苏打）','501',114.00,0,39,'N',NULL),(413,'韩式海鲜套餐（加百事）','502',31.00,0,39,'N',NULL),(414,'韩式套餐','503',42.00,0,39,'N',NULL),(415,'翅中年糕（加果汁）','504',23.00,0,39,'N',NULL),(416,'棒棒腿套餐','570',60.00,0,39,'N',NULL),(417,'风味薯片','544',15.00,0,40,'N',NULL),(418,'香酥原味鸡腿','545',10.00,0,40,'N',NULL),(419,'香酥甜辣鸡腿','546',10.00,0,40,'N',NULL),(420,'香酥香辣鸡腿','547',10.00,0,40,'N',NULL),(421,'香酥蒜香鸡腿','548',10.00,0,40,'N',NULL),(422,'香酥照烧鸡腿','549',10.00,0,40,'N',NULL),(423,'香辣三剑客(中)','550',65.00,0,40,'N',NULL),(424,'甜辣三剑客(中)','551',65.00,0,40,'N',NULL),(425,'照烧三剑客(中)','552',65.00,0,40,'N',NULL),(426,'蒜香三剑客(中)','553',65.00,0,40,'N',NULL),(427,'香辣三剑客(大)','554',75.00,0,40,'N',NULL),(428,'甜辣三剑客(大)','555',75.00,0,40,'N',NULL),(429,'照烧三剑客(大)','556',75.00,0,40,'N',NULL),(430,'蒜香三剑客(大)','557',75.00,0,40,'N',NULL),(431,'原味凤凰展翅','558',12.00,0,40,'N',NULL),(432,'甜辣凤凰展翅','559',12.00,0,40,'N',NULL),(433,'香辣凤凰展翅','560',12.00,0,40,'N',NULL),(434,'照烧凤凰展翅','561',12.00,0,40,'N',NULL),(435,'蒜香凤凰展翅','562',12.00,0,40,'N',NULL),(436,'脆皮原味鸡腿','563',10.00,0,40,'N',NULL),(437,'脆皮甜辣鸡腿','564',10.00,0,40,'N',NULL),(438,'脆皮香辣鸡腿','565',10.00,0,40,'N',NULL),(439,'脆皮蒜香鸡腿','566',10.00,0,40,'N',NULL),(440,'脆皮照烧鸡腿','567',10.00,0,40,'N',NULL),(441,'水果沙拉','568',35.00,0,40,'N',NULL),(442,'虾仁脆饼','596',25.00,0,40,'N','Y'),(443,'凤凰展翅一个','600',12.00,0,40,'N',NULL),(444,'趣味薯条(大)','601',20.00,0,40,'N',NULL),(445,'趣味薯条(中)','602',15.00,0,40,'N',NULL),(446,'凤凰展翅（一对）','603',22.00,0,40,'N',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
