package org.springboot.springboot1.service;

import org.springboot.springboot1.entity.User;


public interface IUserService {
	User getUserByUsername(String username);

	User getUserRoleByUsername(String username);

	// 注册新用户（password 应为加密后的字符串），并分配指定角色 id
	void createUser(org.springboot.springboot1.entity.User user, int roleId);
}
