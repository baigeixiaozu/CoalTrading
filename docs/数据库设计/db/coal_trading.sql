/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/25 19:42:16                           */
/*==============================================================*/


# AlterHeader
SET FOREIGN_KEY_CHECKS=0;  # 取消外键约束


use coal_trading;

drop table if exists coal_trading.tmp_ct_company;

rename table coal_trading.ct_company to tmp_ct_company;

drop table if exists coal_trading.tmp_ct_fund;

rename table coal_trading.ct_fund to tmp_ct_fund;

drop table if exists coal_trading.tmp_ct_fund_log;

rename table coal_trading.ct_fund_log to tmp_ct_fund_log;

drop table if exists coal_trading.tmp_ct_news;

rename table coal_trading.ct_news to tmp_ct_news;

drop table if exists coal_trading.tmp_ct_order;

rename table coal_trading.ct_order to tmp_ct_order;

drop table if exists coal_trading.tmp_ct_privilege;

rename table coal_trading.ct_privilege to tmp_ct_privilege;

drop table if exists coal_trading.tmp_ct_request;

rename table coal_trading.ct_request to tmp_ct_request;

drop table if exists coal_trading.tmp_ct_role_pri_relationships;

rename table coal_trading.ct_role_pri_relationships to tmp_ct_role_pri_relationships;

drop table if exists coal_trading.tmp_ct_user_role_relationships;

rename table coal_trading.ct_user_role_relationships to tmp_ct_user_role_relationships;

drop table if exists coal_trading.ct_userext;

drop table if exists coal_trading.tmp_ct_userrole;

rename table coal_trading.ct_userrole to tmp_ct_userrole;

drop table if exists coal_trading.tmp_ct_users;

rename table coal_trading.ct_users to tmp_ct_users;

/*==============================================================*/
/* Table: ct_company                                            */
/*==============================================================*/
create table ct_company
(
   user_id              bigint not null comment '用户ID',
   com_name             varchar(20) not null comment '企业名称',
   legal_name           varchar(20) comment '法人代表',
   legal_id             varchar(20) comment '法人身份证号',
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
   manage_license_file  varchar(255) comment '煤炭经营许可证（文件）[买方无]',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_company comment '企业基本信息表';

insert into ct_company (user_id, com_name, legal_name, legal_id, com_addr, com_contact, com_zip, business_license_id, business_license_file, manage_license_id, fax, registered_capital, oib_code, oib_code_file, tr_cert, tr_cert_file, manage_license_file)
select user_id, com_name, legal_name, legal_id, com_addr, com_contact, com_zip, business_license_id, business_license_file, manage_license_id, fax, registered_capital, oib_code, oib_code_file, tr_cert, tr_cert_file, manage_license_file
from coal_trading.tmp_ct_company;

drop table if exists coal_trading.tmp_ct_company;

/*==============================================================*/
/* Index: Index_com_name                                        */
/*==============================================================*/
create unique index Index_com_name on ct_company
(
   com_name
);

/*==============================================================*/
/* Table: ct_fund                                               */
/*==============================================================*/
create table ct_fund
(
   user_id              bigint not null comment '财务用户ID',
   com_name             varchar(20) comment '汇款单位名称',
   bank_name            varchar(20) comment '开户行名称',
   bank_acc             varchar(19) comment '银行账号',
   fund_total           decimal(10,2) comment '账户金额',
   fund_freeze          decimal(10,2) comment '报价冻结金额',
   ao_permit_file       varchar(255) comment '开户许可证（文件）',
   primary key (user_id)
)
engine = InnoDB;

insert into ct_fund (user_id, com_name, bank_name, bank_acc, fund_total, fund_freeze, ao_permit_file)
select user_id, com_name, bank_name, bank_acc, fund_total, fund_freeze, ao_permit_file
from coal_trading.tmp_ct_fund;

drop table if exists coal_trading.tmp_ct_fund;

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
   user_id              bigint not null comment '用户ID',
   log_date             datetime comment '变动时间',
   log_type             bigint comment '变动操作',
   log_fund_count       decimal(10,2) comment '金额数量',
   log_cert             varchar(255) comment '交易凭证',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_fund_log comment '变动操作：
1. 预存（增加）
2. 缴纳给平台（冻结）
3. 平台扣除指定额度的冻';

insert into ct_fund_log (user_id, log_date, log_type, log_fund_count, log_cert)
select user_id, log_date, log_type, log_fund_count, log_cert
from coal_trading.tmp_ct_fund_log;

drop table if exists coal_trading.tmp_ct_fund_log;

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
   log_type
);

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   news_id              bigint not null comment '新闻ID',
   user_id              bigint comment '用户ID',
   ct__user_id          bigint comment '用户ID',
   news_title           varchar(20) comment '新闻标题',
   news_content         text comment '内容',
   news_date            datetime comment '创建时间',
   news_status          int comment '状态',
   primary key (news_id)
)
engine = InnoDB;

