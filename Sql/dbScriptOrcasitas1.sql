CREATE TABLE `tik`.`purchase` (
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
