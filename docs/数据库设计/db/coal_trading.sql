/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/30 22:43:42                           */
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
   user_id              bigint(20) not null comment '�û�ID',
   com_name             varchar(20) not null comment '��ҵ����',
   com_intro            text comment '��ҵ����',
   legal_name           varchar(20) comment '���˴���',
   legal_id             varchar(20) comment '�������֤��',
   legal_id_file        varchar(255) comment '�������֤���ļ���',
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
   manage_license_file  varchar(255) comment 'ú̿��Ӫ���֤���ļ���[��Ӧ��]',
   coal_store_site      varchar(255) comment 'úԴ��ŵص�[��Ӧ��]',
   coal_quantity        bigint comment 'úԴ����[��Ӧ��]',
   coal_quality         varchar(10) comment 'úԴ����[��Ӧ��]',
   coal_transport       varchar(20) comment '���䷽ʽ����������[��Ӧ��]',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_company comment '��ҵ������Ϣ��';

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
   bargain_id           bigint(20) not null comment '�����û�ID',
   finance_id           bigint(20) not null comment '�����û�ID',
   primary key (bargain_id, finance_id)
)
engine = InnoDB;

alter table ct_finance_bargain_bind comment '�����û��뽻���û����ɹ��̣���Ӧ�̣���';

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
   user_id              bigint(20) not null comment '�����û�ID',
   com_name             varchar(20) comment '��λ����',
   bank_name            varchar(20) comment '����������',
   bank_acc             varchar(19) comment '�����˺�',
   total                decimal(10,2) comment '�˻����',
   freeze               decimal(10,2) comment '���۶�����',
   ao_permit_file       varchar(255) comment '�������֤���ļ���',
   primary key (user_id)
)
engine = InnoDB;

INSERT INTO ct_fund
(`user_id`, `com_name`, `bank_name`, `bank_acc`, `total`, `freeze`, `ao_permit_file`) 
VALUES
(8 , "��Ӧ�̹�˾1", "����1", "123456789", 10000.00, 5000.55, "path/to/file"),
(10, "�ɹ��̹�˾2", "����1", "123456789", 10000.00, 5000.55, "path/to/file");

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
   user_id              bigint(20) not null comment '�û�ID',
   date                 datetime comment '�䶯ʱ��',
   type                 bigint comment '�䶯������
            1. Ԥ�棨���ӣ�
            2. ���ɸ�ƽ̨�����ᣩ
            3. ƽ̨�۳�ָ����ȵĶ��������٣�
            ',
   fund_quantity        decimal(10,2) comment '�������',
   cert                 varchar(255) comment '����ƾ֤���ļ���',
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
   id                   bigint(20) not null auto_increment comment '����ID',
   title                varchar(20) comment '���ű���',
   context              text comment '����',
   date                 datetime default CURRENT_TIMESTAMP comment '����ʱ��',
   status               smallint comment '״̬��
            1. �ݸ�
            2. �����
            3. ���أ���˲�ͨ����
            4. ����
            5. ���������أ�
            6. ɾ������¼ֱ��û�ˣ�',
   author_id            bigint(20) comment '��д��Ա',
   auditor_id           bigint(20) comment '�����Ա',
   primary key (id)
)
engine = InnoDB;

INSERT INTO ct_news(id, title, context, status, author_id, auditor_id)
VALUES
(1, "�ݸ���Ѷ", "�ݸ���Ѷ������", 1, 2, NULL),
(2, "�������Ѷ", "�������Ѷ������", 2, 2, 3),
(3, "��˲�����Ѷ", "��˲�����Ѷ������", 3, 2, 3),
(4, "��������Ѷ", "��������Ѷ������", 4, 2, 3),
(5, "��������Ѷ", "��������Ѷ������", 5, 2, 3);

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
   id                   bigint(20) not null auto_increment comment '����ID',
   num                  varchar(30) not null,
   req_id               bigint(20) comment '����ID',
   user_id              bigint(20) comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   status               smallint(6) comment '����״̬��
            1. ������
            2. ��ʱ
            3. ���
            4. ȡ��',
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
   id                   bigint(20) not null comment 'Ȩ��ID',
   name                 varchar(15) comment 'Ȩ����',
   primary key (id)
)
engine = InnoDB;

alter table ct_permissions comment 'Ȩ�ޣ�
1. ��Ѷ�༭Ȩ��
2. ��Ѷ���Ȩ��
3. ��Ѷά��Ȩ��
4.';

