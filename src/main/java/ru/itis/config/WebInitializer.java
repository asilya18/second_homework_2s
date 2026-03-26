package ru.itis.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class WebInitializer extends AbstractDispatcherServletInitializer {
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }

    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(Config.class, MvcConfigurer.class);
        return context;
    }

    protected String[] getServletMappings() {
        return new String[]{"/"}; // все запросы идут через dispatcherServlet
    }
}
