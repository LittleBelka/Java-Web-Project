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

/**
 * This is a class for creating test. Also used to "re-create" test after editing it.
 */
public class CreateTest extends HttpServlet {

    private ArrayList<Pair<String, HashMap<String, Boolean>>> test = new ArrayList<>();
    private ArrayList<String> question = new ArrayList<>();
    private ArrayList<ArrayList<String>> answer = new ArrayList<>();
    private ArrayList<ArrayList<String>> correctAnswer = new ArrayList<>();
    private ArrayList<HashMap<String, Boolean>> responseOptions = new ArrayList<>();
    private WorkDatabase db = new WorkDatabase();
    private HashMap<String, String[]> param = new HashMap<>();

    /**
     * This is the method that handles the POST request.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        param.clear();
        question.clear();
        answer.clear();
        correctAnswer.clear();
        responseOptions.clear();
        test.clear();
        HttpSession session = request.getSession(true);
        param.putAll(request.getParameterMap());

        createTest(session, request);

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

    /**
     * This is the method that creates the test (or "re-create" test after editing it).
     * @param session session object
     * @param request servlet request
     */
    private void createTest(HttpSession session, HttpServletRequest request) {

        String idTest = new String();
        if (param.containsKey("id_test")) {
            idTest = param.get("id_test")[0];
        } else {
            idTest = null;
        }
        if (!param.containsKey("question")) {
            db.deleteTest(Integer.parseInt(idTest));
        } else {
            String separator = request.getParameter("separator");
            if (separator.equals("your")) {
                separator = request.getParameter("yourSeparator");
            }
            for (String o : param.get("question")) {
                question.add(o);
            }
            for (int i = 0; i < param.get("response_options").length; i++) {
                responseOptions.add(new HashMap<>());
                String answerString = new String();
                answerString = param.get("answer")[i].replaceAll(" ", "");
                answer.add(splitStringParameter(param.get("response_options")[i], separator));
                correctAnswer.add(splitStringParameter(answerString, separator));

                for (int j = 0; j < answer.get(i).size(); j++) {
                    boolean correct = false;
                    if (correctAnswer.get(i).contains(Integer.toString(j + 1))) {
                        correct = true;
                    }
                    responseOptions.get(i).put(answer.get(i).get(j), correct);
                }
                test.add(new Pair<>(question.get(i), responseOptions.get(i)));
            }
            if(idTest == null) {
                db.createTest(session.getAttribute("id").toString(), param.get("name_subject")[0], param.get("name_test")[0],
                        separator, test);
            } else {
                String subject = db.getSubjectTest(Integer.parseInt(idTest));
                db.deleteTest(Integer.parseInt(idTest));
                db.createTest(session.getAttribute("id").toString(), subject, param.get("name_test")[0],
                        separator, test);
            }
        }
    }

    /**
     * It is a method that divides the string into parts.
     * @param param a string that contains the response options to a question or numbers of correct answers
     * @param separator a string that contains a separator for response options and numbers of the correct answers
     * @return array of strings that contains the response options or the numbers of correct answers
     */
    private ArrayList<String> splitStringParameter(String param, String separator) {

        ArrayList<String> array = new ArrayList<>();
        String sep = "";
        if (!separator.equals(";") && !separator.equals("/")) {
            sep = "%%'#";
            param = param.replace(separator, sep);
            separator = sep;
        }
        String[] str = param.split(separator);
        for (int i = 0; i < str.length; i++) {
            boolean bol = true;
            while (bol) {
                if (str[i].indexOf(System.lineSeparator()) == 0) {
                    str[i] = str[i].replaceFirst(System.lineSeparator(), "");
                } else if (str[i].lastIndexOf(System.lineSeparator()) == str[i].length() - 2 && str[i].length() >= 2){
                    String t = str[i].substring(0, str[i].length() - 2);
                    str[i] = t;
                } else {
                    bol = false;
                }
            }
            if (!str[i].equals("") && str[i] != null) {
                array.add(str[i]);
            }
        }
        return array;
    }

}

