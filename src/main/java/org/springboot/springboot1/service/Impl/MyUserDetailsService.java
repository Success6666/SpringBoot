package org.springboot.springboot1.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springboot.springboot1.entity.User;
import org.springboot.springboot1.service.IUserService;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUserRoleByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("当前用户不存在！");
		}
	}
}
