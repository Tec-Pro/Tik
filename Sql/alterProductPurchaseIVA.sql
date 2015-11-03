ALTER TABLE `tik`.`pproducts_purchases` 
ADD COLUMN `iva` FLOAT NULL DEFAULT 0 COMMENT '' AFTER `final_price`;
