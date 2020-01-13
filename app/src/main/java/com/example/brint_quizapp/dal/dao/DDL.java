package com.example.brint_quizapp.dal.dao;

import android.os.AsyncTask;

import com.example.brint_quizapp.dal.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DDL{

    private static Connection connection;
    public final static String server = "jdbc:mysql://remotemysql.com/4QRbwapGHC";
    public final static String username = "4QRbwapGHC";
    public final static String password = "DTOQ5QarNE";

    public DDL(){

    }

    public void createTables(Connection c) throws DALException {

        try {
            String query = "CREATE TABLE IF NOT EXISTS " + "user" + " ("
                    + "id int(10) auto_increment,"
                    + "name varchar(50) NOT NULL,"
                    + "email varchar(50) NOT NULL UNIQUE,"
                    + "password varchar(1000) NOT NULL,"
                    + "PRIMARY KEY (id))";
            PreparedStatement statement = c.prepareStatement(query);
            statement.execute();

            query = "CREATE TABLE IF NOT EXISTS " + "quiz" + " ("
                    + "id int(10) auto_increment,"
                    + "user_id int(10),"
                    + "name varchar(50),"
                    + "type int(1),"
                    + "PRIMARY KEY (id)),"
                    + "FOREIGN KEY (user_id) REFERENCES user (id)";
            statement = c.prepareStatement(query);
            statement.execute();

            query = "CREATE TABLE IF NOT EXISTS " + "question" + " ("
                    + "id int(10) auto_increment,"
                    + "quiz_id int(10),"
                    + "number int(10),"
                    + "PRIMARY KEY (id)),"
                    + "FOREIGN KEY (quiz_id) REFERENCES quiz (id) ON DELETE CASCADE";
            statement = c.prepareStatement(query);
            statement.execute();

            query = "CREATE TABLE IF NOT EXISTS " + "answer" + " ("
                    + "id int(10) auto_increment,"
                    + "question_id int(10),"
                    + "answer varchar(10),"
                    + "correct int(1),"
                    + "PRIMARY KEY (id)),"
                    + "FOREIGN KEY question_id REFERENCES question (id) ON DELETE CASCADE";
            statement = c.prepareStatement(query);
            statement.execute();

            query = "CREATE TABLE IF NOT EXISTS " + "result" + " ("
                    + "question_id int(10),"
                    + "user_id int(10),"
                    + "correct int(1),"
                    + "PRIMARY KEY (question_id, user_id)),"
                    + "FOREIGN KEY user_id REFERENCES user (id),"
                    + "FOREIGN KEY question_id REFERENCES question (id) ON DELETE CASCADE";
            statement = c.prepareStatement(query);
            statement.execute();

            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    public void deleteTables(Connection c) throws DALException{

        try {

            PreparedStatement statement = c.prepareStatement("DROP TABLE IF EXISTS " + "user");
            statement.execute();

            statement = c.prepareStatement("DROP TABLE IF EXISTS " + "quiz");
            statement.execute();

            statement = c.prepareStatement("DROP TABLE IF EXISTS " + "question");
            statement.execute();

            statement = c.prepareStatement("DROP TABLE IF EXISTS " + "answer");
            statement.execute();

            statement = c.prepareStatement("DROP TABLE IF EXISTS " + "result");
            statement.execute();

            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public static Connection getConnection() throws DALException {
        return connection;
    }
}
