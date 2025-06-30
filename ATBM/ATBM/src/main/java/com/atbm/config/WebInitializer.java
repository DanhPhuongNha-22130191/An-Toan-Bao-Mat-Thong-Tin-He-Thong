package com.atbm.config;

import com.atbm.filter.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;

import java.util.EnumSet;

@WebListener
public class WebInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
//        FilterRegistration.Dynamic corsBlockFilter = context.addFilter("CorsBlockFilter", CorsBlockFilter.class);
//        corsBlockFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

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
