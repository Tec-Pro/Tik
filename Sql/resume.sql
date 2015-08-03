CREATE TABLE `resumes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `income` float DEFAULT '0',
  `earning` float DEFAULT '0',
  `expenses` float DEFAULT '0',
  `final_balance` float DEFAULT '0',
  `resume_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `adminresumes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resume_id` int(11),
  `admin` VARCHAR(20),
  `deposit` float DEFAULT '0',
  `withdrawal` float DEFAULT '0',
  PRIMARY KEY (`id`)
);