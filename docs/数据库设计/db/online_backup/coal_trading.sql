-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主机： 106.52.202.68
-- 生成日期： 2021-07-24 09:02:47
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
-- 表的结构 `ct_fund`
--

DROP TABLE IF EXISTS `ct_fund`;
CREATE TABLE `ct_fund` (
  `user_id` bigint NOT NULL COMMENT '财务用户ID',
  `com_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '汇款单位名称',
  `bank_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开户行名称',
  `bank_acc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行账号',
  `fund_count` decimal(10,0) DEFAULT NULL COMMENT '账户金额',
  `fund_freeze` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '冻结金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='财务用户资金账户表';

--
-- 插入之前先把表清空（truncate） `ct_fund`
--

TRUNCATE TABLE `ct_fund`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_fund_log`
--

DROP TABLE IF EXISTS `ct_fund_log`;
CREATE TABLE `ct_fund_log` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `log_date` datetime DEFAULT NULL COMMENT '变动时间',
  `log_type` int DEFAULT NULL COMMENT '变动类型',
  `fund_count` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '变动金额',
  `log_verify` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易凭证'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='财务账户变动记录表';

--
-- 插入之前先把表清空（truncate） `ct_fund_log`
--

TRUNCATE TABLE `ct_fund_log`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_grant`
--

DROP TABLE IF EXISTS `ct_grant`;
CREATE TABLE `ct_grant` (
  `grant_id` bigint NOT NULL COMMENT '权限ID',
  `grant_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

--
-- 插入之前先把表清空（truncate） `ct_grant`
--

TRUNCATE TABLE `ct_grant`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_order`
--

DROP TABLE IF EXISTS `ct_order`;
CREATE TABLE `ct_order` (
  `order_id` bigint NOT NULL,
  `req_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `order_create_date` datetime DEFAULT NULL,
  `order_status` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

--
-- 插入之前先把表清空（truncate） `ct_order`
--

TRUNCATE TABLE `ct_order`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_request`
--

DROP TABLE IF EXISTS `ct_request`;
CREATE TABLE `ct_request` (
  `req_id` bigint NOT NULL COMMENT '需求ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `req_start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `req_end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `req_type` int DEFAULT NULL COMMENT '需求类型',
  `req_status` int DEFAULT NULL COMMENT '需求状态',
  `req_detail` text COLLATE utf8mb4_unicode_ci COMMENT '需求详情（JSON）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='需求表';

--
-- 插入之前先把表清空（truncate） `ct_request`
--

TRUNCATE TABLE `ct_request`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_role`
--

DROP TABLE IF EXISTS `ct_role`;
CREATE TABLE `ct_role` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

--
-- 插入之前先把表清空（truncate） `ct_role`
--

TRUNCATE TABLE `ct_role`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_role_grant_relation`
--

DROP TABLE IF EXISTS `ct_role_grant_relation`;
CREATE TABLE `ct_role_grant_relation` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `grant_id` bigint NOT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限表';

--
-- 插入之前先把表清空（truncate） `ct_role_grant_relation`
--

TRUNCATE TABLE `ct_role_grant_relation`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_userext`
--

DROP TABLE IF EXISTS `ct_userext`;
CREATE TABLE `ct_userext` (
  `user_id` bigint NOT NULL,
  `ext_ver` int DEFAULT NULL,
  `ext_info` text COLLATE utf8mb4_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交易用户拓展表';

--
-- 插入之前先把表清空（truncate） `ct_userext`
--

TRUNCATE TABLE `ct_userext`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_users`
--

DROP TABLE IF EXISTS `ct_users`;
CREATE TABLE `ct_users` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_pass` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `user_registered` datetime DEFAULT NULL COMMENT '创建时间',
  `user_status` int DEFAULT NULL COMMENT '用户状态'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表\r\n';

--
-- 插入之前先把表清空（truncate） `ct_users`
--

TRUNCATE TABLE `ct_users`;
-- --------------------------------------------------------

--
-- 表的结构 `ct_user_role_relation`
--

DROP TABLE IF EXISTS `ct_user_role_relation`;
CREATE TABLE `ct_user_role_relation` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色表';

--
-- 插入之前先把表清空（truncate） `ct_user_role_relation`
--

TRUNCATE TABLE `ct_user_role_relation`;
-- --------------------------------------------------------

--
-- 表的结构 `news`
--

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `news_id` int NOT NULL COMMENT '资讯ID',
  `news_title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资讯标题',
  `news_content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资讯内容',
  `news_created_date` datetime DEFAULT NULL COMMENT '资讯创建时间',
  `news_status` int DEFAULT NULL COMMENT '资讯状态',
  `news_author` bigint DEFAULT NULL COMMENT '资讯编写员',
  `news_auditor` bigint DEFAULT NULL COMMENT '资讯审核员'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新闻资讯表';

--
-- 插入之前先把表清空（truncate） `news`
--

TRUNCATE TABLE `news`;
--
-- 转储表的索引
--

--
-- 表的索引 `ct_fund`
--
ALTER TABLE `ct_fund`
  ADD PRIMARY KEY (`user_id`);

--
-- 表的索引 `ct_fund_log`
--
ALTER TABLE `ct_fund_log`
  ADD PRIMARY KEY (`user_id`);

--
-- 表的索引 `ct_grant`
--
ALTER TABLE `ct_grant`
  ADD PRIMARY KEY (`grant_id`);

--
-- 表的索引 `ct_order`
--
ALTER TABLE `ct_order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk_ct_order_ct_users_1` (`user_id`),
  ADD KEY `fk_ct_order_ct_request_1` (`req_id`);

--
-- 表的索引 `ct_request`
--
ALTER TABLE `ct_request`
  ADD PRIMARY KEY (`req_id`),
  ADD KEY `fk_ct_request_ct_users_1` (`user_id`);

--
-- 表的索引 `ct_role`
--
ALTER TABLE `ct_role`
  ADD PRIMARY KEY (`role_id`);

--
-- 表的索引 `ct_role_grant_relation`
--
ALTER TABLE `ct_role_grant_relation`
  ADD KEY `fk_ct_role_grant_relation_ct_role_1` (`role_id`),
  ADD KEY `fk_ct_role_grant_relation_ct_grant_1` (`grant_id`);

--
-- 表的索引 `ct_userext`
--
ALTER TABLE `ct_userext`
  ADD PRIMARY KEY (`user_id`);

--
-- 表的索引 `ct_users`
--
ALTER TABLE `ct_users`
  ADD PRIMARY KEY (`user_id`);

--
-- 表的索引 `ct_user_role_relation`
--
ALTER TABLE `ct_user_role_relation`
  ADD KEY `fk_ct_user_role_relationships_ct_users_1` (`user_id`),
  ADD KEY `fk_ct_user_role_relationships_ct_role_1` (`role_id`);

--
-- 表的索引 `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`news_id`),
  ADD KEY `fk_news_ct_users_1` (`news_author`),
  ADD KEY `fk_news_ct_users_2` (`news_auditor`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `news`
--
ALTER TABLE `news`
  MODIFY `news_id` int NOT NULL AUTO_INCREMENT COMMENT '资讯ID';

--
-- 限制导出的表
--

--
-- 限制表 `ct_fund`
--
ALTER TABLE `ct_fund`
  ADD CONSTRAINT `fk_ct_fund_ct_users_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`);

--
-- 限制表 `ct_fund_log`
--
ALTER TABLE `ct_fund_log`
  ADD CONSTRAINT `fk_ct_fund_log_ct_users_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`);

--
-- 限制表 `ct_order`
--
ALTER TABLE `ct_order`
  ADD CONSTRAINT `fk_ct_order_ct_request_1` FOREIGN KEY (`req_id`) REFERENCES `ct_request` (`req_id`),
  ADD CONSTRAINT `fk_ct_order_ct_users_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`);

--
-- 限制表 `ct_request`
--
ALTER TABLE `ct_request`
  ADD CONSTRAINT `fk_ct_request_ct_users_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`);

--
-- 限制表 `ct_role_grant_relation`
--
ALTER TABLE `ct_role_grant_relation`
  ADD CONSTRAINT `fk_ct_role_grant_relation_ct_grant_1` FOREIGN KEY (`grant_id`) REFERENCES `ct_grant` (`grant_id`),
  ADD CONSTRAINT `fk_ct_role_grant_relation_ct_role_1` FOREIGN KEY (`role_id`) REFERENCES `ct_role` (`role_id`);

--
-- 限制表 `ct_userext`
--
ALTER TABLE `ct_userext`
  ADD CONSTRAINT `fk_ct_userext_ct_users_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`);

--
-- 限制表 `ct_user_role_relation`
--
ALTER TABLE `ct_user_role_relation`
  ADD CONSTRAINT `fk_ct_user_role_relationships_ct_role_1` FOREIGN KEY (`role_id`) REFERENCES `ct_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ct_user_role_relationships_ct_users_1` FOREIGN KEY (`user_id`) REFERENCES `ct_users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- 限制表 `news`
--
ALTER TABLE `news`
  ADD CONSTRAINT `fk_news_ct_users_1` FOREIGN KEY (`news_author`) REFERENCES `ct_users` (`user_id`),
  ADD CONSTRAINT `fk_news_ct_users_2` FOREIGN KEY (`news_auditor`) REFERENCES `ct_users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
