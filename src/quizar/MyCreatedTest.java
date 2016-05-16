package quizar;

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
import java.util.HashSet;

public class MyCreatedTest extends HttpServlet{

    private WorkDatabase db = new WorkDatabase();
    private HashMap<String, String[]> param = new HashMap<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idTest = Integer.parseInt(request.getParameter("subjectFooter"));

        HttpSession session = request.getSession(true);
        param.clear();
        param.putAll(request.getParameterMap());
        boolean bol = false;
        if (param.containsKey("subjectFooterChange")) {
            bol = true;
        }
        if (session.getAttribute("status").equals("student") || (session.getAttribute("status").equals("tutor") &&
                (!bol || (request.getParameter("subjectFooterChange").equals(""))))) {

            openTest(request, response, session, idTest);
            request.getRequestDispatcher("take_test.jsp").forward(request, response);
        } else if(request.getParameter("subjectFooterChange").equals("0")) {
            editTest(request, response, idTest);
            request.getRequestDispatcher("edit_test.jsp").forward(request, response);
        } else {
            db.deleteTest(idTest);
            ArrayList<String> subjects = new ArrayList<>();
            subjects.addAll(db.findAllSubjects());
            if (subjects.size() > 0) {
                session.setAttribute("subject", subjects);
            }
            HashMap<Integer, Pair<String, String>> myCreatedTest = new HashMap<>();
            myCreatedTest.putAll(db.findMyCreatedTest(Integer.parseInt(session.getAttribute("id").toString())));
            session.setAttribute("myCreatedTest", myCreatedTest);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    private void editTest(HttpServletRequest request, HttpServletResponse response, int idTest) {

        Pair<String, String> infTest = db.findSeparatorForTest(idTest);
        String separator = infTest.getValue();
        String nameTest = infTest.getKey();
        request.setAttribute("idTestOpen", idTest);
        request.setAttribute("nameTest", nameTest);
        request.setAttribute("separator", separator);
        ArrayList<Pair<String, Pair<StringBuilder, StringBuilder>>> test = new ArrayList<>();
        test.addAll(db.openTestForEdit(idTest, separator));
        /*System.out.println("_____________________________________________________________");
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i).getKey());
            System.out.println(test.get(i).getValue().getKey() + "  " + test.get(i).getValue().getValue());
            System.out.println("_____________________________________________________________");
        }*/
        request.setAttribute("editTest", test);
    }

    private void openTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, int idTest) {

        ArrayList<Pair<Integer, String>> listQuestion = new ArrayList<>();
        listQuestion.addAll(db.findQuestionForOpenTest(idTest));
        HashSet<Integer> allCorrectAnswer = new HashSet<>();
        ArrayList<Pair<String, ArrayList<Pair<Integer, String>>>> listQuestionAndAnswer = new ArrayList<>();
        ArrayList<Pair<Integer, ArrayList<Pair<Integer, String>>>> listIdQuestionAndAnswer = new ArrayList<>();
        for (int i = 0; i < listQuestion.size(); i++) {
            ArrayList<Pair<Integer, Pair<String, Integer>>> listAnswer = new ArrayList<>();
            listAnswer.addAll(db.getAnswerTheQuestion(listQuestion.get(i).getKey()));
            ArrayList<Pair<Integer, String>> tmp = new ArrayList<>();
            for (int j = 0; j < listAnswer.size(); j++) {
                tmp.add(new Pair<>(listAnswer.get(j).getKey(), listAnswer.get(j).getValue().getKey()));
                if (listAnswer.get(j).getValue().getValue() == 1) {
                    allCorrectAnswer.add(listAnswer.get(j).getKey());
                }
            }
            listQuestionAndAnswer.add(new Pair<>(listQuestion.get(i).getValue(),tmp));
            listIdQuestionAndAnswer.add(new Pair<>(listQuestion.get(i).getKey(),tmp));
        }
        session.setAttribute("allCorrectAnswer", allCorrectAnswer);
        session.setAttribute("idTest", idTest);
        session.setAttribute("countQuestionTest", listQuestion.size());
        session.setAttribute("listQuestion", listQuestion);
        session.setAttribute("listIdQuestionAndAnswer", listIdQuestionAndAnswer);
        request.setAttribute("listQuestionAndAnswer", listQuestionAndAnswer);
        request.setAttribute("nameTest", db.getNameTest(idTest));
        /*for (int i = 0; i < listQuestionAndAnswer.size(); i++) {
            System.out.println("________________________________________________");
            System.out.println("question:  " + listQuestion.get(i).getKey());
            for (int j = 0; j < listQuestionAndAnswer.get(i).getValue().size(); j++) {
                System.out.println(listQuestionAndAnswer.get(i).getValue().get(j).getKey() + "  " +
                        listQuestionAndAnswer.get(i).getValue().get(j).getValue());
            }
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - --  - - - - - - - - - - - - - - -");*/
    }
}
