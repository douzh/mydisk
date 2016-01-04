create database mybatis;
use mybatis;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) CHARACTER SET latin1 DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO users(NAME, age) VALUES('名称1', 27);
INSERT INTO users(NAME, age) VALUES('名称2', 27);