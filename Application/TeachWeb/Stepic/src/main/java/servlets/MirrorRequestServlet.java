package servlets;


import templater.PageGenerator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilin on 26.05.2017.
 */
public class MirrorRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        /*Map<String, Object> pageVariables = new HashMap<>();
        String keyVal = req.getParameterMap().get("key")[0];
        pageVariables.put("key", keyVal);
        resp.getWriter().println(PageGenerator.instance().getPage("page.html", pageVariables));

        resp.setContentType("text/html;charset=utf-8");*/
        //resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(req.getParameterMap().get("key")[0].toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
