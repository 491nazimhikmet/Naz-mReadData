-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 26 Eki 2015, 17:29:17
-- Sunucu sürümü: 5.6.17
-- PHP Sürümü: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Veritabanı: `db_nazim_hikmet`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `BookId` int(3) NOT NULL AUTO_INCREMENT,
  `Name` varchar(150) NOT NULL,
  `Year` varchar(50) DEFAULT NULL,
  `Location` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`BookId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Tablo döküm verisi `book`
--


-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `word`
--

CREATE TABLE IF NOT EXISTS `word` (
  `wordId` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) NOT NULL,
  `wordStart` double(10,6) NOT NULL,
  `wordFinish` double(10,6) NOT NULL,
  `workLineId` int(8) DEFAULT NULL,
  `isBold` tinyint(1) NOT NULL,
  `isItalic` tinyint(1) NOT NULL,
  `font` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`wordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `wordcharacter`
--

CREATE TABLE IF NOT EXISTS `wordcharacter` (
  `characterId` int(11) NOT NULL AUTO_INCREMENT,
  `characterText` varchar(1) NOT NULL,
  `characterStart` float(10,6) NOT NULL,
  `characterFinish` float(10,6) NOT NULL,
  `wordId` int(11) DEFAULT NULL,
  `isBold` tinyint(1) NOT NULL,
  `isItalic` tinyint(1) NOT NULL,
  `font` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`characterId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `work`
--

CREATE TABLE IF NOT EXISTS `work` (
  `WorkId` int(8) NOT NULL AUTO_INCREMENT,
  `Name` varchar(150) NOT NULL,
  `Year` varchar(50) DEFAULT NULL,
  `BookId` int(4) DEFAULT NULL,
  `LocationOfComp` varchar(100) DEFAULT NULL,
  `length` int(10) NOT NULL,
  `Title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`WorkId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `workline`
--

CREATE TABLE IF NOT EXISTS `workline` (
  `lineId` int(8) NOT NULL AUTO_INCREMENT,
  `line` text NOT NULL,
  `lineStart` double(10,6) NOT NULL,
  `lineFinish` double(10,6) NOT NULL,
  `workTextId` int(8) DEFAULT NULL,
  PRIMARY KEY (`lineId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `worktext`
--

CREATE TABLE IF NOT EXISTS `worktext` (
  `WorkTextId` int(8) NOT NULL AUTO_INCREMENT,
  `Text` text NOT NULL,
  `numLines` int(8) NOT NULL,
  `WorkId` int(8) NOT NULL,
  PRIMARY KEY (`WorkTextId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
