package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.brint_quizapp.dal.DALException;
import com.example.brint_quizapp.dal.dao.DDL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_test extends AppCompatActivity implements View.OnClickListener {

    Button createTables, deleteTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_test_activity_layout);

        createTables = (Button) findViewById(R.id.createTables);
        createTables.setOnClickListener(this);

        deleteTables = (Button) findViewById(R.id.deleteTables);
        deleteTables.setOnClickListener(this);

    }

    private static class CreateTablesClass extends AsyncTask<String, Void, Void> {
        Connection connection;
        private final static String server = "jdbc:sqlserver://127.0.0.1/test:3306";
        private final static String username = "root";
        private final static String password = "";

        @Override
        protected Void doInBackground(String... strings) {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                connection = DriverManager.getConnection(server, username, password);
                connection.setAutoCommit(false);

                DDL ddl = new DDL();
                ddl.createTables(connection);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    private static class DeletetablesClass extends AsyncTask<String, Void, Void> {
        Connection connection;
        private final static String server = "jdbc:mysql://";
        private final static String username = "4QRbwapGHC";
        private final static String password = "DTOQ5QarNE";

        @Override
        protected Void doInBackground(String... strings) {
            try {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                connection = DriverManager.getConnection(server, username, password);
                connection.setAutoCommit(false);

                DDL ddl = new DDL();
                ddl.deleteTables(connection);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    @Override
    public void onClick(View v){

        if(v.getId() == createTables.getId()){
            new CreateTablesClass().execute();
            }
        else if(v.getId() == deleteTables.getId()){
            new DeletetablesClass().execute();
        }

    }

}

