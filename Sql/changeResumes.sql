ALTER TABLE `tik`.`resumes` 
ADD COLUMN `earning_m` float DEFAULT '0' AFTER `resume_date`,
ADD COLUMN `earning_a` float DEFAULT '0' AFTER `earning_m`;