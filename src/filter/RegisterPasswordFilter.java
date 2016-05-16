package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class RegisterPasswordFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        ResourceBundle bundle = ResourceBundle.getBundle("localization.registration.locale_" + session.getAttribute("local"));

        if (session.getAttribute("passwordTmp").toString().length() >= 6) {
            if (session.getAttribute("passwordTmp").equals(session.getAttribute("passwordRepeatTmp"))) {
                chain.doFilter(request, response);
            } else {
                session.removeAttribute("passwordTmp");
                session.removeAttribute("passwordRepeatTmp");
                session.setAttribute("messageReg", bundle.getString("local.messageIncorrectSecondPas"));
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else {
            session.removeAttribute("passwordTmp");
            session.removeAttribute("passwordRepeatTmp");
            session.setAttribute("messageReg", bundle.getString("local.messageIncorrectLengthPas"));
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {}
}
