-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 18, 2015 at 03:11 AM
-- Server version: 5.5.41-cll-lve
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `wanna`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `eventID` int(11) NOT NULL AUTO_INCREMENT,
  `eventCreaterID` int(11) NOT NULL,
  `eventType` varchar(50) DEFAULT NULL,
  `eventName` varchar(225) DEFAULT NULL,
  `eventImageURI` varchar(225) DEFAULT NULL,
  `eventDate` date DEFAULT NULL,
  `eventTime` time DEFAULT NULL,
  `eventVenue` varchar(225) DEFAULT NULL,
  `eventAddress` varchar(225) DEFAULT NULL,
  `eventPriceRange` varchar(225) DEFAULT NULL,
  `eventDescription` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`eventID`),
  UNIQUE KEY `eventCreaterID_2` (`eventCreaterID`,`eventType`,`eventName`,`eventImageURI`,`eventDate`,`eventTime`,`eventVenue`,`eventAddress`,`eventPriceRange`),
  KEY `eventID` (`eventID`),
  KEY `eventCreaterID` (`eventCreaterID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eventID`, `eventCreaterID`, `eventType`, `eventName`, `eventImageURI`, `eventDate`, `eventTime`, `eventVenue`, `eventAddress`, `eventPriceRange`, `eventDescription`) VALUES
(2, 14, 'Sports', 'swiming try', NULL, '2014-12-10', '15:59:00', 'Centennial', 'Progress', '10', 'swim'),
(6, 14, 'Sports', 'basketball', NULL, '2014-12-04', '18:32:00', 'STC', 'stc', '10', 'basketball'),
(12, 14, 'Sports', 'basketball', NULL, '2014-12-23', '18:32:00', 'STC', 'stc', '10', 'basketball'),
(13, 18, 'Sports', 'football', NULL, '2014-12-23', '15:38:00', 'downtown', 'yonge', '20', 'football'),
(14, 18, 'Sports', 'jog', NULL, '2015-02-19', '21:40:00', 'scarborough', 'progress', '5', 'jog');

-- --------------------------------------------------------

--
-- Table structure for table `eventjoinin`
--

