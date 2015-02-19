# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.11)
# Database: armada
# Generation Time: 2015-02-11 23:49:43 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table container
# ------------------------------------------------------------

DROP TABLE IF EXISTS `container`;

CREATE TABLE `container` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `container_unique_id` varchar(255) NOT NULL,
  `cadvisor_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `container_unique_id` (`container_unique_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table container_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `container_log`;

CREATE TABLE `container_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `timestamp` datetime NOT NULL,
  `mem_used` bigint(20) DEFAULT NULL,
  `mem_total` bigint(20) DEFAULT NULL,
  `cpu_used` bigint(20) DEFAULT NULL,
  `cpu_total` bigint(20) DEFAULT NULL,
  `disk_used` bigint(20) DEFAULT NULL,
  `disk_total` bigint(20) DEFAULT NULL,
  `container_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_container_to_log` (`container_id`),
  CONSTRAINT `fk_container_to_log` FOREIGN KEY (`container_id`) REFERENCES `container` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dump of table preferences
# ------------------------------------------------------------

DROP TABLE IF EXISTS `preferences`;

CREATE TABLE `preferences` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pref_key` varchar(50) DEFAULT NULL,
  `pref_value` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
