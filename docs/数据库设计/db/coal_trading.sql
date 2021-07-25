/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/25 16:59:32                           */
/*==============================================================*/


# HEADER
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO"; # ��������
SET FOREIGN_KEY_CHECKS=0;  # ȡ�����Լ��


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
   user_id              bigint not null comment '�û�ID',
   com_name             varchar(20) comment '��Ӧ������',
   legal_name           varchar(20) comment '���˴���',
   legal_id             varchar(20) comment '�������֤��',
   com_addr             varchar(20) comment 'ע�����',
   com_contact          varchar(20) comment '��ϵ�绰',
   com_zip              varchar(20) comment '��������',
   business_license_id  varchar(20) comment 'Ӫҵִ�պ�',
   business_license_file varchar(255) comment 'Ӫҵִ�գ��ļ���',
   manage_license_id    varchar(20) comment '��Ӫ���֤���',
   fax                  varchar(20) comment '����',
   registered_capital   varchar(20) comment 'ע���ʽ���Ԫ��',
   oib_code             varchar(20) comment '��֯��������',
   oib_code_file        varchar(255) comment '��֯��������֤���ļ���',
   tr_cert              varchar(20) comment '˰��Ǽ�֤����',
   tr_cert_file         varchar(255) comment '˰��Ǽ�֤���ļ���',
   manage_license_file  varchar(255) comment 'ú̿��Ӫ���֤���ļ���[����]',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_company comment '��ҵ������Ϣ��';

/*==============================================================*/
/* Table: ct_fund                                               */
/*==============================================================*/
create table ct_fund
(
   user_id              bigint not null comment '�����û�ID',
   user_login           varchar(20) not null comment '��¼��',
   com_name             varchar(20) comment '��λ����',
   bank_name            varchar(20) comment '����������',
   bank_acc             varchar(19) comment '�����˺�',
   fund_total           decimal(10,2) comment '�˻����',
   fund_freeze          decimal(10,2) comment '���۶�����',
   ao_permit_file       varchar(255) comment '�������֤���ļ���',
   primary key (user_id, user_login)
)
engine = InnoDB;

/*==============================================================*/
/* Table: ct_fund_log                                           */
/*==============================================================*/
create table ct_fund_log
(
   user_id              bigint not null comment '�û�ID',
   user_login           varchar(20) not null comment '��¼��',
   log_date             datetime comment '�䶯ʱ��',
   log_type             bigint comment '�䶯����',
   log_fund_count       decimal(10,2) comment '�������',
   log_cert             varchar(255) comment '����ƾ֤',
   primary key (user_id, user_login)
)
engine = InnoDB;

alter table ct_fund_log comment '�䶯������
1. Ԥ�棨���ӣ�
2. ���ɸ�ƽ̨�����ᣩ
3. ƽ̨�۳�ָ����ȵĶ�';

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   news_id              bigint not null comment '����ID',
   user_id              bigint comment '�û�ID',
   ct__user_id          bigint comment '�û�ID',
   news_title           varchar(20) comment '���ű���',
   news_content         text comment '����',
   news_date            datetime comment '����ʱ��',
   news_status          int comment '״̬',
   primary key (news_id)
)
engine = InnoDB;

alter table ct_news comment '״̬��
1. �ݸ�
2. ����
3. ���������أ�
4. ɾ������¼ֱ��';

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
   status               int comment '����״̬',
   primary key (oid)
)
engine = InnoDB;

alter table ct_order comment '����״̬��
1. ������
2. ��ʱ
3. ���';

/*==============================================================*/
/* Table: ct_privilege                                          */
/*==============================================================*/
create table ct_privilege
(
   pri_id               bigint not null comment 'Ȩ��ID',
   pri_name             varchar(10) comment 'Ȩ����',
   primary key (pri_id)
)
engine = InnoDB;

alter table ct_privilege comment 'Ȩ�ޣ�
1. ��Ѷ�༭Ȩ��
2. ��Ѷ���Ȩ��
3. ��Ѷά��Ȩ��
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
   req_id               bigint not null comment '����ID',
   user_id              bigint comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   ended_time           datetime comment '����ʱ��',
   type                 bigint comment '����/����',
   status               int comment '����״̬
            1. �ݸ�
            2. ����
            3. ��ժȡ
            4. ����
            5. ���',
   deatil               text comment '������Ϣ(JSON)',
   primary key (req_id)
)
engine = InnoDB;

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
   user_id              bigint comment '�û�ID'
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
   user_id              bigint not null comment '�û�ID',
   user_login           varchar(20) not null comment '��¼��',
   ext_ver              int comment '��չ�汾',
   ext_info             text comment '��չ��Ϣ',
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
   role_id              bigint not null comment '��ɫID',
   role_name            varchar(20) comment '��ɫ��',
   primary key (role_id)
)
engine = InnoDB;

alter table ct_userrole comment '1. ���ֹ���Ա��
     a. ��Ѷ�༭Ա
     b. ��Ѷ���Ա
    ';

/*==============================================================*/
/* Table: ct_users                                              */
/*==============================================================*/
create table ct_users
(
   user_id              bigint not null comment '�û�ID',
   user_login           varchar(20) not null comment '��¼��',
   user_pass            varchar(50) comment '�û�����',
   user_nick            varchar(20) comment '�û��ǳ�',
   user_email           varchar(20) comment '�û�����',
   created_time         datetime comment '����ʱ��',
   user_status          int comment '�û�״̬',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_users comment '�û����ͣ�
1. ����Ա
2. �����û�
3. �����û�

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
