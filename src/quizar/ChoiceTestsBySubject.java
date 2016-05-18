package quizar;

import database.WorkDatabase;
import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * This is the class that creates a list all the tests for a particular subject.
 */
public class ChoiceTestsBySubject extends HttpServlet{

    /**
     * This is the method that handles the POST request.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String subject = request.getParameter("subjectFooter");
        WorkDatabase db = new WorkDatabase();
        HashMap<Integer, Pair<String, String>> testsBySubject;
        testsBySubject = db.findTestsBySubject(subject);
        if (testsBySubject.size() > 0) {
            request.setAttribute("nameTestsBySubject", testsBySubject);
        }
        HttpSession session = request.getSession(true);
        if(session.getAttribute("status") == null) {
            request.getRequestDispatcher("subjects_test.jsp").forward(request, response);
        } else if(session.getAttribute("status").equals("student")) {
            request.getRequestDispatcher("student_home_subject_test.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("tutor_home_subject_test.jsp").forward(request, response);
        }
    }
}