alter table ct_news comment '状态：
1. 草稿
2. 发布
3. 撤销（隐藏）
4. 删除（记录直接';

insert into ct_news (news_id, user_id, ct__user_id, news_title, news_content, news_date, news_status)
select news_id, user_id, ct__user_id, news_title, news_content, news_date, news_status
from coal_trading.tmp_ct_news;

drop table if exists coal_trading.tmp_ct_news;

/*==============================================================*/
/* Index: Index_news_id                                         */
/*==============================================================*/
create unique index Index_news_id on ct_news
(
   news_id
);

/*==============================================================*/
/* Table: ct_order                                              */
/*==============================================================*/
create table ct_order
(
   ooder_id             varchar(20) not null comment '订单ID',
   req_id               bigint comment '需求ID',
   user_id              bigint comment '用户ID',
   created_time         datetime comment '创建时间',
   status               int comment '订单状态',
   primary key (ooder_id)
)
engine = InnoDB;

alter table ct_order comment '订单状态：
1. 进行中
2. 超时
3. 完成';

insert into ct_order (ooder_id, req_id, user_id, created_time, status)
select ooder_id, req_id, user_id, created_time, status
from coal_trading.tmp_ct_order;

drop table if exists coal_trading.tmp_ct_order;

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create index Index_user_id on ct_order
(
   user_id
);

/*==============================================================*/
/* Index: Index_order_id                                        */
/*==============================================================*/
create unique index Index_order_id on ct_order
(
   ooder_id
);

/*==============================================================*/
/* Table: ct_privilege                                          */
/*==============================================================*/
create table ct_privilege
(
   pri_id               bigint not null comment '权限ID',
   pri_name             varchar(10) comment '权限名',
   primary key (pri_id)
)
engine = InnoDB;

alter table ct_privilege comment '权限：
1. 资讯编辑权限
2. 资讯审核权限
3. 资讯维护权限
4.';

insert into ct_privilege (pri_id, pri_name)
select pri_id, pri_name
from coal_trading.tmp_ct_privilege;

drop table if exists coal_trading.tmp_ct_privilege;

/*==============================================================*/
/* Index: Index_pri                                             */
/*==============================================================*/
create unique index Index_pri on ct_privilege
(
   pri_id
);

/*==============================================================*/
/* Table: ct_request                                            */
/*==============================================================*/
create table ct_request
(
   req_id               bigint not null comment '需求ID',
   user_id              bigint comment '用户ID',
   created_time         datetime comment '创建时间',
   ended_time           datetime comment '结束时间',
   type                 bigint comment '购入/卖出',
   status               int comment '需求状态
            1. 草稿
            2. 发布
            3. 被摘取
            4. 隐藏
            5. 完成',
   deatil               text comment '需求信息(JSON)',
   primary key (req_id)
)
engine = InnoDB;

insert into ct_request (req_id, user_id, created_time, ended_time, type, status, deatil)
select req_id, user_id, created_time, ended_time, type, status, deatil
from coal_trading.tmp_ct_request;

