-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Jul 2015 um 15:25
-- Server-Version: 5.6.24
-- PHP-Version: 5.5.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `mytrade`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `orderID` bigint(20) unsigned NOT NULL,
  `userFK` bigint(20) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `stockFK` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price_per_share` double NOT NULL,
  `typeFK` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `order_types`
--

CREATE TABLE IF NOT EXISTS `order_types` (
  `typeID` bigint(20) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `order_types`
--

INSERT INTO `order_types` (`typeID`, `type`) VALUES
(1, 'Kauf'),
(2, 'Verkauf');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `stock_data`
--

CREATE TABLE IF NOT EXISTS `stock_data` (
  `stockID` bigint(20) NOT NULL,
  `symbol` char(4) NOT NULL,
  `name` varchar(30) NOT NULL,
  `nominal_price` double NOT NULL,
  `last_dividend` double NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `stock_pool`
--

CREATE TABLE IF NOT EXISTS `stock_pool` (
  `stock_poolID` bigint(20) NOT NULL,
  `stockFK` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `ownerFK` bigint(20) NOT NULL,
  `orderFK` bigint(20) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `userID` bigint(20) NOT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` char(40) NOT NULL COMMENT 'SHA1 hashed password 40 chars',
  `administrator` tinyint(1) NOT NULL DEFAULT '0',
  `account_balance` double DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`userID`, `firstname`, `lastname`, `username`, `password`, `administrator`, `account_balance`) VALUES
(1, 'Siro', 'Duschletta', 't137', '57a908a0a0e5886f60a93c44be96060bcfaf38c5', 1, NULL),
(2, 'Sacha', 'Weiersmüller', 'tj33', 'aba1ccfc224594bfc5be4f072b32d59ca54f6c33', 0, 10000),
(3, 'Sven', 'Gross', 't948', '2b12f8c0b9a31140235d3dc9da1c0587ea7c0860', 0, 10000);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `orders`
--
ALTER TABLE `orders`
  ADD UNIQUE KEY `orderID_UNIQUE` (`orderID`), ADD UNIQUE KEY `orderID` (`orderID`), ADD KEY `fk_orders_order_types1_idx` (`typeFK`), ADD KEY `fk_orders_users1_idx` (`userFK`), ADD KEY `fk_orders_stock_data1_idx` (`stockFK`);

--
-- Indizes für die Tabelle `order_types`
--
ALTER TABLE `order_types`
  ADD PRIMARY KEY (`typeID`), ADD UNIQUE KEY `typeID_UNIQUE` (`typeID`), ADD UNIQUE KEY `type_UNIQUE` (`type`);

--
-- Indizes für die Tabelle `stock_data`
--
ALTER TABLE `stock_data`
  ADD PRIMARY KEY (`stockID`), ADD UNIQUE KEY `stockID_UNIQUE` (`stockID`);

--
-- Indizes für die Tabelle `stock_pool`
--
ALTER TABLE `stock_pool`
  ADD PRIMARY KEY (`stock_poolID`), ADD UNIQUE KEY `stock_poolID_UNIQUE` (`stock_poolID`), ADD KEY `fk_stock_pool_stock_data1_idx` (`stockFK`), ADD KEY `fk_stock_pool_users1_idx` (`ownerFK`), ADD KEY `fk_stock_pool_orders1_idx` (`orderFK`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`), ADD UNIQUE KEY `userID_UNIQUE` (`userID`), ADD UNIQUE KEY `username_UNIQUE` (`username`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `orders`
--
ALTER TABLE `orders`
  MODIFY `orderID` bigint(20) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT für Tabelle `order_types`
--
ALTER TABLE `order_types`
  MODIFY `typeID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `stock_data`
--
ALTER TABLE `stock_data`
  MODIFY `stockID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT für Tabelle `stock_pool`
--
ALTER TABLE `stock_pool`
  MODIFY `stock_poolID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=56;
--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `userID` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `stock_pool`
--
ALTER TABLE `stock_pool`
ADD CONSTRAINT `fk_stock_pool_stock_data1` FOREIGN KEY (`stockFK`) REFERENCES `stock_data` (`stockID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_stock_pool_users1` FOREIGN KEY (`ownerFK`) REFERENCES `users` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
