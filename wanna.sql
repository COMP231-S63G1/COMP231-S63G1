-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 03, 2014 at 09:45 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

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
`eventID` int(11) NOT NULL,
  `eventCreaterID` int(11) NOT NULL,
  `eventType` varchar(50) DEFAULT NULL,
  `eventName` varchar(225) DEFAULT NULL,
  `eventImageURI` varchar(225) DEFAULT NULL,
  `eventDate` date DEFAULT NULL,
  `eventTime` time DEFAULT NULL,
  `eventVenue` varchar(225) DEFAULT NULL,
  `eventAddress` varchar(225) DEFAULT NULL,
  `eventPriceRange` varchar(225) DEFAULT NULL,
  `eventDescription` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eventID`, `eventCreaterID`, `eventType`, `eventName`, `eventImageURI`, `eventDate`, `eventTime`, `eventVenue`, `eventAddress`, `eventPriceRange`, `eventDescription`) VALUES
(2, 14, 'Sports', 'swiming', NULL, '2014-12-10', '15:59:00', 'Centennial', 'Progress', '10', 'swim'),
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
  `eventID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `eventjoinin`
--

INSERT INTO `eventjoinin` (`userID`, `eventID`) VALUES
(14, 2),
(14, 6),
(14, 12),
(18, 13),
(18, 14);

-- --------------------------------------------------------

--
-- Table structure for table `eventlocation_table`
--

