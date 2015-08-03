INSERT INTO `categories` (`id`,`name`) VALUES (1,'CATEGORIA POR DEFECTO');
INSERT INTO `subcategories` (`id`,`name`,`category_id`) VALUES (1,'SUBCATEGORIA POR DEFECTO',1);


INSERT INTO `tik`.`innings`(`id`,`turn`) VALUES (1,'N');

INSERT INTO `tik`.`cashboxes`(`turn`,`balance`,`collect`,`entry_cash`,`spend`,`withdrawal`,`delivery_cash`,`delivery_waiter`)VALUES ('M',0,0,0,0,0,0,0);
INSERT INTO `tik`.`cashboxes`(`turn`,`balance`,`collect`,`entry_cash`,`spend`,`withdrawal`,`delivery_cash`,`delivery_waiter`)VALUES ('T',0,0,0,0,0,0,0);