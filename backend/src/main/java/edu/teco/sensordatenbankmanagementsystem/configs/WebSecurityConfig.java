package edu.teco.sensordatenbankmanagementsystem.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public
/**
 * A websecurity config is used to prohibit usage by non-authorized users and other security features
 * It implements Springs {@Code WebSecurityConfigurerAdapter.class}
 */

class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * This configures which webpages should be secured and which should be open to everyone
     *
     * @param http is the default HttpSecurity config provided by Spring Boot
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
  http.authorizeRequests()
                .antMatchers("/sensor", "/observation").denyAll();


    }

    @Bean
    @Override
    /**
     * Sets up a single user for testing purposes and will be removed before deployment
     */
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
