package org.springboot.springboot1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springboot.springboot1.entity.User;

@Mapper
public interface UserMapper {
    User getUserByUserName(String username);

    User getUserByUsername(String username);

    User getUserRoleByUsername(String username);

    // 插入用户，使用数据库自增id返回到user.id
    void insertUser(User user);

    // 为用户分配角色
    void insertUserRole(java.util.Map<String, Object> params);
}
