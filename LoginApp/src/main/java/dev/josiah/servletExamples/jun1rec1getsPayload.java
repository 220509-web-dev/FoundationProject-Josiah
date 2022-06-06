package dev.josiah.servletExamples;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        String payload = "";
        while ((line = payloadReader.readLine()) != null) payload += line;

        try {
            System.out.println("Hello");
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(payload, User.class);
            // Jackson takes the input stream and converts it into a User object
            System.out.println(user);

        } catch (Exception e) {
            Complain(e);
            e.printStackTrace();
        }

        resp.setStatus(204);
    }

    }
