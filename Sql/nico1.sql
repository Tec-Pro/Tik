ALTER TABLE `tik`.`orders_fproducts` 
ADD COLUMN `paid` TINYINT(1) NULL AFTER `updated_at`;
ALTER TABLE `tik`.`orders_fproducts` 
CHANGE COLUMN `paid` `paid` TINYINT(1) NULL DEFAULT 0 ;
