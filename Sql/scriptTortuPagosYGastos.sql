CREATE TABLE `tik`.`payments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `provider_id` INT NULL,
  `detail` VARCHAR(200) NULL,
  `amount` FLOAT NULL,
  `purchase_id` INT NULL,
  `date` DATE NULL,
  `name_admin` VARCHAR(45) NULL,

  PRIMARY KEY (`id`));
  
  ALTER TABLE `tik`.`providers` 
ADD COLUMN `current_account` FLOAT DEFAULT 0 AFTER `phones`;

