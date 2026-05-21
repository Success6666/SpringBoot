package org.springboot.springboot1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springboot.springboot1.dao.UserMapper;
import org.springboot.springboot1.entity.User;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public User getUserByUsername(String username) {
		return userMapper.getUserByUsername(username);
	}

	@Override
	public User getUserRoleByUsername(String username) {
		return userMapper.getUserRoleByUsername(username);
	}	

	@Override
	@Transactional
	public void createUser(org.springboot.springboot1.entity.User user, int roleId) {
		// 在插入前检查用户名是否已存在，抛出异常由上层处理
		User existing = userMapper.getUserByUsername(user.getUsername());
		if (existing != null) {
			throw new IllegalArgumentException("用户名已存在");
		}
		// 插入用户，MyBatis 会回填 user.id（useGeneratedKeys=true）
		userMapper.insertUser(user);
		java.util.Map<String, Object> params = new java.util.HashMap<>();
		params.put("userId", user.getId());
		params.put("roleId", roleId);
		userMapper.insertUserRole(params);
	}
}
