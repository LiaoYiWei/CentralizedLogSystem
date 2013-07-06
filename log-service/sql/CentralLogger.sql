------------------------------------------------
-- Export file for user CEN_LOGGER            --
-- Created by qizhou on 8/7/2012, 10:27:35 AM --
------------------------------------------------

spool Logger.log

prompt
prompt Creating table APPLICATION
prompt ==========================
prompt
create table APPLICATION
(
  APP_ID   VARCHAR2(50) not null,
  APP_NAME VARCHAR2(255) not null
)
;
alter table APPLICATION
  add constraint APP_PK primary key (APP_ID);

prompt
prompt Creating table APP_ENV
prompt ======================
prompt
create table APP_ENV
(
  ENV_ID   VARCHAR2(50) not null,
  ENV_NAME VARCHAR2(255) not null,
  APP_ID   VARCHAR2(50) not null
)
;
alter table APP_ENV
  add constraint ENV_PK primary key (ENV_ID);
alter table APP_ENV
  add constraint APP_EVN_FK foreign key (APP_ID)
  references APPLICATION (APP_ID);

prompt
prompt Creating table HOST
prompt ===================
prompt
create table HOST
(
  HOST_ID    VARCHAR2(50) not null,
  HOST_NAME  VARCHAR2(100) not null,
  IP_ADDRESS VARCHAR2(40) not null
)
;
alter table HOST
  add constraint HOST_PK primary key (HOST_ID);

prompt
prompt Creating table APP_ENV_NODE
prompt ===========================
prompt
create table APP_ENV_NODE
(
  NODE_ID   VARCHAR2(50) not null,
  NODE_NAME VARCHAR2(100) not null,
  ENV_ID    VARCHAR2(50) not null,
  HOST_ID   VARCHAR2(50) not null,
  STATUS    NUMBER default 1 not null
)
;
alter table APP_ENV_NODE
  add constraint APP_ENV_NODE_PK primary key (NODE_ID);
alter table APP_ENV_NODE
  add constraint NODE_APP_ENV_FK foreign key (ENV_ID)
  references APP_ENV (ENV_ID);
alter table APP_ENV_NODE
  add constraint NODE_HOST_FK foreign key (HOST_ID)
  references HOST (HOST_ID);
create unique index NODE_ENV_UNIQUE on APP_ENV_NODE (ENV_ID, NODE_NAME);

prompt
prompt Creating table RUNTIME_INSTANCE
prompt ===============================
prompt
create table RUNTIME_INSTANCE
(
  RUN_ID     VARCHAR2(50) not null,
  NODE_ID    VARCHAR2(50) not null,
  START_TIME DATE not null,
  CLIENT_START_TIME DATE not null
)
;
alter table RUNTIME_INSTANCE
  add constraint RUNTIME_PK primary key (RUN_ID);
alter table RUNTIME_INSTANCE
  add constraint RUNTIME_NODE_FK foreign key (NODE_ID)
  references APP_ENV_NODE (NODE_ID);
create unique index RUNTIME_INSTANCE_IDX1 on RUNTIME_INSTANCE (NODE_ID, START_TIME);

prompt
prompt Creating table APP_ENV_RULE
prompt ======================
prompt
create table APP_ENV_RULE
(
  RULE_ID   VARCHAR2(50) not null,
  ENV_ID 	VARCHAR2(50) not null,
  RULE_NAME VARCHAR2(255) not null,
  SUPPRESSION_TIME  NUMBER default 1 not null,
  EMAIL_PDL VARCHAR2(500) not null,  
  DISCRIMINATOR VARCHAR2(50) not null,
  SEVERITY          NUMBER,
  THROW_MSG_STATUS	NUMBER default -1,
  MSG_STATUS		NUMBER default -1,
  MESSAGE           VARCHAR2(3000) ,                           
  ATTRIBUTE1VALUE   VARCHAR2(255),
  ATTRIBUTE2VALUE   VARCHAR2(255),
  ATTRIBUTE3VALUE   VARCHAR2(255),
  ATTRIBUTE4VALUE   VARCHAR2(255),
  ATTRIBUTE5VALUE   VARCHAR2(255),
  THROWABLE_MESSAGE VARCHAR2(3000),
  MESSAGE_TYPE      VARCHAR2(20),
  THREAD_NAME       VARCHAR2(200),
  LOGGER_NAME       VARCHAR2(255),
  ATTRIBUTE1NAME    VARCHAR2(255),
  ATTRIBUTE2NAME    VARCHAR2(255),
  ATTRIBUTE3NAME    VARCHAR2(255),
  ATTRIBUTE4NAME    VARCHAR2(255),
  ATTRIBUTE5NAME    VARCHAR2(255),
  CREATE_TIME DATE not null,
  UPDATE_TIME DATE not null
)
;
alter table APP_ENV_RULE
  add constraint RULE_PK primary key (RULE_ID);
alter table APP_ENV_RULE
  add constraint APP_EVN_RULE_FK foreign key (ENV_ID)
  references APP_ENV (ENV_ID);

prompt
prompt Creating procedure EVENT_GEN
prompt ====================
prompt
       DECLARE
       	PART_NAME varchar2(500);
       	EVENT_SQL varchar2(5000);
       	KEY_STR varchar2(100);
       BEGIN
            EVENT_SQL := 'create table EVENT(
             EVENT_ID          VARCHAR2(50) not null,
             SEVERITY          NUMBER,
             MESSAGE           VARCHAR2(3000) ,
             RUN_ID            VARCHAR2(50) not null,
             CREATE_TIME       TIMESTAMP(6) not null,
             LOG_SEQUENCE      NUMBER,
             ATTRIBUTE1VALUE   VARCHAR2(255),
             ATTRIBUTE2VALUE   VARCHAR2(255),
             ATTRIBUTE3VALUE   VARCHAR2(255),
             ATTRIBUTE4VALUE   VARCHAR2(255),
             ATTRIBUTE5VALUE   VARCHAR2(255),
             THROWABLE_MESSAGE VARCHAR2(3000),
             MESSAGE_TYPE      VARCHAR2(20) not null,
             THREAD_NAME       VARCHAR2(200),
             LOGGER_NAME       VARCHAR2(255),
             ATTRIBUTE1NAME    VARCHAR2(255),
             ATTRIBUTE2NAME    VARCHAR2(255),
             ATTRIBUTE3NAME    VARCHAR2(255),
             ATTRIBUTE4NAME    VARCHAR2(255),
             ATTRIBUTE5NAME    VARCHAR2(255)
            )
            partition by range (CREATE_TIME)(';
            for i in -6..8 LOOP
                KEY_STR := to_char(sysdate + to_number(i),'YYYYMMDD');
                PART_NAME := 'event_'||KEY_STR;
                if i=8 then
                   EVENT_SQL := EVENT_SQL||'partition '||PART_NAME||' values less than(MAXVALUE)';
                else  
                   EVENT_SQL := EVENT_SQL||  'partition '|| PART_NAME ||' values less than(to_date(' || KEY_STR || ',''YYYYMMDD'')),';
                end if;
            END LOOP; 
            EVENT_SQL := EVENT_SQL || ')';
            execute immediate EVENT_SQL;    
         End;

alter table EVENT
  add constraint EVENT_RUNTIME_INSTANCE_FK1 foreign key (RUN_ID)
  references RUNTIME_INSTANCE (RUN_ID);
  
create index event_idx1 on event(CREATE_TIME) LOCAL;


spool off
