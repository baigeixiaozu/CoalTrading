/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/8/11 21:37:18                           */
/*==============================================================*/


# HEADER
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; # 自增设置
SET FOREIGN_KEY_CHECKS=0;  # 取消外键约束


drop database if exists coal_trading;

/*==============================================================*/
/* Database: coal_trading                                       */
/*==============================================================*/
create database coal_trading;

use coal_trading;

/*==============================================================*/
/* Table: ct_company                                            */
/*==============================================================*/
create table ct_company
(
   user_id              bigint(20) not null comment '用户ID',
   com_name             varchar(20) comment '企业名称',
   com_intro            text comment '企业介绍',
   legal_name           varchar(20) comment '法人代表',
   legal_id             varchar(20) comment '法人身份证号',
   legal_id_file        varchar(255) comment '法人身份证（文件）',
   com_addr             varchar(20) comment '注册地区',
   com_contact          varchar(20) comment '联系电话',
   com_zip              varchar(20) comment '邮政编码',
   business_license_id  varchar(20) comment '营业执照号',
   business_license_file varchar(255) comment '营业执照（文件）',
   manage_license_id    varchar(20) comment '经营许可证编号',
   fax                  varchar(20) comment '传真',
   registered_capital   varchar(20) comment '注册资金（万元）',
   oib_code             varchar(20) comment '组织机构代码',
   oib_code_file        varchar(255) comment '组织机构代码证（文件）',
   tr_cert              varchar(20) comment '税务登记证代码',
   tr_cert_file         varchar(255) comment '税务登记证（文件）',
   manage_license_file  varchar(255) comment '煤炭经营许可证（文件）[供应商]',
   coal_store_site      varchar(255) comment '煤源存放地点[供应商]',
   coal_quantity        bigint comment '煤源数量[供应商]',
   coal_quality         varchar(10) comment '煤源质量[供应商]',
   coal_transport       varchar(20) comment '运输方式及保障能力[供应商]',
   status               enum('1','2','3') not null default '1' comment '信息状态
            1. 不可用
            2. 审核阶段
            3. 可用',
   audit_opinion        varchar(255) comment '审核意见',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_company comment '企业基本信息表';

/*==============================================================*/
/* Index: Index_userid                                          */
/*==============================================================*/
create unique index Index_userid on ct_company
(
   user_id
);

/*==============================================================*/
/* Table: ct_finance                                            */
/*==============================================================*/
create table ct_finance
(
   main_userid          bigint(20) not null comment '交易用户ID',
   finance_userid       bigint(20) default NULL comment '财务用户ID',
   com_name             varchar(20) comment '汇款单位名称',
   bank_name            varchar(20) comment '开户行名称',
   bank_acc             varchar(19) comment '银行账号',
   balance              decimal(10,2) comment '账户金额',
   freeze               decimal(10,2) comment '报价冻结金额',
   ao_permit_file       varchar(255) comment '开户许可证（文件）',
   status               enum('1','2','3') not null default '1' comment '信息状态
            1. 不可用
            2. 审核阶段
            3. 可用',
   audit_opinion        varchar(255) comment '审核意见',
   primary key (main_userid)
)
engine = InnoDB;

alter table ct_finance comment '企业财务账户表';

INSERT INTO ct_finance
(`main_userid`, `finance_userid`, `com_name`, `bank_name`, `bank_acc`, `balance`, `freeze`, `ao_permit_file`) 
VALUES
(7, 8 , "供应商公司1", "银行1", "123456789", 10000.00, 5000.55, "path/to/file"),
(9, 10, "采购商公司2", "银行1", "123456789", 10000.00, 5000.55, "path/to/file");

/*==============================================================*/
/* Index: Index_main_userid                                     */
/*==============================================================*/
create unique index Index_main_userid on ct_finance
(
   main_userid
);

/*==============================================================*/
/* Index: Index_finance_userid                                  */
/*==============================================================*/
create unique index Index_finance_userid on ct_finance
(
   finance_userid
);

/*==============================================================*/
/* Table: ct_finance_log                                        */
/*==============================================================*/
create table ct_finance_log
(
   user_id              bigint(20) not null comment '财务用户ID',
   date                 datetime default CURRENT_TIMESTAMP comment '变动时间',
   type                 enum('1','2','3') comment '变动操作：
            1. 预存（增加）
            2. 缴纳给平台（冻结）
            3. 平台扣除指定额度的冻结款项（减少）
            ',
   quantity             decimal(10,2) comment '金额数量',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_finance_log comment '资金变动记录表';

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create index Index_user_id on ct_finance_log
(
   user_id
);

/*==============================================================*/
/* Index: Index_log_type                                        */
/*==============================================================*/
create index Index_log_type on ct_finance_log
(
   type
);

/*==============================================================*/
/* Table: ct_finance_store                                      */
/*==============================================================*/
create table ct_finance_store
(
   id                   bigint(20) not null auto_increment comment '预存ID',
   user_id              bigint(20) not null comment '财务用户ID',
   date                 datetime default CURRENT_TIMESTAMP comment '变动时间',
   quantity             decimal(10,2) comment '金额数量',
   cert                 varchar(255) comment '交易凭证（文件）',
   status               enum('1','2','3') comment '预存状态：
            1. 待审核
            2. 驳回（审核不通过）
            3. 审核通过',
   primary key (id)
)
engine = InnoDB;

alter table ct_finance_store comment '资金预存表';

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create index Index_user_id on ct_finance_store
(
   user_id
);

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   id                   bigint(20) not null auto_increment comment '新闻ID',
   title                varchar(20) not null comment '新闻标题',
   content              text not null comment '内容',
   date                 datetime default CURRENT_TIMESTAMP comment '创建时间',
   status               enum('1','2','3','4','5') not null comment '状态：
            1. 草稿
            2. 审核中
            3. 驳回（审核不通过）
            4. 发布
            5. 撤销（隐藏）
            删除（记录直接没了）',
   author_id            bigint(20) not null comment '编写人员',
   auditor_id           bigint(20) comment '审核人员',
   audit_opinion        varchar(255) comment '审核意见',
   primary key (id)
)
engine = InnoDB;

alter table ct_news comment '新闻资讯表';

INSERT INTO ct_news(title, content, status, author_id, auditor_id)
VALUES
("草稿资讯0", "草稿资讯的内容0", 1, 2, NULL),
("草稿资讯1", "草稿资讯的内容1", 1, 2, NULL),
("草稿资讯2", "草稿资讯的内容2", 1, 2, NULL),
("草稿资讯3", "草稿资讯的内容3", 1, 2, NULL),
("草稿资讯4", "草稿资讯的内容4", 1, 2, NULL),
("草稿资讯5", "草稿资讯的内容5", 1, 2, NULL),
("草稿资讯6", "草稿资讯的内容6", 1, 2, NULL),
("草稿资讯7", "草稿资讯的内容7", 1, 2, NULL),
("草稿资讯8", "草稿资讯的内容8", 1, 2, NULL),
("草稿资讯9", "草稿资讯的内容9", 1, 2, NULL),
("草稿资讯10", "草稿资讯的内容10", 1, 2, NULL),
("草稿资讯11", "草稿资讯的内容11", 1, 2, NULL),
("草稿资讯12", "草稿资讯的内容12", 1, 2, NULL),
("草稿资讯13", "草稿资讯的内容13", 1, 2, NULL),
("草稿资讯14", "草稿资讯的内容14", 1, 2, NULL),
("草稿资讯15", "草稿资讯的内容15", 1, 2, NULL),
("草稿资讯16", "草稿资讯的内容16", 1, 2, NULL),
("草稿资讯17", "草稿资讯的内容17", 1, 2, NULL),
("草稿资讯18", "草稿资讯的内容18", 1, 2, NULL),
("草稿资讯19", "草稿资讯的内容19", 1, 2, NULL),
("草稿资讯20", "草稿资讯的内容20", 1, 2, NULL),
("草稿资讯21", "草稿资讯的内容21", 1, 2, NULL),
("草稿资讯22", "草稿资讯的内容22", 1, 2, NULL),
("草稿资讯23", "草稿资讯的内容23", 1, 2, NULL),
("草稿资讯24", "草稿资讯的内容24", 1, 2, NULL),
("草稿资讯25", "草稿资讯的内容25", 1, 2, NULL),
("草稿资讯26", "草稿资讯的内容26", 1, 2, NULL),
("草稿资讯27", "草稿资讯的内容27", 1, 2, NULL),
("草稿资讯28", "草稿资讯的内容28", 1, 2, NULL),
("草稿资讯29", "草稿资讯的内容29", 1, 2, NULL),
("草稿资讯30", "草稿资讯的内容30", 1, 2, NULL),
("草稿资讯31", "草稿资讯的内容31", 1, 2, NULL),
("草稿资讯32", "草稿资讯的内容32", 1, 2, NULL),
("草稿资讯33", "草稿资讯的内容33", 1, 2, NULL),
("草稿资讯34", "草稿资讯的内容34", 1, 2, NULL),
("草稿资讯35", "草稿资讯的内容35", 1, 2, NULL),
("草稿资讯36", "草稿资讯的内容36", 1, 2, NULL),
("草稿资讯37", "草稿资讯的内容37", 1, 2, NULL),
("草稿资讯38", "草稿资讯的内容38", 1, 2, NULL),
("草稿资讯39", "草稿资讯的内容39", 1, 2, NULL),
("草稿资讯40", "草稿资讯的内容40", 1, 2, NULL),
("草稿资讯41", "草稿资讯的内容41", 1, 2, NULL),
("草稿资讯42", "草稿资讯的内容42", 1, 2, NULL),
("草稿资讯43", "草稿资讯的内容43", 1, 2, NULL),
("草稿资讯44", "草稿资讯的内容44", 1, 2, NULL),
("草稿资讯45", "草稿资讯的内容45", 1, 2, NULL),
("草稿资讯46", "草稿资讯的内容46", 1, 2, NULL),
("草稿资讯47", "草稿资讯的内容47", 1, 2, NULL),
("草稿资讯48", "草稿资讯的内容48", 1, 2, NULL),
("草稿资讯49", "草稿资讯的内容49", 1, 2, NULL),
("草稿资讯50", "草稿资讯的内容50", 1, 2, NULL),
("草稿资讯51", "草稿资讯的内容51", 1, 2, NULL),
("草稿资讯52", "草稿资讯的内容52", 1, 2, NULL),
("草稿资讯53", "草稿资讯的内容53", 1, 2, NULL),
("草稿资讯54", "草稿资讯的内容54", 1, 2, NULL),
("草稿资讯55", "草稿资讯的内容55", 1, 2, NULL),
("草稿资讯56", "草稿资讯的内容56", 1, 2, NULL),
("草稿资讯57", "草稿资讯的内容57", 1, 2, NULL),
("草稿资讯58", "草稿资讯的内容58", 1, 2, NULL),
("草稿资讯59", "草稿资讯的内容59", 1, 2, NULL),
("草稿资讯60", "草稿资讯的内容60", 1, 2, NULL),
("草稿资讯61", "草稿资讯的内容61", 1, 2, NULL),
("草稿资讯62", "草稿资讯的内容62", 1, 2, NULL),
("草稿资讯63", "草稿资讯的内容63", 1, 2, NULL),
("草稿资讯64", "草稿资讯的内容64", 1, 2, NULL),
("草稿资讯65", "草稿资讯的内容65", 1, 2, NULL),
("草稿资讯66", "草稿资讯的内容66", 1, 2, NULL),
("草稿资讯67", "草稿资讯的内容67", 1, 2, NULL),
("草稿资讯68", "草稿资讯的内容68", 1, 2, NULL),
("草稿资讯69", "草稿资讯的内容69", 1, 2, NULL),
("草稿资讯70", "草稿资讯的内容70", 1, 2, NULL),
("草稿资讯71", "草稿资讯的内容71", 1, 2, NULL),
("草稿资讯72", "草稿资讯的内容72", 1, 2, NULL),
("草稿资讯73", "草稿资讯的内容73", 1, 2, NULL),
("草稿资讯74", "草稿资讯的内容74", 1, 2, NULL),
("草稿资讯75", "草稿资讯的内容75", 1, 2, NULL),
("草稿资讯76", "草稿资讯的内容76", 1, 2, NULL),
("草稿资讯77", "草稿资讯的内容77", 1, 2, NULL),
("草稿资讯78", "草稿资讯的内容78", 1, 2, NULL),
("草稿资讯79", "草稿资讯的内容79", 1, 2, NULL),
("草稿资讯80", "草稿资讯的内容80", 1, 2, NULL),
("草稿资讯81", "草稿资讯的内容81", 1, 2, NULL),
("草稿资讯82", "草稿资讯的内容82", 1, 2, NULL),
("草稿资讯83", "草稿资讯的内容83", 1, 2, NULL),
("草稿资讯84", "草稿资讯的内容84", 1, 2, NULL),
("草稿资讯85", "草稿资讯的内容85", 1, 2, NULL),
("草稿资讯86", "草稿资讯的内容86", 1, 2, NULL),
("草稿资讯87", "草稿资讯的内容87", 1, 2, NULL),
("草稿资讯88", "草稿资讯的内容88", 1, 2, NULL),
("草稿资讯89", "草稿资讯的内容89", 1, 2, NULL),
("草稿资讯90", "草稿资讯的内容90", 1, 2, NULL),
("草稿资讯91", "草稿资讯的内容91", 1, 2, NULL),
("草稿资讯92", "草稿资讯的内容92", 1, 2, NULL),
("草稿资讯93", "草稿资讯的内容93", 1, 2, NULL),
("草稿资讯94", "草稿资讯的内容94", 1, 2, NULL),
("草稿资讯95", "草稿资讯的内容95", 1, 2, NULL),
("草稿资讯96", "草稿资讯的内容96", 1, 2, NULL),
("草稿资讯97", "草稿资讯的内容97", 1, 2, NULL),
("草稿资讯98", "草稿资讯的内容98", 1, 2, NULL),
("草稿资讯99", "草稿资讯的内容99", 1, 2, NULL),

("审核中资讯0", "审核中资讯的内容0", 2, 2, 3),
("审核中资讯1", "审核中资讯的内容1", 2, 2, 3),
("审核中资讯2", "审核中资讯的内容2", 2, 2, 3),
("审核中资讯3", "审核中资讯的内容3", 2, 2, 3),
("审核中资讯4", "审核中资讯的内容4", 2, 2, 3),
("审核中资讯5", "审核中资讯的内容5", 2, 2, 3),
("审核中资讯6", "审核中资讯的内容6", 2, 2, 3),
("审核中资讯7", "审核中资讯的内容7", 2, 2, 3),
("审核中资讯8", "审核中资讯的内容8", 2, 2, 3),
("审核中资讯9", "审核中资讯的内容9", 2, 2, 3),
("审核中资讯10", "审核中资讯的内容10", 2, 2, 3),
("审核中资讯11", "审核中资讯的内容11", 2, 2, 3),
("审核中资讯12", "审核中资讯的内容12", 2, 2, 3),
("审核中资讯13", "审核中资讯的内容13", 2, 2, 3),
("审核中资讯14", "审核中资讯的内容14", 2, 2, 3),
("审核中资讯15", "审核中资讯的内容15", 2, 2, 3),
("审核中资讯16", "审核中资讯的内容16", 2, 2, 3),
("审核中资讯17", "审核中资讯的内容17", 2, 2, 3),
("审核中资讯18", "审核中资讯的内容18", 2, 2, 3),
("审核中资讯19", "审核中资讯的内容19", 2, 2, 3),
("审核中资讯20", "审核中资讯的内容20", 2, 2, 3),
("审核中资讯21", "审核中资讯的内容21", 2, 2, 3),
("审核中资讯22", "审核中资讯的内容22", 2, 2, 3),
("审核中资讯23", "审核中资讯的内容23", 2, 2, 3),
("审核中资讯24", "审核中资讯的内容24", 2, 2, 3),
("审核中资讯25", "审核中资讯的内容25", 2, 2, 3),
("审核中资讯26", "审核中资讯的内容26", 2, 2, 3),
("审核中资讯27", "审核中资讯的内容27", 2, 2, 3),
("审核中资讯28", "审核中资讯的内容28", 2, 2, 3),
("审核中资讯29", "审核中资讯的内容29", 2, 2, 3),
("审核中资讯30", "审核中资讯的内容30", 2, 2, 3),
("审核中资讯31", "审核中资讯的内容31", 2, 2, 3),
("审核中资讯32", "审核中资讯的内容32", 2, 2, 3),
("审核中资讯33", "审核中资讯的内容33", 2, 2, 3),
("审核中资讯34", "审核中资讯的内容34", 2, 2, 3),
("审核中资讯35", "审核中资讯的内容35", 2, 2, 3),
("审核中资讯36", "审核中资讯的内容36", 2, 2, 3),
("审核中资讯37", "审核中资讯的内容37", 2, 2, 3),
("审核中资讯38", "审核中资讯的内容38", 2, 2, 3),
("审核中资讯39", "审核中资讯的内容39", 2, 2, 3),
("审核中资讯40", "审核中资讯的内容40", 2, 2, 3),
("审核中资讯41", "审核中资讯的内容41", 2, 2, 3),
("审核中资讯42", "审核中资讯的内容42", 2, 2, 3),
("审核中资讯43", "审核中资讯的内容43", 2, 2, 3),
("审核中资讯44", "审核中资讯的内容44", 2, 2, 3),
("审核中资讯45", "审核中资讯的内容45", 2, 2, 3),
("审核中资讯46", "审核中资讯的内容46", 2, 2, 3),
("审核中资讯47", "审核中资讯的内容47", 2, 2, 3),
("审核中资讯48", "审核中资讯的内容48", 2, 2, 3),
("审核中资讯49", "审核中资讯的内容49", 2, 2, 3),

("审核驳回资讯0", "审核驳回资讯的内容0", 3, 2, 3),
("审核驳回资讯1", "审核驳回资讯的内容1", 3, 2, 3),
("审核驳回资讯2", "审核驳回资讯的内容2", 3, 2, 3),
("审核驳回资讯3", "审核驳回资讯的内容3", 3, 2, 3),
("审核驳回资讯4", "审核驳回资讯的内容4", 3, 2, 3),
("审核驳回资讯5", "审核驳回资讯的内容5", 3, 2, 3),
("审核驳回资讯6", "审核驳回资讯的内容6", 3, 2, 3),
("审核驳回资讯7", "审核驳回资讯的内容7", 3, 2, 3),
("审核驳回资讯8", "审核驳回资讯的内容8", 3, 2, 3),
("审核驳回资讯9", "审核驳回资讯的内容9", 3, 2, 3),
("审核驳回资讯10", "审核驳回资讯的内容10", 3, 2, 3),
("审核驳回资讯11", "审核驳回资讯的内容11", 3, 2, 3),
("审核驳回资讯12", "审核驳回资讯的内容12", 3, 2, 3),
("审核驳回资讯13", "审核驳回资讯的内容13", 3, 2, 3),
("审核驳回资讯14", "审核驳回资讯的内容14", 3, 2, 3),
("审核驳回资讯15", "审核驳回资讯的内容15", 3, 2, 3),
("审核驳回资讯16", "审核驳回资讯的内容16", 3, 2, 3),
("审核驳回资讯17", "审核驳回资讯的内容17", 3, 2, 3),
("审核驳回资讯18", "审核驳回资讯的内容18", 3, 2, 3),
("审核驳回资讯19", "审核驳回资讯的内容19", 3, 2, 3),
("审核驳回资讯20", "审核驳回资讯的内容20", 3, 2, 3),
("审核驳回资讯21", "审核驳回资讯的内容21", 3, 2, 3),
("审核驳回资讯22", "审核驳回资讯的内容22", 3, 2, 3),
("审核驳回资讯23", "审核驳回资讯的内容23", 3, 2, 3),
("审核驳回资讯24", "审核驳回资讯的内容24", 3, 2, 3),
("审核驳回资讯25", "审核驳回资讯的内容25", 3, 2, 3),
("审核驳回资讯26", "审核驳回资讯的内容26", 3, 2, 3),
("审核驳回资讯27", "审核驳回资讯的内容27", 3, 2, 3),
("审核驳回资讯28", "审核驳回资讯的内容28", 3, 2, 3),
("审核驳回资讯29", "审核驳回资讯的内容29", 3, 2, 3),
("审核驳回资讯30", "审核驳回资讯的内容30", 3, 2, 3),
("审核驳回资讯31", "审核驳回资讯的内容31", 3, 2, 3),
("审核驳回资讯32", "审核驳回资讯的内容32", 3, 2, 3),
("审核驳回资讯33", "审核驳回资讯的内容33", 3, 2, 3),
("审核驳回资讯34", "审核驳回资讯的内容34", 3, 2, 3),
("审核驳回资讯35", "审核驳回资讯的内容35", 3, 2, 3),
("审核驳回资讯36", "审核驳回资讯的内容36", 3, 2, 3),
("审核驳回资讯37", "审核驳回资讯的内容37", 3, 2, 3),
("审核驳回资讯38", "审核驳回资讯的内容38", 3, 2, 3),
("审核驳回资讯39", "审核驳回资讯的内容39", 3, 2, 3),
("审核驳回资讯40", "审核驳回资讯的内容40", 3, 2, 3),
("审核驳回资讯41", "审核驳回资讯的内容41", 3, 2, 3),
("审核驳回资讯42", "审核驳回资讯的内容42", 3, 2, 3),
("审核驳回资讯43", "审核驳回资讯的内容43", 3, 2, 3),
("审核驳回资讯44", "审核驳回资讯的内容44", 3, 2, 3),
("审核驳回资讯45", "审核驳回资讯的内容45", 3, 2, 3),
("审核驳回资讯46", "审核驳回资讯的内容46", 3, 2, 3),
("审核驳回资讯47", "审核驳回资讯的内容47", 3, 2, 3),
("审核驳回资讯48", "审核驳回资讯的内容48", 3, 2, 3),
("审核驳回资讯49", "审核驳回资讯的内容49", 3, 2, 3),

("发布的资讯0", "发布的资讯的内容0", 4, 2, 3),
("发布的资讯1", "发布的资讯的内容1", 4, 2, 3),
("发布的资讯2", "发布的资讯的内容2", 4, 2, 3),
("发布的资讯3", "发布的资讯的内容3", 4, 2, 3),
("发布的资讯4", "发布的资讯的内容4", 4, 2, 3),
("发布的资讯5", "发布的资讯的内容5", 4, 2, 3),
("发布的资讯6", "发布的资讯的内容6", 4, 2, 3),
("发布的资讯7", "发布的资讯的内容7", 4, 2, 3),
("发布的资讯8", "发布的资讯的内容8", 4, 2, 3),
("发布的资讯9", "发布的资讯的内容9", 4, 2, 3),
("发布的资讯10", "发布的资讯的内容10", 4, 2, 3),
("发布的资讯11", "发布的资讯的内容11", 4, 2, 3),
("发布的资讯12", "发布的资讯的内容12", 4, 2, 3),
("发布的资讯13", "发布的资讯的内容13", 4, 2, 3),
("发布的资讯14", "发布的资讯的内容14", 4, 2, 3),
("发布的资讯15", "发布的资讯的内容15", 4, 2, 3),
("发布的资讯16", "发布的资讯的内容16", 4, 2, 3),
("发布的资讯17", "发布的资讯的内容17", 4, 2, 3),
("发布的资讯18", "发布的资讯的内容18", 4, 2, 3),
("发布的资讯19", "发布的资讯的内容19", 4, 2, 3),
("发布的资讯20", "发布的资讯的内容20", 4, 2, 3),
("发布的资讯21", "发布的资讯的内容21", 4, 2, 3),
("发布的资讯22", "发布的资讯的内容22", 4, 2, 3),
("发布的资讯23", "发布的资讯的内容23", 4, 2, 3),
("发布的资讯24", "发布的资讯的内容24", 4, 2, 3),
("发布的资讯25", "发布的资讯的内容25", 4, 2, 3),
("发布的资讯26", "发布的资讯的内容26", 4, 2, 3),
("发布的资讯27", "发布的资讯的内容27", 4, 2, 3),
("发布的资讯28", "发布的资讯的内容28", 4, 2, 3),
("发布的资讯29", "发布的资讯的内容29", 4, 2, 3),
("发布的资讯30", "发布的资讯的内容30", 4, 2, 3),
("发布的资讯31", "发布的资讯的内容31", 4, 2, 3),
("发布的资讯32", "发布的资讯的内容32", 4, 2, 3),
("发布的资讯33", "发布的资讯的内容33", 4, 2, 3),
("发布的资讯34", "发布的资讯的内容34", 4, 2, 3),
("发布的资讯35", "发布的资讯的内容35", 4, 2, 3),
("发布的资讯36", "发布的资讯的内容36", 4, 2, 3),
("发布的资讯37", "发布的资讯的内容37", 4, 2, 3),
("发布的资讯38", "发布的资讯的内容38", 4, 2, 3),
("发布的资讯39", "发布的资讯的内容39", 4, 2, 3),
("发布的资讯40", "发布的资讯的内容40", 4, 2, 3),
("发布的资讯41", "发布的资讯的内容41", 4, 2, 3),
("发布的资讯42", "发布的资讯的内容42", 4, 2, 3),
("发布的资讯43", "发布的资讯的内容43", 4, 2, 3),
("发布的资讯44", "发布的资讯的内容44", 4, 2, 3),
("发布的资讯45", "发布的资讯的内容45", 4, 2, 3),
("发布的资讯46", "发布的资讯的内容46", 4, 2, 3),
("发布的资讯47", "发布的资讯的内容47", 4, 2, 3),
("发布的资讯48", "发布的资讯的内容48", 4, 2, 3),
("发布的资讯49", "发布的资讯的内容49", 4, 2, 3),
("发布的资讯50", "发布的资讯的内容50", 4, 2, 3),
("发布的资讯51", "发布的资讯的内容51", 4, 2, 3),
("发布的资讯52", "发布的资讯的内容52", 4, 2, 3),
("发布的资讯53", "发布的资讯的内容53", 4, 2, 3),
("发布的资讯54", "发布的资讯的内容54", 4, 2, 3),
("发布的资讯55", "发布的资讯的内容55", 4, 2, 3),
("发布的资讯56", "发布的资讯的内容56", 4, 2, 3),
("发布的资讯57", "发布的资讯的内容57", 4, 2, 3),
("发布的资讯58", "发布的资讯的内容58", 4, 2, 3),
("发布的资讯59", "发布的资讯的内容59", 4, 2, 3),
("发布的资讯60", "发布的资讯的内容60", 4, 2, 3),
("发布的资讯61", "发布的资讯的内容61", 4, 2, 3),
("发布的资讯62", "发布的资讯的内容62", 4, 2, 3),
("发布的资讯63", "发布的资讯的内容63", 4, 2, 3),
("发布的资讯64", "发布的资讯的内容64", 4, 2, 3),
("发布的资讯65", "发布的资讯的内容65", 4, 2, 3),
("发布的资讯66", "发布的资讯的内容66", 4, 2, 3),
("发布的资讯67", "发布的资讯的内容67", 4, 2, 3),
("发布的资讯68", "发布的资讯的内容68", 4, 2, 3),
("发布的资讯69", "发布的资讯的内容69", 4, 2, 3),
("发布的资讯70", "发布的资讯的内容70", 4, 2, 3),
("发布的资讯71", "发布的资讯的内容71", 4, 2, 3),
("发布的资讯72", "发布的资讯的内容72", 4, 2, 3),
("发布的资讯73", "发布的资讯的内容73", 4, 2, 3),
("发布的资讯74", "发布的资讯的内容74", 4, 2, 3),
("发布的资讯75", "发布的资讯的内容75", 4, 2, 3),
("发布的资讯76", "发布的资讯的内容76", 4, 2, 3),
("发布的资讯77", "发布的资讯的内容77", 4, 2, 3),
("发布的资讯78", "发布的资讯的内容78", 4, 2, 3),
("发布的资讯79", "发布的资讯的内容79", 4, 2, 3),
("发布的资讯80", "发布的资讯的内容80", 4, 2, 3),
("发布的资讯81", "发布的资讯的内容81", 4, 2, 3),
("发布的资讯82", "发布的资讯的内容82", 4, 2, 3),
("发布的资讯83", "发布的资讯的内容83", 4, 2, 3),
("发布的资讯84", "发布的资讯的内容84", 4, 2, 3),
("发布的资讯85", "发布的资讯的内容85", 4, 2, 3),
("发布的资讯86", "发布的资讯的内容86", 4, 2, 3),
("发布的资讯87", "发布的资讯的内容87", 4, 2, 3),
("发布的资讯88", "发布的资讯的内容88", 4, 2, 3),
("发布的资讯89", "发布的资讯的内容89", 4, 2, 3),
("发布的资讯90", "发布的资讯的内容90", 4, 2, 3),
("发布的资讯91", "发布的资讯的内容91", 4, 2, 3),
("发布的资讯92", "发布的资讯的内容92", 4, 2, 3),
("发布的资讯93", "发布的资讯的内容93", 4, 2, 3),
("发布的资讯94", "发布的资讯的内容94", 4, 2, 3),
("发布的资讯95", "发布的资讯的内容95", 4, 2, 3),
("发布的资讯96", "发布的资讯的内容96", 4, 2, 3),
("发布的资讯97", "发布的资讯的内容97", 4, 2, 3),
("发布的资讯98", "发布的资讯的内容98", 4, 2, 3),
("发布的资讯99", "发布的资讯的内容99", 4, 2, 3),
("撤销的资讯", "撤销的资讯的内容", 5, 2, 3);

/*==============================================================*/
/* Index: Index_news_id                                         */
/*==============================================================*/
create unique index Index_news_id on ct_news
(
   id
);

/*==============================================================*/
/* Index: Index_news_title                                      */
/*==============================================================*/
create unique index Index_news_title on ct_news
(
   title
);

/*==============================================================*/
/* Table: ct_order                                              */
/*==============================================================*/
create table ct_order
(
   id                   bigint(20) not null auto_increment comment '订单ID',
   num                  varchar(30) not null comment '订单号',
   req_id               bigint(20) comment '需求ID',
   user_id              bigint(20) comment '用户ID',
   created_time         datetime comment '创建时间',
   status               enum('1','2','3','4') not null comment '订单状态：
            1. 进行中
            2. 超时
            3. 完成
            4. 取消',
   primary key (id)
)
engine = InnoDB;

alter table ct_order comment '订单表';

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create index Index_user_id on ct_order
(
   user_id
);

/*==============================================================*/
/* Index: Index_req_id                                          */
/*==============================================================*/
create index Index_req_id on ct_order
(
   req_id
);

/*==============================================================*/
/* Index: Index_order_id                                        */
/*==============================================================*/
create unique index Index_order_id on ct_order
(
   id
);

/*==============================================================*/
/* Index: Index_order_num                                       */
/*==============================================================*/
create unique index Index_order_num on ct_order
(
   num
);

/*==============================================================*/
/* Table: ct_permissions                                        */
/*==============================================================*/
create table ct_permissions
(
   id                   bigint(20) not null comment '权限ID',
   name                 varchar(15) comment '权限名',
   primary key (id)
)
engine = InnoDB;

alter table ct_permissions comment '权限表：
1. 超级管理员
2. 资讯编辑权限
3. 资讯审核权限
4.';

/*
权限：
1. 超级管理员
2. 资讯编辑权限
3. 资讯审核权限
4. 资讯维护权限
5. 注册用户审核权限
6. 交易审核权限
7. 用户创建权限
8. 挂牌权限
9. 挂牌权限
10.财务权限
11.信息编辑权限
*/
INSERT INTO ct_permissions VALUES(1, "SUPER_ADMIN"),
(2, "NEWS_EDITOR"),
(3, "NEWS_AUDITOR"),
(4, "NEWS_MANAGER"),
(5, "USER_REG_AUDITOR"),
(6, "TRADE_AUDITOR"),
(7, "USER_ADD"),
(8, "PUB_SALE"),
(9, "PUB_BUY"),
(10, "FINANCE_MAG");

/*==============================================================*/
/* Index: Index_pri                                             */
/*==============================================================*/
create unique index Index_pri on ct_permissions
(
   id
);

/*==============================================================*/
/* Table: ct_request                                            */
/*==============================================================*/
create table ct_request
(
   id                   bigint(20) not null auto_increment comment '需求ID',
   user_id              bigint(20) comment '用户ID',
   request_company      varchar(100) comment '申请单位',
   request_num          varchar(100) comment '单据编号',
   created_time         datetime default CURRENT_TIMESTAMP comment '创建时间',
   ended_time           datetime comment '结束时间',
   type                 enum('1','2') comment '1. 卖出
            2. 采购',
   status               enum('1','2','3','4','6', '7','8','9','10','11','12') not null comment '需求状态
            1. 草稿
            2. 审核中
            3. 发布
            4. 被成功摘取，隐藏（摘牌方交完保证金）
            6. 完成
            7. 审核不通过（驳回）
            8. 等待上传合同
            9. 合同已上传，等待确认
            10. 合同被拒绝
            11. 合同被确认，同时生成订单
            12. 交易完成',
   detail               json comment '需求信息(JSON)',
   zp_id                bigint(20) comment '最终摘牌信息ID（摘牌表--摘牌ID）',
   contract_file        varchar(255) comment '合同文件（路径）',
   opinion              varchar(255) comment '审核意见',
   deposit              decimal(10,2) comment '挂牌保证金',
   primary key (id)
)
engine = InnoDB;

alter table ct_request comment '挂牌需求表';

/*==============================================================*/
/* Index: Index_req_id                                          */
/*==============================================================*/
create index Index_req_id on ct_request
(
   id
);

/*==============================================================*/
/* Table: ct_role_permission_relationships                      */
/*==============================================================*/
create table ct_role_permission_relationships
(
   role_id              bigint(20) not null comment '角色ID',
   permission_id        bigint(20) not null comment '权限ID',
   primary key (role_id, permission_id)
)
engine = InnoDB;

alter table ct_role_permission_relationships comment '角色权限关联表
1个角色可以对应多个权限';

/*role_id, permission*/
INSERT INTO ct_role_permission_relationships VALUES(1,1),
(1,7),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,8),
(8,9),
(9,10);

