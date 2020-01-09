package com.example.brint_quizapp.dal.dao;

import com.example.brint_quizapp.dal.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DDL {

    public final static String server = "";
    public final static String username = "";
    public final static String password = "";



    public static void createTables(Connection c) throws DALException {

        try {
            String query = "CREATE TABLE IF NOT EXISTS " + "" + " ("
                    + "id int(10) auto_increment,"
                    + "navn varchar(50),"
                    + "PRIMARY KEY (id))";
            PreparedStatement statement = c.prepareStatement(query);
            statement.execute();



            c.commit();
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public static void deleteTables(Connection c) throws DALException{

        try {
            PreparedStatement statement = c.prepareStatement("DROP TABLE IF EXISTS " + "");
            statement.execute();


            c.commit();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    public static Connection createConnection() throws DALException {
        Connection connection = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection(server, username, password);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        return connection;
    }
}
