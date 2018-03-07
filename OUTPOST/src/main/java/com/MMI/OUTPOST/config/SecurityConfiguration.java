package com.MMI.OUTPOST.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

import com.MMI.OUTPOST.security.authentication.MongoDBAuthenticationProvider;

/**
*
* @author Sameer Sharma
*/

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter { 
		
	private MyOAuth2AuthenticationEntryPoint authenticationEntryPoint;
	
	private AccessDecisionManager accessDecisionManager;
	
    private MongoDBAuthenticationProvider authenticationProvider;
	
	@Autowired
	public SecurityConfiguration( MyOAuth2AuthenticationEntryPoint authenticationEntryPoint,AccessDecisionManager accessDecisionManager, MongoDBAuthenticationProvider authenticationProvider) {
		this.authenticationEntryPoint = authenticationEntryPoint; 
		this.accessDecisionManager = accessDecisionManager;
		this.authenticationProvider = authenticationProvider;
	}

/*	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("javabycode").password("123456").roles("USER")
        .and()
        .withUser("admin").password("admin123").roles("ADMIN");
    }*/

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
		/*.addFilterAfter(
		          new CustomFilter(), BasicAuthenticationFilter.class)*/
		.csrf().disable()
		.anonymous().disable()
	  	.authorizeRequests()
	  	.antMatchers("/oauth/token").fullyAuthenticated()
	  	.and()
	  	.httpBasic()
	  	.authenticationEntryPoint(authenticationEntryPoint)
	  	.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		
		http
		/*.addFilterAfter(
		          new CustomFilter(), BasicAuthenticationFilter.class)*/
		.csrf().disable()
		.anonymous().disable()
	  	.authorizeRequests()
	  	.antMatchers("/oauth/check_token").fullyAuthenticated()
	  	.and()
	  	.httpBasic()
	  	.authenticationEntryPoint(authenticationEntryPoint)
	  	.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
	  	.and()
	  	.authorizeRequests()
	  	.anyRequest()
	    .authenticated()
	    .accessDecisionManager(accessDecisionManager);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider)
        /*.inMemoryAuthentication()
        .withUser("javabycode").password("123456").roles("USER")
        .and()
        .withUser("admin").password("admin123").roles("ADMIN");*/;
    }
    
    
/*    @Autowired
    public ClientDetailsUserDetailsService clientDetailsUserDetailsService(ClientDetailsServiceImpl clientDetailsServiceImpl) {
    	ClientDetailsUserDetailsService clientdetails = new ClientDetailsUserDetailsService(clientDetailsServiceImpl);
    	return clientdetails;
    }*/

    
 /*   @Bean
    public FilterRegistrationBean registerRequestLogFilter(CustomFilter filter) {
        FilterRegistrationBean reg = new FilterRegistrationBean(filter);
        reg.setOrder(2);
        return reg;
    }  */

 
}
