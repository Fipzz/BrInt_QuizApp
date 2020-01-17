package com.example.brint_quizapp.dal.dao;

import com.example.brint_quizapp.Question;
import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizDAO {
    public ArrayList<QuizDTO> getQuizzesByUserId(int id, Connection c){
        ArrayList<QuizDTO> quizzes = new ArrayList<QuizDTO>();

        try {
            String query = "SELECT * FROM quiz WHERE user_id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            while(result.next()){
                QuizDTO quiz = getQuizByQuizId(result.getInt("id"), c);
                quizzes.add(quiz);
            }

        }catch (SQLException e){
            return null;
        }

        return quizzes;
    }

    private QuizDTO getQuizByQuizId(int id, Connection c){
        QuizDTO quiz = new QuizDTO();
        ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();

        try {
            String query = "SELECT * FROM quiz WHERE id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            if (!result.next()){
                return null;
            }

            quiz.setId(id);
            quiz.setName(result.getString("name"));
            quiz.setCode(result.getInt("id"));
            quiz.setType(result.getInt("type"));

            query = "SELECT * FROM question WHERE quiz_id = " + id + ";";
            statement = c.prepareStatement(query);
            result = statement.executeQuery();

            c.commit();

            while(result.next()){
                QuestionDTO question = new QuestionDTO();
                question.setId(result.getInt("id"));
                question.setNumber(result.getInt("number"));
                question.setText(result.getString("text"));
                questions.add(question);
            }

            query = "SELECT * FROM answer WHERE ";
            for (int i = 0; i < questions.size(); i++) {
                query = query + "question_id = " + questions.get(i).getId();
                if(i != questions.size()-1){
                    query = query + " OR ";
                }
            }
            query = query + ";";

            statement = c.prepareStatement(query);
            result = statement.executeQuery();

            c.commit();

            ArrayList<AnswerDTO> allAnswers = new ArrayList<>();

            while(result.next()){
                AnswerDTO answer = new AnswerDTO();
                answer.setCorrect(result.getBoolean("correct"));
                answer.setId(result.getInt("id"));
                answer.setText(result.getString("text"));
                answer.setQuestion_id(result.getInt("question_id"));

                allAnswers.add(answer);
            }

            for (QuestionDTO question : questions) {
                ArrayList<AnswerDTO> answers = new ArrayList<>();
                for (AnswerDTO answer : allAnswers) {
                    if(answer.getQuestion_id() == question.getId()){
                        answers.add(answer);
                    }
                }

                question.setAnswers(answers);

            }

            quiz.setQuestions(questions);

        }catch (SQLException e){
            return null;
        }

        return quiz;
    };
}
