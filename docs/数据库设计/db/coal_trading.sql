/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/25 16:59:32                           */
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
   user_id              bigint not null comment '用户ID',
   com_name             varchar(20) comment '供应商名称',
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

/*==============================================================*/
/* Table: ct_fund                                               */
/*==============================================================*/
create table ct_fund
(
   user_id              bigint not null comment '财务用户ID',
   user_login           varchar(20) not null comment '登录名',
   com_name             varchar(20) comment '汇款单位名称',
   bank_name            varchar(20) comment '开户行名称',
   bank_acc             varchar(19) comment '银行账号',
   fund_total           decimal(10,2) comment '账户金额',
   fund_freeze          decimal(10,2) comment '报价冻结金额',
   ao_permit_file       varchar(255) comment '开户许可证（文件）',
   primary key (user_id, user_login)
)
engine = InnoDB;

/*==============================================================*/
/* Table: ct_fund_log                                           */
/*==============================================================*/
create table ct_fund_log
(
   user_id              bigint not null comment '用户ID',
   user_login           varchar(20) not null comment '登录名',
   log_date             datetime comment '变动时间',
   log_type             bigint comment '变动操作',
   log_fund_count       decimal(10,2) comment '金额数量',
   log_cert             varchar(255) comment '交易凭证',
   primary key (user_id, user_login)
)
engine = InnoDB;

alter table ct_fund_log comment '变动操作：
1. 预存（增加）
2. 缴纳给平台（冻结）
3. 平台扣除指定额度的冻';

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
   oid                  varchar(20) not null comment '订单ID',
   req_id               bigint comment '需求ID',
   user_id              bigint comment '用户ID',
   created_time         datetime comment '创建时间',
   status               int comment '订单状态',
   primary key (oid)
)
engine = InnoDB;

alter table ct_order comment '订单状态：
1. 进行中
2. 超时
3. 完成';

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

/*==============================================================*/
/* Table: ct_role_pri_relationships                             */
/*==============================================================*/
create table ct_role_pri_relationships
(
   role_id              bigint comment '角色ID',
   pri_id               bigint comment '权限ID'
)
engine = InnoDB;

/*==============================================================*/
/* Table: ct_user_role_relationships                            */
/*==============================================================*/
create table ct_user_role_relationships
(
   role_id              bigint not null comment '角色ID',
   user_id              bigint comment '用户ID'
)
engine = InnoDB;

/*==============================================================*/
/* Index: user_role_index                                       */
/*==============================================================*/
create unique index user_role_index on ct_user_role_relationships
(
   role_id
);

/*==============================================================*/
/* Table: ct_userext                                            */
/*==============================================================*/
create table ct_userext
(
   user_id              bigint not null comment '用户ID',
   user_login           varchar(20) not null comment '登录名',
   ext_ver              int comment '拓展版本',
   ext_info             text comment '拓展信息',
   primary key (user_id, user_login)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_user                                            */
/*==============================================================*/
create unique index Index_user on ct_userext
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

alter table ct_userext add constraint FK_UE_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;



# FOOTER
