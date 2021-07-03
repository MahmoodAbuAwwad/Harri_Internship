package com.Harri.InvoiceTrackerBE.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Value("${origin}")
    private String origin;
    public static String VALID_METHODS = "DELETE, HEAD, GET, OPTIONS, POST, PUT";


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        String originValue;
        if (request.getHeader("Origin") != null) {
            originValue = request.getHeader("Origin");
        } else if (request.getHeader("origin") != null) {
            originValue = request.getHeader("origin");
        } else {
            originValue = origin;
        }

        if(originValue.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            String headers = request.getHeader("Access-Control-Request-Headers");
            if (headers == null) {
                headers = "Accept,Content-Type,If-Modified-Since,Origin,X-Requested-With,X-CSRFToken,Authorization";
            }

            response.setHeader("Access-Control-Allow-Headers", headers);
        }
        // We can do further validation of our tokens here as well
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Allow", VALID_METHODS);
            response.setStatus(200);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}