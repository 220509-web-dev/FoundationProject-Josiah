package dev.josiah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.filters.ExampleFilter;
import dev.josiah.servlets.LoginPageServlet;
import dev.josiah.servlets.LoginServlet;
import dev.josiah.servlets.LoginServletJSON;
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
        */ // unnecessary

        // Instantiate Servlet Objects
        LoginPageServlet loginPageServlet = new LoginPageServlet();
        LoginServlet loginServlet = new LoginServlet(userDAO, upDAO);
        UserServiceServlet userServiceServlet = new UserServiceServlet(userDAO);
        LoginServletJSON loginServletJSON = new LoginServletJSON(mapper, userDAO, upDAO);

        // registration
        context.addServlet("LoginPageServlet", loginPageServlet).addMapping("/login");
        context.addServlet("UserServiceServlet", userServiceServlet).addMapping("/userauth");
        context.addServlet("LoginServletJSON", loginServletJSON).addMapping("/loginjson");


        // LoginServlet dynamic registration
        ServletRegistration.Dynamic registeredServlet = context.addServlet("LoginServlet", loginServlet);
        registeredServlet.setLoadOnStartup(3);
        registeredServlet.setInitParameter("user-servlet-key1", "user-servlet-value");
        registeredServlet.addMapping("/loginuser/*");
        // Servlet registered and will handle all /login/ or /login/anything

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
    }
}
