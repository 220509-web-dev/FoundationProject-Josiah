package dev.josiah.servletExamples;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class LoaderRegisteredServlet extends HttpServlet {
    private final static String name = "LoaderRegisteredServlet";
    private final ObjectMapper mapper;

    public LoaderRegisteredServlet(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - "+name+" instantiated!");
        System.out.println("[LOG] - Init param, test-init-key: " + this.getServletConfig().getInitParameter("servlet-key1"));
        System.out.println("[LOG] - Context param, test-context-key: " + this.getServletContext().getInitParameter("all-servlet-key1"));
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - "+name+" received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request query string: " + req.getQueryString());

        resp.setStatus(200); // it is 200 by default
        resp.setHeader("Content-type", "text/html");
        ObjectMapper mapper = new ObjectMapper();
        User user = new User(999, "", "", "", "", "", "", "", "");
        String payload = mapper.writeValueAsString(user);
        resp.setContentType("application/json");
        resp.getWriter().write(payload);


    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - "+name+" received a POST request at " + LocalDateTime.now());
        BufferedReader payloadReader = new BufferedReader(new InputStreamReader(req.getInputStream()));


        String payload = "";
        String line;
        while ((line = payloadReader.readLine()) != null) payload += line;

        try {
            System.out.println("Hello");
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(payload, User.class);
            // Jackson takes the input stream and converts it into a User object
            System.out.println(user);



        } catch (Exception e) {
            Complain(e);
            e.printStackTrace();
        }

        resp.setStatus(204);
    }

}
