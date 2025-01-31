package com.expense.tracker.auth.config;


import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    //this will automatically registered in spring because of component
    //this will log all the headers of the request

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        httpRequest.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            logger.info("Header: {} = {}", headerName, httpRequest.getHeader(headerName));
        });

        filterChain.doFilter(servletRequest, servletResponse);

    }

}