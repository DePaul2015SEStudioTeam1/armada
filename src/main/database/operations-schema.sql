# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.11)
# Database: operations
# Generation Time: 2014-10-23 02:33:38 +0000
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
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `agent_id` varchar(45) DEFAULT NULL,
  `mem_total` bigint(11) DEFAULT NULL,
  `mem_used` bigint(11) DEFAULT NULL,
  `mem_free` bigint(11) DEFAULT NULL,
  `os_description` varchar(200) DEFAULT NULL,
  `os_name` varchar(100) DEFAULT NULL,
  `os_data_model` varchar(100) DEFAULT NULL,
  `primary_ip_address` varchar(200) DEFAULT NULL,
  `primary_mac_address` varchar(200) DEFAULT NULL,
  `host_name` varchar(200) DEFAULT NULL,
  `cpu_vendor` varchar(45) DEFAULT NULL,
  `cpu_model` varchar(45) DEFAULT NULL,
  `cpu_count` int(11) DEFAULT NULL,
  `disk_space_total` bigint(11) DEFAULT NULL,
  `disk_space_free` bigint(11) DEFAULT NULL,
  `disk_space_used` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
