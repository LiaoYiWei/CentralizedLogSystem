----------------------------------------------
-- Export file for user LOGGER              --
-- Created by chenkan on 6/4/2012, 18:04:41 --
----------------------------------------------

spool Logger.log

prompt
prompt Creating table APPLICATION
prompt ==========================
prompt
create table APPLICATION
(
  APP_ID   NUMBER not null,
  APP_NAME VARCHAR2(255) not null
)
tablespace SBS_TB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table APPLICATION
  add constraint APP_PK primary key (APP_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table ENVIRONMENT
prompt ==========================
prompt
create table ENVIRONMENT
(
  ENV_ID   NUMBER not null,
  ENV_NAME VARCHAR2(255) not null
)
tablespace SBS_TB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ENVIRONMENT
  add constraint ENV_PK primary key (ENV_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table HOST
prompt ===================
prompt
create table HOST
(
  HOST_ID NUMBER not null,
  IP      VARCHAR2(40) not null
)
tablespace SBS_TB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table HOST
  add constraint HOST_PK primary key (HOST_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;

prompt
prompt Creating table APP_INSTANCE
prompt ===========================
prompt
create table APP_INSTANCE
(
  INSTANCE_ID NUMBER not null,
  APP_ID      NUMBER not null,
  HOST_ID     NUMBER not null,
  EVN_ID      NUMBER not null
)
tablespace SBS_TB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table APP_INSTANCE
  add constraint INSTANCE_PK primary key (INSTANCE_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table APP_INSTANCE
  add constraint INSTANCE_APP_FK foreign key (APP_ID)
  references APPLICATION (APP_ID);
alter table APP_INSTANCE
  add constraint INSTANCE_ENV_FK foreign key (EVN_ID)
  references ENVIRONMENT (ENV_ID);
alter table APP_INSTANCE
  add constraint INSTANCE_HOST_FK foreign key (HOST_ID)
  references HOST (HOST_ID);

prompt
prompt Creating table RUNTIME_INSTANCE
prompt ===============================
prompt
create table RUNTIME_INSTANCE
(
  RUN_ID      NUMBER not null,
  INSTANCE_ID NUMBER not null,
  START_TIME  DATE not null
)
tablespace SBS_TB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table RUNTIME_INSTANCE
  add constraint RUNTIME_PK primary key (RUN_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
alter table RUNTIME_INSTANCE
  add constraint RUN_INSTANCE_FK foreign key (INSTANCE_ID)
  references APP_INSTANCE (INSTANCE_ID);

prompt
prompt Creating table EVENT
prompt ====================
prompt
create table EVENT
(
  EVENT_ID    NUMBER not null,
  CREATE_DATE DATE,
  SEVERITY    VARCHAR2(255),
  MESSAGE     VARCHAR2(500),
  RUN_ID      NUMBER
)
tablespace SBS_TB
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table EVENT
  add constraint EVENT_PK primary key (EVENT_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table EVENT
  add constraint EVENT_RUNTIME_INSTANCE_FK1 foreign key (RUN_ID)
  references RUNTIME_INSTANCE (RUN_ID);

prompt
prompt Creating sequence SEQ_APP_ID
prompt ============================
prompt
create sequence SEQ_APP_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ENV_ID
prompt ============================
prompt
create sequence SEQ_ENV_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_EVENT_ID
prompt ==============================
prompt
create sequence SEQ_EVENT_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HOST_ID
prompt =============================
prompt
create sequence SEQ_HOST_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INSTANCE_ID
prompt =================================
prompt
create sequence SEQ_INSTANCE_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_RUN_ID
prompt ============================
prompt
create sequence SEQ_RUN_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating trigger TRG_INS_APP
prompt ============================
prompt
create or replace trigger TRG_INS_APP
  before insert on application  
  for each row
declare
  -- local variables here
begin
  SELECT SEQ_APP_ID.NEXTVAL INTO :NEW.APP_ID
  FROM DUAL;
end TRG_INS_APP;
/

prompt
prompt Creating trigger TRG_INS_ENV
prompt ============================
prompt
create or replace trigger TRG_INS_ENV
  before insert on environment  
  for each row
declare
  -- local variables here
begin
  SELECT SEQ_ENV_ID.NEXTVAL INTO :NEW.ENV_ID
  FROM DUAL;
end TRG_INS_APP;
/

prompt
prompt Creating trigger TRG_INS_EVENT
prompt ==============================
prompt
create or replace trigger TRG_INS_EVENT
  before insert on event  
  for each row
declare
  -- local variables here
begin
  SELECT SEQ_EVENT_ID.NEXTVAL INTO :NEW.EVENT_ID
  FROM DUAL;
end TRG_INS_APP;
/

prompt
prompt Creating trigger TRG_INS_HOST
prompt =============================
prompt
create or replace trigger TRG_INS_HOST
  before insert on host  
  for each row
declare
  -- local variables here
begin
  SELECT SEQ_HOST_ID.NEXTVAL INTO :NEW.HOST_ID
  FROM DUAL;
end TRG_INS_APP;
/

prompt
prompt Creating trigger TRG_INS_INSTANCE
prompt =================================
prompt
create or replace trigger TRG_INS_INSTANCE
  before insert on app_instance  
  for each row
declare
  -- local variables here
begin
  SELECT SEQ_INSTANCE_ID.NEXTVAL INTO :NEW.INSTANCE_ID
  FROM DUAL;
end TRG_INS_APP;
/

prompt
prompt Creating trigger TRG_INS_RUNTIME
prompt ================================
prompt
create or replace trigger TRG_INS_RUNTIME
  before insert on RUNTIME_INSTANCE  
  for each row
declare
  -- local variables here
begin
  SELECT SEQ_RUN_ID.NEXTVAL INTO :NEW.RUN_ID
  FROM DUAL;
end TRG_INS_APP;
/


spool off
