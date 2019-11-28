package com.example.brint_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button student, lector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        student = (Button) findViewById(R.id.student);
        student.setOnClickListener(this);
        lector = (Button) findViewById(R.id.lector);
        lector.setOnClickListener(this);

        TextView header = (TextView) findViewById(R.id.questionView);
        header.setText("Quiz app (B5)");

    }

    @Override
    public void onClick(View v) {

        if(R.id.student == v.getId()){

            startActivity(new Intent(MainActivity.this, Login_student.class));

        } else if(R.id.lector == v.getId()){

            startActivity(new Intent(MainActivity.this, Login_lector.class));

        }

    }

}
