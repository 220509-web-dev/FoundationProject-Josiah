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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
                User user = login(userDAO, upDAO, username, password);
                HttpSession session = req.getSession();
                session.setAttribute("auth-user", user);
                resp.setStatus(204);
                HashMap<String, Object> message = new HashMap<>();
                message.put("message", "Logged in");
                message.put("timestamp", LocalDateTime.now().toString());
                resp.getWriter().write(mapper.writeValueAsString(message));
                System.out.println("[LOG] - "+user.getUsername()+" logged in!");
                return;
            } catch (Throwable t){
                resp.setStatus(400);
                feedback = "Invalid input";
            }

            HashMap<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("code", 400);
            errorMessage.put("message", "No user found with provided credentials");
            errorMessage.put("timestamp", LocalDateTime.now().toString());
            resp.getWriter().write(mapper.writeValueAsString(errorMessage));
        }

        if (uri.equals("/register")) {
            resp.setContentType("text/html");
            resp.getWriter().write("Register not implemented");
            return;
        }
    }
}
