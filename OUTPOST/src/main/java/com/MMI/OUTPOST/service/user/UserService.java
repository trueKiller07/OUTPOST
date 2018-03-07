package com.MMI.OUTPOST.service.user;

import com.MMI.OUTPOST.model.Users;

public interface UserService {
	
	 public void addUser(Users user);

    public Users fetchUserByClientId(String clientId);

    public Users fetchUserByUserName(String clientId);

}
