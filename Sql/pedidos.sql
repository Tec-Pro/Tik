CREATE TABLE `tik`.`orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` varchar(200) DEFAULT NULL,
  `closed` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `tik`.`orders_fproducts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `fproduct_id` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL, #hecho
  `commited` tinyint(1) DEFAULT NULL, #entregado
  `issued` tinyint(1) DEFAULT NULL, #enviado
  PRIMARY KEY (`id`));
