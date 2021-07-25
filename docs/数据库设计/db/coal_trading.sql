/*==============================================================*/
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/25 9:25:09                            */
/*==============================================================*/


# HEADER
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; # ��������
SET FOREIGN_KEY_CHECKS=0;  # ȡ�����Լ��


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
   user_id              bigint not null comment '�����û�ID',
   com_name             varchar(20) comment '��λ����',
   bank_name            varchar(20) comment '����������',
   bank_acc             varchar(19) comment '�����˺�',
   fund_total           decimal(10,2) comment '�˻����',
   fund_freeze          decimal(10,2) comment '���۶�����'
)
engine = InnoDB;

alter table ct_fund
   add primary key (user_id);

/*==============================================================*/
/* Table: ct_fund_log                                           */
/*==============================================================*/
create table ct_fund_log
(
   user_id              bigint not null comment '�û�ID',
   log_date             datetime comment '�䶯ʱ��',
   log_type             bigint comment '�䶯����',
   log_fund_count       decimal(10,2) comment '�������',
   log_cert             varchar(255) comment '����ƾ֤'
)
engine = InnoDB;

alter table ct_fund_log comment '�䶯������
1. Ԥ�棨���ӣ�
2. ���ɸ�ƽ̨�����ᣩ
3. ƽ̨�۳�ָ����ȵĶ�';

alter table ct_fund_log
   add primary key (user_id);

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   news_id              bigint not null comment '����ID',
   news_title           varchar(20) comment '���ű���',
   news_content         text comment '����',
   news_date            datetime comment '����ʱ��',
   news_status          int comment '״̬',
   news_author          bigint comment '��д��Ա',
   news_auditor         bigint comment '�����Ա'
)
engine = InnoDB;

alter table ct_news comment '״̬��
1. �ݸ�
2. ����
3. ���������أ�
4. ɾ������¼ֱ��';

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
   oid                  varchar(20) not null comment '����ID',
   req_id               bigint comment '����ID',
   user_id              bigint comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   status               int comment '����״̬'
)
engine = InnoDB;

alter table ct_order comment '����״̬��
1. ������
2. ��ʱ
3. ���';

alter table ct_order
   add primary key (oid);

/*==============================================================*/
/* Table: ct_privilege                                          */
/*==============================================================*/
create table ct_privilege
(
   pri_id               bigint not null comment 'Ȩ��ID',
   pri_name             varchar(10) comment 'Ȩ����'
)
engine = InnoDB;

alter table ct_privilege comment 'Ȩ�ޣ�
1. ��Ѷ�༭Ȩ��
2. ��Ѷ���Ȩ��
3. ��Ѷά��Ȩ��
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
   req_id               bigint not null comment '����ID',
   user_id              bigint comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   ended_time           datetime comment '����ʱ��',
   type                 bigint comment '����/����',
   status               int comment '����״̬',
   deatil               text comment '������Ϣ(JSON)'
)
engine = InnoDB;

alter table ct_request comment '����״̬��
1. �ݸ�
2. ����
3. ��ժȡ
4. ����
                               -&#&';

alter table ct_request
   add primary key (req_id);

/*==============================================================*/
/* Table: ct_role_pri_relationships                             */
/*==============================================================*/
create table ct_role_pri_relationships
(
   role_id              bigint comment '��ɫID',
   pri_id               bigint comment 'Ȩ��ID'
)
engine = InnoDB;

/*==============================================================*/
/* Table: ct_user_role_relationships                            */
/*==============================================================*/
create table ct_user_role_relationships
(
   role_id              bigint not null comment '��ɫID',
   user_id              bigint not null comment '�û�ID'
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
   user_id              bigint not null comment '�û�ID',
   ext_ver              int comment '��չ�汾',
   ext_info             text comment '��չ��Ϣ'
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
   role_id              bigint not null comment '��ɫID',
   role_name            varchar(20) comment '��ɫ��'
)
engine = InnoDB;

alter table ct_userrole comment '1. ���ֹ���Ա��
     a. ��Ѷ�༭Ա
     b. ��Ѷ���Ա
    ';

alter table ct_userrole
   add primary key (role_id);

/*==============================================================*/
/* Table: ct_users                                              */
/*==============================================================*/
create table ct_users
(
   user_id              bigint not null comment '�û�ID',
   user_login           varchar(20) comment '��¼��',
   user_pass            varchar(50) comment '�û�����',
   created_time         datetime comment '����ʱ��',
   user_status          int comment '�û�״̬'
)
engine = InnoDB;

alter table ct_users comment '�û����ͣ�
1. ����Ա
2. �����û�
3. �����û�

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
