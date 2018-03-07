package com.MMI.OUTPOST.security.authentication;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.MMI.OUTPOST.dao.userdao.UserRepository;
import com.MMI.OUTPOST.model.Users;

/**
*
* @author Sameer Sharma
*/

@Service
public class MongoDBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private UserRepository userRepo;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String clientId, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        System.out.println("<<<<<<<<<<<<<<<<<<Inside MongoDBAuthenticationProvider >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        try {
        	Users user = null;
        	BaseClientDetails clientDetails = new BaseClientDetails();
            user = userRepo.findByClientId(clientId);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            loadedUser = new User(user.getClientId(), user.getPassword(), authorities);
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }
}