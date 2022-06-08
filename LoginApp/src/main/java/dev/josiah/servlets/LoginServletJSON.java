package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoginServletJSON extends HttpServlet {
    private final static String name = "LoginServletJSON";
    private final ObjectMapper mapper;
    private final UserDAO userDAO;
    private final UserPrivDAO upDAO;

    public LoginServletJSON(ObjectMapper mapper, UserDAO userDAO, UserPrivDAO upDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
        this.upDAO = upDAO;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - "+name+" instantiated!");
        System.out.println("[LOG] - Init param, test-init-key: " + this.getServletConfig().getInitParameter("test-init-key"));
        System.out.println("[LOG] - Context param, test-context-key: " + this.getServletContext().getInitParameter("test-context-key"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - "+name+" received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request query string: " + req.getQueryString());

}}
 /*
    fetch('/test/auth/login', {
        method: 'POST',
        body: JSON.stringify({username,password})
    }).then(response=> response.json().then(data =>
        if (response.ok) {
            let user = data;
            header.innerHTML = "Welcome " + user.firstName + " " + user.lastName;
            //console.log(user);
        } else {
            error.innerHTML = "invalid username + password";
        }
    })).catch(()=>{
        error.innerHTML = "Issue attempting to log in";
    });
     */