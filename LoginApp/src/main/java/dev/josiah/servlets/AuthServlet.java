package dev.josiah.servlets;

import com.fasterxml.jackson.databind.DeserializationFeature;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;
import static dev.josiah.services.ServicePost.login;
import static dev.josiah.services.ServicePost.register;


public class AuthServlet extends HttpServlet {
    private final static String name = "AuthServlet";
    private static ObjectMapper mapper;
    private final UserDAO userDAO;
    private final UserPrivDAO upDAO;
    private static final ArrayList<String> supportedDestinations =
            new ArrayList<String>(Arrays.asList("login", "register"));

    public AuthServlet(ObjectMapper mapper, UserDAO userDAO, UserPrivDAO upDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
        this.upDAO = upDAO;
    }

    @Override public void init() { System.out.println("[LOG] - "+name+" instantiated!"); }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HashMap<String, Object> errorMessage = new HashMap<>();
        HashMap<String, Object> message = new HashMap<>();

        String destination = "none";
        String[] supportedDestinations = {"login", "register"};

        // Determine the intended destination

        /*
        // for whatever reason, it only successfully maps the first one regardless of input
        try {
            ObjectMapper mapper3 = new ObjectMapper();
            mapper3.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            userInfo = mapper3.readValue(req.getInputStream(), UserInfo.class);
            destination = "register";
        } catch (Throwable t) {System.out.println("Couldn't map to UserInfo");}
        try {
            ObjectMapper mapper2 = new ObjectMapper();
            mapper2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            userPass = mapper2.readValue(req.getInputStream(), UserPass.class);
            destination = "login";
        } catch (Throwable t) { System.out.println("Couldn't map to UserPass");}

        Boolean supported = false;
        for (String loc: supportedDestinations) {
            if(destination.equals(loc)) {
                supported = true;
                break;
            }
        }

        if (!supported) { Send(400, "Invalid Request", resp); return;   }
         */

        String loc = "/login-service/userauth";
        String uri = req.getRequestURI().replace(loc,"");
        System.out.println(uri);

        destination = uri.replace("/","");
        if (!destination.equals("login") && !destination.equals("register")) {
            Send(400, "Invalid Request", resp); return;
        }

        if (destination.equals("login")) {
            try {
                UserPass userPass = mapper.readValue(req.getInputStream(), UserPass.class);
                System.out.println(userPass);
                User user = login(userDAO, upDAO, userPass);
                //resp.setStatus(204);  // logged in, no other content
                HttpSession session = req.getSession(); // use req.getSession(false) to prevent a session from being made
                session.setAttribute("auth-user", user);
                                                    Send(204, "Logged in",                             resp); return; }
            catch (UserNotFoundException e) {       Send(404, "User not found",                        resp); return;}
            catch (IllegalCharacterException e) {   Send(400, "Input contained an illegal character",  resp); return; }
            catch (UsernameFormatException e) {     Send(400, "Username must end with @revature.net",  resp); return; }
            catch (InputWasNullException e) {       Send(400, "An input field was left blank",         resp); return; }
            catch (ValueOutOfRangeException e) {    Send(400, "An input was too long or short",        resp); return; }
            catch (InvalidCredentialsException e) { Send(401, "Invalid credentials",                   resp); return; }
            catch (SQLException e) { Complain(e);   Send(500, "There was a problem with the database", resp); return; }
            catch (Throwable t) {    Complain(t);   Send(500, "An unknown error occurred",             resp); return; }
        }

        if (destination.equals("register")) {
            try {
                System.out.println("Flag 1 :)");
                UserInfo userInfo = mapper.readValue(req.getInputStream(), UserInfo.class);
                System.out.println("Flag 2 :)");
                register(userDAO, upDAO, userInfo);
                System.out.println("Registration succeeded...");
//                UserPass userPass = new UserPass(userInfo.getUsername(),userInfo.getPassword());
//                login(userDAO,upDAO,userPass);  System.out.println("And Login also");
                                                      Send(204, "Registered",                              resp); return; }
            catch (InputWasNullException e) {         Send(400, "Form input was blank",                    resp); return; }
            catch (ValueOutOfRangeException e) {      Send(400, "Username length was incorrect",           resp); return; }
            catch (UsernameFormatException e) {       Send(400, "Username must end with @revature.net",    resp); return; }
            catch (IllegalCharacterException e) {     Send(400, "Username contained an illegal character", resp); return; }
            catch (UsernameNotAvailableException e) { Send(409, "Username already taken!",                 resp); return; }
//            catch (UserNotFoundException e) {
//                Complain(" just registered, but now can't login.");
//                                   Complain(e);     Send(404, "User not found",                          resp); return; }
//            catch (InvalidCredentialsException e) {
//                Complain(" just registered, but now can't login.");
//                                   Complain(e);     Send(401, "Invalid Credentials",                     resp); return; }
            catch (SQLException e) { Complain(e);     Send(500, "There was a problem with the database",   resp); return; }
            catch (Throwable t) {    Complain(t);     Send(500, "An unknown error occurred",               resp); return; }
        }
    }
    private static void Send(int code, String msg, HttpServletResponse resp) {
        try {
            HashMap<String, Object> message = new HashMap<>();
            resp.setStatus(code);
            resp.setContentType("application/json");
            message.put("code", code);
            message.put("message", msg);
            message.put("timestamp", LocalDateTime.now().toString());
            resp.getWriter().write(mapper.writeValueAsString(message));
        } catch (Throwable t) {
            Complain(t);
            System.out.println("Error in "+name+". Can't return anything!");
        }
        return;
    }
}
