CREATE TABLE `tik`.`salesstatistics` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `waiter_name` VARCHAR(45) NULL,
  `user_id` INT NULL,
  `sale_amount` VARCHAR(45) NULL,
  `tables` INT NULL,
  `customers` INT NULL,
  `products` INT NULL,
  `average_tables` VARCHAR(45) NULL,
  `average_customers` VARCHAR(45) NULL,
  `average_products` VARCHAR(45) NULL,
  `discounts` VARCHAR(45) NULL,
  `exceptions` VARCHAR(45) NULL,
  `turn` VARCHAR(45) NULL,
  `day` TIMESTAMP NULL,
  PRIMARY KEY (`id`));
