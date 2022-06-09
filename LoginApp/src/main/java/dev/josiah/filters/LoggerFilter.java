package dev.josiah.filters;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class LoggerFilter extends HttpFilter {
    private static String at_square = new String(new char[6]).replace("\0", new String(new char[20]).replace("\0", "@")+"\n");
    private static void block() {System.out.println(at_square);}
    final private static String logFile = "filterlogs.log";

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - LoggerFilter initialized at " + LocalDateTime.now());
    }
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain){
        try {
            String log = "\n"+LocalDateTime.now();
            log += "\nRequest URI: " + req.getRequestURI();
            log += "\nRequest method: " + req.getMethod();
            log += "\nRequest query string: " + req.getQueryString();
            log(log);
            chain.doFilter(req, res);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }
    private static void log(String log) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            SimpleDateFormat formatter= new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            writer.write(formatter.format(date) + "\n" + log + "\n");
        } catch (Throwable t) { t.printStackTrace(); }
    }
}
