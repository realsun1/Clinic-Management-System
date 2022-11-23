/*
 Navicat Premium Data Transfer
 Source Server         : DataBase
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : clinicsystem
 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001
 Date: 09/11/2022 23:09:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Users
-- ----------------------------
CREATE TABLE `Users`  (
  id int NOT NULL,
  type varchar(15) CHARACTER SET utf8mb4,
  username varchar(20) CHARACTER SET utf8mb4,
  password varchar(20) CHARACTER SET utf8mb4,
  PRIMARY KEY (username) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

INSERT into Users (id, type, username, password)
values
(1,'admin','admin','pass'),
(2,'doctor','doctor','pass'),
(3,'receptionist','receptionist','pass');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `dno` int NOT NULL,
  `dname` varchar(30) CHARACTER SET utf8mb4,
  `location` varchar(10) CHARACTER SET utf8mb4,
  PRIMARY KEY (`dno`) USING BTREE,
  INDEX `dname`(`dname` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

INSERT into doctor (dno, dname, location)
values
(1,'Jimmy Neutron','Canada'),
(2,'Bill Gates','USA'),
(3,'Amber Heard','USA');

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `pname` varchar(30) CHARACTER SET utf8mb4,
  `pcontact` varchar(30) CHARACTER SET utf8mb4,
  `symptom` varchar(100) CHARACTER SET utf8mb4,
  PRIMARY KEY (`pname`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
INSERT into patient (pname, pcontact, symptom)
values
('Elon Musk','5191234567','COVID'),
('Jessie Jay','5199876543','high'),
('Bob Smith','5191111111','fever');

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `mname` varchar(20) CHARACTER SET utf8mb4,
  `mamount` int NOT NULL,
  `mcost` float NULL DEFAULT NULL,
  PRIMARY KEY (`mname`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `dname` varchar(30) CHARACTER SET utf8mb4,
  `dno` int NOT NULL,
  `pname` varchar(30) CHARACTER SET utf8mb4,
  `pcontact` varchar(30) CHARACTER SET utf8mb4,
  `date` varchar(20) CHARACTER SET utf8mb4,
  `cost` float NOT NULL,
  `location` varchar(10) CHARACTER SET utf8mb4,
  PRIMARY KEY (`dno`, `pname`) USING BTREE,
  INDEX `pname`(`pname` ASC) USING BTREE,
  CONSTRAINT `dno` FOREIGN KEY (`dno`) REFERENCES `doctor` (`dno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pname` FOREIGN KEY (`pname`) REFERENCES `patient` (`pname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4;