package notificationsystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


//webconfig, restconfig, websecurityconfig, 'webinitializerconfig'

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //TODO: Fix (application.properties)
    @Bean
    public String backendURL() {
        return "http://localhost:8080";
    }
}
