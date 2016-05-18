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

/**
 * This is the class that checks the test results after its passage.
 */
public class TakeTest extends HttpServlet{

    private WorkDatabase db = new WorkDatabase();
    private HashMap<String, String[]> param = new HashMap<>();

    /**
     * This is the method that handles the GET request.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if the request can not be handled
     * @throws IOException if an input or output error was detected
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

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
        HttpSession session = request.getSession(true);
        param.putAll(request.getParameterMap());
        param.remove("take_test");
        HashSet<Integer> idAnswers = new HashSet<>();
        for (String o: param.keySet()) {
            idAnswers.add(Integer.parseInt(o));
        }
        HashSet<Integer> idAnswersCorrect = new HashSet<>();
        idAnswersCorrect.addAll((HashSet<Integer>) session.getAttribute("allCorrectAnswer"));
        ArrayList<Pair<Integer, String>> listQuestion = new ArrayList<>();
        listQuestion.addAll((ArrayList<Pair<Integer, String>>) session.getAttribute("listQuestion"));
        if (idAnswersCorrect.containsAll(idAnswers) && idAnswersCorrect.size() == idAnswers.size()) {
            request.setAttribute("result", session.getAttribute("countQuestionTest"));
            saveResult(session, Integer.parseInt(session.getAttribute("countQuestionTest").toString()));
        } else {
            resultTest(session, request, idAnswersCorrect, listQuestion, idAnswers);
        }
        request.getRequestDispatcher("result_test.jsp").forward(request, response);
    }

    /**
     * It is a method that counts passing the test results and forms information about wrong answers.
     * @param session session object
     * @param request servlet request
     * @param idAnswersCorrect set of id of correct answers
     * @param listQuestion list with questions
     * @param idAnswers set of id of user answers
     */
    private void resultTest(HttpSession session, HttpServletRequest request,
                                HashSet<Integer> idAnswersCorrect, ArrayList<Pair<Integer, String>> listQuestion,
                                HashSet<Integer> idAnswers) {

        ArrayList<Pair<Integer, ArrayList<Pair<Integer, String>>>> listIdQuestionAndAnswer = new ArrayList<>();
        listIdQuestionAndAnswer.addAll((ArrayList<Pair<Integer, ArrayList<Pair<Integer, String>>>>)
                session.getAttribute("listIdQuestionAndAnswer"));
        ArrayList<Pair<String, Pair<ArrayList<String>, ArrayList<String>>>> questYourAnswerAndAnswer =
                new ArrayList<>();
        int result = 0;
        for (int i = 0; i < listIdQuestionAndAnswer.size(); i++) {
            boolean bol = true;
            ArrayList<String> yourAnswer = new ArrayList<>();
            ArrayList<String> answer = new ArrayList<>();
            for (int j = 0; j < listIdQuestionAndAnswer.get(i).getValue().size(); j++) {
                if (param.containsKey((listIdQuestionAndAnswer.get(i).getValue().get(j).getKey()).toString())) {
                    yourAnswer.add(param.get((listIdQuestionAndAnswer.get(i).getValue().get(j).getKey()).
                            toString())[0]);
                }
                if (idAnswersCorrect.contains(listIdQuestionAndAnswer.get(i).getValue().get(j).getKey())) {
                    answer.add(listIdQuestionAndAnswer.get(i).getValue().get(j).getValue());
                }
                if (idAnswersCorrect.contains(listIdQuestionAndAnswer.get(i).getValue().get(j).getKey()) &&
                        !idAnswers.contains(listIdQuestionAndAnswer.get(i).getValue().get(j).getKey()) ||
                        !idAnswersCorrect.contains(listIdQuestionAndAnswer.get(i).getValue().get(j).getKey()) &&
                                idAnswers.contains(listIdQuestionAndAnswer.get(i).getValue().get(j).getKey())) {
                    bol = false;
                }
            }
            if (!bol) {
                result = result + 1;
                questYourAnswerAndAnswer.add(new Pair<>(listQuestion.get(i).getValue(),
                        new Pair<>(yourAnswer, answer)));
            }
        }
        result = Integer.parseInt(session.getAttribute("countQuestionTest").toString()) - result;
        showResultTest(session, request, result, questYourAnswerAndAnswer);
    }

    /**
     * It is a method that sets the information on the test results for display to the user.
     * @param session session object
     * @param request servlet request
     * @param result the number correct answers
     * @param questYourAnswerAndAnswer It contains questions which the user answered incorrectly. It also
     * contains the answers to them.
     */
    private void showResultTest(HttpSession session, HttpServletRequest request, int result,
                 ArrayList<Pair<String, Pair<ArrayList<String>, ArrayList<String>>>> questYourAnswerAndAnswer) {

        request.setAttribute("result", result);
        request.setAttribute("questYourAnswerAndAnswer", questYourAnswerAndAnswer);
        request.setAttribute("separator",
                db.findSeparatorForTest(Integer.parseInt(session.getAttribute("idTest").toString())).getValue());
        saveResult(session, result);
    }

    /**
     * This method saves the test result if the user is a student.
     * @param session session object
     * @param result the number correct answers
     */
    private void saveResult(HttpSession session, int result) {

        if (session.getAttribute("status").equals("student")) {
            String res = result + "/" + session.getAttribute("countQuestionTest").toString();
            db.takeTest(Integer.parseInt(session.getAttribute("id").toString()),
                    Integer.parseInt(session.getAttribute("idTest").toString()), res);
            ArrayList<Pair<Integer, Pair<String, Pair<String, Pair<String, Pair<String, String>>>>>> passedTest =
                    new ArrayList<>();
            passedTest.addAll(db.findMyPassedTest(Integer.parseInt(session.getAttribute("id").toString())));

            for (int i = 0; i < passedTest.size(); i++) {
                System.out.println(passedTest.get(i));
            }

            session.setAttribute("passedTest", passedTest);

        }
    }
}
