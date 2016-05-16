package quizar;

import database.WorkDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class ChoiceTutors extends HttpServlet{

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
