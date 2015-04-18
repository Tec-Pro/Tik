delimiter $$

CREATE DATABASE `tik` /*!40100 DEFAULT CHARACTER SET latin1 */$$

USE tik $$

delimiter $$

CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `is_admin` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `providers_products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `pass` varchar(45) DEFAULT NULL,
  `entry_date` date DEFAULT NULL,
  `exit_date` date DEFAULT NULL,
  `turn` varchar(45) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `place_of_birth` varchar(45) DEFAULT NULL,
  `id_type` varchar(45) DEFAULT NULL,
  `id_number` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `home_phone` varchar(45) DEFAULT NULL,
  `emergency_phone` varchar(45) DEFAULT NULL,
  `mobile_phone` varchar(45) DEFAULT NULL,
  `marital_status` varchar(45) DEFAULT NULL,
  `blood_type` varchar(45) DEFAULT NULL,
  `position` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `categories` (
`id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `providers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `cuit` varchar(45) DEFAULT '-1',
  `address` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `phones` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `providercategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

CREATE TABLE `providers_providercategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `providercategory_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$

## PARTE DE JAZU Y ALAN.
delimiter $$
#productos elaborados
CREATE TABLE `eproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `stock` float DEFAULT NULL,
  `measure_unit` varchar(45) DEFAULT NULL,
  `subcategory_id` int(11) DEFAULT NULL,
  `removed` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$
#relacion n n produtos elaborados y productos primarios
CREATE TABLE `eproducts_pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eproduct_id` int(11) DEFAULT NULL,
  `pproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$
#productos finales
CREATE TABLE `fproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `subcategory_id` int(11) DEFAULT NULL,
  `removed` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$
#relacion n n produtos elaborados y productos finales
CREATE TABLE `fproducts_eproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fproduct_id` int(11) DEFAULT NULL,
  `eproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$
#relacion n n produtos finales y productos primarios
CREATE TABLE `fproducts_pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fproduct_id` int(11) DEFAULT NULL,
  `pproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `subcategories` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`name` varchar(45) DEFAULT NULL,
`category_id` int(11) DEFAULT '-1',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

delimiter $$
#productos primarios
CREATE TABLE `pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT 'empty',
  `stock` float DEFAULT '-1',
  `subcategory_id` int(11) NOT NULL DEFAULT '-1',
  `measure_unit` varchar(45) DEFAULT NULL,
  `unit_price` float DEFAULT '0',
  `removed` int(1) DEFAULT '0',
  `amount` float DEFAULT '0',	
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


