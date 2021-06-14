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
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**").allowedMethods("GET", "POST");
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    public ViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        return bean;
    }
    @Bean
    public ViewResolver resourceBundleViewResolver() {
        ResourceBundleViewResolver bean = new ResourceBundleViewResolver();
        bean.setBasename("views");
        return bean;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
