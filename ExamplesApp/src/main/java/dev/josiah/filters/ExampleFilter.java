package dev.josiah.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class ExampleFilter extends HttpFilter {

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - CustomFilter initialized at " + LocalDateTime.now());
    }

    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
        try {
            System.out.println("ExampleFilter leveraged");
            req.setAttribute("was-filtered", true);
            System.out.println("ExampleFilter: Flag 1");
            resp.setHeader("response-header-set-by-ExampleFilter","some-example-value");
            // servlet can change this later

            System.out.println("ExampleFilter: Flag 2");

            chain.doFilter(req, resp); // pass the req and response down the chain

            System.out.println("ExampleFilter: Flag 3");
        } catch (ServletException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            Complain(e);
            throw new RuntimeException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }
}
