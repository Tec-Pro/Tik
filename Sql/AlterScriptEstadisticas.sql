ALTER TABLE `tik`.`salesstatistics` 
CHANGE COLUMN `sale_amount` `sale_amount` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `average_tables` `average_tables` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `average_customers` `average_customers` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `average_products` `average_products` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `discounts` `discounts` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `exceptions` `exceptions` DOUBLE NULL DEFAULT NULL ;

