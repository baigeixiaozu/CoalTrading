/*==============================================================*/
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/25 9:25:09                            */
/*==============================================================*/


# HEADER
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; # 自增设置
SET FOREIGN_KEY_CHECKS=0;  # 取消外键约束


alter table ct_fund
   drop primary key;

drop table if exists ct_fund;

alter table ct_fund_log
   drop primary key;

drop table if exists ct_fund_log;

alter table ct_news
   drop primary key;

drop table if exists ct_news;

alter table ct_order
   drop primary key;

drop table if exists ct_order;

alter table ct_privilege
   drop primary key;

drop table if exists ct_privilege;

alter table ct_request
   drop primary key;

drop table if exists ct_request;

drop table if exists ct_role_pri_relationships;

drop table if exists ct_user_role_relationships;

alter table ct_userext
   drop primary key;

drop table if exists ct_userext;

alter table ct_userrole
   drop primary key;

drop table if exists ct_userrole;

alter table ct_users
   drop primary key;

drop table if exists ct_users;

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
   fund_freeze          decimal(10,2) comment '报价冻结金额'
)
engine = InnoDB;

alter table ct_fund
   add primary key (user_id);

/*==============================================================*/
/* Table: ct_fund_log                                           */
/*==============================================================*/
create table ct_fund_log
(
   user_id              bigint not null comment '用户ID',
   log_date             datetime comment '变动时间',
   log_type             bigint comment '变动操作',
   log_fund_count       decimal(10,2) comment '金额数量',
   log_cert             varchar(255) comment '交易凭证'
)
engine = InnoDB;

alter table ct_fund_log comment '变动操作：
1. 预存（增加）
2. 缴纳给平台（冻结）
3. 平台扣除指定额度的冻';

alter table ct_fund_log
   add primary key (user_id);

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   news_id              bigint not null comment '新闻ID',
   news_title           varchar(20) comment '新闻标题',
   news_content         text comment '内容',
   news_date            datetime comment '创建时间',
   news_status          int comment '状态',
   news_author          bigint comment '编写人员',
   news_auditor         bigint comment '审核人员'
)
engine = InnoDB;

alter table ct_news comment '状态：
1. 草稿
2. 发布
3. 撤销（隐藏）
4. 删除（记录直接';

alter table ct_news
   add primary key (news_id);

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
   status               int comment '订单状态'
)
engine = InnoDB;

alter table ct_order comment '订单状态：
1. 进行中
2. 超时
3. 完成';

alter table ct_order
   add primary key (oid);

/*==============================================================*/
/* Table: ct_privilege                                          */
/*==============================================================*/
create table ct_privilege
(
   pri_id               bigint not null comment '权限ID',
   pri_name             varchar(10) comment '权限名'
)
engine = InnoDB;

alter table ct_privilege comment '权限：
1. 资讯编辑权限
2. 资讯审核权限
3. 资讯维护权限
4.';

alter table ct_privilege
   add primary key (pri_id);

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
   status               int comment '需求状态',
   deatil               text comment '需求信息(JSON)'
)
engine = InnoDB;

alter table ct_request comment '需求状态：
1. 草稿
2. 发布
3. 被摘取
4. 隐藏
                               -&#&';

alter table ct_request
   add primary key (req_id);

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
   user_id              bigint not null comment '用户ID'
)
engine = InnoDB;

/*==============================================================*/
/* Index: user_role_index                                       */
/*==============================================================*/
create unique index user_role_index on ct_user_role_relationships
(
   role_id,
   user_id
);

/*==============================================================*/
/* Table: ct_userext                                            */
/*==============================================================*/
create table ct_userext
(
   user_id              bigint not null comment '用户ID',
   ext_ver              int comment '拓展版本',
   ext_info             text comment '拓展信息'
)
engine = InnoDB;

alter table ct_userext
   add primary key (user_id);

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
   role_name            varchar(20) comment '角色名'
)
engine = InnoDB;

alter table ct_userrole comment '1. 几种管理员：
     a. 资讯编辑员
     b. 资讯审核员
    ';

alter table ct_userrole
   add primary key (role_id);

/*==============================================================*/
/* Table: ct_users                                              */
/*==============================================================*/
create table ct_users
(
   user_id              bigint not null comment '用户ID',
   user_login           varchar(20) comment '登录名',
   user_pass            varchar(50) comment '用户密码',
   created_time         datetime comment '创建时间',
   user_status          int comment '用户状态'
)
engine = InnoDB;

alter table ct_users comment '用户类型：
1. 管理员
2. 交易用户
3. 财务用户

';

alter table ct_users
   add primary key (user_id);

/*==============================================================*/
/* Index: Index_user_login                                      */
/*==============================================================*/
create unique index Index_user_login on ct_users
(
   user_login
);

/*==============================================================*/
/* Index: Index_user_login_pass                                 */
/*==============================================================*/
create index Index_user_login_pass on ct_users
(
   user_login,
   user_pass
);

alter table ct_fund add constraint FK_FU_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_fund_log add constraint FK_FL_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_1 foreign key (news_author)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_2 foreign key (news_auditor)
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
