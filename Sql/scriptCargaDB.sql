/*
-- Query: SELECT * FROM tik.admins
LIMIT 0, 1000

-- Date: 2015-04-23 17:41
*/

#Cargo administrador
INSERT INTO `admins` (`id`,`name`,`pass`,`is_admin`) VALUES (1,'admin','V×Øöñ²*)´lïVõ)Zï',1);

#Cargo categorías de productos
INSERT INTO `categories` (`id`,`name`) VALUES (1,'Bebidas');
INSERT INTO `categories` (`id`,`name`) VALUES (2,'Carnes');
INSERT INTO `categories` (`id`,`name`) VALUES (3,'Verduras');
INSERT INTO `categories` (`id`,`name`) VALUES (4,'Frutas');
INSERT INTO `categories` (`id`,`name`) VALUES (5,'Pizzas');

#Cargo productos primarios
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (2,'Fernet branca',5000,'ml',0.08,0,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (3,'Coca Cola',10000,'ml',0.018,0,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (4,'Lomo de ternera',4000,'gr',0.04,0,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (5,'Pechuga de pollo',2000,'gr',0.025,0,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (6,'Zanahoria',3000,'gr',0.01,0,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (7,'Naranja',4000,'gr',0.008,0,0,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (8,'Harina',10000,'gr',0.005,0,2,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (9,'Huevo',12,'unitario',2,0,2,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (10,'Queso',1000,'gr',0.012,0,2,2);
INSERT INTO `pproducts` (`id`,`name`,`stock`,`measure_unit`,`unit_price`,`removed`,`provider_id`) VALUES (11,'Tomate',3000,'gr',0.008,0,2,2);


#Cargo productos elaborados
INSERT INTO `eproducts` (`id`,`name`,`removed`) VALUES (1,'Masa de pizza ',0);
INSERT INTO `eproducts` (`id`,`name`,`removed`) VALUES (2,'Salsa roja',0);

#Cargo productos finales
INSERT INTO `fproducts` (`id`,`name`,`subcategory_id`,`removed`,`sell_price`) VALUES (1,'Pizza Mozzarella',5,0,40);

#Cargo categorías de proveedor
INSERT INTO `providercategories` (`id`,`name`) VALUES (1,'Bebidas');
INSERT INTO `providercategories` (`id`,`name`) VALUES (2,'Carnes');
INSERT INTO `providercategories` (`id`,`name`) VALUES (3,'Verduras');
INSERT INTO `providercategories` (`id`,`name`) VALUES (4,'Frutas');
INSERT INTO `providercategories` (`id`,`name`) VALUES (5,'Insumos');

#Cargo proveedores
INSERT INTO `providers` (`id`,`name`,`cuit`,`address`,`description`,`phones`) VALUES (2,'Verdulería Milenio','20-11233321-0','San Martín 1234','Verdulería / Frutería','4659392');
INSERT INTO `providers` (`id`,`name`,`cuit`,`address`,`description`,`phones`) VALUES (3,'Limpito','12-18282232-9','Buenos Aires 800','','');

#Cargo una compra
INSERT INTO `purchases` (`id`,`cost`,`paid`,`date`,`provider_id`,`date_paid`) VALUES (1,26,26,'2015-04-23',2,'2015-04-23');

#Cargo subcategorías de productos
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (1,'Alcoholicas',1);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (2,'Sin alcohol',1);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (3,'Rojas',2);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (4,'Blancas',2);
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (5,'Pizza ',5);

#Cargo un mozo
INSERT INTO `users` (`id`,`name`,`surname`,`pass`,`date_hired`,`date_discharged`,`turn`,`date_of_birth`,`place_of_birth`,`id_type`,`id_number`,`address`,`home_phone`,`emergency_phone`,`mobile_phone`,`marital_status`,`blood_type`,`position`,`photo`) VALUES (1,'Federico','Mollea','ãÌã<$Øˆü§ŒwÌø','2015-04-16',NULL,'Manana','1994-06-18','Río Cuarto','DNI','38182819','San Martín 123','465773','4658883','154281982','Soltero','O+','Mozo',NULL);

#Cargo productos / compra
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (8,'6','2','1.0','10.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (9,'7','2','1.0','8.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (10,'11','2','1.0','8.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (11,'6','3','1.0','10.0');
INSERT INTO `pproducts_purchases` (`id`,`pproduct_id`,`purchase_id`,`amount`,`final_price`) VALUES (12,'7','3','1.0','8.0');

#Cargo relación eproducts / pproducts
INSERT INTO `eproducts_pproducts` (`id`,`eproduct_id`,`pproduct_id`,`amount`) VALUES (1,1,8,200);
INSERT INTO `eproducts_pproducts` (`id`,`eproduct_id`,`pproduct_id`,`amount`) VALUES (2,1,9,2);
INSERT INTO `eproducts_pproducts` (`id`,`eproduct_id`,`pproduct_id`,`amount`) VALUES (3,2,11,100);

#Cargo relación fproducts / eproducts
INSERT INTO `fproducts_eproducts` (`id`,`fproduct_id`,`eproduct_id`,`amount`) VALUES (1,1,1,1);
INSERT INTO `fproducts_eproducts` (`id`,`fproduct_id`,`eproduct_id`,`amount`) VALUES (2,1,2,100);

#Cargo relación fproducts / pproducts
INSERT INTO `fproducts_pproducts` (`id`,`fproduct_id`,`pproduct_id`,`amount`) VALUES (1,1,10,100);

