ALTER TABLE `tik`.`orders_fproducts` 
ADD COLUMN `discount` TINYINT(1) NULL DEFAULT '0' AFTER `paid`;
