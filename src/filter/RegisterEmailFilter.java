package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the class that is invoked for email validation when a user attempts to register.
 */
public class RegisterEmailFilter implements Filter {

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
        ResourceBundle bundle = ResourceBundle.getBundle("localization.registration.locale_" + session.getAttribute("local"));
        Pattern pat = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b");
        Matcher match = pat.matcher(session.getAttribute("emailTmp").toString());
        System.out.println(session.getAttribute("emailTmp"));
        if (match.matches()) {
            chain.doFilter(request, response);
        } else {
            session.removeAttribute("emailTmp");
            session.setAttribute("messageReg", bundle.getString("local.messageIncorrectEmail"));
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
