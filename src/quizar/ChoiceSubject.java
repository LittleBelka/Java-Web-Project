package quizar;

import database.WorkDatabase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class ChoiceSubject extends HttpServlet{

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        WorkDatabase db = new WorkDatabase();
        HttpSession session = request.getSession(true);
        ArrayList<String> subjects = new ArrayList<>();
        subjects.addAll(db.findAllSubjects());
        if (subjects.size() > 0) {
            session.setAttribute("subject", subjects);
        }
        request.getRequestDispatcher("subjects.jsp").forward(request, response);
    }
}

