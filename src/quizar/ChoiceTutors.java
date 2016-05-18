package quizar;

import database.WorkDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * This is the class that creates a list of all tutors.
 */
public class ChoiceTutors extends HttpServlet{

    /**
     * This is the method that handles the POST request.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        WorkDatabase db = new WorkDatabase();
        HttpSession session = request.getSession(true);
        HashMap<Integer, String> tutors = new HashMap<>();
        tutors.putAll(db.findAllTutors());
        if (tutors.size() > 0) {
            session.setAttribute("tutors", tutors);
        }
        request.getRequestDispatcher("tutors.jsp").forward(request, response);
    }
}
