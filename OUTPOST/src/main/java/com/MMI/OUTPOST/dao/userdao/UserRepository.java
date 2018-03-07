
package com.MMI.OUTPOST.dao.userdao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.MMI.OUTPOST.model.Users;

/**
*
* @author Sameer Sharma
*/

public interface UserRepository extends MongoRepository<Users, String> {
	
	Users findByClientId(String client_id);
	
	Users findByProjectname(String Username);
	
}
