package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserPass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.ServiceLogin.login;

// Handles Login and Register
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

//AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);

        UserPass userPass;
        try {
            userPass = mapper.readValue(req.getInputStream(), UserPass.class);
        } catch (Throwable e) {
            System.out.println("Invalid input in JSON servlet");
            resp.setStatus(400);
            return;
            // TODO : return something to user's JS to show
            // this happens because of the browser or code, not user's input
        }
        System.out.println("Got the data: " + userPass);
        String username = userPass.getUsername();
        String password = userPass.getPassword();

        // Henceforth, copy+paste from LoginServlet
        String message = "";
        int responseCode;
        try {
            responseCode = login(userDAO,upDAO,username,password);
            switch (responseCode) {
                case 0: message = "Logged in"; break;
                case 1: message = "User not found"; break;
                case 2: message = "Incorrect password"; break;
                case 3: message = "An internal error occurred"; break;
                default: message = "You shouldn't be getting this message"; break;
            }
        } catch (Throwable t) {
            Complain(t);
            message = "Invalid input";
        }

        resp.setContentType("text/html");
        resp.getWriter().write(message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - "+name+" received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request query string: " + req.getQueryString());
        String self_loc = "/login-service/loginjson";
        String uri = req.getRequestURI().replace(self_loc, "");

        if (uri.equals("/login")) {
            System.out.println("Doing JSON login");
            try {
                UserPass userPass = mapper.readValue(req.getInputStream(), UserPass.class);
                System.out.println("input mapped to " + userPass);
            } catch (Throwable t) {
                System.out.println("Invalid JSON in POST request to " + name);
            }

        }
        if (uri.equals("/register")) {
            System.out.println("Doing JSON register");

        }


    }
}
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