delimiter $$

CREATE DATABASE `tik` /*!40100 DEFAULT CHARACTER SET latin1 */$$

USE tik $$

delimiter $$

CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `eproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `stock` float DEFAULT NULL,
  `measure_unit` varchar(45) DEFAULT NULL,
  `subcategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `eproducts_pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eproduct_id` int(11) DEFAULT NULL,
  `pproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `fproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `subcategory_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `fproducts_eproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fproduct_id` int(11) DEFAULT NULL,
  `eproduct_id` int(11) DEFAULT NULL,
  `amount` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$


delimiter $$

CREATE TABLE `fproducts_pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fproduct_id` int(11) DEFAULT NULL,
  `pproduct_id` int(11) DEFAULT NULL,
  `pproduct_amount` float DEFAULT NULL,
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

CREATE TABLE `pproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT 'empty',
  `stock` float DEFAULT '-1',
  `subcategory_id` int(11) NOT NULL DEFAULT '-1',
  `measure_unit` varchar(45) DEFAULT NULL,
  `unit_price` float DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$



