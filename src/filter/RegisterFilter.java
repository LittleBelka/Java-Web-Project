package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

public class RegisterFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        setSession(session, request);
        ResourceBundle bundle = ResourceBundle.getBundle("localization.registration.locale_" + session.getAttribute("local"));
        if (session.getAttribute("loginTmp") == null || session.getAttribute("passwordTmp") == null ||
                session.getAttribute("passwordRepeatTmp") == null || session.getAttribute("firstNameTmp") == null ||
                session.getAttribute("lastNameTmp") == null || session.getAttribute("middleNameTmp") == null ||
                session.getAttribute("emailTmp") == null || session.getAttribute("statusTmp") == null) {

            session.setAttribute("messageReg", bundle.getString("local.messageNotAllFields"));
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private void setSession(HttpSession session, ServletRequest request) {

        if (session.getAttribute("local") == null) {
            session.setAttribute("local", "ru");
        }
        if (!request.getParameter("login").equals("")) {
            session.setAttribute("loginTmp", request.getParameter("login").toLowerCase());
        }
        if (!request.getParameter("password").equals("")) {
            session.setAttribute("passwordTmp", request.getParameter("password"));
        }
        if (!request.getParameter("repeat_password").equals("")) {
            session.setAttribute("passwordRepeatTmp", request.getParameter("repeat_password"));
        }
        if (!request.getParameter("first_name").equals("")) {
            session.setAttribute("firstNameTmp", request.getParameter("first_name"));
        }
        if (!request.getParameter("last_name").equals("")) {
            session.setAttribute("lastNameTmp", request.getParameter("last_name"));
        }
        if (!request.getParameter("middle_name").equals("")) {
            session.setAttribute("middleNameTmp", request.getParameter("middle_name"));
        }
        if (!request.getParameter("email").equals("")) {
            session.setAttribute("emailTmp", request.getParameter("email").toLowerCase());
        }
        if (request.getParameter("status") != null) {
            session.setAttribute("statusTmp", request.getParameter("status"));
        }
    }

}
