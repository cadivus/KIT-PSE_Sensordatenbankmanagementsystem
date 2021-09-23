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

/**
 * A custom filter that checks if the entity trying to consume the API has a valid cookie.
 */
@Component
@Order(1)
@CommonsLog
public class APIFilter implements Filter {

    private final Controller controller;
    private final static String POSTSUBSCRIPTION_URI = "/postSubscription";
    private final static String POSTUNSUBSCRIBE_URI = "/postUnsubscribe";
    private final static String GETSUBSCRIPTION_URI = "/getSubscriptions";
    private final static String LOG_NOCODE = "No authentication cookie.";
    private final static String LOG_SUCCESS = "Successful authentication.";
    private final static String LOG_WRONGCODE = "Wrong authentication cookie.";

    @Autowired
    public APIFilter(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!(httpServletRequest.getRequestURL().toString().endsWith(POSTSUBSCRIPTION_URI)
            || httpServletRequest.getRequestURL().toString().endsWith(POSTUNSUBSCRIBE_URI)
            || httpServletRequest.getRequestURL().toString().contains(GETSUBSCRIPTION_URI))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            log.info(LOG_NOCODE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getValue().equals(controller.getHashMap().get(cookie.getName()))) {
                log.info(LOG_SUCCESS);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        log.info(LOG_WRONGCODE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
    }


}
