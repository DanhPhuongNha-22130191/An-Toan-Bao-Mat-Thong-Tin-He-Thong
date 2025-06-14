package com.atbm.config;

import com.atbm.filter.AuthenticationFilter;
import com.atbm.filter.AuthorizationFilter;
import com.atbm.filter.EncodingFilter;
import com.atbm.filter.ExceptionHandlerFilter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

import java.util.EnumSet;

@WebListener
public class WebInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        FilterRegistration.Dynamic authenticationFilter = context.addFilter(
                "AuthenticationFilter", AuthenticationFilter.class
        );
        authenticationFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                false,
                "/user/*", "/admin/*"
        );
        FilterRegistration.Dynamic authorizationFilter = context.addFilter(
                "AuthorizationFilter", AuthorizationFilter.class
        );
        authorizationFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                false,
                "/user/*", "/admin/*"
        );
        FilterRegistration.Dynamic exceptionHandlerFilter = context.addFilter(
                "ExceptionHandlerFilter", ExceptionHandlerFilter.class
        );
        exceptionHandlerFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                false,
                "/*"
        );
        FilterRegistration.Dynamic encodingFilter = context.addFilter("EncodingFilter", EncodingFilter.class);
        encodingFilter.addMappingForUrlPatterns(
                EnumSet.of(DispatcherType.REQUEST),
                false, "/*");

    }
}
