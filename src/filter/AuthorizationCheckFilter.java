package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationCheckFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

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

    @Override
    public void destroy() {}
}
