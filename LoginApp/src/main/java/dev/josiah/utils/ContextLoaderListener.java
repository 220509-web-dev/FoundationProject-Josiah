package dev.josiah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.servlets.LoginServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import java.time.LocalDateTime;

public class ContextLoaderListener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was initialized at " + LocalDateTime.now());
        ObjectMapper mapper = new ObjectMapper();

        LoginServlet loginServlet = new LoginServlet(mapper);

        // Obtain the context from ServletContextEvent
        ServletContext context = sce.getServletContext();

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
