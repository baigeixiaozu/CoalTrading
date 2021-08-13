/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/8/13 14:34:50                           */
/*==============================================================*/


# AlterHeader
SET FOREIGN_KEY_CHECKS=0;  # 取消外键约束


use coal_trading;

drop table if exists coal_trading.tmp_ct_order;

rename table coal_trading.ct_order to tmp_ct_order;

use coal_trading;

/*==============================================================*/
/* Table: ct_order                                              */
/*==============================================================*/
create table ct_order
(
   id                   bigint(20) not null auto_increment comment '订单ID',
   num                  varchar(30) not null comment '订单号',
   req_id               bigint(20) comment '需求ID',
   gp_userid            bigint(20) comment '挂牌方用户ID',
   zp_userid            bigint(20) comment '摘牌方用户ID',
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

insert into ct_order (id, num, req_id, gp_userid, zp_userid, created_time, status)
select id, num, req_id, gp_userid, zp_userid, created_time, status
from coal_trading.tmp_ct_order;

drop table if exists coal_trading.tmp_ct_order;

/*==============================================================*/
/* Index: Index_req_id                                          */
/*==============================================================*/
create unique index Index_req_id on ct_order
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
/* Index: Index_user_id_1                                       */
/*==============================================================*/
create index Index_user_id_1 on ct_order
(
   gp_userid
);

/*==============================================================*/
/* Index: Index_user_id_2                                       */
/*==============================================================*/
create index Index_user_id_2 on ct_order
(
   zp_userid
);

alter table ct_order add constraint FK_ORDER_REF_REQ foreign key (req_id)
      references ct_request (id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_USER_1 foreign key (gp_userid)
      references ct_users (id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_USER_2 foreign key (zp_userid)
      references ct_users (id) on delete restrict on update restrict;

