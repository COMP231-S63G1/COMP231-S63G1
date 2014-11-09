-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 09, 2014 at 08:40 AM
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
`eventID` int(11) unsigned NOT NULL,
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eventID`, `eventCreaterID`, `eventType`, `eventName`, `eventImageURI`, `eventDate`, `eventTime`, `eventVenue`, `eventAddress`, `eventPriceRange`, `eventDescription`) VALUES
(1, 0, NULL, 'basketball', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 0, NULL, 'dddd', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, 0, NULL, 'dddd', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(4, 0, NULL, 'cvvv', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(5, 0, NULL, 'cvvv', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(6, 0, NULL, 'cvvv', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(7, 0, NULL, 'nnnjjjj', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(8, 0, NULL, 'eventName', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(9, 0, NULL, 'hutJ', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(10, 0, NULL, 'event name', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(11, 0, NULL, 'fvbhu', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(12, 0, NULL, 'play basketball', NULL, '2014-10-22', '11:16:40', 'ddddadaf', 'dagegg', 'ettetere', 'eqtqt'),
(13, 0, NULL, 'ddd', 'ddddege', '2014-10-08', '12:16:00', 'ddddht ', 'thr gs', '30-60', 'des'),
(14, 0, 'sport', 'play basketball', NULL, '2014-10-09', '08:38:14', 'venue', 'adress', 'priceRange', 'description'),
(15, 0, '$eventType', '$eventName', NULL, '0000-00-00', '00:00:00', '$eventVenue', '$eventAddress', '$eventPriceRange', '$eventDescription'),
(16, 0, 'Sports', 'cbnjdv', NULL, '2014-04-05', '12:11:30', 'dcvh', 'fcbjh', '4885', 'dvbdgb'),
(17, 0, 'Sports', 'vbsndjb', NULL, '2014-12-01', '12:14:11', 'bdvdhdb', 'bdbdjdb', '36', 'sbdvd'),
(18, 0, 'Sports', 'fvhhvvb', NULL, '2017-02-27', '19:24:00', 'fghjgvb', 'sfbhygb', '23', 'fgvvjjf'),
(19, 0, 'Sports', 'xvbvcf', NULL, '2014-02-05', '22:05:00', 'svbyrc', 'scvhydv', '48', 'dcbhy'),
(20, 0, 'Sports', 'dvbb ', NULL, '2014-01-01', '23:16:00', 'dcbj', 'dcvgg', '25', 'fcbhddv'),
(21, 0, 'Sports', 'fgjk', NULL, '2014-10-06', '23:42:00', 'ghji', 'ghjkk', '25', 'hvvhjn'),
(22, 0, 'Entertainment', 'fgbvc', NULL, '2014-02-01', '23:45:00', 'fvsdcvb', 'dcbhh', '25', 'tgbjjjv'),
(23, 0, 'Entertainment', 'rfvbcxg', NULL, '2014-02-01', '02:42:00', 'fvbj', 'fvnyfc', '458', 'gv xdg '),
(24, 0, 'Entertainment', 'Jis', NULL, '2014-02-03', '14:16:00', 'fdvv', 'cvddv', '25', 'dgvv'),
(25, 0, 'Entertainment', 'go swim', NULL, '2014-10-03', '10:14:00', 'lee center', 'progress avenue', '25', 'bring swim glass'),
(26, 0, 'Sports', 'fghv', NULL, '2014-10-07', '20:05:00', 'ddcvh', 'ddcggc', '2', 'tfcvhhv'),
(27, 0, 'Sports', 'fgbvv', NULL, '2014-10-07', '21:10:00', 'gvbff', 'gvbffh', '2', 'gbnj'),
(28, 0, 'Entertainment', 'see a movie', NULL, '2014-02-03', '22:53:00', 'ndndnp', 'dndbbd', '25', 'bebdbxj'),
(29, 0, 'Entertainment', 'see a movie', NULL, '2014-03-03', '22:56:00', 'gbbjjtg', 'ggbjjt', '98', 'ggvvvn'),
(30, 0, 'Sports', 'ghjj', NULL, '2014-10-08', '17:09:00', 'hhj', 'hjj', '36', 'uhhjj'),
(31, 0, 'Entertainment', 'gdhrn', NULL, '2014-10-04', '17:07:00', 'bdndj', 'hdndjd', '25', 'djdbdn'),
(32, 0, 'Entertainment', 'hdhdj', NULL, '2014-10-04', '17:09:00', 'jdjsj', 'jejej', '25', 'jejsj'),
(33, 0, 'Entertainment', 'see movie', NULL, '2014-10-04', '17:24:00', 'jjj', 'ggg', '36', 'uip'),
(34, 0, 'Entertainment', 'ttt', NULL, '2014-10-04', '17:26:00', 'hhhh', 'tghh', '2', 'hhh'),
(35, 0, 'Entertainment', 'fghhj', NULL, '2014-10-04', '17:33:00', 'vvhj', 'ghjkl', '69', 'tghj'),
(36, 0, 'Entertainment', 'dghj', NULL, '2014-10-04', '17:54:00', '', '', '', ''),
(37, 0, 'Entertainment', 'tgghh', NULL, '2014-10-04', '18:02:00', 'vvbj', 'vbjjj', '36', 'ujjn'),
(38, 0, 'Sports', 'ujj', NULL, '2014-10-04', '18:04:00', 'jjj', '', '', ''),
(39, 0, 'Entertainment', 'fghh', NULL, '2014-10-04', '18:05:00', 'bbggh', 'vgjjk', '98', 'hvbj'),
(40, 0, 'Select Event Type', '', NULL, '0000-00-00', '00:00:00', '', '', '', ''),
(41, 0, 'Entertainment', 'bhj', NULL, '2014-10-04', '18:32:00', 'gjjkhh', 'hjjkk', '36', 'ikjhbh'),
(42, 0, 'Sports', 'dfg', NULL, '2014-10-04', '18:34:00', '', '', '', ''),
(43, 0, 'Sports', 'tfgjj', NULL, '2014-10-04', '18:35:00', 'hjjj', '', '', ''),
(44, 0, 'Entertainment', 'dfgh', NULL, '2014-10-04', '18:38:00', 'dfg', 'dffg', '8', 'ffgh'),
(45, 0, 'Sports', 'ddfgh', NULL, '2014-10-04', '18:39:00', 'rrtty', 'dfggh', '25', 'ggghh'),
(46, 0, 'Entertainment', 'ghjj', NULL, '2014-10-04', '19:02:00', 'hhj', 'hhjk', '25', 'ghji'),
(47, 0, 'Entertainment', 'see movie', '', '2014-10-06', '21:54:00', 'k', 'j', '3', 'k'),
(48, 0, 'Sports', 'g', '', '2014-10-07', '01:26:00', 'g', 'b', '36', 'h'),
(49, 0, 'Entertainment', 'see mohsena', '', '2014-10-07', '17:56:00', 'Jake', 'upload', '36', 'will'),
(50, 31, 'Sports', 'bb', '', '2014-11-08', '14:51:00', 'jghjj', 'jhjj', '62', 'shhj'),
(51, 31, 'Entertainment', 'cvh', '', '2014-11-08', '14:55:00', 'f', 'h', '68', 'j'),
(52, 31, 'Sports', 'fgh', NULL, '2014-11-08', '14:57:00', 'cvb', 'ghhj', '65', 'ggh'),
(53, 31, 'Entertainment', 'vbn', NULL, '2014-11-08', '15:05:00', 'jn', 'jj', '58', 'hjj'),
(54, 31, 'Select Event Type', 'basketball', NULL, '2014-11-09', '01:30:00', 'ggg', 'hhh', '20', 'jjj');

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
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
`profileID` int(11) NOT NULL,
  `nickName` varchar(225) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `age` int(11) NOT NULL,
  `description` varchar(225) NOT NULL,
  `pictureURI` varchar(225) DEFAULT NULL,
  `userid` int(11) NOT NULL,
  `eventID` int(11) unsigned DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `profile`
--

INSERT INTO `profile` (`profileID`, `nickName`, `gender`, `age`, `description`, `pictureURI`, `userid`, `eventID`) VALUES
(8, 'AK', 'Male', 22, 'cool', NULL, 23, NULL),
(10, 'yonastecle', 'Male', 31, 'rfgh', NULL, 25, 1),
(13, 'yu', 'Female', 26, 'yu', NULL, 28, NULL),
(14, 'yu', 'Female', 26, 'des', NULL, 29, NULL),
(15, 'Darren', 'Male', 25, 'Darren is a big pig @', NULL, 31, 54);

-- --------------------------------------------------------

--
-- Table structure for table `uploads`
--

CREATE TABLE IF NOT EXISTS `uploads` (
`id` int(11) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `path` varchar(255) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=44 ;

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
(43, 'C:xampp	mpphpE7EA.tmp', '1415475957383.jpg');

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
 ADD PRIMARY KEY (`eventID`);

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
-- Indexes for table `profile`
--
ALTER TABLE `profile`
 ADD PRIMARY KEY (`profileID`), ADD UNIQUE KEY `eventID_2` (`eventID`), ADD KEY `userid` (`userid`), ADD KEY `eventID` (`eventID`);

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
MODIFY `eventID` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=55;
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
-- AUTO_INCREMENT for table `profile`
--
ALTER TABLE `profile`
MODIFY `profileID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `uploads`
--
ALTER TABLE `uploads`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=44;
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
-- Constraints for table `profile`
--
ALTER TABLE `profile`
ADD CONSTRAINT `profile_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`userid`),
ADD CONSTRAINT `profile_ibfk_2` FOREIGN KEY (`eventID`) REFERENCES `event` (`eventID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
