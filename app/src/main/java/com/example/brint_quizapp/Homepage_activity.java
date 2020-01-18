package com.example.brint_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Homepage_activity extends AppCompatActivity implements View.OnClickListener {

    //TODO rediger quiz skal ændres til "Mine Quizzer" og skal føre til quiz oversigt

    Button quiz, profile, edit;
    EditText quiz_code;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_activity_layout);

        quiz = (Button) findViewById(R.id.start_quiz_knap);
        quiz.setOnClickListener(this);

        profile = (Button) findViewById(R.id.profil_knap);
        profile.setOnClickListener(this);

        edit = (Button) findViewById(R.id.rediger_knap);
        edit.setOnClickListener(this);

        quiz_code = (EditText) findViewById(R.id.unikkode);


        toast = Toast.makeText(getApplicationContext(),
                "Indsæt unik kode",
                Toast.LENGTH_SHORT);

    }

    @Override
    public void onClick(View v) {

        if(quiz.getId() == v.getId()){

            if (quiz_code.getText().toString() == ""){

                toast.show();

            } else {

                startActivity(new Intent(Homepage_activity.this, Question.class));

            }



        } else if(profile.getId() == v.getId()){


        } else if(edit.getId() == v.getId()){

            //startActivity(new Intent(Homepage_activity.this, Edit_Quiz.class));
            //startActivity(new Intent(Homepage_activity.this, Statistics.class));
            startActivity(new Intent(Homepage_activity.this, Quiz_Menu.class));


        }

    }

}
