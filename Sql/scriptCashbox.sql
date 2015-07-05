CREATE TABLE `tik`.`cashboxes` (
 `id` INT NOT NULL AUTO_INCREMENT,
 `day` DATE NULL,
 `turn` VARCHAR(1) NULL,
 `balance` FLOAT NULL,
 `collect` FLOAT NULL,
 `entry_cash` FLOAT NULL,
 `spend` FLOAT NULL,
 `withdrawal` FLOAT NULL,
 `delivery_cash` FLOAT NULL,
 `delivery_waiter` FLOAT NULL,
 PRIMARY KEY (`id`));


INSERT INTO `tik`.`cashboxes`(`balance`,`collect`,`entry_cash`,`spend`,`withdrawal`,`delivery_cash`,`delivery_waiter`)VALUES (0,0,0,0,0,0,0);
