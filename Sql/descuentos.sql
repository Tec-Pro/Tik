CREATE TABLE `tik`.`discounts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fproduct_id` INT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `user_id` INT NULL,
  `order_id` INT NULL,
  PRIMARY KEY (`id`));