/*==============================================================*/
/* Index: Index_role_pri_id                                     */
/*==============================================================*/
create unique index Index_role_pri_id on ct_role_permission_relationships
(
   role_id,
   permission_id
);

/*==============================================================*/
/* Table: ct_user_role_relationships                            */
/*==============================================================*/
create table ct_user_role_relationships
(
   user_id              bigint(20) not null comment '用户ID',
   role_id              bigint(20) not null comment '角色ID',
   primary key (role_id, user_id)
)
engine = InnoDB;

alter table ct_user_role_relationships comment '用户角色关联表';

INSERT INTO ct_user_role_relationships(`user_id`, `role_id`) 
VALUES(1, 1),(2,2),(3,3),(4,4),(5,5),(6,6),(7, 7),(8,9),(9,8),(10,9);

/*==============================================================*/
/* Index: Index_user_role                                       */
/*==============================================================*/
create index Index_user_role on ct_user_role_relationships
(
   role_id
);

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create unique index Index_user_id on ct_user_role_relationships
(
   user_id
);

/*==============================================================*/
/* Table: ct_usermeta                                           */
/*==============================================================*/
create table ct_usermeta
(
   id                   bigint(20) not null auto_increment comment '元数据ID',
   user_id              bigint(20) not null comment '用户ID',
   mkey                 varchar(255) not null comment '键',
   mvalue               longtext comment '值',
   primary key (id)
)
engine = InnoDB;

