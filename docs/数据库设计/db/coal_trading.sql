/*==============================================================*/
/* Database name:  coal_trading                                 */
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2021/7/25 19:42:16                           */
/*==============================================================*/


# AlterHeader
SET FOREIGN_KEY_CHECKS=0;  # ȡ�����Լ��


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
   user_id              bigint not null comment '�û�ID',
   com_name             varchar(20) not null comment '��ҵ����',
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
   user_id              bigint not null comment '�����û�ID',
   com_name             varchar(20) comment '��λ����',
   bank_name            varchar(20) comment '����������',
   bank_acc             varchar(19) comment '�����˺�',
   fund_total           decimal(10,2) comment '�˻����',
   fund_freeze          decimal(10,2) comment '���۶�����',
   ao_permit_file       varchar(255) comment '�������֤���ļ���',
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
   user_id              bigint not null comment '�û�ID',
   log_date             datetime comment '�䶯ʱ��',
   log_type             bigint comment '�䶯����',
   log_fund_count       decimal(10,2) comment '�������',
   log_cert             varchar(255) comment '����ƾ֤',
   primary key (user_id)
)
engine = InnoDB;

alter table ct_fund_log comment '�䶯������
1. Ԥ�棨���ӣ�
2. ���ɸ�ƽ̨�����ᣩ
3. ƽ̨�۳�ָ����ȵĶ�';

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
   ooder_id             varchar(20) not null comment '����ID',
   req_id               bigint comment '����ID',
   user_id              bigint comment '�û�ID',
   created_time         datetime comment '����ʱ��',
   status               int comment '����״̬',
   primary key (ooder_id)
)
engine = InnoDB;

alter table ct_order comment '����״̬��
1. ������
2. ��ʱ
3. ���';

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
   role_id              bigint not null comment '��ɫID',
   pri_id               bigint not null comment 'Ȩ��ID',
   primary key (role_id, pri_id)
)
engine = InnoDB;

alter table ct_role_pri_relationships comment '1����ɫ���Զ�Ӧ1��Ȩ��';

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
   role_id              bigint not null comment '��ɫID',
   user_id              bigint not null comment '�û�ID',
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
   role_id              bigint not null comment '��ɫID',
   role_name            varchar(20) comment '��ɫ��',
   primary key (role_id)
)
engine = InnoDB;

alter table ct_userrole comment '1. ���ֹ���Ա��
     a. ��Ѷ�༭Ա
     b. ��Ѷ���Ա
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

