package database;

import javafx.util.Pair;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WorkDatabase {

    private Logger logger = LogManager.getLogger(getClass().getName());
    ConnectionPool connect;
    private Connection conn;

    public WorkDatabase(){

        connect = new ConnectionPool();
        connect.initPoolData();
        conn = connect.takeConnection();
    }

    public ArrayList<Users> authorization(String userName, String password) {

        ArrayList<Users> dataUser = new ArrayList<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs =
                    stmt.executeQuery("select * from Users where (password = \'" +
                            password + "\' and (login = \'" + userName + "\' or  e_mail =\'" + userName +"\'))");
            if (rs.next()) {
                dataUser.add(new Users(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("last_name"),
                        rs.getString("user_status"),
                        rs.getString("e_mail")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return dataUser;
    }

    public String findUser(String userName, String email) {

        String str = "";
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsLog =
                    stmt.executeQuery("select id from Users where login = \'" + userName+"\'");
            if (rsLog.next()) {
                str = "login";
            }
            ResultSet rsEmail =
                    stmt.executeQuery("select id from Users where e_mail = \'" + email+"\'");
            if (rsEmail.next()) {
                str = "email";
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return str;
    }

    public int register(String userName, String password, String firstName, String lastName,
                            String middleName, String email, String status) {

        int id = -1;
        try(Statement stmt = conn.createStatement()) {
            String insertUser = "INSERT INTO Users" +
                    "(login, password, first_name, middle_name, last_name, user_status, e_mail)" +
                    "VALUES (\'" + userName + "\', \'" + password + "\', \'" + firstName + "\', \'" +
                    middleName + "\', \'" + lastName + "\', \'" + status + "\', \'" + email + "\')";
            stmt.executeUpdate(insertUser);
            ResultSet rs =
                    stmt.executeQuery("select id from Users where login = \'" + userName+"\'");
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return id;
    }

    public ArrayList<String> findAllSubjects() {

        ArrayList<String> subject = new ArrayList<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs =
                    stmt.executeQuery("select DISTINCT subject from Test");
            while (rs.next()) {
                subject.add(rs.getString("subject"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return subject;
    }

    public HashMap<Integer, String> findAllTutors() {

        HashMap<Integer, String> tutors = new HashMap<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs =
                    stmt.executeQuery("select id, last_name, first_name, middle_name from Users where user_status = 'tutor'");
            while (rs.next()) {
                String name = rs.getString("last_name") + " " + rs.getString("first_name") + " " + rs.getString("middle_name");
                tutors.put(Integer.parseInt(rs.getString("id")), name);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return tutors;
    }

    public void createTest(String id, String nameSubject, String nameTest, String separator,
                           ArrayList<Pair<String, HashMap<String, Boolean>>> test) {

        try(Statement stmt = conn.createStatement()) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String insertTest = "INSERT INTO Test" +
                    "(name_test, subject, create_test_date, separator) " +
                    "VALUES (\'" + nameTest + "\', \'" + nameSubject +
                    "\', TO_DATE(\'"+format1.format(date)+"\', 'dd-MM-yyyy'), \'" + separator + "\')";
            stmt.executeUpdate(insertTest);
            ResultSet rsIdTest =
                    stmt.executeQuery("select id from Test where name_test = \'" + nameTest
                            + "\' and  subject = \'" + nameSubject + "\'");
            int idTest = 0;
            if (rsIdTest.next()) {
                idTest = rsIdTest.getInt("id");
            }
            String insertTestTutors = "INSERT INTO TestTutors" + "(id_tutor, id_test)" +
                    "VALUES (\'" + Integer.parseInt(id) + "\', \'" + idTest  + "\')";
            stmt.executeUpdate(insertTestTutors);

            for (int i = 0; i < test.size(); i++) {

                String insertQuestions = "INSERT INTO Questions" +
                        "(question)" + "VALUES (\'" + test.get(i).getKey() + "\')";
                stmt.executeUpdate(insertQuestions);
                ResultSet rsIdQuestion =
                        stmt.executeQuery("select id from Questions where question = \'" + test.get(i).getKey() + "\'");
                int idQuestion = 0;
                if (rsIdQuestion.next()) {
                    idQuestion = rsIdQuestion.getInt("id");
                }
                String insertTestQuestion = "INSERT INTO TestQuestion" +
                        "(id_test, id_question)" +
                        "VALUES (\'" + idTest  + "\',\'" + idQuestion + "\')";
                stmt.executeUpdate(insertTestQuestion);

                for (String o: test.get(i).getValue().keySet()) {
                    int correct;
                    if (test.get(i).getValue().get(o)) {
                        correct = 1;
                    } else {
                        correct = 0;
                    }
                    String insertAnswers = "INSERT INTO Answers" + "(id_question, answer, correct)" +
                            "VALUES (\'" + idQuestion + "\', \'" + o  + "\', \'" + correct + "\')";
                    stmt.executeUpdate(insertAnswers);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
    }

    public void takeTest(int idStudent, int idTest, String result) {

        try(Statement stmt = conn.createStatement()) {
            String nameTest = null;
            String subject = null;
            String nameTutor = null;
            ResultSet rsSub =
                    stmt.executeQuery("select t.name_test, t.subject, u.first_name, u.middle_name, u.last_name " +
                            "from Test t, TestTutors tt, Users u where t.id = \'" + idTest + "\' and " +
                            "t.id = tt.id_test and tt.id_tutor = u.id");
            if (rsSub.next()) {
                nameTest = rsSub.getString("name_test");
                subject = rsSub.getString("subject");
                nameTutor = rsSub.getString("last_name") + " " + rsSub.getString("first_name").substring(0, 1) +
                        "." + rsSub.getString("middle_name").substring(0, 1) + ".";
            }
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String insertTest = "INSERT INTO Students" +
                    "(id_student, id_test, name_test, subject, name_tutor, result, pass_test_date)" +
                    "VALUES (\'" + idStudent + "\', \'" + idTest + "\', \'" + nameTest +
                    "\', \'" + subject + "\', \'" + nameTutor + "\', \'" + result +
                    "\', TO_DATE(\'"+format1.format(date)+"\', 'dd-MM-yyyy'))";
            stmt.executeUpdate(insertTest);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
    }

    public String getSubjectTest(int idTest) {

        String subjectTest = null;
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsSub =
                    stmt.executeQuery("select subject from Test where id = \'" + idTest + "\'");
            if (rsSub.next()) {
                subjectTest = rsSub.getString("subject");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return subjectTest;
    }

    public String getNameTest(int idTest) {

        String nameTest = null;
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsTest =
                    stmt.executeQuery("select name_test from Test where id = \'" + idTest + "\'");
            if (rsTest.next()) {
                nameTest = rsTest.getString("name_test");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return nameTest;
    }

    public Pair<String, String> findSeparatorForTest(int idTest) {

        Pair<String, String> infTest = null;
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsSep =
                    stmt.executeQuery("select name_test, separator from Test where id = \'" + idTest + "\'");
            while (rsSep.next()) {
                infTest = new Pair<>(rsSep.getString("name_test"), rsSep.getString("separator"));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return infTest;
    }

    public ArrayList<Pair<String, Pair<StringBuilder, StringBuilder>>> openTestForEdit(int idTest, String separator) {

        ArrayList<Pair<String, Pair<StringBuilder, StringBuilder>>> test = new ArrayList<>();
        ArrayList<String> question = new ArrayList<>();

        try(Statement stmt = conn.createStatement()) {
            ResultSet rsQuestion =
                    stmt.executeQuery("select q.id, q.question from Questions q, TestQuestion tq " +
                            "where q.id = tq.id_question and tq.id_test = \'" + idTest + "\'");
            ArrayList<Integer> idQuestion = new ArrayList<>();
            while (rsQuestion.next()) {
                question.add(rsQuestion.getString("question"));
                idQuestion.add(Integer.parseInt(rsQuestion.getString("id")));
            }
            for(int i = 0; i < idQuestion.size(); i++) {
                test.add(new Pair<>(question.get(i), findAnswerForEditTest(idQuestion.get(i), separator)));
            }

        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return test;
    }

    private Pair<StringBuilder, StringBuilder> findAnswerForEditTest(int idQuestion, String sep) {

        StringBuilder answer = new StringBuilder();
        StringBuilder correctAnswer = new StringBuilder();
        Pair<StringBuilder, StringBuilder> responseOption = null;
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsAnswer =
                    stmt.executeQuery("select id, answer, correct from Answers where id_question = \'" + idQuestion + "\'");
            int i = 1;
            while (rsAnswer.next()) {

                String correct = new String();
                if (Integer.parseInt(rsAnswer.getString("correct")) == 1) {
                    correct = String.valueOf(i);
                } else {
                    correct = null;
                }
                answer.append(rsAnswer.getString("answer") + sep);
                if (correct != null) {
                    correctAnswer.append(correct + sep + " ");
                }
                i++;
            }

            responseOption = new Pair<>(answer, correctAnswer);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return responseOption;
    }

    public ArrayList<Pair<Integer, String>> findQuestionForOpenTest(int idTest) {

        ArrayList<Pair<Integer, String>> listQuestion = new ArrayList<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsQuestion =
                    stmt.executeQuery("select q.id, q.question from Questions q, TestQuestion tq " +
                            "where tq.id_test = \'" + idTest + "\' and q.id = tq.id_question");
            while (rsQuestion.next()) {
                listQuestion.add(new Pair<>(Integer.parseInt(rsQuestion.getString("id")),
                        rsQuestion.getString("question")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return listQuestion;
    }

    public ArrayList<Pair<Integer, Pair<String, Integer>>> getAnswerTheQuestion(int idQuestion) {

        ArrayList<Pair<Integer, Pair<String, Integer>>> listAnswer = new ArrayList<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsQuestion =
                    stmt.executeQuery("select id, answer, correct from Answers " +
                            "where id_question = \'" + idQuestion + "\'");
            while (rsQuestion.next()) {
                listAnswer.add(new Pair<>(Integer.parseInt(rsQuestion.getString("id")),
                        new Pair<>(rsQuestion.getString("answer"),
                                Integer.parseInt(rsQuestion.getString("correct")))));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return listAnswer;
    }

    public HashMap<Integer, Pair<String, String>> findTestsBySubject(String subject) {

        HashMap<Integer, Pair<String, String>> testsBySubject = new HashMap<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsTest =
                    stmt.executeQuery("select t.id, t.name_test, u.first_name, u.middle_name, u.last_name " +
                            "from Test t, TestTutors tt, Users u " +
                            "where t.id = tt.id_test and tt.id_tutor = u.id and t.subject = \'" + subject + "\'");
            while (rsTest.next()) {
                String nameTutor = rsTest.getString("last_name") + " " + rsTest.getString("first_name").substring(0, 1) +
                        "." + rsTest.getString("middle_name").substring(0, 1) + ".";
                testsBySubject.put(Integer.parseInt(rsTest.getString("id")), new Pair<>(rsTest.getString("name_test"),
                        nameTutor));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return testsBySubject;
    }

    public HashMap<Integer, Pair<String, String>> findTestsByTutors(int idTutor) {

        HashMap<Integer, Pair<String, String>> testsByTutors = new HashMap<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsTest =
                    stmt.executeQuery("select t.id, t.name_test, t.subject " +
                            "from Test t, TestTutors tt, Users u " +
                            "where t.id = tt.id_test and tt.id_tutor = u.id and u.id = \'" + idTutor + "\'");
            while (rsTest.next()) {
                testsByTutors.put(Integer.parseInt(rsTest.getString("id")), new Pair<>(rsTest.getString("name_test"),
                        rsTest.getString("subject")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return testsByTutors;
    }

    public HashMap<Integer, Pair<String, String>> findMyCreatedTest(int idTutor) {

        HashMap<Integer, Pair<String, String>> myCreatedTest = new HashMap<>();
        try(Statement stmt = conn.createStatement()) {
            ResultSet rsTest =
                    stmt.executeQuery("select t.id, t.name_test, to_char(t.create_test_date, 'DD.MM.YYYY') as ctd " +
                            "from Test t, TestTutors tt " +
                            "where t.id = tt.id_test and tt.id_tutor = \'" + idTutor + "\'");
            while (rsTest.next()) {
                myCreatedTest.put(Integer.parseInt(rsTest.getString("id")), new Pair<>(rsTest.getString("name_test"),
                        rsTest.getString("ctd")));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return myCreatedTest;
    }

    public ArrayList<Pair<Integer, Pair<String, Pair<String, Pair<String, Pair<String, String>>>>>>
                                                                                    findMyPassedTest(int idStudent) {

        ArrayList<Pair<Integer, Pair<String, Pair<String, Pair<String, Pair<String, String>>>>>> passedTest =
                new ArrayList<>();
        try(Statement stmt = conn.createStatement()) {
            ArrayList<Integer> idTests = new ArrayList<>();
            ResultSet rsIdTest =
                    stmt.executeQuery("select distinct id_test, pass_test_date from Students " +
                            "where id_student = \'" + idStudent + "\' order by pass_test_date");
            while (rsIdTest.next()) {
                idTests.add(Integer.parseInt(rsIdTest.getString("id_test")));
            }
            for (Integer idTest: idTests) {
                ResultSet rsTest =
                        stmt.executeQuery("select name_test, subject, name_tutor, result, " +
                                "to_char(pass_test_date, 'DD.MM.YYYY') as ctd " +
                                "from Students where id_student = \'" + idStudent + "\' and id_test = \'" +
                                idTest + "\'");
                while (rsTest.next()) {
                    passedTest.add(new Pair<>(idTest, new Pair<>(rsTest.getString("name_test"),
                            new Pair<>(rsTest.getString("subject"), new Pair<>(rsTest.getString("name_tutor"),
                                    new Pair<>(rsTest.getString("result"), rsTest.getString("ctd")))))));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
        return passedTest;
    }

    public void deleteTest(int idTest) {

        try(Statement stmt = conn.createStatement()) {
            ResultSet rsIdQuestion =
                    stmt.executeQuery("select id_question from TestQuestion where id_test = \'" + idTest + "\'");
            ArrayList<Integer> idQuestions = new ArrayList<>();
            while (rsIdQuestion.next()) {
                idQuestions.add(rsIdQuestion.getInt("id_question"));
            }
            String deleteThisTest = "DELETE FROM Test where id=\'" + idTest + "\'";
            stmt.executeUpdate(deleteThisTest);
            if (idQuestions.size() > 0) {
                for(Integer o:idQuestions) {
                    String deleteTestQuestions = "DELETE FROM Questions where id = \'" + o +"\'";
                    stmt.executeUpdate(deleteTestQuestions);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error in Statement", e);
        }
    }

}
