package com.MMI.OUTPOST.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.MMI.OUTPOST.dao.userdao.UserRepository;
import com.MMI.OUTPOST.model.Users;
import com.MMI.OUTPOST.service.user.UserService;

@Service
/* @Transactional */
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addUser(Users user) {
		// TODO ACCESS-API or OAUTHSERVER ?? who's responsibility

	}

	@Override
	@Cacheable(value = "users", key = "#clientId", unless = "#result == null")
	public Users fetchUserByClientId(String clientId) {
		
		System.out.println("$$$$$$ Fetching data from MongoDB $$$$$$$$$$$");
		Users user = null;
		try {
			user = userRepository.findByClientId(clientId);
		} catch (Exception ex) {
			ex.printStackTrace();
			return user;
		}
		return user;
	}

	@Override
	@Cacheable(value = "users", key = "#clientId", unless = "#result == null")
	public Users fetchUserByUserName(String clientId) {

		Users user = null;
		try {
			user = userRepository.findByProjectname(clientId);
		} catch (Exception ex) {
			ex.printStackTrace();
			return user;
		}
		return user;

	}

}
