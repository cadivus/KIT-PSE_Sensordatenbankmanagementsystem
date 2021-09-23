package notificationsystem.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import notificationsystem.controller.Controller;


@Configuration
public class AppConfig {

    @Autowired
    Controller controller;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String backendURL() {
        return "http://backend:8081";
    }

    /*@Bean
    public FilterRegistrationBean<APIFilter> filter() {
        FilterRegistrationBean<APIFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new APIFilter(controller));
        registrationBean.addUrlPatterns("/postSubscription", "/postUnsubscribe", "/getSubscriptions/*");
        return registrationBean;
    }*/
}
