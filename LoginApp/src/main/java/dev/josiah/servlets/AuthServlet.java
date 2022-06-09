package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.entities.User;
import dev.josiah.services.ServiceGetUserById;
import dev.josiah.services.ServiceGetUserByUsername;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.IllegalFormatException;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.ServiceGetAllUsers.ServiceAllUsersRequest;

@AllArgsConstructor
public class AuthServlet extends HttpServlet {
    private final static String name = "AuthServlet";
    private final ObjectMapper mapper;
    private final UserDAO userDAO;
    private final UserPrivDAO upDAO;

    @Override public void init() { System.out.println("[LOG] - "+name+" instantiated!"); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        System.out.println("[LOG] - " + name + " received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request query string: " + req.getQueryString());

        String potentialId = req.getParameter("id");
        String potentialUsername = req.getParameter("username");

        if (potentialId != null) {
            System.out.println("UserServiceServlet is forwarding to services ID : " + potentialId);
            String feedback;
            try {
                User user = ServiceGetUserById.ServiceIdRequest(potentialId, userDAO);
                feedback = "Got back from service layer with " + user;
                // TODO : prepare for JS to put in nice HTML

            } catch (AuthExceptions.UserNotFoundException e) {
                feedback = "User not found";
                resp.setStatus(400);
            } catch (AuthExceptions.InputWasNullException e) {
                feedback = "Form input was blank";
                resp.setStatus(400);
            } catch (AuthExceptions.InputNotAnIntegerException e) {
                feedback = "Input must be an integer";
                resp.setStatus(400);
            } catch (NumberFormatException e) {
                feedback = "Please enter a whole number";
                resp.setStatus(400);
            } catch (AuthExceptions.ValueOutOfRangeException e) {
                feedback = "ID inputted was out of bounds";
                resp.setStatus(400);
            } catch (SQLException e) {
                feedback = "There was a problem with the database";
                resp.setStatus(500);
            } catch (Throwable t) { // happens if service or DAO layer throws anything
                Complain(t);
                feedback = "Invalid Input.";
                resp.setStatus(400);
            }
            System.out.println(feedback);
            resp.setContentType("text/html");
            resp.getWriter().write(feedback); // TODO : Prepare for JS to HTML
            return;
        }

        // This line is reached only if (potentialId == null)
        if (potentialUsername != null) {
            System.out.println("UserServiceServlet is forwarding to services Username : " + potentialUsername);
            String feedback;
            try {
                User user = ServiceGetUserByUsername.ServiceUsernameRequest(potentialUsername, userDAO);
                feedback = "Got back from service layer with " + user;
                // TODO : prepare for JS to put in nice HTML

            } catch (AuthExceptions.UserNotFoundException e) {
                feedback = "User not found";
                resp.setStatus(400);
            } catch (AuthExceptions.InputWasNullException e) {
                feedback = "Form input was blank";
                resp.setStatus(400);
            } catch (IllegalFormatException e) {
                feedback = "Username must end with @revature.net";
                resp.setStatus(500);
            } catch (AuthExceptions.IllegalCharacterException e) {
                feedback = "Username contained an illegal character";
                resp.setStatus(400);
            } catch (SQLException e) {
                feedback = "There was a problem with the database";
                resp.setStatus(500);
            } catch (Throwable t) { // happens if service or DAO layer throws anything
                Complain(t);
                feedback = "Invalid Input.";
                resp.setStatus(500);
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
    }

}
