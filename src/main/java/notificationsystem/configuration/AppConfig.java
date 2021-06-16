package notificationsystem.configuration;

import notificationsystem.controller.MailManager;
import notificationsystem.controller.MailSender;
import notificationsystem.controller.SubscriptionManager;
import notificationsystem.model.SensorDAO;
import notificationsystem.view.MailBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MailManager getMailManager() {
        return new MailManager();
    }

    @Bean
    public SubscriptionManager getSubscriptionManager() {
        return new SubscriptionManager();
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


}
