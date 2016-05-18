package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * This is the class that is invoked for checking correct the password when a user attempts to register.
 */
public class RegisterPasswordFilter implements Filter {

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
        ResourceBundle bundle =
                ResourceBundle.getBundle("localization.registration.locale_" + session.getAttribute("local"));

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

}
