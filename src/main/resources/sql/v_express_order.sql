DELIMITER $$

USE `reconciliation2`$$

DROP VIEW IF EXISTS `v_express_order`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`reconciliation`@`localhost` SQL SECURITY DEFINER VIEW `v_express_order` AS 
SELECT
  `o`.`oid`                AS `oid`,
  `o`.`checkout_time`      AS `checkout_time`,
  `o`.`day`                AS `day`,
  `o`.`no_discount_amount` AS `no_discount_amount`,
  `o`.`opendesk_time`      AS `opendesk_time`,
  `o`.`order_no`           AS `order_no`,
  `o`.`origin_price`       AS `origin_price`,
  `o`.`pay_no`             AS `pay_no`,
  `o`.`paymodes`           AS `paymodes`,
  `o`.`real_amount`        AS `real_amount`,
  `o`.`scheme_name`        AS `scheme_name`,
  `o`.`is_single_discount` AS `is_single_discount`,
  `o`.`is_unusual`         AS `is_unusual`,
  `o`.`version`            AS `version`,
  `o`.`table_no`           AS `table_no`,
  `o`.`warning_info`       AS `warning_info`,
  `o`.`framework`          AS `framework`
FROM (`bus_tb_order` `o`
   LEFT JOIN `bus_tb_table` `t`
     ON ((`o`.`table_no` = `t`.`table_no`)))
WHERE ((`o`.`real_amount` > 0)
       AND (`t`.`table_type` = '03'))$$

DELIMITER ;