drop table if exists coal_trading.tmp_ct_request;

/*==============================================================*/
/* Index: Index_req_id                                          */
/*==============================================================*/
create index Index_req_id on ct_request
(
   req_id
);

/*==============================================================*/
/* Table: ct_role_pri_relationships                             */
/*==============================================================*/
create table ct_role_pri_relationships
(
   role_id              bigint not null comment '角色ID',
   pri_id               bigint not null comment '权限ID',
   primary key (role_id, pri_id)
)
engine = InnoDB;

alter table ct_role_pri_relationships comment '1个角色可以对应1个权限';

insert into ct_role_pri_relationships (role_id, pri_id)
select role_id, pri_id
from coal_trading.tmp_ct_role_pri_relationships;

drop table if exists coal_trading.tmp_ct_role_pri_relationships;

/*==============================================================*/
/* Index: Index_role_pri_id                                     */
/*==============================================================*/
create unique index Index_role_pri_id on ct_role_pri_relationships
(
   role_id,
   pri_id
);

/*==============================================================*/
/* Table: ct_user_role_relationships                            */
/*==============================================================*/
create table ct_user_role_relationships
(
   role_id              bigint not null comment '角色ID',
   user_id              bigint not null comment '用户ID',
   primary key (role_id, user_id)
)
engine = InnoDB;

insert into ct_user_role_relationships (role_id, user_id)
select role_id, user_id
from coal_trading.tmp_ct_user_role_relationships;

drop table if exists coal_trading.tmp_ct_user_role_relationships;

/*==============================================================*/
/* Index: user_role_index                                       */
/*==============================================================*/
create unique index user_role_index on ct_user_role_relationships
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
   role_id              bigint not null comment '角色ID',
   role_name            varchar(20) comment '角色名',
   primary key (role_id)
)
engine = InnoDB;

alter table ct_userrole comment '1. 几种管理员：
     a. 资讯编辑员
     b. 资讯审核员
    ';

insert into ct_userrole (role_id, role_name)
select role_id, role_name
from coal_trading.tmp_ct_userrole;

drop table if exists coal_trading.tmp_ct_userrole;

/*==============================================================*/
/* Index: Index_role_id                                         */
/*==============================================================*/
create index Index_role_id on ct_userrole
(
   role_id
);

/*==============================================================*/
/* Table: ct_users                                              */
/*==============================================================*/
create table ct_users
(
   user_id              bigint not null comment '用户ID',
   user_login           varchar(20) not null comment '登录名',
   user_pass            varchar(50) comment '用户密码',
   user_nick            varchar(20) comment '用户昵称',
   user_email           varchar(20) comment '用户邮箱',
   created_time         datetime comment '创建时间',
   user_status          int comment '用户状态',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_users comment '用户类型：
1. 管理员
2. 交易用户
3. 财务用户

';

insert into ct_users (user_id, user_login, user_pass, user_nick, user_email, created_time, user_status)
select user_id, user_login, user_pass, user_nick, user_email, created_time, user_status
from coal_trading.tmp_ct_users;

drop table if exists coal_trading.tmp_ct_users;

/*==============================================================*/
/* Index: Index_user_login                                      */
/*==============================================================*/
create unique index Index_user_login on ct_users
(
   user_login
);

alter table ct_company add constraint FK_CU_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_fund add constraint FK_FU_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_fund_log add constraint FK_FL_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_1 foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_2 foreign key (ct__user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_REQ foreign key (req_id)
      references ct_request (req_id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_request add constraint FK_REQ_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_role_pri_relationships add constraint FK_GR_REF_GRANT foreign key (pri_id)
      references ct_privilege (pri_id) on delete restrict on update restrict;

alter table ct_role_pri_relationships add constraint FK_GR_REF_ROLE foreign key (role_id)
      references ct_userrole (role_id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_ROLE foreign key (role_id)
      references ct_userrole (role_id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

