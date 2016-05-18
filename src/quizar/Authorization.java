package quizar;

import database.Users;
import database.WorkDatabase;
import javafx.util.Pair;

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
 * This is the class that is invoked when a user attempts to authorization.
 */
public class Authorization extends HttpServlet{

    /**
     * This is the method that handles the POST request. After authorization depending on the user's status
     * is its forwarding to the specific page.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = (String) request.getAttribute("login");
        String password = request.getParameter("password");
        WorkDatabase db = new WorkDatabase();
        HttpSession session = request.getSession(true);
        String locale = "";
        if (session.getAttribute("local") != null) {
            locale = "_" + session.getAttribute("local");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("localization.home.locale" + locale);
        Users dataUser = db.authorization(login, password);

        if (dataUser != null) {
            sessionSetAttribute(session, db, bundle, dataUser);
            if (session.getAttribute("status").equals("student")) {
                ArrayList<Pair<Integer, Pair<String, Pair<String, Pair<String, Pair<String, String>>>>>> passedTest =
                        new ArrayList<>();
                passedTest.addAll(db.findMyPassedTest(dataUser.getId()));
                session.setAttribute("passedTest", passedTest);
                request.getRequestDispatcher("student_home.jsp").forward(request, response);
            } else {
                HashMap<Integer, Pair<String, String>> myCreatedTest = new HashMap<>();
                myCreatedTest.putAll(db.findMyCreatedTest(dataUser.getId()));
                session.setAttribute("myCreatedTest", myCreatedTest);
                request.getRequestDispatcher("tutor_home.jsp").forward(request, response);
            }
        } else {
            session.setAttribute("messageAuth", bundle.getString("local.messageNotUserAuth"));
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    /**
     * It is a method that sets the values in the session (user data, data about the available subjects and tutors).
     * @param session session object
     * @param db DAO class object
     * @param bundle object to access resources
     * @param dataUser object containing user data
     */
    private void sessionSetAttribute(HttpSession session, WorkDatabase db, ResourceBundle bundle,
                                     Users dataUser) {

        session.setAttribute("login", dataUser.getLogin());
        session.setAttribute("password", dataUser.getPassword());
        session.setAttribute("messageAuth", bundle.getString("local.messageOKAuth"));
        session.setAttribute("authOK", "disabled");
        session.setAttribute("id" , dataUser.getId());
        session.setAttribute("status", dataUser.getStatus());
        session.setAttribute("firstName", dataUser.getFirstName());
        session.setAttribute("middleName", dataUser.getMiddleName());
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
