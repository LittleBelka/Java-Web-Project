package quizar;

import database.WorkDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * This is the class that is invoked when a user attempts to register.
 */
public class Register extends HttpServlet {

    private String login;
    private String password;
    private  ResourceBundle bundle;

    /**
     * This is the method that handles the POST request. After registration depending on the user's status
     * is its forwarding to the specific page.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        login = session.getAttribute("loginTmp").toString();
        password = session.getAttribute("passwordTmp").toString();
        String email = session.getAttribute("emailTmp").toString();
        String firstName = session.getAttribute("firstNameTmp").toString();
        String middleName = session.getAttribute("middleNameTmp").toString();
        String lastName = session.getAttribute("lastNameTmp").toString();
        String status = session.getAttribute("statusTmp").toString();
        WorkDatabase db = new WorkDatabase();
        bundle = ResourceBundle.getBundle("localization.registration.locale_" + session.getAttribute("local"));

        if (db.findUser(login, email).equals("")) {
            int id = db.register(login, password, firstName, lastName, middleName, email, status);
            if (id < 1) {
                session.setAttribute("messageErrorReg", "The error on the server. Registration failed.");
            } else {
                changeSessionAttributes(session, id, db);
                if (status.equals("student")) {
                    request.getRequestDispatcher("student_home.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("tutor_home.jsp").forward(request, response);
                }
            }

        } else if (db.findUser(login, email).equals("login")){
            session.removeAttribute("loginTmp");
            session.setAttribute("messageReg", bundle.getString("local.messageNotLogin"));
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            session.removeAttribute("emailTmp");
            session.setAttribute("messageReg", bundle.getString("local.messageNotEmail"));
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    /**
     * It is a method that sets and deletes the values in the session (user data, data about the available
     * subjects and tutors).
     * @param session session object
     * @param id id user
     * @param db DAO class object
     */
    private void changeSessionAttributes(HttpSession session, int id, WorkDatabase db) {

        session.setAttribute("id" , id);
        session.setAttribute("status", session.getAttribute("statusTmp").toString());
        session.setAttribute("firstName", session.getAttribute("firstNameTmp").toString());
        session.setAttribute("middleName", session.getAttribute("middleNameTmp").toString());
        session.setAttribute("login", login);
        session.setAttribute("password", password);
        session.setAttribute("regOK", bundle.getString("local.messageOKReg"));
        session.setAttribute("messageAuth", bundle.getString("local.messageOKAuth"));
        session.setAttribute("authOK", "disabled");
        session.removeAttribute("loginTmp");
        session.removeAttribute("passwordTmp");
        session.removeAttribute("firstNameTmp");
        session.removeAttribute("lastNameTmp");
        session.removeAttribute("middleNameTmp");
        session.removeAttribute("emailTmp");
        session.removeAttribute("statusTmp");
        session.removeAttribute("passwordRepeatTmp");
        ArrayList<String> subjects = new ArrayList<>();
        subjects.addAll(db.findAllSubjects());
        if (subjects.size() > 0) {
            session.setAttribute("subject", subjects);
        }
        HashMap<Integer, String> tutors = new HashMap<>();
        tutors.putAll(db.findAllTutors());
        if (tutors.size() > 0) {
            session.setAttribute("tutors", tutors);
        }
    }
}
