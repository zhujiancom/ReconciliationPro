CREATE VIEW v_dish_sale_statistic AS 
	SELECT oi.dish_no 'dishno',d.dish_name 'dishname',d.dish_price 'dishprice',oi.count 'count',oi.count_back 'backcount',oi.consume_time 'consumeTime'
	FROM bus_tb_order_item oi,bus_tb_dish d WHERE oi.dish_no=d.dish_no AND d.statistic_flag = 'Y' AND oi.suit_flag IN ('P','N');
	
CREATE VIEW v_trunover_statistic AS SELECT oar.real_amount 'realamount',oar.accno 'accno',oar.framework 'framework',oar.post_time 'postTime',acc.name 'name',acc.symbol 'symbol'
FROM bus_tb_order_account_ref oar ,bus_tb_account acc
WHERE oar.accno=acc.acc_no;

CREATE VIEW v_express_order AS SELECT o.* FROM bus_tb_order o LEFT JOIN bus_tb_table t ON o.table_no=t.table_no
WHERE o.real_amount > 0 AND t.table_type='03';

-- CREATE VIEW v_daily_post_account AS SELECT ref.accno,sum(ref.real_amount),ref.post_time from bus_tb_order_account_ref ref GROUP BY ref.post_time,ref.accno

DELIMITER $$

USE `reconciliation2`$$

DROP VIEW IF EXISTS `v_daily_post_account`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`reconciliation`@`localhost` SQL SECURITY DEFINER VIEW `v_daily_post_account` AS 
SELECT
  `ref`.`accno`        AS `accno`,
  SUM(`ref`.`real_amount`) AS `amount`,
  `ref`.`main_account` AS `main_account`,
  `ref`.`post_time`    AS `post_time`
FROM `bus_tb_order_account_ref` `ref`
GROUP BY `ref`.`post_time`,`ref`.`accno`$$

DELIMITER ;