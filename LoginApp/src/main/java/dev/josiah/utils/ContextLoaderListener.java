package dev.josiah.utils;

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

        // Instantiate necessary Objects
        ObjectMapper mapper = new ObjectMapper();
        UserDAO userDAO = new UserDaoPostgres();
        UserPrivDAO upDAO = new UserPrivDaoPostgres();

        // Obtain the context from ServletContextEvent
        ServletContext context = sce.getServletContext();

        // Instantiate Servlet Objects
        LoginPageServlet loginPageServlet = new LoginPageServlet();
        AuthServlet authServlet = new AuthServlet(mapper, userDAO, upDAO);
        UserServlet userServlet = new UserServlet(userDAO);

        // registration
        context.addServlet("LoginPageServlet", loginPageServlet).addMapping("/login/*");
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");

        // AuthServlet dynamic registration
        ServletRegistration.Dynamic registeredServlet = context.addServlet("AuthServlet", authServlet);
        registeredServlet.setLoadOnStartup(3);
        registeredServlet.setInitParameter("user-servlet-key1", "user-servlet-value");
        registeredServlet.addMapping("/userauth/*"); // handle anything starting with /userauth

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
    }
}
