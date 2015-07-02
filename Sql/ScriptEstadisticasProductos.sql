CREATE TABLE `tik`.`productstatistics` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fproduct_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL DEFAULT 0,
  `turn` VARCHAR(45) NOT NULL,
  `day` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));

