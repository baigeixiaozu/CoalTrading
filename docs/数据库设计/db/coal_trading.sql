/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/27 18:27:09                           */
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

/*==============================================================*/
/* Index: Index_1                                               */
/*==============================================================*/
create unique index Index_1 on ct_finance_bargain_bind
(
   bargain_id,
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
   fund_total           decimal(10,2) comment '�˻����',
   fund_freeze          decimal(10,2) comment '���۶�����',
   ao_permit_file       varchar(255) comment '�������֤���ļ���',
   primary key (user_id)
)
engine = InnoDB;

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
   log_date             datetime comment '�䶯ʱ��',
   log_type             bigint comment '�䶯������
            1. Ԥ�棨���ӣ�
            2. ���ɸ�ƽ̨�����ᣩ
            3. ƽ̨�۳�ָ����ȵĶ��������٣�
            ',
   log_fund_count       decimal(10,2) comment '�������',
   log_cert             varchar(255) comment '����ƾ֤���ļ���',
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
   log_type
);

/*==============================================================*/
/* Table: ct_news                                               */
/*==============================================================*/
create table ct_news
(
   news_id              bigint(20) not null comment '����ID',
   news_title           varchar(20) comment '���ű���',
   news_content         text comment '����',
   news_date            datetime comment '����ʱ��',
   news_status          int comment '״̬��
            1. �ݸ�
            2. ����
            3. ���������أ�
            4. ɾ������¼ֱ��û�ˣ�',
   news_author_id       bigint(20) comment '������Ա',
   news_auditor_id      bigint(20) comment '�����Ա',
   primary key (news_id)
)
engine = InnoDB;

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
   order_id             bigint(20) not null comment '����ID',
   order_num            varchar(30),
   req_id               bigint(20) comment '����ID',
   user_id              bigint(20) comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   status               smallint(6) comment '����״̬��
            1. ������
            2. ��ʱ
            3. ���
            4. ȡ��',
   primary key (order_id)
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
   order_id
);

