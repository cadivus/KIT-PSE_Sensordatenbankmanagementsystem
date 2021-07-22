package notificationsystem.configuration;

import notificationsystem.controller.Controller;
import notificationsystem.controller.MailSender;
import notificationsystem.model.SensorDAO;
import notificationsystem.model.SubscriptionDAO;
import notificationsystem.view.MailBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//webconfig, restconfig, websecurityconfig, 'webinitializerconfig'

@Configuration
public class AppConfig {

 /*   @Bean
    public Controller getController() {
        return new Controller();
    }

    @Bean
    public MailSender getMailSender() {
        return new MailSender();
    }

    @Bean
    public MailBuilder getMailBuilder() {
        return new MailBuilder();
    }

    @Bean
    public SensorDAO getSensorDAO() {
        return new SensorDAO();
    }

    @Bean
    public SubscriptionDAO getSubscriptionDAO() {
        return new SubscriptionDAO();
    }*/
}
