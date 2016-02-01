-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET latin1 ;
USE `hotel` ;

-- -----------------------------------------------------
-- Table `hotel`.`apartment_classes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`apartment_classes` (
  `class_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`class_id`),
  UNIQUE INDEX `class_id_UNIQUE` (`class_id` ASC),
  UNIQUE INDEX `type_UNIQUE` (`type` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hotel`.`apartments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`apartments` (
  `apartment_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `count_places` INT(10) UNSIGNED NOT NULL,
  `apartment_class` INT(10) UNSIGNED NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  `number` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`apartment_id`),
  UNIQUE INDEX `apartment_id_UNIQUE` (`apartment_id` ASC),
  INDEX `apartment_class` (`apartment_class` ASC),
  CONSTRAINT `apartments_ibfk_1`
    FOREIGN KEY (`apartment_class`)
    REFERENCES `hotel`.`apartment_classes` (`class_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hotel`.`busy_apartments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`busy_apartments` (
  `busy_apartment_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `apartment` INT(10) UNSIGNED NOT NULL,
  `busy_from` DATETIME NOT NULL,
  `busy_to` DATETIME NOT NULL,
  PRIMARY KEY (`busy_apartment_id`),
  UNIQUE INDEX `busy_apartment_id_UNIQUE` (`busy_apartment_id` ASC),
  INDEX `apartment` (`apartment` ASC),
  CONSTRAINT `busy_apartments_ibfk_1`
    FOREIGN KEY (`apartment`)
    REFERENCES `hotel`.`apartments` (`apartment_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hotel`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`roles` (
  `role_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hotel`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`users` (
  `user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(30) NOT NULL,
  `password` VARCHAR(16) NOT NULL,
  `phone` VARCHAR(13) NULL DEFAULT NULL,
  `email` VARCHAR(45) NOT NULL,
  `registration_date` DATETIME NOT NULL,
  `change_password_date` DATETIME NOT NULL,
  `count_uncorr_attempts_to_login` INT(11) NULL DEFAULT '0',
  `role` INT(10) UNSIGNED NOT NULL,
  `is_blocked` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `role` (`role` ASC),
  CONSTRAINT `users_ibfk_1`
    FOREIGN KEY (`role`)
    REFERENCES `hotel`.`roles` (`role_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hotel`.`invoices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`invoices` (
  `invoice_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `client` INT(10) UNSIGNED NOT NULL,
  `apartment` INT(10) UNSIGNED NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`invoice_id`),
  UNIQUE INDEX `invoice_id_UNIQUE` (`invoice_id` ASC),
  INDEX `client` (`client` ASC),
  INDEX `apartment` (`apartment` ASC),
  CONSTRAINT `invoices_ibfk_1`
    FOREIGN KEY (`client`)
    REFERENCES `hotel`.`users` (`user_id`),
  CONSTRAINT `invoices_ibfk_2`
    FOREIGN KEY (`apartment`)
    REFERENCES `hotel`.`apartments` (`apartment_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `hotel`.`requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`requests` (
  `request_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `client` INT(10) UNSIGNED NOT NULL,
  `count_places` INT(11) NOT NULL,
  `apartment_class` INT(10) UNSIGNED NOT NULL,
  `busy_from` DATETIME NOT NULL,
  `busy_to` DATETIME NOT NULL,
  PRIMARY KEY (`request_id`),
  UNIQUE INDEX `request_id_UNIQUE` (`request_id` ASC),
  INDEX `client` (`client` ASC),
  INDEX `apartment_class` (`apartment_class` ASC),
  CONSTRAINT `requests_ibfk_1`
    FOREIGN KEY (`client`)
    REFERENCES `hotel`.`users` (`user_id`),
  CONSTRAINT `requests_ibfk_2`
    FOREIGN KEY (`apartment_class`)
    REFERENCES `hotel`.`apartment_classes` (`class_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = latin1;
