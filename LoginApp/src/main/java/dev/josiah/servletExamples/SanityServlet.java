package dev.josiah.servletExamples;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;


//A "health check" servlet to ensure the web application deployed
public class SanityServlet extends HttpServlet {
    private final static String name = "SanityServlet";
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
        String htmlpage = "<!DOCTYPE html> <html> <head> <title>"+name+"</title> <style> * { font-family: sans-serif; box-sizing: border-box; } header li { list-style: none; display: inline; } header :link, header :visited { text-decoration: none; background-color: ivory; border: 1px solid blue; color: sky-blue; width: 400px; margin: 3px; padding: 12px; } #current_page a:link, #current_page a:visited { background-color: deepskyblue; color: ivory; } #q_control, #a_control { background-color: DeepSkyBlue; border:2px solid blue; max-width: 500px; min-height: 300px; margin-top: 5px; opacity: 90%; width:50%; float: left; padding: 10px; } #a_control { margin-left: 3px; } #question, #answer { color: HoneyDew; font-size: 30px; } #block_quote { text-indent: 50px; } #keyword { font-weight: bold; } h3 { font-weight: bold; } body { background-image: url('images/merapi-2330885_1920.jpg'); background-repeat: no-repeat; } </style> <script type=\"text/javascript\"> let data_db = ''; const db_raw = []; const db_c = []; var sel = -1; var elems = 0; const memory = []; var mem_len = 0; <!-- Fetch content from another webpage --> let loc = 'https://raw.githubusercontent.com/220509-web-dev/FoundationProject-Josiah/main/Quizzard/db-java.html'; fetch(loc) .then(response => response.text()) .then(data => set_db(data)); function set_db(text) { <!-- sets global variable data_db --> <!-- Runs after fetch completes --> window.data_db = text; window.db_raw = window.data_db.split(\";;;;;;;;\"); <!-- Set db_c to a blank 2-d array of proper length --> window.elems = window.db_raw.length; window.db_c = [...Array(window.elems)].map(x => Array(2).fill('')); for (var i = 0; i < window.elems; i++) { window.db_c[i] = window.db_raw[i].split(\";;;;\"); } } function getRandomInt() { <!-- window.elems is >= 2 --> let max = window.elems-1-memory.length; let rnum = Math.floor(Math.random() * (max+1)); <!-- count how many in memory are <= rnum --> var i = 0; var final_rnum = rnum-0; <!-- step --> while (i <= final_rnum) { if (memory.includes(i)) { final_rnum++; } i++; } var adjust = final_rnum - rnum; memory.push(final_rnum); var max_size = Math.floor(window.elems /2); if (memory.length > max_size) { memory.shift(); } console.log(\"adjust\"); console.log(adjust); console.log(\"rnum\"); console.log(rnum); console.log(\"memory\"); console.log(memory.toString()); return final_rnum; } function set_sel(val) { window.sel = val; } function remember(val) { var max_size = Math.floor(window.elems /2); if (max_size > 0) { memory.push(val); if (memory.length > max_size) { memory.shift(val); } } } function rnd_sel() { if (window.elems > 1) { var rnd_int = getRandomInt(); set_sel(rnd_int); } else if (window.elems == 1) {set_sel(0);} } function repl_q() { rnd_sel(); document.getElementById('question').innerHTML = window.db_c[window.sel][0]; document.getElementById('answer').innerHTML = \"<p>Click to see answer</p>\"; <!-- window.db_c[window.sel][1] --> } function repl_a() { document.getElementById('answer').innerHTML = window.db_c[window.sel][1]; } </script> </head> <body> <header> <ul> <li><a href=\"index.html\">Main</a></li> <li><a href=\"sql.html\">SQL</a></li> <li id=\"current_page\"><a href=\"java.html\">Java</a></li> <li><a href=\"webdev.html\">Web Dev</a></li> <li><a href=\"about.html\">About</a></li> </ul> </header> <span> <div id=\"q_control\" type=\"button\" onclick=\"repl_q()\"> <p id=\"question\">Click me to display a question.</p> </div> </span> <span> <div id=\"a_control\" type=\"button\" onclick=\"repl_a()\"> <p id=\"answer\"></p> </div> </span> </body> </html>";
        resp.setContentType("text/html");
        resp.getWriter().write(htmlpage);


    }
}