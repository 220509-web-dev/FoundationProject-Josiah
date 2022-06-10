package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.ServiceGet.*;


@AllArgsConstructor
public class UserServlet extends HttpServlet {
    private final static String name = "UserServlet";
    private final ObjectMapper mapper;
    private final UserDAO userDAO;

    @Override public void init() { System.out.println("[LOG] - "+name+" instantiated!"); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("[LOG] - " + name + " received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request query string: " + req.getQueryString());

        String potentialId = req.getParameter("id");
        String potentialUsername = req.getParameter("username");
        HashMap<String, Object> errorMessage = new HashMap<>();

        // TODO : Should instead be returning a webpage with the message or result nested
        if (potentialId != null) {
            System.out.println(potentialId+" being sent from UserServlet to the service layer");
            try {
                User user = ServiceIdRequest(potentialId, userDAO);
                resp.setStatus(200);  // unnecessary
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(user));
                return;
            } catch (UserNotFoundException e) {
                resp.setStatus(404);  // ANY RESOURCE NOT FOUND IS 404
                errorMessage.put("code", 404);
                errorMessage.put("message", "User not found");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (InputWasNullException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Form input was blank");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (InputNotAnIntegerException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Input must be an integer");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (NumberFormatException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Please enter a whole number");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (ValueOutOfRangeException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "ID inputted was out of bounds");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (SQLException e) {
                resp.setStatus(500); // MAYBE, BUT DEPENDS ON WHAT KIND OF SQL EXCEPTION
                errorMessage.put("code", 500);
                errorMessage.put("message", "There was a problem with the database");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (Throwable t) {
                Complain(t);
                resp.setStatus(500);
                errorMessage.put("code", 500);
                errorMessage.put("message", "An unknown error occurred");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            }
        }

        if(potentialUsername != null) {
            System.out.println(potentialUsername + " being sent from UserServlet to the service layer");
            try {
                User user = ServiceUsernameRequest(potentialUsername, userDAO);
                resp.setStatus(200);  // unnecessary
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(user));
                return;
            } catch (UserNotFoundException e) {
                resp.setStatus(404);
                errorMessage.put("code", 404);
                errorMessage.put("message", "User not found");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (InputWasNullException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Form input was blank");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (ValueOutOfRangeException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Username length was incorrect");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (UsernameFormatException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Username must end with @revature.net");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (IllegalCharacterException e) {
                resp.setStatus(400);
                errorMessage.put("code", 400);
                errorMessage.put("message", "Username contained an illegal character");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (SQLException e) {
                resp.setStatus(500);
                errorMessage.put("code", 500);
                errorMessage.put("message", "There was a problem with the database");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (Throwable t) {
                Complain(t);
                resp.setStatus(500);
                errorMessage.put("code", 500);
                errorMessage.put("message", "An unknown error occurred");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            }
        }


        System.out.println("UserServlet is forwarding to services request for all user data");
        try {
            List<User> users = ServiceAllUsersRequest(userDAO);
            resp.setStatus(200);  // unnecessary
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(users));
            return;
        } catch (UserNotFoundException e) {
            resp.setStatus(204);
            HashMap<String, Object> message = new HashMap<>();
            message.put("code", 204); // successful request, but no data to return
            message.put("message", "No users exist in database");
            message.put("timestamp", LocalDateTime.now().toString());
            resp.getWriter().write(mapper.writeValueAsString(message));
            return;
        } catch (SQLException e) {
            resp.setStatus(500);
            errorMessage.put("code", 500);
            errorMessage.put("message", "There was a problem with the database");
            errorMessage.put("timestamp", LocalDateTime.now().toString());
            resp.getWriter().write(mapper.writeValueAsString(errorMessage));
            return;
        } catch (Throwable t) {
            Complain(t);
            resp.setStatus(500);
            errorMessage.put("code", 500);
            errorMessage.put("message", "An unknown error occurred");
            errorMessage.put("timestamp", LocalDateTime.now().toString());
            resp.getWriter().write(mapper.writeValueAsString(errorMessage));
            return;
        }
    }
}