/*
Ȩ�ޣ�
1. ��������Ա
2. ��Ѷ�༭Ȩ��
3. ��Ѷ���Ȩ��
4. ��Ѷά��Ȩ��
5. ע���û����Ȩ��
6. �������Ȩ��
7. �û�����Ȩ��
8. ����Ȩ��
9. ����Ȩ��
10.��Ϣ�༭Ȩ��
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
   id                   bigint(20) not null comment '����ID',
   user_id              bigint(20) comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   ended_time           datetime comment '����ʱ��',
   type                 smallint(6) comment '����/����',
   status               smallint comment '����״̬
            1. �ݸ�
            2. ����
            3. ��ժȡ
            4. ����
            5. ���',
   deatil               text comment '������Ϣ(JSON)',
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
   role_id              bigint(20) not null comment '��ɫID',
   permission_id        bigint(20) not null comment 'Ȩ��ID',
   primary key (role_id, permission_id)
)
engine = InnoDB;

alter table ct_role_permission_relationships comment '1����ɫ���Զ�Ӧ1��Ȩ��';

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
   user_id              bigint(20) not null comment '�û�ID',
   role_id              bigint(20) not null comment '��ɫID',
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
   id                   bigint(20) not null comment '��ɫID',
   mark                 varchar(20) comment '��ɫ���',
   name                 varchar(20) comment '��ɫ��',
   type                 varchar(10) comment '��ɫ����',
   primary key (id)
)
engine = InnoDB;

alter table ct_userrole comment '1. ���ֹ���Ա��
     a. ��Ѷ�༭Ա
     b. ��Ѷ���Ա
    ';

/*
1. ���ֹ���Ա��
     a. ��������Ա
     b. ��Ѷ�༭Ա
     c. ��Ѷ���Ա
     d. ��Ѷά��Ա
     e. ע���û����Ա
     f. �������Ա
     
2. �����û�[��Ӧ��,�ɹ���]
3. �����û�
*/
INSERT INTO ct_userrole(`id`, `name`, `mark`, `type`) VALUES
(1, "��������Ա", "SUPER_ADMIN", "admin"),
(2, "��Ѷ�༭Ա", "NEWS_EDITOR", "admin"),
(3, "��Ѷ���Ա", "NEWS_AUDITOR", "admin"),
(4, "��Ѷά��Ա", "NEWS_MANAGER", "admin"),
(5, "ע���û����Ա", "USER_REG_AUDITOR", "admin"),
(6, "�������Ա", "TRADE_AUDITOR", "admin"),
(7, "��Ӧ��", "USER_SALE", "user"),
(8, "�ɹ���", "USER_BUY", "user"),
(9, "�����û�", "USER_MONEY", "user");

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
   id                   bigint(20) not null auto_increment comment '�û�ID��Ψһ��',
   login                varchar(20) not null comment '��¼����Ψһ��',
   pass                 varchar(100) comment '�û����룺
            Ҫ��������',
   nick                 varchar(20) comment '�û��ǳ�',
   email                varchar(20) not null comment '�û����䣨Ψһ��',
   registered           datetime default CURRENT_TIMESTAMP comment '����ʱ��',
   status               smallint default 1 comment '�û�״̬��
            1. �����
            2. ���ͨ�������ã�
            ',
   primary key (id)
)
engine = InnoDB;

alter table ct_users comment '�洢���û����ͣ�
1. ����Ա
2. �����û�
3. �����û�
';

INSERT INTO `ct_users` (`id`, `login`, `pass`, `nick`, `email`, `status`) 
VALUES 
(1, 'superadmin', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '��������Ա', "1@mail.com", 2),
(2, 'newseditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '��Ѷ�༭Ա1', '2@mail.com', 2),
(3, 'newsauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '��Ѷ���Ա1', '3@mail.com', 2),
(4, 'newsmanager1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '��Ѷά��Ա1', "4@mail.com", 2),
(5, 'userregauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', 'ע���û����Ա1', "5@mail.com", 2),
(6, 'tradeauditor1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '�������Ա1', "6@mail.com", 2),
(7, 'saleuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '��Ӧ���û�1', "7@mail.com", 2),
(8, 'salemoneyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '��Ӧ�̻�1', "8@mail.com", 2),
(9, 'buyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '�ɹ����û�', "9@mail.com", 2),
(10, 'buymoneyuser1', '$2a$10$.y9u24yrwyy6aw96ny/HVOgPxa.z/WuWzmBKVP0ELbE7w5U.9/EA2', '�ɹ��̲����û�1', "10@mail.com", 2);

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
   id                   bigint(20) not null auto_increment comment '��������',
   title                varchar(50) comment 'վ���ű���',
   context              varchar(1024) comment 'վ��������',
   modified             datetime comment '�޸�ʱ��',
   msg_type             smallint comment '��Ϣ���ͣ�1.ϵͳ��Ϣ��',
   created              datetime default CURRENT_TIMESTAMP comment '����ʱ��',
   from_userid          bigint(20) comment '�������û�ID',
   from_username        varchar(128) comment '�������û���',
   to_userid            bigint(20) comment '�������û�ID',
   to_username          varchar(128) comment '�������û���',
   read_status          smallint default 1 comment '�Ƿ��Ѷ���1. δ����2.�Ѷ���',
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
