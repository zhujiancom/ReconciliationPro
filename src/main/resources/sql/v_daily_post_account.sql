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