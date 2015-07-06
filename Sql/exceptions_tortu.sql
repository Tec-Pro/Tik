ALTER TABLE `tik`.`orders` 
ADD COLUMN `exceptions` FLOAT NULL DEFAULT 0 AFTER `persons`;

ALTER TABLE `tik`.`orders` 
ADD COLUMN `paid_exceptions` FLOAT NULL DEFAULT 0 AFTER `exceptions`;
