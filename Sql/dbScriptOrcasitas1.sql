CREATE TABLE `tik`.`purchases` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cost` FLOAT NULL,
  `paid` FLOAT NULL,
  `date` DATE NULL,
  `provider_id` INT NULL,
  `date_paid` DATE NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `tik`.`pproducts_purchases` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pproduct_id` VARCHAR(45) NOT NULL,
  `purchase_id` VARCHAR(45) NOT NULL,
  `amount` VARCHAR(45) NOT NULL,
  `final_price` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
  ALTER TABLE `tik`.`pproducts` 
ADD COLUMN `provider_id` INT NULL AFTER `removed`;

ALTER TABLE `tik`.`pproducts` 
CHANGE COLUMN `provider_id` `provider_id` INT(11) NULL DEFAULT 0 ;
ALTER TABLE `tik`.`pproducts` 
CHANGE COLUMN `provider_id` `provider_id` INT(11) NOT NULL DEFAULT '0' ;

