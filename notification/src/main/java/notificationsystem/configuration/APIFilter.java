package notificationsystem.configuration;

import lombok.extern.apachecommons.CommonsLog;
import notificationsystem.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    Controller controller;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            log.info("No authentication cookie.");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getValue().equals(controller.getHashMap().get(cookie.getName()))) {
                log.info("Successful authentication");
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        log.info("No authentication cookie.");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}