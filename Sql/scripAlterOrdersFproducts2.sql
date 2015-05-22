ALTER TABLE `tik`.`orders_fproducts` 
ADD COLUMN `created_at` TIMESTAMP NULL DEFAULT NULL AFTER `issued`,
ADD COLUMN `updated_at` TIMESTAMP NULL DEFAULT NULL AFTER `created_at`;
