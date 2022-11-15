USE db;

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
                           `dno` int NOT NULL,
                           `dname` varchar(30) NOT NULL,
                           `location` varchar(10) NOT NULL,
                           PRIMARY KEY (`dno`) USING BTREE,
                           INDEX `dname`(`dname` ASC) USING BTREE
);

-- ----------------------------
-- Table structure for login_info
-- ----------------------------
DROP TABLE IF EXISTS `login_info`;
CREATE TABLE `login_info`  (
                               `username` varchar(20) NOT NULL,
                               `password` varchar(20) NOT NULL,
                               `name` varchar(30) NOT NULL,
                               `id` int NOT NULL,
                               `type` varchar(15) NOT NULL,
                               PRIMARY KEY (`username`) USING BTREE
);

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
                             `mname` varchar(20) NOT NULL,
                             `mamount` int NOT NULL,
                             `mcost` float NULL DEFAULT NULL,
                             PRIMARY KEY (`mname`) USING BTREE
);

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
                            `pname` varchar(30) NOT NULL,
                            `pcontact` varchar(30) NOT NULL,
                            `symptom` varchar(100) NULL DEFAULT NULL,
                            PRIMARY KEY (`pname`) USING BTREE
);

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
                                `dname` varchar(30) NOT NULL,
                                `dno` int NOT NULL,
                                `pname` varchar(30) NOT NULL,
                                `pcontact` varchar(30) NOT NULL,
                                `date` varchar(20) NOT NULL,
                                `cost` float NOT NULL,
                                `location` varchar(10) NOT NULL,
                                PRIMARY KEY (`dno`, `pname`) USING BTREE,
                                INDEX `pname`(`pname` ASC) USING BTREE,
                                CONSTRAINT `dno` FOREIGN KEY (`dno`) REFERENCES `doctor` (`dno`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `pname` FOREIGN KEY (`pname`) REFERENCES `patient` (`pname`) ON DELETE CASCADE ON UPDATE CASCADE
);
