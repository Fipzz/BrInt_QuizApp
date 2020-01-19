package com.example.brint_quizapp.dal.dao;

import com.example.brint_quizapp.dal.dto.ResultDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultDAO {

    public boolean createResult(ResultDTO result, Connection c){
        try {

            String query = "INSERT INTO result (question_id, user_id, answer_id, correct) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = c.prepareStatement(query);

            int correct;
            if (result.isCorrect()) {
                correct = 1;
            } else {
                correct = 0;
            }

            statement.setInt(1, result.getQuestion_id());
            statement.setInt(2, result.getUser_id());
            statement.setInt(3, result.getAnswer_id());
            statement.setInt(4, correct);

            statement.execute();
            c.commit();

        } catch (SQLException p) {
            return false;
        }
        return true;
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
                boolean correct;
                if(result.getInt("correct") == 0){
                    correct = false;
                }else{
                    correct = true;
                }
                r.setCorrect(correct);
                r.setAnswer_id(result.getInt("answer_id"));
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
