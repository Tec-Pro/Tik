ALTER TABLE `tik`.`deposits` 
CHANGE COLUMN `turn` `turn` VARCHAR(45) NULL AFTER `amount`;

ALTER TABLE `tik`.`withdrawals` 
ADD COLUMN `turn` VARCHAR(45) NULL AFTER `detail`;
