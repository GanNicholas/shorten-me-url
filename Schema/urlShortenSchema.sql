CREATE DATABASE /*!32312 IF NOT EXISTS*/ `url_shorten` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `url_shorten`;

--
-- Table structure for table `urlTable`
--

DROP TABLE IF EXISTS `urlTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `urlTable` (
  `urlId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ipAddress` varchar(255) NOT NULL,
  `fullUrl` varchar(255) NOT NULL,
  `shortenUrl` varchar(255) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  `createdDateTime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`urlId`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

