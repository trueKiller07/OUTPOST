package com.MMI.OUTPOST.security.authorization.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


public class SuspendRealTimeVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute arg0) {
		boolean myBool = true;
      return myBool;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int vote(Authentication arg0, Object arg1, Collection<ConfigAttribute> arg2) {

		try {
			String username = arg0.getPrincipal().toString();
			System.out.println(username+"######################");
			return ACCESS_GRANTED;
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		 return ACCESS_DENIED;
	}

}