/*==============================================================*/
/* Table: ct_permissions                                        */
/*==============================================================*/
create table ct_permissions
(
   permission_id        bigint(20) not null comment 'Ȩ��ID',
   permission_name      varchar(10) comment 'Ȩ����',
   primary key (permission_id)
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
INSERT INTO ct_permissions VALUES(1, "SUPER_ADMIN");
INSERT INTO ct_permissions VALUES(2, "NEWS_EDITOR");
INSERT INTO ct_permissions VALUES(3, "NEWS_AUDITOR");
INSERT INTO ct_permissions VALUES(4, "NEWS_MANAGER");
INSERT INTO ct_permissions VALUES(5, "USER_REG_AUDITOR");
INSERT INTO ct_permissions VALUES(6, "TRADE_AUDITOR");
INSERT INTO ct_permissions VALUES(7, "USER_ADD");
INSERT INTO ct_permissions VALUES(8, "PUB_SALE");
INSERT INTO ct_permissions VALUES(9, "PUB_BUY");

/*==============================================================*/
/* Index: Index_pri                                             */
/*==============================================================*/
create unique index Index_pri on ct_permissions
(
   permission_id
);

/*==============================================================*/
/* Table: ct_request                                            */
/*==============================================================*/
create table ct_request
(
   req_id               bigint(20) not null comment '����ID',
   user_id              bigint(20) comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   ended_time           datetime comment '����ʱ��',
   type                 smallint(6) comment '����/����',
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
/* Index: Index_req_id                                          */
/*==============================================================*/
create index Index_req_id on ct_request
(
   req_id
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
   role_id              bigint(20) not null comment '��ɫID',
   role_mark            varchar(20) comment '��ɫ���',
   role_name            varchar(20) comment '��ɫ��',
   primary key (role_id)
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
INSERT INTO ct_userrole(`role_id`, `role_name`, `role_mark`) VALUES
(1, "��������Ա", "SUPER_ADMIN"),
(2, "��Ѷ�༭Ա", "NEWS_EDITOR"),
(3, "��Ѷ���Ա", "NEWS_AUDITOR"),
(4, "��Ѷά��Ա", "NEWS_MANAGER"),
(5, "ע���û����Ա", "USER_REG_AUDITOR"),
(6, "�������Ա", "TRADE_AUDITOR"),
(7, "��Ӧ��", "USER_SALE"),
(8, "�ɹ���", "USER_BUY"),
(9, "�����û�", "USER_MONEY");

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
   user_id              bigint(20) not null auto_increment comment '�û�ID',
   user_login           varchar(20) not null comment '��¼��',
   user_pass            varchar(50) comment '�û����룺
            Ҫ��������',
   user_nick            varchar(20) comment '�û��ǳ�',
   user_email           varchar(20) comment '�û�����',
   user_registered      datetime default CURRENT_TIMESTAMP comment '����ʱ��',
   user_status          int default 1 comment '�û�״̬��
            1. �����
            2. ���ͨ�������ã�
            ',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_users comment '�洢���û����ͣ�
1. ����Ա
2. �����û�
3. �����û�
';

INSERT INTO `ct_users` (`user_id`, `user_login`, `user_pass`, `user_nick`, `user_email`, `user_status`) 
VALUES 
(1, 'superadmin', '123456', '��������Ա', "1@mail.com", 2),
(2, 'newseditor', '123456', '��Ѷ�༭Ա', '2@mail.com', 2),
(3, 'newsauditor', '123456', '��Ѷ���Ա', '3@mail.com', 2),
(4, 'newsmanager', '123456', '��Ѷά��Ա', "4@mail.com", 2),
(5, 'userregauditor', '123456', 'ע���û����Ա', "4@mail.com", 2),
(6, 'tradeauditor', '123456', '�������Ա', "4@mail.com", 2);

/*==============================================================*/
/* Index: Index_user_login                                      */
/*==============================================================*/
create unique index Index_user_login on ct_users
(
   user_login
);

/*==============================================================*/
/* Table: ct_website_message                                    */
/*==============================================================*/
create table ct_website_message
(
   wm_id                bigint(20) not null comment '��������',
   wm_modified          datetime comment '�޸�ʱ��',
   wm_type              smallint comment '��Ϣ���ͣ�1.ϵͳ��Ϣ��',
   wm_context           varchar(1024) comment 'վ��������',
   wm_created           datetime comment '����ʱ��',
   wm_from_userid       bigint(20) comment '�������û�ID',
   wm_from_username     varchar(128) comment '�������û���',
   wm_read              smallint comment '�Ƿ��Ѷ���1. δ����2.�Ѷ���',
   wm_to_userid         bigint(20) comment '�������û�ID',
   wm_to_username       varchar(128) comment '�������û���',
   primary key (wm_id)
)
engine = InnoDB;

/*==============================================================*/
/* Index: Index_msg_id                                          */
/*==============================================================*/
create unique index Index_msg_id on ct_website_message
(
   wm_id
);

alter table ct_company add constraint FK_CU_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_finance_bargain_bind add constraint FK_FT_REF_USER_1 foreign key (bargain_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_finance_bargain_bind add constraint FK_FT_REF_USER_2 foreign key (finance_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_fund add constraint FK_FU_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_fund_log add constraint FK_FL_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_1 foreign key (news_author_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_news add constraint FK_NEWS_REF_USER_2 foreign key (news_auditor_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_REQ foreign key (req_id)
      references ct_request (req_id) on delete restrict on update restrict;

alter table ct_order add constraint FK_ORDER_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_request add constraint FK_REQ_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_role_permission_relationships add constraint FK_RP_REF_PERMISSION foreign key (permission_id)
      references ct_permissions (permission_id) on delete restrict on update restrict;

alter table ct_role_permission_relationships add constraint FK_RP_REF_ROLE foreign key (role_id)
      references ct_userrole (role_id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_ROLE foreign key (role_id)
      references ct_userrole (role_id) on delete restrict on update restrict;

alter table ct_user_role_relationships add constraint FK_UR_REF_USER foreign key (user_id)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_website_message add constraint FK_UM_REF_USER_1 foreign key (wm_from_userid)
      references ct_users (user_id) on delete restrict on update restrict;

alter table ct_website_message add constraint FK_UM_REF_USER_2 foreign key (wm_to_userid)
      references ct_users (user_id) on delete restrict on update restrict;



# FOOTER
