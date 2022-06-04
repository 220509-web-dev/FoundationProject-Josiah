package dev.josiah.servlets;

import dev.josiah.daos.UserDAO;
import dev.josiah.daos.UserDaoPostgres;
import dev.josiah.daos.UserPrivDAO;
import dev.josiah.daos.UserPrivDaoPostgres;
import dev.josiah.entities.User;
import dev.josiah.entities.UserPriv;
import dev.josiah.utils.ConnectionUtil;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

// registered in web.xml
public class LoginHandler  extends HttpServlet {

    /**
     * this life-cycle method is invoked when this servlet is first accessed
     * by the client
     */
    public void init(ServletConfig config) {
        System.out.println("Servlet is being initialized");
    }

    /**
     * handles HTTP GET request
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter writer = response.getWriter();
        writer.println("<html>Hello, I am a Java servlet!</html>");
        writer.flush();
    }

    /**
     * handles HTTP POST request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String paramUsername = request.getParameter("username");
        String paramPassword = request.getParameter("password");
        Connection connection = ConnectionUtil.getConnection();
        /*
        UserDAO userDAO = new UserDaoPostgres();
        User u = userDAO.getUserByUsername(paramUsername);
        String user_info = "";
        if (u == null) user_info = "User not found!";
        else {
            UserPrivDAO pDAO = new UserPrivDaoPostgres();
            UserPriv up = pDAO.getUserInfoById(u.getUser_id());
            UserPriv entered = new UserPriv();
            entered.encryptAndSetPassword(paramPassword);
            if (up.getPassword().equals(entered.getPassword())) {
                user_info = "Logging in as " + paramUsername;
            } else user_info = "Incorrect Password";
        }

        PrintWriter writer = response.getWriter();
        String Return = "<html>Data Entered: <br/>Username : " + paramUsername;
        Return += "<br/>Password : " + paramPassword;
        Return += "<br/>"+user_info+"</html>";
        writer.println(Return);
        writer.flush();

         */

    }

    /**
     * this life-cycle method is invoked when the application or the server
     * is shutting down
     */
    public void destroy() {
        System.out.println("Servlet is being destroyed");
    }
}

