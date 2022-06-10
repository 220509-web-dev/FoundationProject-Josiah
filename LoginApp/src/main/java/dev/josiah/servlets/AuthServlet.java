package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.*;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.dtos.UserInfo;
import dev.josiah.dtos.UserPass;
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
        UserPass userPass = new UserPass();
        UserInfo userInfo;
        // Determine the intended destination
        String destination = "none";
        String[] supportedDestinations = {"login", "register"};

        try {
            userPass = mapper.readValue(req.getInputStream(), UserPass.class);
            destination = "login";
        } catch (Throwable t) {}

        try {
            userInfo = mapper.readValue(req.getInputStream(), UserInfo.class);
            destination = "register";
        } catch (Throwable t) {}


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

            HashMap<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("code", 400);
            errorMessage.put("message", "Invalid Request");
            errorMessage.put("timestamp", LocalDateTime.now().toString());

            resp.getWriter().write(mapper.writeValueAsString(errorMessage));
            return;
        }

        if (destination.equals("login")) {
//            try {
//                login(userDAO, upDAO, userPass);
//            }
            return;
        }

        if (destination.equals("register")) {

        }

//        HashMap<String, Object> errorMessage = new HashMap<>();
//        errorMessage.put("code", 400);
//        errorMessage.put("message", "No user found with provided credentials");
//        errorMessage.put("timestamp", LocalDateTime.now().toString());
//        resp.getWriter().write(mapper.writeValueAsString(errorMessage));
    }
}
