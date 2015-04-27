#Script modificaciones a productos

ALTER TABLE `tik`.`pproducts` 
DROP COLUMN `subcategory_id`,
DROP COLUMN `amount`,
CHANGE COLUMN `stock` `stock` FLOAT NULL DEFAULT 0 ;


ALTER TABLE `tik`.`eproducts` 
DROP COLUMN `subcategory_id`,
DROP COLUMN `measure_unit`,
DROP COLUMN `stock`;
