package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.WebPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserServiceServlet  extends HttpServlet {
    private final static String name = "UserServiceServlet";
    private final ObjectMapper mapper;
    private final UserDAO userDAO;

    public UserServiceServlet(ObjectMapper mapper, UserDAO userDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - "+name+" instantiated!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - "+name+" received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        //System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request query string: " + req.getQueryString());

        String potentialId = req.getParameter("id");
        String potentialUsername = req.getParameter("username");

        if (potentialId != null) {

        }
        if (potentialUsername != null) {

        }
        // return all users

//        resp.setStatus(200); // it is 200 by default
//        resp.setHeader("Content-type", "text/html");
//        WebPage page = new WebPage("webpages/login.html");
//        System.out.println("Flag 1");
//
//        String htmlpage = page.getSource();
//        resp.setContentType("text/html");
//        resp.getWriter().write(htmlpage);
    }
}
