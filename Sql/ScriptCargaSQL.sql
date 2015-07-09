-- Inserto administradores
INSERT INTO `admins` (`id`,`name`,`pass`,`is_admin`) VALUES (1,'admin','V×Øöñ²*)´lïVõ)Zï',1);
INSERT INTO `admins` (`id`,`name`,`pass`,`is_admin`) VALUES (2,'José','ãÌã<$Øˆü§ŒwÌø',1);
INSERT INTO `admins` (`id`,`name`,`pass`,`is_admin`) VALUES (3,'Mariano','ãÌã<$Øˆü§ŒwÌø',0);

-- Inserto proveedores
INSERT INTO `providers` (`id`,`name`,`cuit`,`address`,`description`,`phones`,`current_account`) VALUES (1,'Verduleria Milenio','12-12341234-21','San Martín 1234','Verduras y frutas','123-4567',0);
INSERT INTO `providers` (`id`,`name`,`cuit`,`address`,`description`,`phones`,`current_account`) VALUES (2,'Sanz','12-12345-213456','Urquiza 1123','Bebidas','12345-123456',0);
INSERT INTO `providers` (`id`,`name`,`cuit`,`address`,`description`,`phones`,`current_account`) VALUES (3,'Molino','12345','asd','12345','21345',0);

-- Inserto categorías de proveedores
INSERT INTO `providercategories` (`id`,`name`) VALUES (1,'Verduras');
INSERT INTO `providercategories` (`id`,`name`) VALUES (2,'Frutas');
INSERT INTO `providercategories` (`id`,`name`) VALUES (3,'Bebidas');
INSERT INTO `providercategories` (`id`,`name`) VALUES (4,'Harinas');

-- Inserto categorías de productos
INSERT INTO `categories` (`id`,`name`) VALUES (2,'Pizzas');
INSERT INTO `categories` (`id`,`name`) VALUES (3,'Bebidas Alcoholicas');
INSERT INTO `categories` (`id`,`name`) VALUES (4,'Bebidas Sin Alcohol');
INSERT INTO `categories` (`id`,`name`) VALUES (5,'Ensaladas');

-- Inserto subcategorías de productos
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (2,'Con gas',4);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (3,'Sin gas',4);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (4,'Pizzetas',2);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (5,'Fernet',3);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (6,'Cocteles',3);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (7,'Con carne',5);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (8,'Sin carne',5);

