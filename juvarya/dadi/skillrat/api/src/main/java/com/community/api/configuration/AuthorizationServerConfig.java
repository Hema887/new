package com.community.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfig {
//extends AuthorizationServerConfigurerAdapter {

    static final String CLIEN_ID = "skillrat-client";
    static final String CLIENT_SECRET = "skillrat@2021";
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String CLIENT_CREDENTIALS = "client_credentials";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 365 * 8760 * 60 * 60 * 525600;
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 365 * 8760 * 60 * 60 * 525600;

    static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 365 * 8760 * 60 * 60 * 525600;

    /*
     * @Autowired private TokenStore tokenStore;
     */

//    @Autowired
//    private DataSource dataSource;

//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
//        configurer.inMemory().withClient(CLIEN_ID).secret("{noop}"+CLIENT_SECRET)
//                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT, CLIENT_CREDENTIALS)
//                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST).accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
//                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
//    }

//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource)).authenticationManager(authenticationManager);
//    }
}