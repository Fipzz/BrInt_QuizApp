package com.example.brint_quizapp.dal.dao;

import com.example.brint_quizapp.Quiz_logic_activity;
import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuizDAO {

    int currentQuestionId;

    public ArrayList<QuizDTO> getQuizzesByUserId(int id, Connection c) {
        ArrayList<QuizDTO> quizzes = new ArrayList<QuizDTO>();

        try {
            String query = "SELECT * FROM quiz WHERE user_id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            while (result.next()) {
                QuizDTO quiz = getQuizByQuizId(result.getInt("id"), c);
                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            return null;
        }

        return quizzes;
    }


    public QuizDTO getQuizByQuizId(int id, Connection c) {
        QuizDTO quiz = new QuizDTO();
        ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();

        try {
            String query = "SELECT * FROM quiz WHERE id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            if (!result.next()) {
                return null;
            }

            quiz.setId(id);
            quiz.setName(result.getString("name"));
            quiz.setCode(result.getInt("id"));
            quiz.setType(result.getInt("type"));
            quiz.setUser_id(result.getInt("user_id"));

            query = "SELECT * FROM question WHERE quiz_id = " + id + ";";
            statement = c.prepareStatement(query);
            result = statement.executeQuery();

            c.commit();

            while (result.next()) {
                QuestionDTO question = new QuestionDTO();
                question.setId(result.getInt("id"));
                question.setNumber(result.getInt("number"));
                question.setText(result.getString("question_text"));
                questions.add(question);
            }

            if(questions.size() > 0) {

                query = "SELECT * FROM answer WHERE ";
                for (int i = 0; i < questions.size(); i++) {
                    query = query + "question_id = " + questions.get(i).getId();
                    if (i != questions.size() - 1) {
                        query = query + " OR ";
                    }
                }
                query = query + ";";

                statement = c.prepareStatement(query);
                result = statement.executeQuery();

                c.commit();

                ArrayList<AnswerDTO> allAnswers = new ArrayList<>();

                while (result.next()) {
                    AnswerDTO answer = new AnswerDTO();
                    answer.setCorrect(result.getBoolean("correct"));
                    answer.setId(result.getInt("id"));
                    answer.setText(result.getString("answer_text"));
                    answer.setQuestion_id(result.getInt("question_id"));

                    allAnswers.add(answer);
                }

                for (QuestionDTO question : questions) {
                    ArrayList<AnswerDTO> answers = new ArrayList<>();
                    for (AnswerDTO answer : allAnswers) {
                        if (answer.getQuestion_id() == question.getId()) {
                            answers.add(answer);
                        }
                    }

                    question.setAnswers(answers);
                }

            }

            quiz.setQuestions(questions);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return quiz;
    }

    public boolean deleteQuiz(int id, Connection c) {
        try {
            String query = "DELETE FROM quiz WHERE id = ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setInt(1, id);

            statement.execute();
            c.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean createQuiz(QuizDTO quiz, Connection c) {
        ArrayList<QuestionDTO> questions = new ArrayList<>();
        questions = quiz.getQuestions();

        try {

            String query = "INSERT INTO quiz (id, user_id, name, type) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setInt(1, quiz.getId());
            statement.setInt(2, quiz.getUser_id());
            statement.setString(3, quiz.getName());
            statement.setInt(4, quiz.getType());

            statement.execute();


        } catch (SQLException p) {
            p.printStackTrace();
            return false;
        }

        for (QuestionDTO question : questions) {
            ArrayList<AnswerDTO> answers = new ArrayList<>();

            try {
                String query = "INSERT INTO question (quiz_id, question_text, number) VALUES (?, ?, ?)";
                PreparedStatement statement = c.prepareStatement(query);

                statement.setInt(1, quiz.getId());
                statement.setString(2, question.getText());
                statement.setInt(3, question.getNumber());

                statement.executeUpdate();



                query = "SELECT * FROM question WHERE quiz_id = ? AND number = ?";
                statement = c.prepareStatement(query);

                statement.setInt(1, question.getQuiz_id());
                statement.setInt(2, question.getNumber());

                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()){
                    currentQuestionId = resultSet.getInt("id");
                }else{
                    return false;
                }

            } catch (SQLException p) {
                p.printStackTrace();
                return false;
            }

            answers = question.getAnswers();

            for (AnswerDTO answer : answers) {

                try {
                    String query = "INSERT INTO answer (question_id, answer_text, correct) VALUES (?, ?, ?)";
                    PreparedStatement statement = c.prepareStatement(query);

                    int correct;
                    if (answer.getCorrect()) {
                        correct = 1;
                    } else {
                        correct = 0;
                    }

                    statement.setInt(1, currentQuestionId);
                    statement.setString(2, answer.getText());
                    statement.setInt(3, correct);

                    statement.executeUpdate();
                    c.commit();
                } catch (SQLException p) {
                    p.printStackTrace();
                    return false;
                }

            }


        }

        return true;

    }

}