-- Inserto productos primarios
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (1,'Coca Cola',1050,'ml',0.015,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (2,'Harina',102000,'gr',0.01,0,3);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (3,'Tomates',4000,'gr',0.005,0,1);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (4,'Fernet Branca',3000,'ml',0.08,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (5,'Manteca',10000,'gr',0.013,0,3);

-- Inserto productos elaborados
INSERT INTO `eproducts` (`id`,`name`,`removed`) VALUES (1,'Masa',0);

-- Inserto productos finales 
INSERT INTO `fproducts` (`id`,`name`,`subcategory_id`,`removed`,`sell_price`,`belong`) VALUES (1,'Pizzeta sin queso',4,0,35,'Cocina');
INSERT INTO `fproducts` (`id`,`name`,`subcategory_id`,`removed`,`sell_price`,`belong`) VALUES (2,'Fernet con Coca',5,0,100,'Bar');
INSERT INTO `fproducts` (`id`,`name`,`subcategory_id`,`removed`,`sell_price`,`belong`) VALUES (3,'Pizzeta con Salsa',4,0,23,'Cocina');
INSERT INTO `fproducts` (`id`,`name`,`subcategory_id`,`removed`,`sell_price`,`belong`) VALUES (4,'Combo pizza',4,0,40,'Cocina');

-- Inserto compras
INSERT INTO `purchases` (`id`,`cost`,`paid`,`date`,`provider_id`,`date_paid`) VALUES (1,15,0,'2015-06-07',2,NULL);
INSERT INTO `purchases` (`id`,`cost`,`paid`,`date`,`provider_id`,`date_paid`) VALUES (2,15,15,'2015-06-07',1,'2015-06-07');
INSERT INTO `purchases` (`id`,`cost`,`paid`,`date`,`provider_id`,`date_paid`) VALUES (3,12,0,'2015-06-07',3,NULL);
INSERT INTO `purchases` (`id`,`cost`,`paid`,`date`,`provider_id`,`date_paid`) VALUES (4,10,0,'2015-06-07',3,NULL);

-- Inserto relación entre productos primarios y finales
INSERT INTO `fproducts_pproducts` (`id`,`fproduct_id`,`pproduct_id`,`amount`) VALUES (1,1,2,500);
INSERT INTO `fproducts_pproducts` (`id`,`fproduct_id`,`pproduct_id`,`amount`) VALUES (2,1,3,300);
INSERT INTO `fproducts_pproducts` (`id`,`fproduct_id`,`pproduct_id`,`amount`) VALUES (3,2,1,300);
INSERT INTO `fproducts_pproducts` (`id`,`fproduct_id`,`pproduct_id`,`amount`) VALUES (4,2,4,700);
INSERT INTO `fproducts_pproducts` (`id`,`fproduct_id`,`pproduct_id`,`amount`) VALUES (5,3,3,100);

-- Inserto relación entre productos primarios y elaborados
INSERT INTO `eproducts_pproducts` (`id`,`eproduct_id`,`pproduct_id`,`amount`) VALUES (1,1,2,1000);
INSERT INTO `eproducts_pproducts` (`id`,`eproduct_id`,`pproduct_id`,`amount`) VALUES (2,1,5,200);

-- Inserto relación entre productos elaborados y finales
INSERT INTO `fproducts_eproducts` (`id`,`fproduct_id`,`eproduct_id`,`amount`) VALUES (1,3,1,1);

-- Inserto relación entre productos finales y productos finales
INSERT INTO `fproducts_fproducts` (`id`,`fproduct_id`,`fproduct_id2`,`amount`) VALUES (1,1,4,1);
INSERT INTO `fproducts_fproducts` (`id`,`fproduct_id`,`fproduct_id2`,`amount`) VALUES (2,3,4,1);

-- Inserto relación entre productos primarios y compras
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (1,'1','1','1.0','15.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (2,'3','2','3.0','5.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (3,'2','3','1.0','12.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (4,'2','4','1.0','10.0');


-- Inserto empleados
INSERT INTO `users` (`id`,`name`,`surname`,`pass`,`date_hired`,`date_discharged`,`turn`,`date_of_birth`,`place_of_birth`,`id_type`,`id_number`,`address`,`home_phone`,`emergency_phone`,`mobile_phone`,`marital_status`,`blood_type`,`position`,`order_count`) VALUES (1,'Federico','Mollea','ãÌã<$Øˆü§ŒwÌø','2015-06-26',NULL,'Manana','2015-06-18','Rio Cuarto','DNI','12345678','asdfg','123456','12345','123456','Soltero','O+','Mozo',0);
INSERT INTO `users` (`id`,`name`,`surname`,`pass`,`date_hired`,`date_discharged`,`turn`,`date_of_birth`,`place_of_birth`,`id_type`,`id_number`,`address`,`home_phone`,`emergency_phone`,`mobile_phone`,`marital_status`,`blood_type`,`position`,`order_count`) VALUES (2,'Carlos','Sanchez','[B@6cd64638','1882-02-20',NULL,'Tarde','2015-06-04','Rio Cuarto','DNI','123456','asdfg','123456','123456','2134567','Soltero','AB+','Cocinero',0);
INSERT INTO `users` (`id`,`name`,`surname`,`pass`,`date_hired`,`date_discharged`,`turn`,`date_of_birth`,`place_of_birth`,`id_type`,`id_number`,`address`,`home_phone`,`emergency_phone`,`mobile_phone`,`marital_status`,`blood_type`,`position`,`order_count`) VALUES (3,'Juan','Gomez','\0èüý?eÎäßÂFHÐ¬š','2015-06-12',NULL,'Tarde','2015-06-17','a','Pasaporte','000000000','00000','00000000','0000000','000000000','Viudo','O-','Mozo',0);

-- Inserto presencias de empleados
INSERT INTO `presences` (`id`,`day`,`entry_time`,`user_id`,`departure_time`) VALUES (1,'2015-06-01','21:55:45',1,'00:00:00');
INSERT INTO `presences` (`id`,`day`,`entry_time`,`user_id`,`departure_time`) VALUES (2,'2015-06-01','21:56:27',2,'00:00:00');

-- Inserto relación entre proveedores y sus categorías
INSERT INTO `providers_providercategories` (`id`,`provider_id`,`providercategory_id`) VALUES (1,1,1);
INSERT INTO `providers_providercategories` (`id`,`provider_id`,`providercategory_id`) VALUES (2,1,2);
INSERT INTO `providers_providercategories` (`id`,`provider_id`,`providercategory_id`) VALUES (3,2,3);
INSERT INTO `providers_providercategories` (`id`,`provider_id`,`providercategory_id`) VALUES (4,3,4);
