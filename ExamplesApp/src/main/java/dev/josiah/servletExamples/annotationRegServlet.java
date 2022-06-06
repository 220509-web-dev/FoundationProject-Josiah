package dev.josiah.servletExamples;

import dev.josiah.entities.WebPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet(
    urlPatterns = "/annot"//,
//    loadOnStartup = 2,
//    initParams = {
//            @WebInitParam(name = "flashcard1-servlet-key", value = "flashcard1-servlet-value"),
//            @WebInitParam(name = "another-param", value = "another-value")
//    }
) // annotation-based servlet registration
public class annotationRegServlet extends HttpServlet {
    private final static String name = "annotationRegServlet";
    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - "+name+" instantiated!");
        System.out.println("[LOG] - Init param, test-init-key: " + this.getServletConfig().getInitParameter("test-init-key"));
        System.out.println("[LOG] - Context param, test-context-key: " + this.getServletContext().getInitParameter("test-context-key"));
    }

    @Override
    public void destroy() {
        System.out.println("[LOG] - "+name+" destroyed!");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - "+name+" received a POST request at " + LocalDateTime.now());
        System.out.println("[LOG] - Request URI: " + req.getRequestURI());
        System.out.println("[LOG] - Request method: " + req.getMethod());
        System.out.println("[LOG] - Request header, example: " + req.getHeader("example"));
        System.out.println("[LOG] - Request query3 string: " + req.getQueryString());

        Map<String,String[]> map = req.getParameterMap();

        for(String key : map.keySet()) {
            String[] value = map.get(key);
            System.out.println("[LOG] - loop key " + key);
            System.out.println("[LOG] - loop val " + String.join(", ", value));
        }

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
        WebPage page = new WebPage("webpages/java.html");
        String htmlpage = page.getSource();
        resp.setContentType("text/html");
        resp.getWriter().write(htmlpage);

    }
}
