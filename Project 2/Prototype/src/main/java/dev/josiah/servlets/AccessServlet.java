package dev.josiah.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.complaintDepartment.Exceptions.TokenTimeoutException;
import dev.josiah.dtos.Token;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class AccessServlet extends HttpServlet {
    final private static String name = "AccessServlet";
    private static ObjectMapper mapper;

    public AccessServlet(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - "+name+" instantiated!");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Send(405,"Method not allowed", resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] "+name+" received a POST request.");

        HttpSession session1 = req.getSession(false);
        if (session1 == null) {
            Send(401, "No session found on request", resp);
            // UNAUTHORIZED (user needs to login)
            return;
        }
        else {
            System.out.println("Already logged in");
            System.out.println("Session info:"+session1.getAttribute("auth-user"));
            try {
                Token token = (Token) session1.getAttribute("auth-user");
                token.verifyToken();
            } catch (TokenTimeoutException e) {
                Send(401, "Session Expired", resp);
                return;
            } catch (Throwable t) {
                Send(500, "An unknown error occurred", resp);
                Complain(t);
                return;
            }
        }
        // 403 USER WAS LOGGED IN AND HAD A TOKEN/SESSION BUT...
        // IS TRYING TO HIT AN ENDPOINT THEY'RE "FORBIDDEN" FROM GOING TO

        String todo = "Here goes things the user can access when logged in!";

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
