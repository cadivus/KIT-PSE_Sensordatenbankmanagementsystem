package edu.teco.sensordatenbankmanagementsystem.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Configuration
/**
 * This config holds the configuration for the RESTcontrollers in and output. It is mainly concerned with correct formatting
 * For the general Webconfig see {@link WebConfig} and for the security config see {@link WebSecurityConfig}
 */
public class RESTConfiguration {
    @Bean
    /**
     * This formats the Model into a viewable json format
     */
    public View jsonTemplate() {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        return view;
    }

    @Bean
    /**
     * This returns a BeanNameViewResolver, which formats Beans returned by the Rest Controller to a format readable by modern Webbrowsers
     */
    public ViewResolver viewResolver() {
        return new BeanNameViewResolver();
    }
}
