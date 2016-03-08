DELIMITER $$

USE `reconciliation2`$$

DROP VIEW IF EXISTS `v_dish_sale_statistic`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`reconciliation`@`localhost` SQL SECURITY DEFINER VIEW `v_dish_sale_statistic` AS 
SELECT
  `oi`.`dish_no`      AS `dishno`,
  `d`.`dish_name`     AS `dishname`,
  `d`.`dish_price`    AS `dishprice`,
  `oi`.`count`        AS `count`,
  `oi`.`count_back`   AS `backcount`,
  `oi`.`consume_time` AS `consumeTime`
FROM (`bus_tb_order_item` `oi`
   JOIN `bus_tb_dish` `d`)
WHERE ((`oi`.`dish_no` = `d`.`dish_no`)
       AND (`d`.`statistic_flag` = 'Y')
       AND (`oi`.`suit_flag` IN('P','N')))$$

DELIMITER ;