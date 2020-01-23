package com.example.brint_quizapp.dal.dao;

import com.example.brint_quizapp.dal.DALException;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    public boolean createUser(UserDTO user, Connection c){
        try {

            String query = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            statement.execute();
            c.commit();


        } catch (SQLException p) {
            return false;
        }
        return true;
    }

    public UserDTO getUserByEmail(String email, Connection c){

        UserDTO user = new UserDTO();

        try {

            ArrayList<QuizDTO> quizzes = new ArrayList<QuizDTO>();
            String query = "SELECT * FROM user WHERE email = ?;";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            c.commit();

            if (!result.next()) {
                return null;
            }

            int id = result.getInt("id");

            user.setId(id);
            user.setName(result.getString("name"));
            user.setEmail(email);
            user.setPassword(result.getString("password"));
            QuizDAO quizDAO = new QuizDAO();
            user.setQuizzes(quizDAO.getQuizzesByUserId(id, c));
            ResultDAO resultDAO = new ResultDAO();
            user.setResults(resultDAO.getResultsFromUserId(id, c));

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return user;

    }

    public UserDTO getUserById(int id, Connection c) throws DALException {
        try {
            UserDTO user = new UserDTO();
            ArrayList<QuizDTO> quizzes= new ArrayList<QuizDTO>();
            String query = "SELECT * FROM user WHERE id = " + id + ";";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            c.commit();

            if (!result.next()){
                return null;
            }

            user.setId(id);
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setPassword(result.getString("password"));
            QuizDAO quizDAO = new QuizDAO();
            user.setQuizzes(quizDAO.getQuizzesByUserId(id, c));
            ResultDAO resultDAO = new ResultDAO();
            user.setResults(resultDAO.getResultsFromUserId(id, c));

            return user;

        } catch (SQLException e){
            throw new DALException(e.getMessage());
        }
    }

}
