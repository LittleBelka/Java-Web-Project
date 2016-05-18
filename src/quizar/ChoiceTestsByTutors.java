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
 * This is the class that creates a list all tests that are created a particular tutor.
 */
public class ChoiceTestsByTutors extends HttpServlet {

    /**
     * This is the method that handles the POST request.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id_tutor = Integer.parseInt(request.getParameter("subjectFooter"));
        WorkDatabase db = new WorkDatabase();
        HashMap<Integer, Pair<String, String>> testsByTutors;
        testsByTutors = db.findTestsByTutors(id_tutor);
        if (testsByTutors.size() > 0) {
            request.setAttribute("nameTestsByTutors", testsByTutors);
        }
        HttpSession session = request.getSession(true);
        if(session.getAttribute("status") == null) {
            request.getRequestDispatcher("tutors_test.jsp").forward(request, response);
        } else if(session.getAttribute("status").equals("tutor")) {
            request.getRequestDispatcher("tutor_home_tutors_test.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("student_home_tutors_test.jsp").forward(request, response);
        }
    }
}
