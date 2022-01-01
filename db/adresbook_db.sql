-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: addressbook
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_other_name`
--

DROP TABLE IF EXISTS `t_other_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_other_name` (
  `other_name_id` int NOT NULL AUTO_INCREMENT,
  `other_name` varchar(45) NOT NULL,
  `person_id` int NOT NULL,
  `other_name_type_id` int NOT NULL,
  PRIMARY KEY (`other_name_id`),
  KEY `fk_person_idx` (`person_id`),
  KEY `fk_other_name_type_idx` (`other_name_type_id`),
  CONSTRAINT `fk_other_name_type` FOREIGN KEY (`other_name_type_id`) REFERENCES `t_other_name_type` (`other_name_type_id`),
  CONSTRAINT `fk_person` FOREIGN KEY (`person_id`) REFERENCES `t_person` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_other_name`
--

LOCK TABLES `t_other_name` WRITE;
/*!40000 ALTER TABLE `t_other_name` DISABLE KEYS */;
INSERT INTO `t_other_name` VALUES (1,'Toby',1,4),(2,'Johan',3,6),(3,'Tanck',3,7),(4,'Cristian',3,8),(5,'Christian',3,1),(6,'Houston',4,1);
/*!40000 ALTER TABLE `t_other_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_other_name_type`
--

DROP TABLE IF EXISTS `t_other_name_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_other_name_type` (
  `other_name_type_id` int NOT NULL AUTO_INCREMENT,
  `other_name_type` varchar(45) NOT NULL,
  PRIMARY KEY (`other_name_type_id`),
  UNIQUE KEY `other_name_type_UNIQUE` (`other_name_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_other_name_type`
--

LOCK TABLES `t_other_name_type` WRITE;
/*!40000 ALTER TABLE `t_other_name_type` DISABLE KEYS */;
INSERT INTO `t_other_name_type` VALUES (4,'Firstname Alias'),(6,'Firstname at birth'),(9,'Lastname Alias'),(7,'Lastname at birth'),(1,'Middle Name'),(8,'Middle Name at birth'),(5,'Nickname');
/*!40000 ALTER TABLE `t_other_name_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_person`
--

DROP TABLE IF EXISTS `t_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `birth_date` date NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_person`
--

LOCK TABLES `t_person` WRITE;
/*!40000 ALTER TABLE `t_person` DISABLE KEYS */;
INSERT INTO `t_person` VALUES (1,'Edmund','Barton','1849-01-18'),(2,'Alfred','Deakin','1856-08-03'),(3,'Chris','Watson','1867-04-09'),(4,'George','Reid','1845-02-25');
/*!40000 ALTER TABLE `t_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_person`
--

DROP TABLE IF EXISTS `vw_person`;
/*!50001 DROP VIEW IF EXISTS `vw_person`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_person` AS SELECT 
 1 AS `person_id`,
 1 AS `firstname`,
 1 AS `lastname`,
 1 AS `middle_name`,
 1 AS `nickname`,
 1 AS `firstname_alias`,
 1 AS `lastname_alias`,
 1 AS `birth_firstname`,
 1 AS `birth_lastname`,
 1 AS `birth_middlename`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vw_person`
--

/*!50001 DROP VIEW IF EXISTS `vw_person`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_person` AS select distinct `a`.`person_id` AS `person_id`,`a`.`firstname` AS `firstname`,`a`.`lastname` AS `lastname`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Middle Name'))) AS `middle_name`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Nickname'))) AS `nickname`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Firstname Alias'))) AS `firstname_alias`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Lastname Alias'))) AS `lastname_alias`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Firstname at birth'))) AS `birth_firstname`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Lastname at birth'))) AS `birth_lastname`,(select `p`.`other_name` from (`t_other_name` `p` join `t_other_name_type` `q` on((`p`.`other_name_type_id` = `q`.`other_name_type_id`))) where ((`p`.`person_id` = `a`.`person_id`) and (`q`.`other_name_type` = 'Middle Name at birth'))) AS `birth_middlename` from (`t_person` `a` left join (`t_other_name` `b` join `t_other_name_type` `c` on((`b`.`other_name_type_id` = `c`.`other_name_type_id`))) on((`a`.`person_id` = `b`.`person_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-22 15:19:42
