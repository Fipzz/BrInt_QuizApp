package com.example.brint_quizapp.dal.dao;

import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.ResultDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultDAO {

    public boolean createResult(ResultDTO result, Connection c){
        try {

            String query = "INSERT INTO result (question_id, user_id, answer_id) VALUES (?, ?, ?)";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setInt(1, result.getQuestion_id());
            statement.setInt(2, result.getUser_id());
            statement.setInt(3, result.getAnswer().getId());

            statement.execute();
            c.commit();

        } catch (SQLException p) {
            return false;
        }
        return true;
    }



    public ArrayList<ResultDTO> getResultsFromQuizId(int id, Connection c){
        ArrayList<ResultDTO> results = new ArrayList<>();

        try {
            String query = "SELECT * FROM result WHERE quiz_id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            while(result.next()){
                ResultDTO r = new ResultDTO();
                AnswerDTO answerDTO = new AnswerDTO();
                int answer_id = result.getInt("answer_id");

                query = "SELECT * FROM answer WHERE id = " + answer_id + ";";
                statement = c.prepareStatement(query);
                ResultSet result1 = statement.executeQuery();
                c.commit();

                if(!result1.next()){
                    return null;
                }

                answerDTO.setQuestion_id(result1.getInt("question_id"));
                answerDTO.setText(result1.getString("answer_text"));
                answerDTO.setId(result1.getInt("id"));
                int correct = result1.getInt("correct");

                if(correct == 0) {
                    answerDTO.setCorrect(false);
                }else{
                    answerDTO.setCorrect(true);
                }

                r.setAnswer(answerDTO);
                r.setQuestion_id(result.getInt("question_id"));
                r.setUser_id(result.getInt("user_id"));

                results.add(r);
            }

        }catch (SQLException e){
            return null;
        }
        return results;
    }

    public ArrayList<ResultDTO> getResultsFromUserId(int id, Connection c){
        ArrayList<ResultDTO> results = new ArrayList<>();

        try {
            String query = "SELECT * FROM result WHERE user_id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            while(result.next()){
                ResultDTO r = new ResultDTO();
                AnswerDTO answerDTO = new AnswerDTO();
                int answer_id = result.getInt("answer_id");

                query = "SELECT * FROM answer WHERE id = " + answer_id + ";";
                statement = c.prepareStatement(query);
                ResultSet result1 = statement.executeQuery();
                c.commit();

                if(!result1.next()){
                    return null;
                }

                answerDTO.setQuestion_id(result1.getInt("question_id"));
                answerDTO.setText(result1.getString("answer_text"));
                answerDTO.setId(result1.getInt("id"));
                int correct = result1.getInt("correct");

                if(correct == 0) {
                    answerDTO.setCorrect(false);
                }else{
                    answerDTO.setCorrect(true);
                }

                r.setAnswer(answerDTO);
                r.setQuestion_id(result.getInt("question_id"));
                r.setUser_id(result.getInt("user_id"));

                results.add(r);
            }

        }catch (SQLException e){
            return null;
        }
        return results;
    }

}
