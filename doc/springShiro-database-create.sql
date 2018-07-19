/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/7/19 17:19:48                           */
/*==============================================================*/

drop database if exists springShiro;

/*==============================================================*/
/* Database: springShiro                                       */
/*==============================================================*/
create database springShiro;

use springShiro;


drop table if exists t_permission;

drop table if exists t_role;

drop table if exists t_role_permission;

drop table if exists t_user;

drop table if exists t_user_role;

/*==============================================================*/
/* Table: t_permission                                          */
/*==============================================================*/
create table t_permission
(
   id                   bigint(20) not null auto_increment comment 'id',
   apiUrl               varchar(256) not null comment '接口地址Url',
   description          varchar(50) not null comment '接口描述',
   primary key (id)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4;

alter table t_permission comment '权限表';

/*==============================================================*/
/* Table: t_role                                                */
/*==============================================================*/
create table t_role
(
   id                   bigint not null auto_increment comment 'id',
   name                 varchar(30) not null comment '角色名称',
   type                 varchar(30) not null comment '角色类型',
   primary key (id)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4;

alter table t_role comment '角色表';

/*==============================================================*/
/* Table: t_role_permission                                     */
/*==============================================================*/
create table t_role_permission
(
   id                   bigint(20) not null auto_increment comment 'id,主键',
   roleId               bigint(20) not null comment '角色 id',
   permissionId         bigint(20) not null comment '权限id',
   primary key (id)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4;

alter table t_role_permission comment '角色-权限表';

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint(20) not null auto_increment comment 'id',
   nickName             varchar(30) not null comment '昵称',
   account              varchar(128) not null comment '登陆账号',
   passcode             varchar(50) not null comment '密码',
   createTime           varchar(50) not null comment '创建时间',
   lastLoginTime        varchar(50) not null comment '最近一次登陆时间',
   status               tinyint(2) default 1 comment '账号状态,1有效,-1禁止登陆',
   primary key (id)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4;

alter table t_user comment '管理员用户表';

/*==============================================================*/
/* Table: t_user_role                                           */
/*==============================================================*/
create table t_user_role
(
   id                   bigint(20) not null auto_increment comment 'id,主键',
   userId               bigint(20) not null comment '用户 id',
   roleId               bigint(20) not null comment '角色id',
   primary key (id)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4;

alter table t_user_role comment '用户-角色表';

alter table t_role_permission add constraint FK_Reference_22 foreign key (roleId)
      references t_role (id) on delete restrict on update restrict;

alter table t_role_permission add constraint FK_Reference_23 foreign key (permissionId)
      references t_permission (id) on delete restrict on update restrict;

alter table t_user_role add constraint FK_Reference_20 foreign key (userId)
      references t_user (id) on delete restrict on update restrict;

alter table t_user_role add constraint FK_Reference_21 foreign key (roleId)
      references t_role (id) on delete restrict on update restrict;