CREATE TABLE IF NOT EXISTS `eventjoinin` (
  `userID` int(11) NOT NULL,
  `eventID` int(11) NOT NULL,
  PRIMARY KEY (`userID`,`eventID`),
  KEY `joinedProfileID` (`userID`),
  KEY `joinedEventID` (`eventID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `eventjoinin`
--

INSERT INTO `eventjoinin` (`userID`, `eventID`) VALUES
(14, 2),
(14, 6),
(14, 12),
(15, 2),
(18, 13),
(18, 14);

-- --------------------------------------------------------

--
-- Table structure for table `eventlocation_table`
--

CREATE TABLE IF NOT EXISTS `eventlocation_table` (
  `eventLocationID` int(11) NOT NULL AUTO_INCREMENT,
  `eventID` int(11) NOT NULL,
  `eventLocationCountry` varchar(45) DEFAULT NULL,
  `eventLocationCity` varchar(45) DEFAULT NULL,
  `eventLocationStreet` varchar(45) DEFAULT NULL,
  `eventLocationProtolCode` varchar(45) DEFAULT NULL,
  `eventLongtitude` varchar(45) DEFAULT NULL,
  `eventLatitude` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`eventLocationID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `eventtype_table`
--

CREATE TABLE IF NOT EXISTS `eventtype_table` (
  `eventTypeID` int(11) NOT NULL AUTO_INCREMENT,
  `eventTypeName` varchar(45) NOT NULL,
  PRIMARY KEY (`eventTypeID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `eventtype_table`
--

INSERT INTO `eventtype_table` (`eventTypeID`, `eventTypeName`) VALUES
(1, 'Sports'),
(2, 'Entertainment');

-- --------------------------------------------------------

--
-- Table structure for table `friend`
--

CREATE TABLE IF NOT EXISTS `friend` (
  `friend_one` int(11) NOT NULL,
  `friend_two` int(11) NOT NULL,
  `status` enum('0','1') NOT NULL,
  PRIMARY KEY (`friend_one`,`friend_two`),
  KEY `friend_one` (`friend_one`),
  KEY `friend_two` (`friend_two`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friend`
--

INSERT INTO `friend` (`friend_one`, `friend_two`, `status`) VALUES
(14, 15, '0');

-- --------------------------------------------------------

--
-- Table structure for table `group`
--

CREATE TABLE IF NOT EXISTS `group` (
  `groupID` int(11) NOT NULL AUTO_INCREMENT,
  `groupCreaterID` int(11) NOT NULL,
  `groupPrivacy` varchar(10) NOT NULL,
  `groupType` varchar(50) DEFAULT NULL,
  `groupName` varchar(225) DEFAULT NULL,
  `groupImageURI` varchar(225) DEFAULT NULL,
  `groupDescription` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`groupID`),
  UNIQUE KEY `groupCreaterID_2` (`groupCreaterID`,`groupPrivacy`,`groupType`,`groupName`,`groupImageURI`),
  KEY `groupID` (`groupID`),
  KEY `groupCreaterID` (`groupCreaterID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `group`
--

INSERT INTO `group` (`groupID`, `groupCreaterID`, `groupPrivacy`, `groupType`, `groupName`, `groupImageURI`, `groupDescription`) VALUES
(1, 14, 'Public', 'Sports', 'games', NULL, 'games'),
(2, 14, 'Public', 'Sports', 'movie', NULL, 'movies'),
(3, 18, 'Private', 'Sports', 'camping', NULL, 'camping');

-- --------------------------------------------------------

--
-- Table structure for table `groupjoinin`
--

CREATE TABLE IF NOT EXISTS `groupjoinin` (
  `userID` int(11) NOT NULL,
  `groupID` int(11) NOT NULL,
  PRIMARY KEY (`userID`,`groupID`),
  KEY `groupID` (`groupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupjoinin`
--

INSERT INTO `groupjoinin` (`userID`, `groupID`) VALUES
(14, 1),
(15, 1),
(14, 2),
(15, 2),
(18, 3);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `notificationID` int(11) NOT NULL AUTO_INCREMENT,
  `senderType` varchar(10) NOT NULL,
  `senderID` int(11) NOT NULL,
  `receiverType` varchar(10) NOT NULL,
  `receiverID` int(11) NOT NULL,
  `receiverUserID` int(11) NOT NULL,
  `acceptable` tinyint(1) NOT NULL,
  `readStatus` tinyint(1) NOT NULL,
  `message` varchar(225) NOT NULL,
  `sendTime` datetime NOT NULL,
  PRIMARY KEY (`notificationID`),
  KEY `receiverUserID` (`receiverUserID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notificationID`, `senderType`, `senderID`, `receiverType`, `receiverID`, `receiverUserID`, `acceptable`, `readStatus`, `message`, `sendTime`) VALUES
(7, 'User', 14, 'User', 15, 15, 1, 0, 'gggggggg', '2014-12-07 03:35:31'),
(8, 'Group', 2, 'User', 14, 14, 1, 0, 'The group you have joined has been changed', '2014-12-07 01:12:58'),
(9, 'Event', 2, 'User', 14, 14, 1, 0, 'The event you have joined has been changed', '2014-12-07 01:15:25'),
(10, 'Event', 2, 'User', 15, 15, 1, 0, 'The event you have joined has been changed', '2014-12-07 01:15:25'),
(11, 'Group', 2, 'User', 14, 14, 1, 0, 'The group you have joined has been changed', '2014-12-07 01:52:45'),
(12, 'Group', 2, 'User', 15, 15, 1, 0, 'The group you have joined has been changed', '2014-12-07 01:52:45');

-- --------------------------------------------------------

--
-- Table structure for table `organizationprofile`
--

CREATE TABLE IF NOT EXISTS `organizationprofile` (
  `profileID` int(11) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(225) NOT NULL,
  `description` varchar(225) NOT NULL,
  `pictureURI` varchar(225) DEFAULT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`profileID`),
  UNIQUE KEY `userid_2` (`userid`),
  UNIQUE KEY `userid_3` (`userid`),
  UNIQUE KEY `pictureURI` (`pictureURI`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `organizationprofile`
--

INSERT INTO `organizationprofile` (`profileID`, `nickName`, `description`, `pictureURI`, `userid`) VALUES
(7, 'CIPS', 'cips', NULL, 18);

-- --------------------------------------------------------

--
-- Table structure for table `personprofile`
--

CREATE TABLE IF NOT EXISTS `personprofile` (
  `profileID` int(11) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(225) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `age` int(11) NOT NULL,
  `description` varchar(225) NOT NULL,
  `pictureURI` varchar(225) DEFAULT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`profileID`),
  UNIQUE KEY `userid_2` (`userid`),
  KEY `userid` (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `personprofile`
--

INSERT INTO `personprofile` (`profileID`, `nickName`, `gender`, `age`, `description`, `pictureURI`, `userid`) VALUES
(12, 'DL', 'Male', 24, 'so cool', NULL, 14),
(13, 'AK', 'Male', 22, 'handsome', NULL, 15);

-- --------------------------------------------------------

--
-- Table structure for table `uploads`
--

CREATE TABLE IF NOT EXISTS `uploads` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) CHARACTER SET utf8 NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=48 ;

--
-- Dumping data for table `uploads`
--

INSERT INTO `uploads` (`id`, `filename`, `path`) VALUES
(1, 'C:xampp	mpphpB6F2.tmp', '1415328883081.jpg'),
(2, 'C:xampp	mpphp2E96.tmp', '1415341627634.jpg'),
(3, 'C:xampp	mpphpFF20.tmp', '1415397451017.jpg'),
(4, 'C:xampp	mpphpB419.tmp', '1415434983843.jpg'),
(5, 'C:xampp	mpphpD6C7.tmp', '1415434992751.jpg'),
(6, 'C:xampp	mpphpDFBD.tmp', '1415434994992.jpg'),
(7, 'C:xampp	mpphpE7D9.tmp', '1415434997107.jpg'),
(8, 'C:xampp	mpphpF062.tmp', '1415434999262.jpg'),
(9, 'C:xampp	mpphpFC93.tmp', '1415435001367.jpg'),
(10, 'C:xampp	mpphp4DD.tmp', '1415435004491.jpg'),
(11, '', '1415435111558.jpg'),
(12, '', '1415435131239.jpg'),
(13, '', '1415435150002.jpg'),
(14, '', '1415435169100.jpg'),
(15, '', '1415435189239.jpg'),
(16, '', '1415435207277.jpg'),
(17, '', '1415435225954.jpg'),
(18, '', '1415435243587.jpg'),
(19, '', '1415435262055.jpg'),
(20, '', '1415435281593.jpg'),
(21, '', '1415435301130.jpg'),
(22, 'C:xampp	mpphp20D2.tmp', '1415435929130.jpg'),
(23, 'C:xampp	mpphp2E4B.tmp', '1415435932581.jpg'),
(24, 'C:xampp	mpphp3ADA.tmp', '1415435935775.jpg'),
(25, 'C:xampp	mpphp472A.tmp', '1415435938960.jpg'),
(26, 'C:xampp	mpphp558D.tmp', '1415435942653.jpg'),
(27, 'C:xampp	mpphp62E6.tmp', '1415435946056.jpg'),
(28, 'C:xampp	mpphp70DB.tmp', '1415435949630.jpg'),
(29, 'C:xampp	mpphp7E06.tmp', '1415435952984.jpg'),
(30, 'C:xampp	mpphp8AD3.tmp', '1415435956265.jpg'),
(31, 'C:xampp	mpphp980D.tmp', '1415435959668.jpg'),
(32, 'C:xampp	mpphpC517.tmp', '1415436364403.jpg'),
(33, 'C:xampp	mpphp5A64.tmp', '1415436599254.jpg'),
(34, 'C:xampp	mpphp219.tmp', '1415436642170.jpg'),
(35, 'C:xampp	mpphpC28.tmp', '1415436644742.jpg'),
(36, 'C:xampp	mpphp14FF.tmp', '1415436647006.jpg'),
(37, 'C:xampp	mpphp1EA0.tmp', '1415436649440.jpg'),
(38, 'C:xampp	mpphp27D5.tmp', '1415436651845.jpg'),
(39, 'C:xampp	mpphp3119.tmp', '1415436654164.jpg'),
(40, '', '1415464925216.jpg'),
(41, 'C:xampp	mpphpB380.tmp', '1415475550830.jpg'),
(42, '', '1415475799527.jpg'),
(43, 'C:xampp	mpphpE7EA.tmp', '1415475957383.jpg'),
(44, 'C:xampp	mpphp182B.tmp', '1415937113746.jpg'),
(45, '', '1415937302420.jpg'),
(46, '', '1415937810259.jpg'),
(47, 'C:xampp	mpphp6968.tmp', '1416192686635.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `userlocation_table`
--

CREATE TABLE IF NOT EXISTS `userlocation_table` (
  `userLocation_ID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `userCountry` varchar(45) DEFAULT NULL,
  `userCity` varchar(45) DEFAULT NULL,
  `userStreet` varchar(45) DEFAULT NULL,
  `userProtolCode` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`userLocation_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varbinary(80) NOT NULL,
  `userType` varchar(12) NOT NULL,
  `latitude` decimal(11,7) DEFAULT NULL,
  `longitude` decimal(11,7) DEFAULT NULL,
  `gcm_regid` text,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `username`, `email`, `password`, `userType`, `latitude`, `longitude`, `gcm_regid`) VALUES
(14, 'Darren Liu', 'gulang15@gmail.com', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'Person', '37.5008447', '121.4297596', 'APA91bEOCjgP7T6DjBWtqSZ3pPrpt73Z58nFmlq2WexSW9xXMLQoCt6mgw3DUDq7tcjPBUfq6e6AznIpRLgdt63RBLkkQI66wp9ZSJhnT76TRpb8qfCJJF6_tp_Z-xp-02Sslx_gS0X0'),
(15, 'Anson Kong', 'ansonkong1992@gmail.com', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'Person', '37.5008148', '121.4298933', NULL),
(18, 'CIPS', 'gulang15@hotmail.com', '*23AE809DDACAF96AF0FD78ED04B6A265E05AA257', 'Organization', '0.0000000', NULL, NULL);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`eventCreaterID`) REFERENCES `users` (`userid`);

--
-- Constraints for table `eventjoinin`
--
ALTER TABLE `eventjoinin`
  ADD CONSTRAINT `eventjoinin_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `eventjoinin_ibfk_2` FOREIGN KEY (`eventID`) REFERENCES `event` (`eventID`);

--
-- Constraints for table `friend`
--
ALTER TABLE `friend`
  ADD CONSTRAINT `friend_ibfk_1` FOREIGN KEY (`friend_one`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `friend_ibfk_2` FOREIGN KEY (`friend_two`) REFERENCES `users` (`userid`);

--
-- Constraints for table `group`
--
ALTER TABLE `group`
  ADD CONSTRAINT `group_ibfk_1` FOREIGN KEY (`groupCreaterID`) REFERENCES `users` (`userid`);

--
-- Constraints for table `groupjoinin`
--
ALTER TABLE `groupjoinin`
  ADD CONSTRAINT `groupjoinin_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`userid`),
  ADD CONSTRAINT `groupjoinin_ibfk_2` FOREIGN KEY (`groupID`) REFERENCES `group` (`groupID`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`receiverUserID`) REFERENCES `users` (`userid`);

--
-- Constraints for table `organizationprofile`
--
ALTER TABLE `organizationprofile`
  ADD CONSTRAINT `organizationprofile_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`);

--
-- Constraints for table `personprofile`
--
ALTER TABLE `personprofile`
  ADD CONSTRAINT `personprofile_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
