package dev.josiah.servletExamples;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.josiah.entities.Example;
import dev.josiah.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

@WebServlet(urlPatterns = "/jun1rec1getsPayload")
public class jun1rec1getsPayload  extends HttpServlet {
    private final static String name = "jun1rec1getsPayload";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[LOG] - "+name+" received a POST request at " + LocalDateTime.now());
        BufferedReader payloadReader = new BufferedReader(new InputStreamReader(req.getInputStream()));

        String line;
        while ((line = payloadReader.readLine()) != null) System.out.println(line);

        try {
            System.out.println("Hello");
            ObjectMapper mapper = new ObjectMapper();
        /*
        User user = mapper.readValue(req.getInputStream(), User.class);
        // Jackson takes the input stream and converts it into a User object
        System.out.println("User's toString : ");
        System.out.println(user);
         */
            Example ex = mapper.readValue(req.getInputStream(), Example.class);
            System.out.println("Example's toString : ");
            System.out.println(ex.toString());
            System.out.println(ex);
        /*
        POST raw (type: JSON)
{
    "username": "Jsparks1234",
    "password": "abc123"
}
        in postman to
        http://localhost:8080/login-service/jun1rec1getsPayload
         */
        } catch (Exception e) {
            Complain(e);
            e.printStackTrace();
        }

        resp.setStatus(204);
    }

    }
