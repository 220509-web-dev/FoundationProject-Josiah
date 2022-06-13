package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.Token;
import dev.josiah.dtos.UserInfo;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;

import javax.servlet.ServletException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Send(405,"Method not allowed", resp);
        return;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String destination = "none";
        String[] supportedDestinations = {"login", "register"};

        System.out.println("[LOG] - AuthServlet received a POST request!");

        HashMap<String, Object> input = new HashMap<>();

        try {
//            userInfo = mapper.readValue(req.getInputStream(), UserInfo.class);
            input = mapper.readValue(req.getInputStream(), HashMap.class);
//            userInfo = mapper.readValue((DataInput) input, UserInfo.class);
            System.out.println("[LOG] - AuthServlet received input:");
            System.out.println(new String(new char[20]).replace("\0", "*"));
            for (String name: input.keySet()) {
                String key = name.toString();
                String value = input.get(name).toString();
                System.out.println(key + " " + value);
            }
            System.out.println(new String(new char[20]).replace("\0", "*"));
            //System.out.println(userInfo);
        } catch (Throwable t) {
            System.out.println("Couldn't map Input");
            Complain("Couldn't map Input");
            Complain(t);
            Send(400, "Bad Input", resp);  // mapper will map legitimate requests, so this shouldn't happen
            return;
        }

        // This code is lengthy and doesn't employ abstraction
        // it's just to make use of the ObjectMapper
        String[] regRequired = {"u","p","f","l","a1","a2","c","s","z"};
        String[] logRequired = {"u","p"};
        Boolean isRegister = true;
        Boolean isLogin = true;

        for (String name: input.keySet()) {
            Boolean found = false;
            for (String s : regRequired) {
                if (s.equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                isRegister = false;
                break;
            }
        }
        for (String name: input.keySet()) {
            Boolean found = false;
            for (String s : logRequired) {
                if (s.equals(name)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                isLogin = false;
                break;
            }
        }

        if (isRegister && input.keySet().size()!=regRequired.length) isRegister=false;
        if (isLogin && input.keySet().size()!=logRequired.length) isLogin=false;

        if(isLogin) destination = "login";
        if(isRegister) destination = "register";
        if(isLogin && isRegister) {
            Send(500, "We messed up", resp);
            return;
        }
        System.out.println(isLogin + " and " + isRegister);

        Boolean supported = false;
        for (String loc: supportedDestinations) {
            if(destination.equals(loc)) {
                supported = true;
                break;
            }
        }

        if (!supported) { Send(400, "Invalid Request", resp); return;   }

        if (destination.equals("login")) {
            try {
                UserPass userPass = new UserPass(input.get("u").toString(), input.get("p").toString());
                // check for active sessions
                System.out.println(userPass);
                User user = login(userDAO, upDAO, userPass);
                //resp.setStatus(204);  // logged in, no other content
                HttpSession session = req.getSession(); // use req.getSession(false) to prevent a session from being made
                session.setAttribute("auth-user", new Token(userPass.getUsername(), userPass.getPassword()));
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
                UserInfo userInfo = new UserInfo(
                        input.get("u").toString(),
                        input.get("p").toString(),
                        input.get("f").toString(),
                        input.get("l").toString(),
                        input.get("a1").toString(),
                        input.get("a2").toString(),
                        input.get("c").toString(),
                        input.get("s").toString(),
                        input.get("z").toString()
                        );
                register(userDAO, upDAO, userInfo);
                System.out.println("Registration succeeded...");
                UserPass userPass = new UserPass(userInfo.getUsername(),userInfo.getPassword());
                login(userDAO,upDAO,userPass);  System.out.println("And Login also");
                                                      Send(204, "Registered",                              resp); return; }
            catch (InputWasNullException e) {         Send(400, "Form input was blank",                    resp); return; }
            catch (ValueOutOfRangeException e) {      Send(400, "Username length was incorrect",           resp); return; }
            catch (UsernameFormatException e) {       Send(400, "Username must end with @revature.net",    resp); return; }
            catch (IllegalCharacterException e) {     Send(400, "Username contained an illegal character", resp); return; }
            catch (UsernameNotAvailableException e) { Send(409, "Username already taken!",                 resp); return; }
            catch (UserNotFoundException e) {
                Complain(" just registered, but now can't login.");
                                     Complain(e);     Send(404, "User not found",                          resp); return; }
            catch (InvalidCredentialsException e) {
                Complain(" just registered, but now can't login.");
                                     Complain(e);     Send(401, "Invalid Credentials",                     resp); return; }
            catch (SQLException e) { Complain(e);     Send(500, "There was a problem with the database",   resp); return; }
            catch (Throwable t) {    Complain(t);     Send(500, "An unknown error occurred",               resp); return; }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] "+name+" received a DELETE request.");
        // TODO : Invalidate current session
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
