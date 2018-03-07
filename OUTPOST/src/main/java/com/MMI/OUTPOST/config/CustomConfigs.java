package com.MMI.OUTPOST.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;

import com.MMI.OUTPOST.security.authorization.voter.SuspendRealTimeVoter;


/**
*
* @author Sameer Sharma
*/

@Configuration
public class CustomConfigs {
	
	@Bean  
    public AccessDecisionManager accessDecisionManager() { 
        List<AccessDecisionVoter<? extends Object>> decisionVoters 
          = Arrays.asList(
        		    new RoleVoter(),
        	        new AuthenticatedVoter(),
        	        new SuspendRealTimeVoter());
        return new UnanimousBased(decisionVoters);
    }

}