alter table ct_usermeta comment '用户附加信息表';

/*==============================================================*/
/* Index: Index_userid_key                                      */
/*==============================================================*/
create unique index Index_userid_key on ct_usermeta
(
   user_id,
   mkey
);

/*==============================================================*/
/* Table: ct_userrole                                           */
/*==============================================================*/
create table ct_userrole
(
   id                   bigint(20) not null comment '角色ID',
   mark                 varchar(20) comment '角色标记',
   name                 varchar(20) comment '角色名',
   type                 varchar(10) comment '角色类型',
   primary key (id)
)
engine = InnoDB;

alter table ct_userrole comment '角色表
1. 几种管理员：
     a. 资讯编辑员
     b. 资讯审核员
';

/*
1. 几种管理员：
     a. 超级管理员
     b. 资讯编辑员
     c. 资讯审核员
     d. 资讯维护员
     e. 注册用户审核员
     f. 交易审核员
     
2. 交易用户[供应商,采购商]
3. 财务用户
*/
INSERT INTO ct_userrole(`id`, `name`, `mark`, `type`) VALUES
(1, "超级管理员", "SUPER_ADMIN", "admin"),
(2, "资讯编辑员", "NEWS_EDITOR", "admin"),
(3, "资讯审核员", "NEWS_AUDITOR", "admin"),
(4, "资讯维护员", "NEWS_MANAGER", "admin"),
(5, "注册用户审核员", "USER_REG_AUDITOR", "admin"),
(6, "交易审核员", "TRADE_AUDITOR", "admin"),
(7, "供应商", "USER_SALE", "user"),
(8, "采购商", "USER_BUY", "user"),
(9, "财务用户", "USER_MONEY", "");

