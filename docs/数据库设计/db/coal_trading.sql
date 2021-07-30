/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/30 22:43:42                           */
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
   com_name             varchar(20) not null comment '企业名称',
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
   primary key (user_id)
)
engine = InnoDB;

alter table ct_company comment '企业基本信息表';

/*==============================================================*/
/* Index: Index_com_name                                        */
/*==============================================================*/
create unique index Index_com_name on ct_company
(
   com_name
);

/*==============================================================*/
/* Table: ct_finance_bargain_bind                               */
/*==============================================================*/
create table ct_finance_bargain_bind
(
   bargain_id           bigint(20) not null comment '交易用户ID',
   finance_id           bigint(20) not null comment '财务用户ID',
   primary key (bargain_id, finance_id)
)
engine = InnoDB;

alter table ct_finance_bargain_bind comment '财务用户与交易用户（采购商，供应商）绑定';

INSERT INTO ct_finance_bargain_bind(`bargain_id`, `finance_id`) VALUES
(7, 8),
(9, 10);

/*==============================================================*/
/* Index: Index_bargain_id                                      */
/*==============================================================*/
create unique index Index_bargain_id on ct_finance_bargain_bind
(
   bargain_id,
   finance_id
);

/*==============================================================*/
/* Index: Index_fiance_id                                       */
/*==============================================================*/
create unique index Index_fiance_id on ct_finance_bargain_bind
(
   finance_id
);

/*==============================================================*/
/* Table: ct_fund                                               */
/*==============================================================*/
create table ct_fund
(
   user_id              bigint(20) not null comment '财务用户ID',
   com_name             varchar(20) comment '汇款单位名称',
   bank_name            varchar(20) comment '开户行名称',
   bank_acc             varchar(19) comment '银行账号',
   total                decimal(10,2) comment '账户金额',
   freeze               decimal(10,2) comment '报价冻结金额',
   ao_permit_file       varchar(255) comment '开户许可证（文件）',
   primary key (user_id)
)
engine = InnoDB;

INSERT INTO ct_fund
(`user_id`, `com_name`, `bank_name`, `bank_acc`, `total`, `freeze`, `ao_permit_file`) 
VALUES
(8 , "供应商公司1", "银行1", "123456789", 10000.00, 5000.55, "path/to/file"),
(10, "采购商公司2", "银行1", "123456789", 10000.00, 5000.55, "path/to/file");

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create unique index Index_user_id on ct_fund
(
   user_id
);

/*==============================================================*/
/* Table: ct_fund_log                                           */
/*==============================================================*/
create table ct_fund_log
(
   user_id              bigint(20) not null comment '用户ID',
   date                 datetime comment '变动时间',
   type                 bigint comment '变动操作：
            1. 预存（增加）
            2. 缴纳给平台（冻结）
            3. 平台扣除指定额度的冻结款项（减少）
            ',
   fund_quantity        decimal(10,2) comment '金额数量',
   cert                 varchar(255) comment '交易凭证（文件）',
   primary key (user_id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create index Index_user_id on ct_fund_log
(
   user_id
);

/*==============================================================*/
/* Index: Index_log_type                                        */
/*==============================================================*/
create index Index_log_type on ct_fund_log
(
   type
);

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   id                   bigint(20) not null auto_increment comment '新闻ID',
   title                varchar(20) comment '新闻标题',
   context              text comment '内容',
   date                 datetime default CURRENT_TIMESTAMP comment '创建时间',
   status               smallint comment '状态：
            1. 草稿
            2. 审核中
            3. 驳回（审核不通过）
            4. 发布
            5. 撤销（隐藏）
            6. 删除（记录直接没了）',
   author_id            bigint(20) comment '编写人员',
   auditor_id           bigint(20) comment '审核人员',
   primary key (id)
)
engine = InnoDB;

INSERT INTO ct_news(id, title, context, status, author_id, auditor_id)
VALUES
(1, "草稿资讯", "草稿资讯的内容", 1, 2, NULL),
(2, "审核中资讯", "审核中资讯的内容", 2, 2, 3),
(3, "审核驳回资讯", "审核驳回资讯的内容", 3, 2, 3),
(4, "发布的资讯", "发布的资讯的内容", 4, 2, 3),
(5, "撤销的资讯", "撤销的资讯的内容", 5, 2, 3);

/*==============================================================*/
/* Index: Index_news_id                                         */
/*==============================================================*/
create unique index Index_news_id on ct_news
(
   id
);

/*==============================================================*/
/* Table: ct_order                                              */
/*==============================================================*/
create table ct_order
(
   id                   bigint(20) not null auto_increment comment '订单ID',
   num                  varchar(30) not null,
   req_id               bigint(20) comment '需求ID',
   user_id              bigint(20) comment '用户ID',
   created_time         datetime comment '创建时间',
   status               smallint(6) comment '订单状态：
            1. 进行中
            2. 超时
            3. 完成
            4. 取消',
   primary key (id)
)
engine = InnoDB;

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

alter table ct_permissions comment '权限：
1. 资讯编辑权限
2. 资讯审核权限
3. 资讯维护权限
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
10.信息编辑权限
*/
INSERT INTO ct_permissions VALUES(1, "SUPER_ADMIN"),
(2, "NEWS_EDITOR"),
(3, "NEWS_AUDITOR"),
(4, "NEWS_MANAGER"),
(5, "USER_REG_AUDITOR"),
(6, "TRADE_AUDITOR"),
(7, "USER_ADD"),
(8, "PUB_SALE"),
(9, "PUB_BUY");

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
   id                   bigint(20) not null comment '需求ID',
   user_id              bigint(20) comment '用户ID',
   created_time         datetime comment '创建时间',
   ended_time           datetime comment '结束时间',
   type                 smallint(6) comment '购入/卖出',
   status               smallint comment '需求状态
            1. 草稿
            2. 发布
            3. 被摘取
            4. 隐藏
            5. 完成',
   deatil               text comment '需求信息(JSON)',
   primary key (id)
)
engine = InnoDB;

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

