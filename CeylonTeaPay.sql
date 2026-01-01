-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 01, 2026 at 08:22 AM
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
-- Database: `CeylonTeaPay`
--

-- --------------------------------------------------------

--
-- Table structure for table `DeliveryTea`
--

CREATE TABLE `DeliveryTea` (
  `deliveryId` int(11) NOT NULL,
  `deliveryFactoryId` int(11) NOT NULL,
  `deliveryFactoryName` varchar(100) DEFAULT NULL,
  `StockId` int(11) NOT NULL,
  `deliveryQty` int(11) NOT NULL,
  `deliveryDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `DeliveryTea`
--

INSERT INTO `DeliveryTea` (`deliveryId`, `deliveryFactoryId`, `deliveryFactoryName`, `StockId`, `deliveryQty`, `deliveryDate`) VALUES
(2, 6, 'welangahenaWatta', 3, 80, '2025-12-30'),
(4, 6, 'welangahenaWatta', 3, 20, '2025-11-25'),
(5, 6, 'welangahenaWatta', 3, 100, '2025-12-30'),
(7, 6, 'welangahenaWatta', 3, 50, '2025-12-31'),
(8, 6, 'welangahenaWatta', 6, 80, '2025-12-31');

-- --------------------------------------------------------

--
-- Table structure for table `Employee`
--

CREATE TABLE `Employee` (
  `EmpID` int(11) NOT NULL,
  `Name` varchar(50) NOT NULL,
  `NIC` varchar(20) NOT NULL,
  `Dob` date DEFAULT NULL,
  `Address` varchar(100) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `TelNo` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Employee`
--

INSERT INTO `Employee` (`EmpID`, `Name`, `NIC`, `Dob`, `Address`, `Gender`, `TelNo`) VALUES
(59, 'Chamith', '200424301230', '1972-08-30', 'Deniyaya', 'Female', '0772923793'),
(60, 'Amaraweera', '200424301245', '2004-08-30', 'Galle', 'Male', '0764823793'),
(61, 'Nadeejamax', '200424301230', '1972-08-30', 'Deniyaya', 'Male', '0772923793'),
(62, 'nadeejaamaraweera', '200424301245', '2004-08-30', 'Hapugala', 'Male', '0764823793');

-- --------------------------------------------------------

--
-- Table structure for table `Factory`
--

CREATE TABLE `Factory` (
  `FactoryId` int(11) NOT NULL,
  `FactoryName` varchar(100) NOT NULL,
  `FactoryAddress` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Factory`
--

INSERT INTO `Factory` (`FactoryId`, `FactoryName`, `FactoryAddress`) VALUES
(6, 'welangahenaWatta', 'Deniyaya');

-- --------------------------------------------------------

--
-- Table structure for table `Income`
--

CREATE TABLE `Income` (
  `incomeId` int(11) NOT NULL,
  `Month` varchar(30) NOT NULL,
  `Year` int(11) NOT NULL,
  `teaSalary` decimal(10,2) NOT NULL,
  `otherWorkSalary` decimal(10,2) NOT NULL,
  `thisMonthIncome` decimal(10,2) NOT NULL,
  `finalIncome` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Income`
--

INSERT INTO `Income` (`incomeId`, `Month`, `Year`, `teaSalary`, `otherWorkSalary`, `thisMonthIncome`, `finalIncome`) VALUES
(5, 'December', 2025, 96.00, 5000.00, 10000.00, 4904.00),
(10, 'January', 2026, 46.00, 3000.00, 10000.00, 6954.00);

-- --------------------------------------------------------

--
-- Table structure for table `Land`
--

CREATE TABLE `Land` (
  `LndID` int(11) NOT NULL,
  `LandName` varchar(50) NOT NULL,
  `LandNo` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Land`
--

INSERT INTO `Land` (`LndID`, `LandName`, `LandNo`) VALUES
(12, 'Galpoththawala', 'B'),
(13, 'Akkara 5', 'A');

-- --------------------------------------------------------

--
-- Table structure for table `Login`
--

CREATE TABLE `Login` (
  `UserName` varchar(30) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Login`
--

INSERT INTO `Login` (`UserName`, `Password`) VALUES
('nadeeja', '123');

-- --------------------------------------------------------

--
-- Table structure for table `OtherWork`
--

CREATE TABLE `OtherWork` (
  `Work_ID` int(11) NOT NULL,
  `Emp_ID` int(11) NOT NULL,
  `Lnd_ID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Details` text NOT NULL,
  `Salary` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `OtherWork`
--

INSERT INTO `OtherWork` (`Work_ID`, `Emp_ID`, `Lnd_ID`, `Date`, `Details`, `Salary`) VALUES
(13, 61, 12, '2025-12-30', 'Week', 10000.00),
(14, 60, 12, '2025-12-30', 'Good', 5000.00),
(15, 59, 12, '2026-01-01', 'Good', 3000.00),
(17, 62, 12, '2025-12-30', 'Good', 5000.00);

-- --------------------------------------------------------

--
-- Table structure for table `Payment`
--

CREATE TABLE `Payment` (
  `paymentId` int(11) NOT NULL,
  `rateId` int(11) NOT NULL,
  `empId` int(11) NOT NULL,
  `empName` varchar(100) NOT NULL,
  `teaSalary` decimal(10,2) NOT NULL,
  `expenseSalary` decimal(10,2) NOT NULL,
  `finalSalary` decimal(10,2) NOT NULL,
  `SalaryMonth` varchar(30) NOT NULL,
  `Payment_Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Payment`
--

INSERT INTO `Payment` (`paymentId`, `rateId`, `empId`, `empName`, `teaSalary`, `expenseSalary`, `finalSalary`, `SalaryMonth`, `Payment_Date`) VALUES
(30, 7, 62, 'nadeejaamaraweera', 96.00, 5000.00, 5096.00, 'DECEMBER', '2025-12-31'),
(33, 7, 59, 'Chamith', 46.00, 3000.00, 3046.00, 'JANUARY', '2026-01-01');

-- --------------------------------------------------------

--
-- Table structure for table `Stock`
--

CREATE TABLE `Stock` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `quality` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `availableQuantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Stock`
--

INSERT INTO `Stock` (`id`, `date`, `quality`, `quantity`, `availableQuantity`) VALUES
(3, '2025-12-12', 'Super leef', 100, 50),
(6, '2025-12-12', 'Red Tea', 50, 20),
(7, '2026-01-01', 'White Tea', 70, 70);

-- --------------------------------------------------------

--
-- Table structure for table `Tea`
--

CREATE TABLE `Tea` (
  `Tea_ID` int(11) NOT NULL,
  `Emp_ID` int(11) NOT NULL,
  `EmpName` varchar(100) DEFAULT NULL,
  `Lnd_ID` int(11) NOT NULL,
  `LandName` varchar(100) DEFAULT NULL,
  `Date_Collected` date NOT NULL,
  `Full_Weight` double NOT NULL,
  `Bag_Weight` double NOT NULL,
  `Water_Weight` double NOT NULL,
  `Total_Weight` double NOT NULL,
  `Quality` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `Tea`
--

INSERT INTO `Tea` (`Tea_ID`, `Emp_ID`, `EmpName`, `Lnd_ID`, `LandName`, `Date_Collected`, `Full_Weight`, `Bag_Weight`, `Water_Weight`, `Total_Weight`, `Quality`) VALUES
(52, 62, 'nadeejaamaraweera', 12, 'Akkara 5', '2025-12-30', 100, 2, 2, 96, 'Good'),
(53, 59, 'Chamith', 12, 'Galpoththawala', '2026-01-01', 50, 2, 2, 46, 'Good');

-- --------------------------------------------------------

--
-- Table structure for table `TeaRate`
--

CREATE TABLE `TeaRate` (
  `rateId` int(11) NOT NULL,
  `Month` varchar(30) NOT NULL,
  `Year` int(11) NOT NULL,
  `ratePerKg` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `TeaRate`
--

INSERT INTO `TeaRate` (`rateId`, `Month`, `Year`, `ratePerKg`) VALUES
(7, 'December', 2025, 300.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `DeliveryTea`
--
ALTER TABLE `DeliveryTea`
  ADD PRIMARY KEY (`deliveryId`),
  ADD KEY `deliveryFactoryId` (`deliveryFactoryId`),
  ADD KEY `StockId` (`StockId`),
  ADD KEY `deliveryQty` (`deliveryQty`);

--
-- Indexes for table `Employee`
--
ALTER TABLE `Employee`
  ADD PRIMARY KEY (`EmpID`);

--
-- Indexes for table `Factory`
--
ALTER TABLE `Factory`
  ADD PRIMARY KEY (`FactoryId`);

--
-- Indexes for table `Income`
--
ALTER TABLE `Income`
  ADD PRIMARY KEY (`incomeId`);

--
-- Indexes for table `Land`
--
ALTER TABLE `Land`
  ADD PRIMARY KEY (`LndID`);

--
-- Indexes for table `OtherWork`
--
ALTER TABLE `OtherWork`
  ADD PRIMARY KEY (`Work_ID`),
  ADD KEY `Emp_ID` (`Emp_ID`),
  ADD KEY `Lnd_ID` (`Lnd_ID`);

--
-- Indexes for table `Payment`
--
ALTER TABLE `Payment`
  ADD PRIMARY KEY (`paymentId`),
  ADD KEY `empId` (`empId`),
  ADD KEY `rateId` (`rateId`);

--
-- Indexes for table `Stock`
--
ALTER TABLE `Stock`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Tea`
--
ALTER TABLE `Tea`
  ADD PRIMARY KEY (`Tea_ID`),
  ADD KEY `Lnd_ID` (`Lnd_ID`),
  ADD KEY `Emp_ID` (`Emp_ID`);

--
-- Indexes for table `TeaRate`
--
ALTER TABLE `TeaRate`
  ADD PRIMARY KEY (`rateId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `DeliveryTea`
--
ALTER TABLE `DeliveryTea`
  MODIFY `deliveryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `Employee`
--
ALTER TABLE `Employee`
  MODIFY `EmpID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `Factory`
--
ALTER TABLE `Factory`
  MODIFY `FactoryId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `Income`
--
ALTER TABLE `Income`
  MODIFY `incomeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `Land`
--
ALTER TABLE `Land`
  MODIFY `LndID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `OtherWork`
--
ALTER TABLE `OtherWork`
  MODIFY `Work_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `Payment`
--
ALTER TABLE `Payment`
  MODIFY `paymentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `Stock`
--
ALTER TABLE `Stock`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `Tea`
--
ALTER TABLE `Tea`
  MODIFY `Tea_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `TeaRate`
--
ALTER TABLE `TeaRate`
  MODIFY `rateId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `DeliveryTea`
--
ALTER TABLE `DeliveryTea`
  ADD CONSTRAINT `DeliveryTea_ibfk_1` FOREIGN KEY (`deliveryFactoryId`) REFERENCES `Factory` (`FactoryId`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `DeliveryTea_ibfk_2` FOREIGN KEY (`StockId`) REFERENCES `Stock` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `OtherWork`
--
ALTER TABLE `OtherWork`
  ADD CONSTRAINT `OtherWork_ibfk_1` FOREIGN KEY (`Lnd_ID`) REFERENCES `Land` (`LndID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `OtherWork_ibfk_2` FOREIGN KEY (`Emp_ID`) REFERENCES `Employee` (`EmpID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Payment`
--
ALTER TABLE `Payment`
  ADD CONSTRAINT `Payment_ibfk_1` FOREIGN KEY (`empId`) REFERENCES `Employee` (`EmpID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Payment_ibfk_4` FOREIGN KEY (`rateId`) REFERENCES `TeaRate` (`rateId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Tea`
--
ALTER TABLE `Tea`
  ADD CONSTRAINT `Tea_ibfk_1` FOREIGN KEY (`Emp_ID`) REFERENCES `Employee` (`EmpID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Tea_ibfk_2` FOREIGN KEY (`Lnd_ID`) REFERENCES `Land` (`LndID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
