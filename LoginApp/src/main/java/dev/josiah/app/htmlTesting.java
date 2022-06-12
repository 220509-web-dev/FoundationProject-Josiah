package dev.josiah.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.HashMap;

public class htmlTesting {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws JsonProcessingException {
        code200("Login Successful!");

    }

    private static void code200(String msg) throws JsonProcessingException {
        HashMap<String, Object> message = new HashMap<>();
//        resp.setStatus(code);
//        resp.setContentType("application/json");
        int code = 204;
        message.put("code", code);
        message.put("message", msg);
        message.put("timestamp", LocalDateTime.now().toString());
//        resp.getWriter().write(mapper.writeValueAsString(message));

        // TODO : Do things
        System.out.println(mapper.writeValueAsString(message));
    }

    private static void code204(String msg) throws JsonProcessingException {
        HashMap<String, Object> message = new HashMap<>();
//        resp.setStatus(code);
//        resp.setContentType("application/json");
        int code = 204;
        message.put("code", code);
        message.put("message", msg);
        message.put("timestamp", LocalDateTime.now().toString());
//        resp.getWriter().write(mapper.writeValueAsString(message));

        // TODO : Do things
        System.out.println(mapper.writeValueAsString(message));
    }
}
