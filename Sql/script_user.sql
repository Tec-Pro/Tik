#Script modificaciones a users

ALTER TABLE `tik`.`users` 
CHANGE COLUMN `entry_date` `date_hired` date DEFAULT NULL,
CHANGE COLUMN `exit_date` `date_discharged` date DEFAULT NULL,
ADD COLUMN `photo` BLOB DEFAULT NULL;
