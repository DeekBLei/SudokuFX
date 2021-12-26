CREATE DATABASE  IF NOT EXISTS `quizmaster` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quizmaster`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: quizmaster
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cursus`
--

DROP TABLE IF EXISTS cursus;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE cursus (
  cursusId int NOT NULL AUTO_INCREMENT,
  cursusNaam varchar(45) NOT NULL,
  coordinatorId int NOT NULL,
  PRIMARY KEY (cursusId),
  KEY verzinsel1_idx (coordinatorId),
  CONSTRAINT verzinsel1 FOREIGN KEY (coordinatorId) REFERENCES gebruiker (gebruikerId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursus`
--

INSERT INTO cursus VALUES (1,'Etiquette',3),(2,'Literatuur',3),(3,'Logica',4);

--
-- Table structure for table `cursusinschrijving`
--

DROP TABLE IF EXISTS cursusinschrijving;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE cursusinschrijving (
  studentId int NOT NULL,
  cursusId int NOT NULL,
  PRIMARY KEY (studentId,cursusId),
  KEY verzinsel4_idx (cursusId),
  KEY verzinsel3_idx (studentId),
  CONSTRAINT verzinsel3 FOREIGN KEY (studentId) REFERENCES gebruiker (gebruikerId) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT verzinsel4 FOREIGN KEY (cursusId) REFERENCES cursus (cursusId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursusinschrijving`
--

INSERT INTO cursusinschrijving VALUES (6,1),(7,1),(7,2),(6,3),(7,3);

--
-- Table structure for table `gebruiker`
--

DROP TABLE IF EXISTS gebruiker;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE gebruiker (
  gebruikerId int NOT NULL AUTO_INCREMENT,
  gebruikerNaam varchar(45) NOT NULL,
  rol varchar(45) NOT NULL,
  wachtwoord varchar(45) NOT NULL,
  PRIMARY KEY (gebruikerId),
  UNIQUE KEY gebruikerNaam_UNIQUE (gebruikerNaam)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gebruiker`
--

INSERT INTO gebruiker VALUES (1,'Bommel','technischBeheerder','beer'),(2,'Dorknoper','administrator','hamster'),(3,'TomPoes','coordinator','kat'),(4,'WalRus','coordinator','walrus'),(5,'Pieps','docent','muis'),(6,'WammesWaggel','student','gans'),(7,'BulBas','student','walrus');

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS quiz;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE quiz (
  quizId int NOT NULL AUTO_INCREMENT,
  quizNaam varchar(45) NOT NULL,
  succesDefinitie int NOT NULL,
  cursusId int NOT NULL,
  PRIMARY KEY (quizId),
  KEY verzinzelf4_idx (cursusId),
  CONSTRAINT verzinzelf4 FOREIGN KEY (cursusId) REFERENCES cursus (cursusId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

INSERT INTO quiz VALUES (1,'Etiquette I',3,1),(2,'Etiquette II',0,1),(3,'Literatuur',2,2),(4,'Logica',1,3);

--
-- Table structure for table `quizresultaat`
--

DROP TABLE IF EXISTS quizresultaat;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE quizresultaat (
  quizId int NOT NULL,
  studentId int NOT NULL,
  datum datetime NOT NULL,
  aantalVragenGoed int NOT NULL,
  PRIMARY KEY (studentId,datum),
  KEY verzinzelf5_idx (studentId),
  KEY verzinzelf1_idx (quizId),
  CONSTRAINT verzinzelf1 FOREIGN KEY (quizId) REFERENCES quiz (quizId) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT verzinzelf5 FOREIGN KEY (studentId) REFERENCES gebruiker (gebruikerId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizresultaat`
--

INSERT INTO quizresultaat VALUES (1,6,'2021-08-10 01:48:31',0),(1,6,'2021-08-11 01:48:31',3),(3,6,'2021-09-15 01:48:31',2),(4,6,'2021-10-10 01:48:31',0),(1,7,'2021-08-12 01:48:31',4),(3,7,'2021-09-10 01:48:31',1),(4,7,'2021-09-11 01:48:31',0),(3,7,'2021-09-16 01:48:31',2),(4,7,'2021-10-15 01:48:31',1);

--
-- Table structure for table `vraag`
--

DROP TABLE IF EXISTS vraag;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE vraag (
  vraagId int NOT NULL AUTO_INCREMENT,
  vraag varchar(200) NOT NULL,
  antwoord1 varchar(100) NOT NULL,
  antwoord2 varchar(100) NOT NULL,
  antwoord3 varchar(100) NOT NULL,
  antwoord4 varchar(100) NOT NULL,
  quizId int NOT NULL,
  PRIMARY KEY (vraagId),
  KEY quizId1_idx (quizId),
  CONSTRAINT quizId1 FOREIGN KEY (quizId) REFERENCES quiz (quizId) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vraag`
--

INSERT INTO vraag VALUES (1,'Wat is de roepnaam van Heer Bommel?','Ollie','Olaf','Otto','Omar',1),(2,'Wie bedacht Olivier B. Bommel?','Marten Toonder','Maarten Tonder','Mart der Toon','Marten Tonder',1),(3,'In welk jaar maakte Bommel zijn debuut?','1941','1951','1961','1971',1),(4,'Hoe heet de auto van Bommel?','Oude Schicht','Harde Schicht','Snelle Schicht','Scheve Schicht',2),(5,'Wat is het woonadres van Bommel?','Distellaan 13','Netellaan 47','Klaverpad 33','Lotuspad 1',3),(6,'Hoe heet de bediende van Bommel?','Joost','Joris','Jochem','Johannes',3),(7,'Wat is geen bekende uitspraak van Bommel?','Alsjemenou','Als je begrijpt wat ik bedoel','Geld speelt geen rol','Zoals mijn goede vader zei',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

CREATE USER 'userQuizmaster'@'localhost' IDENTIFIED BY 'userQuizmasterPW';
GRANT ALL PRIVILEGES ON Quizmaster . * TO 'userQuizmaster'@'localhost';

-- Dump completed
