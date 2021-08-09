/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/8/9 19:06:46                            */
/*==============================================================*/


# AlterHeader
SET FOREIGN_KEY_CHECKS=0;  # 取消外键约束


use coal_trading;

drop table if exists coal_trading.tmp_ct_finance;

rename table coal_trading.ct_finance to tmp_ct_finance;

use coal_trading;

/*==============================================================*/
/* Table: ct_finance                                            */
/*==============================================================*/
create table ct_finance
(
   main_userid          bigint(20) not null comment '交易用户ID',
   finance_userid       bigint(20) comment '财务用户ID',
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

insert into ct_finance (main_userid, finance_userid, com_name, bank_name, bank_acc, balance, freeze, ao_permit_file, status, audit_opinion)
select main_userid, finance_userid, com_name, bank_name, bank_acc, balance, freeze, ao_permit_file, status, audit_opinion
from coal_trading.tmp_ct_finance;

drop table if exists coal_trading.tmp_ct_finance;

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

alter table ct_finance add constraint FK_FU_REF_USER_1 foreign key (main_userid)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_finance add constraint FK_FU_REF_USER_2 foreign key (finance_userid)
      references ct_users (id) on delete restrict on update restrict;

