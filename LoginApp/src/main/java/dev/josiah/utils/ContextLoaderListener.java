package dev.josiah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.filters.ExampleFilter;
import dev.josiah.servlets.LoginServlet;
import dev.josiah.servlets.UserServiceServlet;

import javax.servlet.*;
import java.time.LocalDateTime;
import java.util.EnumSet;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was initialized at " + LocalDateTime.now());

        // Instantiate necessary Objects
        ObjectMapper mapper = new ObjectMapper();
        UserDAO userDAO = new UserDaoPostgres();
        UserPrivDAO upDAO = new UserPrivDaoPostgres();

        // Obtain the context from ServletContextEvent
        ServletContext context = sce.getServletContext();

        // Register Filter (done in web.xml for now)
        /*
        // Register ExampleFilter
        // TODO : Make this registration work, then delete web.xml filter registration
        ExampleFilter exampleFilter = new ExampleFilter();
        context.addFilter("ExampleFilter", exampleFilter)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.INCLUDE), true, "/*");
                // intercept everything with "/*"
        */

        // Instantiate Servlet Objects
        LoginServlet loginServlet = new LoginServlet(mapper, userDAO, upDAO);
        UserServiceServlet userServiceServlet = new UserServiceServlet(mapper, userDAO);

        // userServiceServlet registration
        context.addServlet("UserServiceServlet", userServiceServlet).addMapping("/userauth/*");

        // LoginServlet dynamic registration
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
