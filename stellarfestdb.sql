-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2024 at 08:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stellarfestdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `eventId` varchar(255) NOT NULL,
  `eventName` varchar(255) NOT NULL,
  `eventDate` varchar(255) NOT NULL,
  `eventLocation` varchar(255) NOT NULL,
  `eventDescription` varchar(255) NOT NULL,
  `organizerId` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`eventId`, `eventName`, `eventDate`, `eventLocation`, `eventDescription`, `organizerId`) VALUES
('EV1734526895934', 'Test Event', '2024-12-19', 'Test Loc', '', '6be8a149-7b2c-4259-a6b1-a7722233271f'),
('EV1734527373731', 'Test Event 1', '2024-12-19', 'Event Location 1', 'Test Description', '6be8a149-7b2c-4259-a6b1-a7722233271f');

-- --------------------------------------------------------

--
-- Table structure for table `invitations`
--

CREATE TABLE `invitations` (
  `invitationId` varchar(255) NOT NULL,
  `eventId` varchar(255) NOT NULL,
  `userId` varchar(255) NOT NULL,
  `invitationStatus` varchar(255) NOT NULL,
  `invitationRole` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `invitations`
--

INSERT INTO `invitations` (`invitationId`, `eventId`, `userId`, `invitationStatus`, `invitationRole`) VALUES
('INV1734540994922', 'EV1734527373731', 'US1734540963068', 'Pending', 'Vendor'),
('INV1734541014251', 'EV1734527373731', 'US1734538440154', 'Pending', 'Vendor'),
('INV1734541014255', 'EV1734527373731', 'US1734540942019', 'Accepted', 'Vendor');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `userId` varchar(255) NOT NULL,
  `productName` varchar(255) NOT NULL,
  `productDescription` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`userId`, `productName`, `productDescription`) VALUES
('US1734540942019', 'Product 1', 'Description of a Product');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userId` varchar(255) NOT NULL,
  `userEmail` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `userPassword` varchar(255) NOT NULL,
  `userRole` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userId`, `userEmail`, `userName`, `userPassword`, `userRole`) VALUES
('69d57d90-f304-4400-86f7-4efd62bc05e0', 'admin', 'admin', 'admin', 'Admin'),
('6be8a149-7b2c-4259-a6b1-a7722233271f', 'organizer@gmail.com', 'organizer', 'organizer', 'Event Organizer'),
('US1734534525070', 'guest@gmail.com', 'guest', 'guest', 'Guest'),
('US1734538440154', 'vendor@', 'vendor 1', 'vendor', 'Vendor'),
('US1734540942019', 'vendor2@', 'vendor 2', 'vendor', 'Vendor'),
('US1734540963068', 'vendor3@', 'vendor 3', 'vendor', 'Vendor'),
('US1734546992230', 'email@g.com', 'guest 2', 'guest', 'Guest');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`eventId`),
  ADD KEY `organizerId` (`organizerId`);

--
-- Indexes for table `invitations`
--
ALTER TABLE `invitations`
  ADD PRIMARY KEY (`invitationId`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userId`),
  ADD UNIQUE KEY `userEmail` (`userEmail`),
  ADD UNIQUE KEY `userName` (`userName`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `events_ibfk_1` FOREIGN KEY (`organizerId`) REFERENCES `users` (`userId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
