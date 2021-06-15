package edu.teco.sensordatenbankmanagementsystem.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;

@Configuration
class WebSecurityConfig extends GlobalAuthenticationConfigurerAdapter {


    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

    }


}
