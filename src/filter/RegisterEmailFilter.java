package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterEmailFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

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

    @Override
    public void destroy() {}
}
