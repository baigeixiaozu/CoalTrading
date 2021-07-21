-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主机： 106.52.202.68
-- 生成日期： 2021-07-21 03:41:27
-- 服务器版本： 8.0.24
-- PHP 版本： 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `coal_trading`
--
CREATE DATABASE IF NOT EXISTS `coal_trading` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `coal_trading`;

-- --------------------------------------------------------

--
-- 表的结构 `ct_news`
--

DROP TABLE IF EXISTS `ct_news`;
CREATE TABLE `ct_news` (
  `nid` int NOT NULL COMMENT '新闻id',
  `title` int NOT NULL COMMENT '新闻标题',
  `content` int NOT NULL COMMENT '新闻内容',
  `created_time` int NOT NULL COMMENT '创建日期',
  `status` int NOT NULL COMMENT '新闻状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 插入之前先把表清空（truncate） `ct_news`
--

TRUNCATE TABLE `ct_news`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_userext`
--

DROP TABLE IF EXISTS `ct_userext`;
CREATE TABLE `ct_userext` (
  `user_id` bigint NOT NULL,
  `ver` varchar(1) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ext` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 插入之前先把表清空（truncate） `ct_userext`
--

TRUNCATE TABLE `ct_userext`;
--
-- 转存表中的数据 `ct_userext`
--

INSERT INTO `ct_userext` (`user_id`, `ver`, `ext`) VALUES
(1, '1', '{\r\n \"type\": \"superadmin\"\r\n}');

-- --------------------------------------------------------

--
-- 表的结构 `ct_users`
--

DROP TABLE IF EXISTS `ct_users`;
CREATE TABLE `ct_users` (
  `user_id` bigint NOT NULL,
  `user_login` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户登录名',
  `user_pass` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `user_status` int NOT NULL COMMENT '用户状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- 插入之前先把表清空（truncate） `ct_users`
--

TRUNCATE TABLE `ct_users`;
--
-- 转存表中的数据 `ct_users`
--

INSERT INTO `ct_users` (`user_id`, `user_login`, `user_pass`, `user_status`) VALUES
(1, 'superadmin', '123456', 1);

--
-- 转储表的索引
--

--
-- 表的索引 `ct_userext`
--
ALTER TABLE `ct_userext`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- 表的索引 `ct_users`
--
ALTER TABLE `ct_users`
  ADD PRIMARY KEY (`user_id`) USING BTREE,
  ADD UNIQUE KEY `user_login_2` (`user_login`),
  ADD KEY `user_login` (`user_login`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `ct_users`
--
ALTER TABLE `ct_users`
  MODIFY `user_id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- 限制导出的表
--

--
-- 限制表 `ct_userext`
--
ALTER TABLE `ct_userext`
  ADD CONSTRAINT `ct_userext_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
