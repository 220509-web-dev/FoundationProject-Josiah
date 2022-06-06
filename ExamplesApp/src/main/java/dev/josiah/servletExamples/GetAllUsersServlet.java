package dev.josiah.servletExamples;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllUsersServlet extends HttpServlet {
    private final ObjectMapper mapper;
    private final UserDAO userDAO;

    public GetAllUsersServlet(ObjectMapper mapper, UserDAO userDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userDAO.getAllUsers();
        String respPayload = mapper.writeValueAsString(users);
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);
    }

}