alter table ct_role_permission_relationships comment '1个角色可以对应1个权限';

/*role_id, permission*/
INSERT INTO ct_role_permission_relationships VALUES(1,1);
INSERT INTO ct_role_permission_relationships VALUES(1,7);
INSERT INTO ct_role_permission_relationships VALUES(2,2);
INSERT INTO ct_role_permission_relationships VALUES(3,3);
INSERT INTO ct_role_permission_relationships VALUES(4,4);
INSERT INTO ct_role_permission_relationships VALUES(5,5);
INSERT INTO ct_role_permission_relationships VALUES(6,6);
INSERT INTO ct_role_permission_relationships VALUES(7,8);
INSERT INTO ct_role_permission_relationships VALUES(8,9);

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

INSERT INTO ct_user_role_relationships(`user_id`, `role_id`) 
VALUES(1, 1),(2,2),(3,3),(4,4),(5,5),(6,6);

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

alter table ct_userrole comment '1. 几种管理员：
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
(9, "财务用户", "USER_MONEY", "user");

/*==============================================================*/
/* Index: Index_role_id                                         */
/*==============================================================*/
create index Index_role_id on ct_userrole
(
   id
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
   email                varchar(20) not null comment '用户邮箱（唯一）',
   registered           datetime default CURRENT_TIMESTAMP comment '创建时间',
   status               smallint default 1 comment '用户状态：
            1. 待审核
            2. 审核通过（可用）
            ',
   primary key (id)
)
engine = InnoDB;

alter table ct_users comment '存储的用户类型：
1. 管理员
2. 交易用户
3. 财务用户
';

INSERT INTO `ct_users` (`id`, `login`, `pass`, `nick`, `email`, `status`) 
VALUES 
(1, 'superadmin', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '超级管理员', "1@mail.com", 2),
(2, 'newseditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '资讯编辑员1', '2@mail.com', 2),
(3, 'newsauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '资讯审核员1', '3@mail.com', 2),
(4, 'newsmanager1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '资讯维护员1', "4@mail.com", 2),
(5, 'userregauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '注册用户审核员1', "5@mail.com", 2),
(6, 'tradeauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '交易审核员1', "6@mail.com", 2),
(7, 'saleuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '供应商用户1', "7@mail.com", 2),
(8, 'salemoneyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '供应商户1', "8@mail.com", 2),
(9, 'buyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '采购商用户', "9@mail.com", 2),
(10, 'buymoneyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '采购商财务用户1', "10@mail.com", 2);

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
   title                varchar(50) comment '站内信标题',
   context              varchar(1024) comment '站内信内容',
   modified             datetime comment '修改时间',
   msg_type             smallint comment '消息类型（1.系统消息）',
   created              datetime default CURRENT_TIMESTAMP comment '发信时间',
   from_userid          bigint(20) comment '发信人用户ID',
   from_username        varchar(128) comment '发信人用户名',
   to_userid            bigint(20) comment '收信人用户ID',
   to_username          varchar(128) comment '收信人用户名',
   read_status          smallint default 1 comment '是否已读（1. 未读；2.已读）',
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

alter table ct_company add constraint FK_CU_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance_bargain_bind add constraint FK_FT_REF_USER_1 foreign key (bargain_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance_bargain_bind add constraint FK_FT_REF_USER_2 foreign key (finance_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_fund add constraint FK_FU_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_fund_log add constraint FK_FL_REF_USER foreign key (user_id)
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

alter table ct_role_permission_relationships add constraint FK_RP_REF_PERMISSION foreign key (permission_id)
      references ct_permissions (id) on delete restrict on update restrict;

alter table ct_role_permission_relationships add constraint FK_RP_REF_ROLE foreign key (role_id)
      references ct_userrole (id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_ROLE foreign key (role_id)
      references ct_userrole (id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_website_message add constraint FK_UM_REF_USER_1 foreign key (from_userid)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_website_message add constraint FK_UM_REF_USER_2 foreign key (to_userid)
      references ct_users (id) on delete restrict on update restrict;



# FOOTER
