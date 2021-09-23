package notificationsystem.configuration;

import lombok.extern.apachecommons.CommonsLog;
import notificationsystem.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
@CommonsLog
public class APIFilter implements Filter {

    Controller controller;

    @Autowired
    public APIFilter(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!(httpServletRequest.getRequestURL().toString().endsWith("/postSubscription")
            || httpServletRequest.getRequestURL().toString().endsWith("/postUnsubscribe")
            || httpServletRequest.getRequestURL().toString().contains("/getSubscriptions"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            log.info("No authentication cookie.");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        for (Cookie cookie : cookies) {
            log.info(cookie.getName());
            log.info(cookie.getValue());
            log.info(controller.getHashMap().get(cookie.getName()));
            if (cookie.getValue().equals(controller.getHashMap().get(cookie.getName()))) {
                log.info("Successful authentication");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        log.info("Wrong authentication cookie.");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }


}
