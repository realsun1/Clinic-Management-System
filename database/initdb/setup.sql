-- Login Info

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
    `username` varchar(20) NOT NULL,
    `password` varchar(20) NOT NULL,
    `type` varchar(15) NOT NULL,
    `doctor_number` int NULL,
    PRIMARY KEY (`username`),
    INDEX `doctor_number`(`doctor_number` ASC)
);


-- Doctor

DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
    `dnumber` int NOT NULL,
    `dname` varchar(30) NOT NULL,
    `location` varchar(10) NOT NULL,
    PRIMARY KEY (`dnumber`),
    INDEX `dname`(`dname` ASC),
    FOREIGN KEY (`dnumber`) REFERENCES `user` (`doctor_number`) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Patient

DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
    `pnumber` int NOT  NULL,
    `pname` varchar(30) NOT NULL,
    `phone` varchar(20) NOT NULL,
    `symptoms` varchar(100) NULL DEFAULT NULL,
    PRIMARY KEY (`pnumber`)
);


-- Appointment

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
    `dnumber` int NOT NULL,
    `pnumber` int NOT NULL,
    `date` DATETIME NOT NULL,
    PRIMARY KEY (`dnumber`, `pnumber`),
    FOREIGN KEY (`dnumber`) REFERENCES `doctor` (`dnumber`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`pnumber`) REFERENCES `patient` (`pnumber`) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Report

DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
    `rid` int NOT NULL,
    `dnumber` int NOT NULL,
    `pnumber` int NOT NULL,
    `diagnosis` varchar(100) NOT NULL,
    `medicine` varchar(100) NOT NULL,
    PRIMARY KEY (`rid`),
    INDEX `report_pnumber`(`pnumber` ASC),
    INDEX `report_dnumber`(`dnumber` ASC),
    FOREIGN KEY (`dnumber`) REFERENCES `doctor` (`dnumber`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`pnumber`) REFERENCES `patient` (`pnumber`) ON DELETE CASCADE ON UPDATE CASCADE
);
