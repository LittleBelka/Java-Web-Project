package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * This is the class that is invoked when a user attempts to authorization.
 */
public class EnterLoginPasswordFilter implements Filter {

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

        if (request.getParameter("login").equals("") || request.getParameter("password").equals("")) {
            HttpSession session = ((HttpServletRequest) request).getSession();
            ResourceBundle bundle = ResourceBundle.getBundle("localization.home.locale_" + session.getAttribute("local"));
            session.setAttribute("messageAuth", bundle.getString("local.messageNotLogAuth"));
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