/*==============================================================*/
/* Index: Index_role_id                                         */
/*==============================================================*/
create index Index_role_id on ct_userrole
(
   id
);

/*==============================================================*/
/* Index: Index_Role_Type                                       */
/*==============================================================*/
create index Index_Role_Type on ct_userrole
(
   type
);

/*==============================================================*/
/* Table: ct_users                                              */
/*==============================================================*/
create table ct_users
(
   id                   bigint(20) not null auto_increment comment '用户ID（唯一）',
   login                varchar(20) not null comment '登录名（唯一）',
   pass                 varchar(100) comment '用户密码：
            要经过加密',
   nick                 varchar(20) comment '用户昵称',
   email                varchar(50) not null comment '用户邮箱（唯一）',
   registered           datetime default CURRENT_TIMESTAMP comment '创建时间',
   status               enum('1','2') not null default '2' comment '用户状态
            1. 用户未激活
            2. 已激活',
   primary key (id)
)
engine = InnoDB;

alter table ct_users comment '用户表
存储的用户类型：
1. 管理员
2. 交易用户
3. 财务用户
';

INSERT INTO `ct_users` (`id`, `login`, `pass`, `nick`, `email`) 
VALUES 
(1, 'superadmin', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '超级管理员', "1@mail.com"),
(2, 'newseditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '资讯编辑员1', '2@mail.com'),
(3, 'newsauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '资讯审核员1', '3@mail.com'),
(4, 'newsmanager1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '资讯维护员1', "4@mail.com"),
(5, 'userregauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '注册用户审核员1', "5@mail.com"),
(6, 'tradeauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '交易审核员1', "6@mail.com"),
(7, 'saleuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '供应商用户1', "7@mail.com"),
(8, 'salemoneyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '供应商财务用户1', "8@mail.com"),
(9, 'buyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '采购商用户', "9@mail.com"),
(10, 'buymoneyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '采购商财务用户1', "10@mail.com");

/*==============================================================*/
/* Index: Index_user_login                                      */
/*==============================================================*/
create unique index Index_user_login on ct_users
(
   login
);

/*==============================================================*/
/* Index: Index_user_email                                      */
/*==============================================================*/
create unique index Index_user_email on ct_users
(
   email
);

/*==============================================================*/
/* Table: ct_website_message                                    */
/*==============================================================*/
create table ct_website_message
(
   id                   bigint(20) not null auto_increment comment '新闻主键',
   title                varchar(50) not null comment '站内信标题',
   content              varchar(1024) not null comment '站内信内容',
   modified             datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
   msg_type             smallint not null comment '消息类型（1.系统消息）',
   created              datetime default CURRENT_TIMESTAMP comment '发信时间',
   from_userid          bigint(20) comment '发信人用户ID',
   from_username        varchar(128) comment '发信人用户名',
   to_userid            bigint(20) not null comment '收信人用户ID',
   to_username          varchar(128) comment '收信人用户名',
   read_status          enum('1','2') not null default '1' comment '是否已读（1. 未读；2.已读）',
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_to_userid                                       */
/*==============================================================*/
create index Index_to_userid on ct_website_message
(
   to_userid
);

/*==============================================================*/
/* Table: ct_zp                                                 */
/*==============================================================*/
create table ct_zp
(
   id                   bigint(20) not null auto_increment comment '摘牌ID',
   req_id               bigint(20) not null comment '需求ID',
   user_id              bigint(20) not null comment '摘牌用户ID',
   deposit              decimal(10,2) comment '摘牌保证金数额',
   status               enum('1','2','3') comment '摘牌状态
            1. 等待交保证金
            2. 成功
            3. 失败（被抢先摘牌）',
   opinion              varchar(255) comment '审核意见',
   created_time         datetime not null default CURRENT_TIMESTAMP comment '摘牌时间',
   primary key (id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_ZP_USER_ID                                      */
/*==============================================================*/
create index Index_ZP_USER_ID on ct_zp
(
   user_id
);

/*==============================================================*/
/* Index: Index_ZP_REQ_ID                                       */
/*==============================================================*/
create index Index_ZP_REQ_ID on ct_zp
(
   req_id
);

/*==============================================================*/
/* Index: Index_Req_User                                        */
/*==============================================================*/
create unique index Index_Req_User on ct_zp
(
   req_id,
   user_id
);

alter table ct_company add constraint FK_CU_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance add constraint FK_FU_REF_USER_1 foreign key (main_userid)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance add constraint FK_FU_REF_USER_2 foreign key (finance_userid)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance_log add constraint FK_FL_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance_store add constraint FK_UF_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_1 foreign key (author_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_2 foreign key (auditor_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_REQ foreign key (req_id)
      references ct_request (id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_request add constraint FK_REQ_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_request add constraint FK_REQ_REF_USER_2 foreign key (zp_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_request add constraint FK_RZ_REF_ZP foreign key (zp_id)
      references ct_zp (id) on delete restrict on update restrict;

alter table ct_role_permission_relationships add constraint FK_RP_REF_PERMISSION foreign key (permission_id)
      references ct_permissions (id) on delete restrict on update restrict;

alter table ct_role_permission_relationships add constraint FK_RP_REF_ROLE foreign key (role_id)
      references ct_userrole (id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_ROLE foreign key (role_id)
      references ct_userrole (id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_usermeta add constraint FK_UM_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_website_message add constraint FK_UM_REF_USER_1 foreign key (from_userid)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_website_message add constraint FK_UM_REF_USER_2 foreign key (to_userid)
      references ct_users (id) on delete restrict on update restrict;



# FOOTER
