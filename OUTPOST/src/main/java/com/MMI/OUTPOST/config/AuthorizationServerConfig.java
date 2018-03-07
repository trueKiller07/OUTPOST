package com.MMI.OUTPOST.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sameer Sharma
 * 
 */

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static String REALM = "OUTPOST_API";

	private ClientDetailsService clientService;

	@Autowired
	public AuthorizationServerConfig(AuthenticationManager authenticationManager,
			RedisConnectionFactory redisConnectionFactory, ClientDetailsService clientService) {
		this.authenticationManager = authenticationManager;
		this.redisTokenStore = new RedisTokenStore(redisConnectionFactory);
		this.clientService = clientService;
	}

	private AuthenticationManager authenticationManager;

	private TokenStore redisTokenStore;

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		security.tokenKeyAccess("isAuthenticated()").checkTokenAccess("isAuthenticated()")
				.allowFormAuthenticationForClients().realm(REALM + "/client");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {		
		clients.withClientDetails(clientService);
	}

	@Bean
	@Autowired
	public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
		this.redisTokenStore = new RedisTokenStore(redisConnectionFactory);
		return this.redisTokenStore;
	}

	@Bean
	public WebResponseExceptionTranslator loggingExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {
			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
				// This is the line that prints the stack trace to the log. You can customise
				// this to format the trace etc if you like
				e.printStackTrace();

				// Carry on handling the exception
				ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
				HttpHeaders headers = new HttpHeaders();
				headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				OAuth2Exception excBody = responseEntity.getBody();
				return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
			}
		};
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(redisTokenStore).userApprovalHandler(userApprovalHandler)
				.authenticationManager(authenticationManager).exceptionTranslator(loggingExceptionTranslator());
	}

	public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
		this.redisConnectionFactory = redisConnectionFactory;
	}

	@Bean
	public TokenStoreUserApprovalHandler userApprovalHandler() {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(redisTokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientService));
		handler.setClientDetailsService(clientService);
		return handler;
	}

	@Bean
	@Autowired
	public ApprovalStore approvalStore() throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(redisTokenStore);
		return store;
	}

	@Bean
	@Primary
	@Autowired
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(redisTokenStore);
		return tokenServices;
	}

}

@Component
class MyOAuth2AuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {
}