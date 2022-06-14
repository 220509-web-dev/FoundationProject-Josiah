package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.AllDAO;
import dev.josiah.entities.User;

import javax.servlet.ServletException;
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


public class UserServlet extends HttpServlet {
    private final static String name = "UserServlet";
    private static ObjectMapper mapper;
    private final AllDAO allDAO;

    public UserServlet(ObjectMapper mapper, AllDAO allDAO) {
        this.mapper = mapper;
        this.allDAO = allDAO;
    }

    @Override public void init() { System.out.println("[LOG] - "+name+" instantiated!"); }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Send(405,"Method not allowed", resp);
        return;
    }

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
            try { User user = ServiceIdRequest(potentialId, allDAO);
                                                   Send(200, mapper.writeValueAsString(user),         resp); return; }
            catch (UserNotFoundException e) {      Send(404, "User not found",                        resp); return; }
                // ANY RESOURCE NOT FOUND IS 404
            catch (InputWasNullException e) {      Send(400, "Form input was blank",                  resp); return; }
            catch (InputNotAnIntegerException e) { Send(400, "Input must be an integer",              resp); return; }
            catch (NumberFormatException e) {      Send(400, "Please enter a whole number",           resp); return; }
            catch (ValueOutOfRangeException e) {   Send(400, "ID inputted was out of bounds",         resp); return; }
            catch (SQLException e) { Complain(e);  Send(500, "There was a problem with the database", resp); return; }
//                resp.setStatus(500); // MAYBE, BUT DEPENDS ON WHAT KIND OF SQL EXCEPTION
            catch (Throwable t) { Complain(t);     Send(500, "An unknown error occurred",             resp); return; }
        }

        if(potentialUsername != null) {
            System.out.println(potentialUsername + " being sent from UserServlet to the service layer");
            try { User user = ServiceUsernameRequest(potentialUsername, allDAO);
                                                  Send(200, mapper.writeValueAsString(user),           resp); return; }
            catch (UserNotFoundException e) {     Send(404, "User not found",                          resp); return; }
            catch (InputWasNullException e) {     Send(400, "Form input was blank",                    resp); return; }
            catch (ValueOutOfRangeException e) {  Send(400, "Username length was incorrect",           resp); return; }
            catch (UsernameFormatException e) {   Send(400, "Username must end with @revature.net",    resp); return; }
            catch (IllegalCharacterException e) { Send(400, "Username contained an illegal character", resp); return; }
            catch (SQLException e) { Complain(e); Send(500, "There was a problem with the database",   resp); return; }
            catch (Throwable t) { Complain(t);    Send(500, "An unknown error occurred",               resp); return; }
        }


        System.out.println("UserServlet is forwarding to services request for all user data");
        try { List<User> users = ServiceAllUsersRequest(allDAO);
                                              Send(200, mapper.writeValueAsString(users),        resp); return; }
        catch (UserNotFoundException e) {     Send(204, "No users exist in database",            resp); return; }
        // 204 because it's a successful request, but no data to return
        catch (SQLException e) { Complain(e); Send(500, "There was a problem with the database", resp); return; }
        catch (Throwable t) { Complain(t);    Send(500, "An unknown error occurred",             resp); return; }
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
