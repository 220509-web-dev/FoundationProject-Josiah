package dev.josiah.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.servlets.*;

import javax.servlet.*;
import java.time.LocalDateTime;

public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was initialized at " + LocalDateTime.now());

        // Instantiate and configure necessary Objects
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserDAO userDAO = new UserDaoPostgres();
        UserPrivDAO upDAO = new UserPrivDaoPostgres();

        // Obtain the context from ServletContextEvent
        ServletContext context = sce.getServletContext();

        // Instantiate Servlet Objects
        AuthServlet authServlet = new AuthServlet(mapper, userDAO, upDAO);
        UserServlet userServlet = new UserServlet(mapper, userDAO);

        // registration
        context.addServlet("UserServlet", userServlet).addMapping("/users");

        // AuthServlet dynamic registration
        ServletRegistration.Dynamic registeredServlet = context.addServlet("AuthServlet", authServlet);
        registeredServlet.setLoadOnStartup(3);
        registeredServlet.addMapping("/userauth");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
    }
}
