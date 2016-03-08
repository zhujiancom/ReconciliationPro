DELIMITER $$

USE `reconciliation2`$$

DROP VIEW IF EXISTS `v_trunover_statistic`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`reconciliation`@`localhost` SQL SECURITY DEFINER VIEW `v_trunover_statistic` AS 
SELECT
  `oar`.`real_amount` AS `realamount`,
  `oar`.`accno`       AS `accno`,
  `oar`.`framework`   AS `framework`,
  `oar`.`post_time`   AS `postTime`,
  `acc`.`name`        AS `name`,
  `acc`.`symbol`      AS `symbol`
FROM (`bus_tb_order_account_ref` `oar`
   JOIN `bus_tb_account` `acc`)
WHERE (`oar`.`accno` = `acc`.`acc_no`)$$

DELIMITER ;