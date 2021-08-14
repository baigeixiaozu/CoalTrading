/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/8/14 7:56:27                            */
/*==============================================================*/


# AlterHeader
SET FOREIGN_KEY_CHECKS=0;  # 取消外键约束


use coal_trading;

drop table if exists coal_trading.tmp_ct_finance_store;

rename table coal_trading.ct_finance_store to tmp_ct_finance_store;

use coal_trading;

/*==============================================================*/
/* Table: ct_finance_store                                      */
/*==============================================================*/
create table ct_finance_store
(
   id                   bigint(20) not null auto_increment comment '预存ID',
   user_id              bigint(20) not null comment '财务用户ID',
   date                 datetime default CURRENT_TIMESTAMP comment '变动时间',
   quantity             decimal(10,2) comment '金额数量',
   cert                 varchar(255) not null comment '交易凭证（文件）',
   status               enum('1','2','3') default '1' comment '预存状态：
            1. 待审核
            2. 驳回（审核不通过）
            3. 审核通过',
   primary key (id)
)
engine = InnoDB;

alter table ct_finance_store comment '资金预存表';

insert into ct_finance_store (id, user_id, date, quantity, cert, status)
select id, user_id, date, quantity, cert, status
from coal_trading.tmp_ct_finance_store;

drop table if exists coal_trading.tmp_ct_finance_store;

/*==============================================================*/
/* Index: Index_user_id                                         */
/*==============================================================*/
create index Index_user_id on ct_finance_store
(
   user_id
);

/*==============================================================*/
/* Index: Index_cert                                            */
/*==============================================================*/
create unique index Index_cert on ct_finance_store
(
   cert
);

alter table ct_finance_store add constraint FK_UF_REF_USER foreign key (user_id)
      references ct_users (id) on delete restrict on update restrict;

