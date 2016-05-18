package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This is the class that is invoked when a user attempts to authorization.
 */
public class AuthorizationCheckFilter implements Filter {

    /**
     * This method intercepts and processes the requests to the servlet.
     * @param request servlet request
     * @param response servlet response
     * @param chain object to provide a filter chain
     * @throws IOException if an input or output error was detected
     * @throws ServletException if the request can not be handled
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        if (session.getAttribute("local") == null) {
            session.setAttribute("local", "ru");
        }
        if (session.getAttribute("login") != null && session.getAttribute("password") != null) {
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            request.setAttribute("login", request.getParameter("login").toLowerCase());
            chain.doFilter(request, response);
        }
    }

}
