package com.example.brint_quizapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnector {
    String classs = "com.mysql.jdbc.Driver";

    String url = "jdbc:mysql://remotemysql.com:3306/PG17gBGeZc";
    String un = "PG17gBGeZc";
    // Det er første gang nogensinde jeg ser nogen bruge en database direkte fra en app.
    // Det er generelt en meget dårlig idé fordi DB adgangskoden så ligger i app'en. Dog OK til en prototype
    String password = "Yqeou76dpr";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy); // Burde være unødvendigt da I kun kalder CONN fra en baggrundstråd
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);

            conn = DriverManager.getConnection(url, un, password);

            //conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

}