package com.MMI.OUTPOST.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.MMI.OUTPOST.model.Users;
import com.MMI.OUTPOST.service.user.UserService;

/**
*
* @author Sameer Sharma
*/

@Service
@Primary
public class ClientDetailsServiceImpl implements ClientDetailsService {
	
	@Autowired
	private UserService userService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {
    	
    	Users user = null;
        user = userService.fetchUserByClientId(clientId);
        BaseClientDetails clientDetails = new BaseClientDetails();
                
        try {
            if (user != null) {
                List<String> authorizedGrantTypes = new ArrayList<>();
                authorizedGrantTypes.add("password");
                authorizedGrantTypes.add("refresh_token");
                authorizedGrantTypes.add("client_credentials");

                List<String> scope = new ArrayList<>();

                scope.add(user.getScope());
                clientDetails.setClientId(user.getClientId());
                clientDetails.setClientSecret(user.getClientsecret());
                clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
                clientDetails.setScope(scope);
                clientDetails.setAccessTokenValiditySeconds(user.getTtl());

            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientDetails;
    }
}