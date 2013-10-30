-- Create table
create table MOB_USER
(
  NUMID     VARCHAR2(50) not null,
  ITSYSNAME VARCHAR2(50),
  SIMID     VARCHAR2(50),
  USERNAME  VARCHAR2(50),
  USERFULLNAME  VARCHAR2(255),
  PASSWORD  VARCHAR2(50)
);
-- Add comments to the columns 
comment on column MOB_USER.NUMID
  is '序号';
comment on column MOB_USER.ITSYSNAME
  is '相关IT支撑系统';
comment on column MOB_USER.SIMID
  is '用户手机SIM卡号';
comment on column MOB_USER.USERNAME
  is '用户登录名';
comment on column MOB_USER.USERFULLNAME
  is '用户全名';
comment on column MOB_USER.PASSWORD
  is '用户密码';
  alter table MOB_USER
  add primary key (NUMID);

-- Create table
create table MOB_USERONLINE
(
  NUMID           VARCHAR2(50) not null,
  ITSYSNAME       VARCHAR2(50),
  SIMID           VARCHAR2(50),
  USERNAME        VARCHAR2(50),
  PASSWORD        VARCHAR2(50),
  ONLINEIPADDRESS VARCHAR2(50),
  PORT       NUMBER,
  LOGINTIME       NUMBER,
  HEARTBEATTIME   NUMBER,
  ISONLINE        NUMBER
);
-- Add comments to the columns 
comment on column MOB_USERONLINE.NUMID
  is '序号';
comment on column MOB_USERONLINE.ITSYSNAME
  is '相关IT支撑系统';
comment on column MOB_USERONLINE.SIMID
  is '用户手机SIM卡号';
comment on column MOB_USERONLINE.USERNAME
  is '用户登录名';
comment on column MOB_USERONLINE.PASSWORD
  is '用户密码';
comment on column MOB_USERONLINE.PORT
  is '端口';
comment on column MOB_USERONLINE.ONLINEIPADDRESS
  is '在线IP地址';
comment on column MOB_USERONLINE.LOGINTIME
  is '登录时间';
comment on column MOB_USERONLINE.HEARTBEATTIME
  is '心跳时间';
comment on column MOB_USERONLINE.ISONLINE
  is '0：表示不在线  1：表示在线';
   alter table MOB_USERONLINE
  add primary key (NUMID);