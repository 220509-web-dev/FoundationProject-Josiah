package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.dtos.UserPass;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;
import dev.josiah.entities.WebPage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;

// registered in web.xml
public class LoginServlet extends HttpServlet {
    private final static String name = "LoginServlet";
    private final ObjectMapper mapper;
    private final UserDAO userDAO;
    private final UserPrivDAO upDAO;

    public LoginServlet(ObjectMapper mapper, UserDAO userDAO, UserPrivDAO upDAO) {
        this.mapper = mapper;
        this.userDAO = userDAO;
        this.upDAO = upDAO;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - "+name+" instantiated!");
        System.out.println("[LOG] - Init param, test-init-key: " + this.getServletConfig().getInitParameter("test-init-key"));
        System.out.println("[LOG] - Context param, test-context-key: " + this.getServletContext().getInitParameter("test-context-key"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - "+name+" received a request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request query string: " + req.getQueryString());

        resp.setStatus(200); // it is 200 by default
        resp.setHeader("Content-type", "text/html");
        WebPage page = new WebPage("login.html");
        System.out.println("Flag 1");

        String htmlpage = page.getSource();
        resp.setContentType("text/html");
        resp.getWriter().write(htmlpage);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String paramUsername = request.getParameter("username");
        String paramPassword = request.getParameter("password");
        UserPass userPass = new UserPass(paramUsername, paramPassword);

        // TODO : Move this to services
        User u = userDAO.getUserByUsername(paramUsername);
        String user_info = "";
        if (u == null) user_info = "User not found!";
        else {
            UserPriv up = upDAO.getUserInfoById(u.getUser_id());
            UserPriv entered = new UserPriv();
            entered.encryptAndSetPassword(paramPassword);
            if (up.getPassword().equals(entered.getPassword())) {
                user_info = "Logging in as " + paramUsername;
            } else user_info = "Incorrect Password";
        }
        // TODO end

        PrintWriter writer = response.getWriter();
        String Return = "<html>Data Entered: <br/>Username : " + paramUsername;
        Return += "<br/>Password : " + paramPassword;
        Return += "<br/>"+user_info+"</html>";
        writer.println(Return);
        writer.flush();
    }

    @Override
    public void destroy() {
        System.out.println("[LOG] - "+name+" destroyed!");
    }
}