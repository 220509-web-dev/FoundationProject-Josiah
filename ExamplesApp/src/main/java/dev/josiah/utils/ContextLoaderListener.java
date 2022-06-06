package dev.josiah.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.servletExamples.LoaderRegisteredServlet;

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
        System.out.println("ContextLoaderListener FLAG 1");

        LoaderRegisteredServlet testServlet = new LoaderRegisteredServlet(mapper);
        System.out.println("ContextLoaderListener FLAG 2");

        // Obtain the context from ServletContextEvent
        ServletContext context = sce.getServletContext();
        System.out.println("ContextLoaderListener FLAG 3");

        // replaced with dynamic
        //context.addServlet("LoaderRegisteredServlet", testServlet).addMapping("/testloaded/*");
        // Servlet registered and will handle all /* or /* anything
        System.out.println("ContextLoaderListener FLAG 4");

        ServletRegistration.Dynamic registeredServlet = context.addServlet("LoaderRegisteredServlet", testServlet);
        registeredServlet.setLoadOnStartup(3);
        registeredServlet.setInitParameter("user-servlet-key1", "user-servlet-value");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
    }
}
