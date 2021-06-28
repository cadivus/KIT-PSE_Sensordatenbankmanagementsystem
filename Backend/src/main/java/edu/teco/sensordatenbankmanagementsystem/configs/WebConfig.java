package edu.teco.sensordatenbankmanagementsystem.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import javax.servlet.MultipartConfigElement;

@Configuration
@EnableWebMvc
@ComponentScan
/**
 * The WebConfig overrides the default WebMvcConfigurer methods, to mold it to the needs of this application
 * It configures the in and outgoing traffic from the Controllers
 */
public class WebConfig implements WebMvcConfigurer {
    @Override
    /**
     * This restricts Mapping Requests for different URLs, as to not have unwanted traffic, that needs to be handled
     * by the Controller
     */
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedMethods("GET", "POST");
    }

    @Bean
    /**
     * This loads a config regarding the Upload of multipart Files to this Application.
     * It will only be used for testing purposes and will be removed before deployment
     */
    MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    /**
     * This implements an internal Resource View Resolver to prohibit users from directly accessing webpages.
     * This will instead redirect them to the correct file using prefixes and suffixes
     *
     * E.g. /home could be directed to /WEB-INF/home.jsp if the prefix is /WEB-INF/views and the suffix is .jsp
     */
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        return bean;
    }

    @Override
    /**
     * This implements the FrontController, in Spring called DispatcherServlet.
     *
     * It handles every incoming HTTPrequest and forwards it to the specific controllers and viewresolvers.
     * It also handles all of the outgoing traffic, so it will be activated again by controllers after they handled the request
     */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
