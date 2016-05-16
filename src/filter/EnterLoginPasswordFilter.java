package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class EnterLoginPasswordFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

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

    @Override
    public void destroy() {}
}
