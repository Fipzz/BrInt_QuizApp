package com.example.brint_quizapp;

import com.example.brint_quizapp.dal.DALException;
import com.example.brint_quizapp.dal.dao.DDL;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {

    public static void main(String[] args) throws DALException, SQLException {

        try(Connection c = DDL.createConnection()){
            DDL.createTables(c);
        }catch (DALException e){
            System.out.println("Error");
        }

    }
}
