
-- 添加权限  
INSERT INTO t_permission(`apiUrl`,`description`) 
    VALUES('api/user/login','用户登录'),
          ('api/user/addUser','添加用户'),
          ('api/user/updateUserInfo','修改用户基本信息'),
          ('api/user/updateUserRoles','修改用户角色'),
          ('api/user/updateUserPermission','修改用户权限');

-- 插入角色
INSERT INTO t_role(`name`,`type`)
    VALUES('admin','系统管理员'),
          ('deve','开发员工'),
          ('sale','销售、运营员工');

-- 系统管理员拥有所有权限,将不再逐一添加 apiUrl  

-- 添加角色权限
INSERT INTO t_role_permission(`roleId`,`permissionId`)
    VALUES(2,1),
          (2,3),
          (3,1);

-- 添加员工  
INSERT INTO t_user(`account`, `passcode`, `nickName`, `createTime`, `lastLoginTime`)
     VALUES ('tom@demo.com', '123456', 'Tom', NOW(), NOW()),
            ('jerry@demo.com', '123456', 'Jerry', NOW(), NOW()),
            ('jack@demo.com', '123456', 'jack', NOW(), NOW());

-- 给员工分配角色  
INSERT INTO t_user_role(`userId`, `roleId`)
    VALUES (1,1),
           (2,2),
           (3,3);




  

          