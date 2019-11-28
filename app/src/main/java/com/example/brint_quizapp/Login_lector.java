package com.example.brint_quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login_lector extends AppCompatActivity implements View.OnClickListener {

    Button a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);


        a = (Button) findViewById(R.id.answer1);
        a.setOnClickListener(this);

        b = (Button) findViewById(R.id.answer2);
        b.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {



    }

}
