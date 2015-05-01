CREATE TABLE `tik`.`presences` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `day` DATE NULL,
  `entry_time` TIME NULL,
  `user_id` INT NULL,
  `departure_time` TIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`));