CREATE TABLE IF NOT EXISTS `eventlocation_table` (
`eventLocationID` int(11) NOT NULL,
  `eventID` int(11) NOT NULL,
  `eventLocationCountry` varchar(45) DEFAULT NULL,
  `eventLocationCity` varchar(45) DEFAULT NULL,
  `eventLocationStreet` varchar(45) DEFAULT NULL,
  `eventLocationProtolCode` varchar(45) DEFAULT NULL,
  `eventLongtitude` varchar(45) DEFAULT NULL,
  `eventLatitude` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `eventtype_table`
--

CREATE TABLE IF NOT EXISTS `eventtype_table` (
`eventTypeID` int(11) NOT NULL,
  `eventTypeName` varchar(45) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `eventtype_table`
--

INSERT INTO `eventtype_table` (`eventTypeID`, `eventTypeName`) VALUES
(1, 'Sports'),
(2, 'Entertainment');

-- --------------------------------------------------------

--
-- Table structure for table `group`
--

CREATE TABLE IF NOT EXISTS `group` (
`groupID` int(11) NOT NULL,
  `groupCreaterID` int(11) NOT NULL,
  `groupPrivacy` varchar(10) NOT NULL,
  `groupType` varchar(50) DEFAULT NULL,
  `groupName` varchar(225) DEFAULT NULL,
  `groupImageURI` varchar(225) DEFAULT NULL,
  `groupDescription` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `group`
--

INSERT INTO `group` (`groupID`, `groupCreaterID`, `groupPrivacy`, `groupType`, `groupName`, `groupImageURI`, `groupDescription`) VALUES
(1, 14, 'Public', 'Sports', 'games', NULL, 'games'),
(2, 14, 'Public', 'Sports', 'movie', NULL, 'movies'),
(3, 18, 'Public', 'Sports', 'camping', NULL, 'camping');

-- --------------------------------------------------------

--
-- Table structure for table `groupjoinin`
--

CREATE TABLE IF NOT EXISTS `groupjoinin` (
  `userID` int(11) NOT NULL,
  `groupID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupjoinin`
--

INSERT INTO `groupjoinin` (`userID`, `groupID`) VALUES
(14, 1),
(14, 2),
(18, 3);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
`notificationNO` int(11) NOT NULL,
  `senderType` varchar(10) NOT NULL,
  `senderID` int(11) NOT NULL,
  `receiverType` varchar(10) NOT NULL,
  `receiverID` int(11) NOT NULL,
  `receiverUserID` int(11) NOT NULL,
  `acceptable` tinyint(1) NOT NULL,
  `readStatus` tinyint(1) NOT NULL,
  `message` varchar(225) NOT NULL,
  `sendTime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `organizationprofile`
--

CREATE TABLE IF NOT EXISTS `organizationprofile` (
`profileID` int(11) NOT NULL,
  `nickName` varchar(225) NOT NULL,
  `description` varchar(225) NOT NULL,
  `pictureURI` varchar(225) DEFAULT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

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
`profileID` int(11) NOT NULL,
  `nickName` varchar(225) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `age` int(11) NOT NULL,
  `description` varchar(225) NOT NULL,
  `pictureURI` varchar(225) DEFAULT NULL,
  `userid` int(11) NOT NULL
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
`id` int(11) NOT NULL,
  `filename` varchar(255) CHARACTER SET utf8 NOT NULL,
  `path` varchar(255) CHARACTER SET utf8 NOT NULL
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
`userLocation_ID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `userCountry` varchar(45) DEFAULT NULL,
  `userCity` varchar(45) DEFAULT NULL,
  `userStreet` varchar(45) DEFAULT NULL,
  `userProtolCode` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
`userid` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varbinary(80) NOT NULL,
  `userType` varchar(12) NOT NULL,
  `latitude` decimal(11,7) DEFAULT NULL,
  `longitude` decimal(11,7) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `username`, `email`, `password`, `userType`, `latitude`, `longitude`) VALUES
(14, 'Darren Liu', 'gulang15@gmail.com', 0x2a32334145383039444441434146393641463046443738454430344236413236354530354141323537, 'Person', '43.7844285', '-79.2285720'),
(15, 'Anson Kong', 'ansonkong1992@gmail.com', 0x2a32334145383039444441434146393641463046443738454430344236413236354530354141323537, 'Person', '0.0000000', NULL),
(18, 'CIPS', 'gulang15@hotmail.com', 0x2a32334145383039444441434146393641463046443738454430344236413236354530354141323537, 'Organization', '0.0000000', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
 ADD PRIMARY KEY (`eventID`), ADD UNIQUE KEY `eventCreaterID_2` (`eventCreaterID`,`eventType`,`eventName`,`eventImageURI`,`eventDate`,`eventTime`,`eventVenue`,`eventAddress`,`eventPriceRange`), ADD KEY `eventID` (`eventID`), ADD KEY `eventCreaterID` (`eventCreaterID`);

--
-- Indexes for table `eventjoinin`
--
ALTER TABLE `eventjoinin`
 ADD PRIMARY KEY (`userID`,`eventID`), ADD KEY `joinedProfileID` (`userID`), ADD KEY `joinedEventID` (`eventID`);

--
-- Indexes for table `eventlocation_table`
--
ALTER TABLE `eventlocation_table`
 ADD PRIMARY KEY (`eventLocationID`);

--
-- Indexes for table `eventtype_table`
--
ALTER TABLE `eventtype_table`
 ADD PRIMARY KEY (`eventTypeID`);

--
-- Indexes for table `group`
--
ALTER TABLE `group`
 ADD PRIMARY KEY (`groupID`), ADD UNIQUE KEY `groupCreaterID_2` (`groupCreaterID`,`groupPrivacy`,`groupType`,`groupName`,`groupImageURI`), ADD KEY `groupID` (`groupID`), ADD KEY `groupCreaterID` (`groupCreaterID`);

--
-- Indexes for table `groupjoinin`
--
ALTER TABLE `groupjoinin`
 ADD PRIMARY KEY (`userID`,`groupID`), ADD UNIQUE KEY `groupID_2` (`groupID`), ADD KEY `profileID` (`userID`), ADD KEY `groupID` (`groupID`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
 ADD PRIMARY KEY (`notificationNO`), ADD KEY `receiverUserID` (`receiverUserID`);

--
-- Indexes for table `organizationprofile`
--
ALTER TABLE `organizationprofile`
 ADD PRIMARY KEY (`profileID`), ADD UNIQUE KEY `userid_2` (`userid`), ADD UNIQUE KEY `userid_3` (`userid`), ADD UNIQUE KEY `pictureURI` (`pictureURI`), ADD KEY `userid` (`userid`);

--
-- Indexes for table `personprofile`
--
ALTER TABLE `personprofile`
 ADD PRIMARY KEY (`profileID`), ADD UNIQUE KEY `userid_2` (`userid`), ADD KEY `userid` (`userid`);

--
-- Indexes for table `uploads`
--
ALTER TABLE `uploads`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userlocation_table`
--
ALTER TABLE `userlocation_table`
 ADD PRIMARY KEY (`userLocation_ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`userid`), ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
MODIFY `eventID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `eventlocation_table`
--
ALTER TABLE `eventlocation_table`
MODIFY `eventLocationID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `eventtype_table`
--
ALTER TABLE `eventtype_table`
MODIFY `eventTypeID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `group`
--
ALTER TABLE `group`
MODIFY `groupID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
MODIFY `notificationNO` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `organizationprofile`
--
ALTER TABLE `organizationprofile`
MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `personprofile`
--
ALTER TABLE `personprofile`
MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `uploads`
--
ALTER TABLE `uploads`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=48;
--
-- AUTO_INCREMENT for table `userlocation_table`
--
ALTER TABLE `userlocation_table`
MODIFY `userLocation_ID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=19;
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
