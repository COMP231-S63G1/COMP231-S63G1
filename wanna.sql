-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 24, 2014 at 12:11 AM
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=61 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eventID`, `eventCreaterID`, `eventType`, `eventName`, `eventImageURI`, `eventDate`, `eventTime`, `eventVenue`, `eventAddress`, `eventPriceRange`, `eventDescription`) VALUES
(59, 10, 'Sports', 'basketball', NULL, '2014-11-25', '03:25:23', 'Progress', '941', '20', 'basketball game'),
(60, 15, 'Sports', 'Jog', NULL, '2014-11-29', '13:16:19', 'Yonge', '100', '30', 'Jogging');

-- --------------------------------------------------------

--
-- Table structure for table `eventjoinin`
--

CREATE TABLE IF NOT EXISTS `eventjoinin` (
  `joinedProfileID` int(11) NOT NULL,
  `joinedEventID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `eventjoinin`
--

INSERT INTO `eventjoinin` (`joinedProfileID`, `joinedEventID`) VALUES
(8, 59),
(10, 59),
(13, 60),
(14, 60);

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
(1, 15, 'Public', 'Sports', 'aaaaa', NULL, 'bbbbb'),
(2, 15, 'Private', 'Sports', 'bbbbbb', NULL, 'bbbbbbbb'),
(3, 15, 'Public', 'Sports', 'sports', NULL, 'sksks');

-- --------------------------------------------------------

--
-- Table structure for table `groupjoinin`
--

CREATE TABLE IF NOT EXISTS `groupjoinin` (
  `profileID` int(11) NOT NULL,
  `groupID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupjoinin`
--

INSERT INTO `groupjoinin` (`profileID`, `groupID`) VALUES
(8, 1),
(10, 1),
(10, 2),
(13, 1),
(13, 2),
(14, 1),
(15, 2);

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
`profileID` int(11) NOT NULL,
  `nickName` varchar(225) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `age` int(11) NOT NULL,
  `description` varchar(225) NOT NULL,
  `pictureURI` varchar(225) DEFAULT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`profileID`, `nickName`, `gender`, `age`, `description`, `pictureURI`, `userid`) VALUES
(8, 'AK', 'Male', 22, 'cool', NULL, 23),
(10, 'yonastecle', 'Male', 31, 'rfgh', NULL, 25),
(13, 'yu', 'Female', 26, 'yu', NULL, 28),
(14, 'yu', 'Female', 26, 'des', NULL, 29),
(15, 'Darren', 'Male', 25, 'monkey', NULL, 31);

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
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varbinary(80) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=32 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `firstname`, `lastname`, `email`, `password`) VALUES
(23, 'Anson', 'Kong', 'ansonkong1992@gmail.com', 0x2a36424234383337454237343332393130354545343536384444413744433637454432434132414439),
(24, 'Yu', 'Chang', 'changyu19982013@gmail.com', 0x2a36424234383337454237343332393130354545343536384444413744433637454432434132414439),
(25, 'yonas', 'tecle', 'yonastecle@hotmail.com', 0x2a32413536413432344333443733463237373938464641303532323031443433393038463045323941),
(28, 'yu', 'chang', 'changyu19882013@gmail.com', 0x2a39434334433138463939383735334437444641324438383038433044423732414441323543314230),
(29, 'yulisa', 'chang', '584287240@qq.com', 0x2a36424234383337454237343332393130354545343536384444413744433637454432434132414439),
(30, 'yu', 'chang', 'changyu@gmail.com', 0x2a36424234383337454237343332393130354545343536384444413744433637454432434132414439),
(31, 'Darren', 'liu', 'gulang15@gmail.com', 0x2a32334145383039444441434146393641463046443738454430344236413236354530354141323537);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
 ADD PRIMARY KEY (`eventID`), ADD KEY `eventID` (`eventID`), ADD KEY `eventCreaterID` (`eventCreaterID`);

--
-- Indexes for table `eventjoinin`
--
ALTER TABLE `eventjoinin`
 ADD PRIMARY KEY (`joinedProfileID`,`joinedEventID`), ADD KEY `joinedProfileID` (`joinedProfileID`), ADD KEY `joinedEventID` (`joinedEventID`);

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
 ADD PRIMARY KEY (`groupID`), ADD KEY `groupID` (`groupID`), ADD KEY `groupCreaterID` (`groupCreaterID`);

--
-- Indexes for table `groupjoinin`
--
ALTER TABLE `groupjoinin`
 ADD PRIMARY KEY (`profileID`,`groupID`), ADD KEY `profileID` (`profileID`), ADD KEY `groupID` (`groupID`);

--
-- Indexes for table `profile`
--
ALTER TABLE `profile`
 ADD PRIMARY KEY (`profileID`), ADD KEY `userid` (`userid`);

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
MODIFY `eventID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=61;
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
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
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
MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=32;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `event`
--
ALTER TABLE `event`
ADD CONSTRAINT `event_ibfk_1` FOREIGN KEY (`eventCreaterID`) REFERENCES `profile` (`profileID`);

--
-- Constraints for table `eventjoinin`
--
ALTER TABLE `eventjoinin`
ADD CONSTRAINT `eventjoinin_ibfk_1` FOREIGN KEY (`joinedProfileID`) REFERENCES `profile` (`profileID`),
ADD CONSTRAINT `eventjoinin_ibfk_2` FOREIGN KEY (`joinedEventID`) REFERENCES `event` (`eventID`);

--
-- Constraints for table `group`
--
ALTER TABLE `group`
ADD CONSTRAINT `group_ibfk_1` FOREIGN KEY (`groupCreaterID`) REFERENCES `profile` (`profileID`);

--
-- Constraints for table `groupjoinin`
--
ALTER TABLE `groupjoinin`
ADD CONSTRAINT `groupjoinin_ibfk_1` FOREIGN KEY (`profileID`) REFERENCES `profile` (`profileID`),
ADD CONSTRAINT `groupjoinin_ibfk_2` FOREIGN KEY (`groupID`) REFERENCES `group` (`groupID`);

--
-- Constraints for table `profile`
--
ALTER TABLE `profile`
ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
