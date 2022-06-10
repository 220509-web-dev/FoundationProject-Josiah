package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserInfo;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.ServicePost.login;
import static dev.josiah.services.ServicePost.register;


@AllArgsConstructor
public class AuthServlet extends HttpServlet {
    private final static String name = "AuthServlet";
    private final ObjectMapper mapper;
    private final ObjectMapper caster;
    private final UserDAO userDAO;
    private final UserPrivDAO upDAO;
    private static final ArrayList<String> supportedDestinations =
            new ArrayList<String>(Arrays.asList("login", "register"));

    @Override public void init() { System.out.println("[LOG] - "+name+" instantiated!"); }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> errorMessage = new HashMap<>();
        HashMap<String, Object> message = new HashMap<>();

        UserPass userPass = new UserPass();
        UserInfo userInfo = new UserInfo();

        String destination = "none";
        String[] supportedDestinations = {"login", "register"};

        // Determine the intended destination


        try {
            userInfo = mapper.readValue(req.getInputStream(), UserInfo.class);
            destination = "register";
        } catch (Throwable t) {System.out.println("Couldn't map to UserInfo");}
        try {
            userPass = mapper.readValue(req.getInputStream(), UserPass.class);
            destination = "login";
        } catch (Throwable t) { System.out.println("Couldn't map to UserPass");}

        Boolean supported = false;
        for (String loc: supportedDestinations) {
            if(destination.equals(loc)) {
                supported = true;
                break;
            }
        }

        if (!supported) {
            resp.setStatus(400);
            resp.setContentType("application/json");
            errorMessage.put("code", 400);
            errorMessage.put("message", "Invalid Request");
            errorMessage.put("timestamp", LocalDateTime.now().toString());

            resp.getWriter().write(mapper.writeValueAsString(errorMessage));
            return;
        }

        if (destination.equals("login")) {
            try {
                login(userDAO, upDAO, userPass);
                resp.setStatus(204);  // logged in, no other content
                resp.setContentType("application/json");
                message.put("code", 204); // successful request, but no data to return
                message.put("message", "Logged in");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (UserNotFoundException e) {
                resp.setStatus(404);
                resp.setContentType("application/json");
                message.put("code", 404);
                message.put("message", "User not found");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (IllegalCharacterException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                message.put("code", 400);
                message.put("message", "Input contained an illegal character");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (UsernameFormatException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                message.put("code", 400);
                message.put("message", "Username must end with @revature.net");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (InputWasNullException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                message.put("code", 400);
                message.put("message", "An input field was left blank");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (ValueOutOfRangeException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                message.put("code", 400);
                message.put("message", "Username or password length was too large or small");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (InvalidCredentialsException e) {
                resp.setStatus(401);
                resp.setContentType("application/json");
                message.put("code", 401);
                message.put("message", "Username length was incorrect");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (SQLException e) {
                resp.setStatus(500);
                resp.setContentType("application/json");
                message.put("code", 500);
                message.put("message", "There was a problem with the database");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (Throwable t) {
                Complain(t);
                resp.setContentType("application/json");
                resp.setStatus(500);
                message.put("code", 500);
                message.put("message", "An unknown error occurred");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            }
        }
        System.out.println(req.getLocalAddr());
        System.out.println("@@@@@@@@@@@@^^^^");
        if (destination.equals("register")) {
            try {
                register(caster, userDAO, upDAO, userInfo);
                userPass = caster.readValue(caster.writeValueAsString(userInfo), UserPass.class);
                login(userDAO,upDAO,userPass);
                resp.setStatus(204);  // registered, no other content
                resp.setContentType("application/json");
                message.put("code", 204); // successful request, but no data to return
                message.put("message", "Registered and logged in");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (InputWasNullException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                errorMessage.put("code", 400);
                errorMessage.put("message", "Form input was blank");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (ValueOutOfRangeException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                errorMessage.put("code", 400);
                errorMessage.put("message", "Username length was incorrect");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (UsernameFormatException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                errorMessage.put("code", 400);
                errorMessage.put("message", "Username must end with @revature.net");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (IllegalCharacterException e) {
                resp.setStatus(400);
                resp.setContentType("application/json");
                errorMessage.put("code", 400);
                errorMessage.put("message", "Username contained an illegal character");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (UsernameNotAvailableException e) {
                resp.setStatus(409);
                resp.setContentType("application/json");
                errorMessage.put("code", 409);
                errorMessage.put("message", "Username already taken!");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (UserNotFoundException e) {
                Complain(userInfo.getUsername() + " just registered, but now can't login.");
                Complain(e);
                resp.setStatus(404);
                resp.setContentType("application/json");
                errorMessage.put("code", 404);
                errorMessage.put("message", "User not found");
                errorMessage.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(errorMessage));
                return;
            } catch (InvalidCredentialsException e) {
                Complain(userInfo.getUsername() + " just registered, but now can't login.");
                Complain(e);
                resp.setStatus(401);
                resp.setContentType("application/json");
                message.put("code", 401);
                message.put("message", "Username length was incorrect");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            }  catch (SQLException e) {
                resp.setStatus(500);
                resp.setContentType("application/json");
                message.put("code", 500);
                message.put("message", "There was a problem with the database");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            } catch (Throwable t) {
                Complain(t);
                resp.setContentType("application/json");
                resp.setStatus(500);
                message.put("code", 500);
                message.put("message", "An unknown error occurred");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                return;
            }
        }
    }
}
