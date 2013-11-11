SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `nms_db` ;
CREATE SCHEMA IF NOT EXISTS `nms_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `nms_db` ;

-- -----------------------------------------------------
-- Table `nms_db`.`host`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nms_db`.`host` ;

CREATE  TABLE IF NOT EXISTS `nms_db`.`host` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '	' ,
  `name` VARCHAR(45) NOT NULL ,
  `ip` VARCHAR(45) NOT NULL ,
  `port` VARCHAR(45) NULL ,
  `trap_port` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nms_db`.`service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nms_db`.`service` ;

CREATE  TABLE IF NOT EXISTS `nms_db`.`service` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `desc` VARCHAR(128) NOT NULL ,
  `oid` VARCHAR(45) NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nms_db`.`host_has_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nms_db`.`host_has_service` ;

CREATE  TABLE IF NOT EXISTS `nms_db`.`host_has_service` (
  `host_id` INT NOT NULL ,
  `service_id` INT NOT NULL ,
  `id` INT NOT NULL AUTO_INCREMENT ,
  `th_value` VARCHAR(45) NULL ,
  `timeout` MEDIUMTEXT NULL ,
  `active` TINYINT(1) NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_host_has_service_service1` (`service_id` ASC) ,
  INDEX `fk_host_has_service_host` (`host_id` ASC) ,
  CONSTRAINT `fk_host_has_service_host`
    FOREIGN KEY (`host_id` )
    REFERENCES `nms_db`.`host` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_host_has_service_service1`
    FOREIGN KEY (`service_id` )
    REFERENCES `nms_db`.`service` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nms_db`.`value`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nms_db`.`value` ;

CREATE  TABLE IF NOT EXISTS `nms_db`.`value` (
  `hs_id` INT NOT NULL ,
  `timestamp` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
  `value` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`hs_id`, `timestamp`) ,
  CONSTRAINT `fk_value_host_has_service1`
    FOREIGN KEY (`hs_id` )
    REFERENCES `nms_db`.`host_has_service` (`id` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nms_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nms_db`.`user` ;

CREATE  TABLE IF NOT EXISTS `nms_db`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '	' ,
  `username` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
INSERT INTO user(username , password, email) VALUES("admin","admin","admin@domain.com");
