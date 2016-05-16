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

public class Authorization extends HttpServlet{

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
        ArrayList<Users> dataUser = new ArrayList<>();
        dataUser.addAll(db.authorization(login, password));

        if (dataUser.size() > 0) {
            sessionSetAttribute(session, db, bundle, dataUser);
            if (session.getAttribute("status").equals("student")) {
                ArrayList<Pair<Integer, Pair<String, Pair<String, Pair<String, Pair<String, String>>>>>> passedTest =
                        new ArrayList<>();
                passedTest.addAll(db.findMyPassedTest(dataUser.get(0).getId()));
                session.setAttribute("passedTest", passedTest);
                request.getRequestDispatcher("student_home.jsp").forward(request, response);
            } else {
                HashMap<Integer, Pair<String, String>> myCreatedTest = new HashMap<>();
                myCreatedTest.putAll(db.findMyCreatedTest(dataUser.get(0).getId()));
                session.setAttribute("myCreatedTest", myCreatedTest);
                request.getRequestDispatcher("tutor_home.jsp").forward(request, response);
            }
        } else {
            session.setAttribute("messageAuth", bundle.getString("local.messageNotUserAuth"));
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    private void sessionSetAttribute(HttpSession session, WorkDatabase db, ResourceBundle bundle,
                                     ArrayList<Users> dataUser) {

        session.setAttribute("login", dataUser.get(0).getLogin());
        session.setAttribute("password", dataUser.get(0).getPassword());
        session.setAttribute("messageAuth", bundle.getString("local.messageOKAuth"));
        session.setAttribute("authOK", "disabled");
        session.setAttribute("id" , dataUser.get(0).getId());
        session.setAttribute("status", dataUser.get(0).getStatus());
        session.setAttribute("firstName", dataUser.get(0).getFirstName());
        session.setAttribute("middleName", dataUser.get(0).getMiddleName());
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
