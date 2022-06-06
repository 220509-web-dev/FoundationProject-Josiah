package dev.josiah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.filters.ExampleFilter;
import dev.josiah.servlets.LoginServlet;

import javax.servlet.*;
import java.time.LocalDateTime;
import java.util.EnumSet;

public class ContextLoaderListener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was initialized at " + LocalDateTime.now());
        ObjectMapper mapper = new ObjectMapper();

        // Obtain the context from ServletContextEvent
        ServletContext context = sce.getServletContext();

        /*
        // Register ExampleFilter
        // TODO : Make this registration work, then delete web.xml filter registration
        ExampleFilter exampleFilter = new ExampleFilter();
        context.addFilter("ExampleFilter", exampleFilter)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.INCLUDE), true, "/*");
                // intercept everything with "/*"
        */

        LoginServlet loginServlet = new LoginServlet(mapper);

        // replaced with dynamic
        //context.addServlet("LoginServlet", loginServlet).addMapping("/login/*");

        ServletRegistration.Dynamic registeredServlet = context.addServlet("LoginServlet", loginServlet);
        registeredServlet.setLoadOnStartup(3);
        registeredServlet.setInitParameter("user-servlet-key1", "user-servlet-value");
        registeredServlet.addMapping("/login/*");
        // Servlet registered and will handle all /login/ or /login/anything

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
    }
}
