package dev.josiah.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

// Registered in web.xml
public class CorsFilter extends HttpFilter { // implements Filter
    // Http filter is an abstract implementation of Filter

    public void doFilter(HttpServletRequest request, HttpServletResponse res, FilterChain chain){
        //(ServletRequest request, ServletResponse response, FilterChain chain)
        // doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) {
        try {
//            System.out.println("CORS Filter leveraged");
//            if(!(res instanceof HttpServletResponse)) {
//                chain.doFilter(request, res); return;
//            }
//            HttpServletResponse res = (HttpServletResponse) response; // take regular response form http server and casts to HttpServletResponse
            res.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            res.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, " +
                    "Access-Control-Request-Method, Access-Control-Request-Headers");

            res.setHeader("Access-Control-Allow-Credentials", "true");
            chain.doFilter(request, res);
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
