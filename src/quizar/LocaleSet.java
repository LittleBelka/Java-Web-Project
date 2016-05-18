package quizar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This is the class that sets the locale.
 */
public class LocaleSet extends HttpServlet{

    /**
     * This is the method that handles the POST request.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        session.setAttribute("local", request.getParameter("local"));
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
