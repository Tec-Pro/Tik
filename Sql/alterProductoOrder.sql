ALTER TABLE `tik`.`orders_fproducts` 
ADD COLUMN `discount` TINYINT(1) NULL DEFAULT '0' AFTER `paid`;

ALTER TABLE `tik`.`orders` 
ADD COLUMN `discount` FLOAT NULL  DEFAULT '0' AFTER `paid_exceptions`;
