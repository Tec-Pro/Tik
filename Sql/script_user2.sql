#Script2 modificaciones a users

ALTER TABLE `tik`.`users` 
CHANGE COLUMN `photo` `photo` varchar(45) DEFAULT NULL;
