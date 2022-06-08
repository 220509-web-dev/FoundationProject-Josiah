package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;
import dev.josiah.entities.WebPage;
import dev.josiah.services.ServiceGetUserById;
import dev.josiah.services.ServiceGetUserByUsername;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.AuthUser.authenticate;
import static dev.josiah.services.ServiceGetAllUsers.ServiceAllUsersRequest;

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

        System.out.println("potentialId = " + potentialId);
        System.out.println("potentialId is"+((potentialId == null)? "":" not")+" null");

        System.out.println("potentialUsername = " + potentialUsername);
        System.out.println("potentialUsername is"+((potentialUsername == null)? "":" not")+" null");

        if (potentialId != null) {
            System.out.println("UserServiceServlet is forwarding to services ID : " + potentialId);
            String feedback;
            try {
                User user = ServiceGetUserById.ServiceIdRequest(potentialId, userDAO);
                feedback = "Got back from service layer with " + user;
                if (user == null) {
                    feedback = "User not found!";
                    resp.setStatus(200);
                }
            } catch (Throwable t) { // happens if service or DAO layer throws anything
                Complain(t);
                // not throwing an exception
                feedback = "Invalid Input.";
                resp.setStatus(400);
                // throw new RuntimeException(); // I don't want to throw an error here because ...
                // Throwing an error here gives a stacktrace to the website visitor
                // But I don't want them to see the servlet structure
            }
            System.out.println(feedback);
            resp.setContentType("text/html");
            resp.getWriter().write(feedback);
            return;
        }

        // This line is reached only if (potentialId == null)
        if (potentialUsername != null) {
            System.out.println("UserServiceServlet is forwarding to services Username : " + potentialUsername);
            String feedback;
            try {
                User user = ServiceGetUserByUsername.ServiceUsernameRequest(potentialUsername, userDAO);
                feedback = "Got back from service layer with " + user;
                if (user == null) {
                    feedback = "User not found!";
                    resp.setStatus(200);
                }
            } catch (Throwable t) { // happens if service or DAO layer throws anything
                Complain(t);
                // not throwing an exception
                feedback = "Invalid Input.";
                resp.setStatus(400);
                // throw new RuntimeException(); // I don't want to throw an error here because ...
                // Throwing an error here gives a stacktrace to the website visitor
                // But I don't want them to see the servlet structure
            }
            System.out.println(feedback);
            resp.setContentType("text/html");
            resp.getWriter().write(feedback);
            return;
        }
        if (true) {  // (potentialId == null && potentialUsername == null)
            System.out.println("UserServiceServlet is forwarding to services request for all user data");
            String feedback;
            try {
                List<User> users = ServiceAllUsersRequest(userDAO);
                if (users == null) {
                    feedback = "No users found.";
                } else {
                    feedback = "<h2>Got back from service layer</h2>\n <p>" + users + "</p>";
                }

            } catch (Throwable t) { // happens if service or DAO layer throws anything
                Complain(t);
                // not throwing an exception
                feedback = "An internal error occurred.";
                resp.setStatus(400);
                // throw new RuntimeException(); // I don't want to throw an error here because ...
                // Throwing an error here gives a stacktrace to the website visitor
                // But I don't want them to see the servlet structure
            }
            System.out.println(feedback);
            resp.setContentType("text/html");
            resp.getWriter().write(feedback);
        }

        /*
        if (potentialId != null) {
            // feed into service validation layer
                // there in service/authService, sees if we entered valid input
                // if the input is a proper integer, service layer uses the DAO
                // the results gets sent back to service layer then here
            //now, write to page the result
        }
        if (potentialUsername != null) {
            String feedback = authenticate(potentialUsername);
            resp.setContentType("text/html");
            resp.getWriter().write(feedback);
            // person sending request must want to look up a username
            // send to services
                // services makess sure the username is valid
                // can't be blank, can't just be spaces, no newlines, length > 0, has to end with @revature.net, etc
                // if it's good, query the database
                // return
        }
        // return all users

         */

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