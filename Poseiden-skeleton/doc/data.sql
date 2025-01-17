DROP DATABASE IF EXISTS `test`;
CREATE DATABASE IF NOT EXISTS `test`;
USE test;

DROP TABLE IF EXISTS `BidList`;
CREATE TABLE IF NOT EXISTS `BidList` (
  `BidListId` tinyint(4) NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(30) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `bidQuantity` DOUBLE,
  `askQuantity` DOUBLE,
  `bid` DOUBLE ,
  `ask` DOUBLE,
  `benchmark` VARCHAR(125),
  `bidListDate` TIMESTAMP,
  `commentary` VARCHAR(125),
  `security` VARCHAR(125),
  `status` VARCHAR(10),
  `trader` VARCHAR(125),
  `book` VARCHAR(125),
  `creationName` VARCHAR(125),
  `creationDate` TIMESTAMP ,
  `revisionName` VARCHAR(125),
  `revisionDate` TIMESTAMP ,
  `dealName VARCHAR(125),
  dealType` VARCHAR(125),
  `sourceListId` VARCHAR(125),
  `side` VARCHAR(125),

  PRIMARY KEY (`BidListId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Trade`;
CREATE TABLE IF NOT EXISTS `Trade` (
  `TradeId` tinyint(4) NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(30) NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  `buyQuantity` DOUBLE,
  `sellQuantity` DOUBLE,
  `buyPrice` DOUBLE ,
  `sellPrice` DOUBLE,
  `tradeDate` TIMESTAMP,
  `security` VARCHAR(125),
  `status` VARCHAR(10),
  `trader` VARCHAR(125),
  `benchmark` VARCHAR(125),
  `book` VARCHAR(125),
  `creationName` VARCHAR(125),
  `creationDate` TIMESTAMP ,
  `revisionName` VARCHAR(125),
  `revisionDate` TIMESTAMP ,
  `dealName` VARCHAR(125),
  `dealType` VARCHAR(125),
  `sourceListId` VARCHAR(125),
  `side` VARCHAR(125),

  PRIMARY KEY (`TradeId`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 DROP TABLE IF EXISTS `CurvePoint`;
CREATE TABLE IF NOT EXISTS  `CurvePoint` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `CurveId` tinyint,
  `asOfDate` TIMESTAMP,
  `term` DOUBLE ,
  `value` DOUBLE ,
  `creationDate` TIMESTAMP ,

  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Rating`;
CREATE TABLE IF NOT EXISTS `Rating` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `moodysRating` VARCHAR(125),
  `sandPRating` VARCHAR(125),
  `fitchRating` VARCHAR(125),
  `orderNumber` tinyint,

  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `RuleName`;
CREATE TABLE IF NOT EXISTS `RuleName` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125),
  `description` VARCHAR(125),
  `json` VARCHAR(125),
  `template` VARCHAR(512),
  `sqlStr` VARCHAR(125),
  `sqlPart` VARCHAR(125),

  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Users`;
CREATE TABLE IF NOT EXISTS `Users` (
  `Id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(125),
  `password` VARCHAR(125),
  `fullname` VARCHAR(125),
  `role` VARCHAR(125),

  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Users`(`fullname`, `username`, `password`, `role`) VALUES ('Administrator', 'admin', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'ADMIN');
INSERT INTO `Users`(`fullname`, `username`, `password`, `role`) VALUES ('User', 'user', '$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa', 'USER');
