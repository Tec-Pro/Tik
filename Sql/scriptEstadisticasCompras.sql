CREATE TABLE `tik`.`pproductcategories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `tik`.`pproductsubcategories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `pproductcategory_id` INT NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `tik`.`pproducts` 
ADD COLUMN `pproductsubcategory_id` INT NOT NULL AFTER `provider_id`;

CREATE TABLE `tik`.`purchasestatistics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `pproductcategory_id` INT NOT NULL,
  `pproductsubcategory_id` INT NOT NULL,
  `pproductcategory_name` VARCHAR(45) NULL,
  `pproductsubcategory_name` VARCHAR(45) NULL,
  `pproduct_id` INT NOT NULL,
  `pproduct_name` VARCHAR(45) NULL,
  `quantity` FLOAT NOT NULL,
  `measure_unit` VARCHAR(10) NULL,
  `unit_price` FLOAT NULL,
  `total_price` FLOAT NOT NULL,
  `provider_id` INT NULL,
  `provider_name` VARCHAR(45) NULL,
  `day` DATE NOT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `tik`.`pproducts_purchases` 
CHANGE COLUMN `pproduct_id` `pproduct_id` INT NOT NULL ,
CHANGE COLUMN `purchase_id` `purchase_id` INT NOT NULL ,
CHANGE COLUMN `amount` `amount` FLOAT NOT NULL ,
CHANGE COLUMN `final_price` `final_price` FLOAT NOT NULL ;

