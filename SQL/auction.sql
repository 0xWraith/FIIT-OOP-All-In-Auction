-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Май 13 2022 г., 00:21
-- Версия сервера: 10.3.34-MariaDB-0+deb10u1-log
-- Версия PHP: 7.3.31-1~deb10u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `gs19188`
--

-- --------------------------------------------------------

--
-- Структура таблицы `accounts`
--

CREATE TABLE `accounts` (
  `ID` int(11) NOT NULL,
  `Login` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FirstName` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LastName` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Mail` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Hash` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Salt` varchar(17) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Balance` bigint(20) NOT NULL DEFAULT 0,
  `Admin` tinyint(1) NOT NULL DEFAULT 0,
  `Sex` tinyint(1) NOT NULL,
  `AccountType` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `accounts`
--

INSERT INTO `accounts` (`ID`, `Login`, `FirstName`, `LastName`, `Mail`, `Hash`, `Salt`, `Balance`, `Admin`, `Sex`, `AccountType`) VALUES
(1, '0xAdmin', 'Dmytro', 'Admin', 'admin@stuba.sk', 'admin', 'fsehfse', 0, 0, 0, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `categories`
--

CREATE TABLE `categories` (
  `ID` int(11) NOT NULL,
  `Name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `items`
--

CREATE TABLE `items` (
  `ID` int(11) NOT NULL,
  `Name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Description` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL,
  `imgURL` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `startingPrice` int(11) NOT NULL,
  `currentPrice` int(11) NOT NULL DEFAULT 0,
  `ownerID` int(11) NOT NULL,
  `newOwnerID` int(11) DEFAULT -1,
  `Category` int(11) DEFAULT NULL,
  `Accepted` int(11) DEFAULT 0,
  `Started` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `notifications`
--

CREATE TABLE `notifications` (
  `ID` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `Message` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Date` datetime NOT NULL DEFAULT current_timestamp(),
  `Read` smallint(6) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `accounts`
--
ALTER TABLE `accounts`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `categories`
--
ALTER TABLE `categories`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `items`
--
ALTER TABLE `items`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `notifications`
--
ALTER TABLE `notifications`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
