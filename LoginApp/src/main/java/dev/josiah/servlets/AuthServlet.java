package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.AuthExceptions;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;
import dev.josiah.services.ServiceGetUserById;
import dev.josiah.services.ServiceGetUserByUsername;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.IllegalFormatException;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.ServiceGetAllUsers.ServiceAllUsersRequest;
import static dev.josiah.services.ServiceLogin.login;

@AllArgsConstructor
public class AuthServlet extends HttpServlet {
    private final static String name = "AuthServlet";
    private final ObjectMapper mapper;
    private final UserDAO userDAO;
    private final UserPrivDAO upDAO;

    @Override public void init() { System.out.println("[LOG] - "+name+" instantiated!"); }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String self_loc = "/login-service/userauth";
        String uri = req.getRequestURI().replace(self_loc, "");

        if (!(uri.equals("/login") || uri.equals("/register"))) {
            String complaint = "Unhandled URI posted to: " + self_loc + uri;
            Complain(complaint);
            System.out.println(complaint);
            resp.setContentType("text/html");
            resp.getWriter().write(uri+" is not a supported URI");
            return;
        }
        if (uri.equals("/login")) {
            UserPass userPass = new UserPass();
            String feedback = "";
            try {
                userPass = mapper.readValue(req.getInputStream(), UserPass.class);
            } catch (Throwable t) {
                resp.setStatus(400);
                feedback = "Invalid input";
                resp.setContentType("text/html");
                resp.getWriter().write(feedback);
                return;
            }
            String username = userPass.getUsername();
            String password = userPass.getPassword();

            if (username == null || password == null) {
                resp.setStatus(400);
                feedback = "Invalid input";
            }
            try {
                login(userDAO, upDAO, username, password);
                feedback = "Data Entered: \nUsername : " + username;
                feedback += "\nPassword : " + password;

            } catch (Throwable t){
                resp.setStatus(400);
                feedback = "Invalid input";
            }

            resp.setContentType("text/html");
            resp.getWriter().write(feedback);
        }

        if (uri.equals("/register")) {
            resp.setContentType("text/html");
            resp.getWriter().write("Register not implemented");
            return;
        }

    }

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
                System.out.println("##############################");
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
            } catch (AuthExceptions.ValueOutOfRangeException e) {
                feedback = "Username length was incorrect";
                resp.setStatus(500);
            } catch (AuthExceptions.UsernameFormatException e) {
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
                feedback = "<h2>Got back from service layer</h2>\n <p>" + users + "</p>";
                // TODO : prepare for JS to put in nice HTML

            } catch (AuthExceptions.UserNotFoundException e) {
                feedback = "No users exist in database";
                resp.setStatus(200);
            } catch (SQLException e) {
                feedback = "There was a problem with the database";
                resp.setStatus(500);
            }
            catch (Throwable t) {
                Complain(t);
                feedback = "An internal error occurred.";
                resp.setStatus(500);
            }
            System.out.println(feedback);
            resp.setContentType("text/html");
            resp.getWriter().write(feedback);
        }
    }
}
