package com.example.brint_quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.UserDTO;
import com.google.firebase.internal.InternalTokenProvider;

import java.sql.Connection;
import java.util.ArrayList;

public class Quiz_list_activity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private ImageView addQuiz;

    private ArrayList<String> quizNames;
    private ArrayList<QuizDTO> myQuizDTO;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.quiz_list_activity_layout);

        listView = (ListView) findViewById(R.id.quiz_listView);

        quizNames = new ArrayList<String>();

        addQuiz = (ImageView) findViewById(R.id.addQuiz); addQuiz.setOnClickListener(this);


        initMyQuiz();

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                getBaseContext(),
                android.R.layout.simple_list_item_1,
                quizNames
        ){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position,convertView,parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.buttonblue));
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(0, 25, 0,25 );
                tv.setTextSize(30);

                return view;
            }

        };

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast toast = Toast.makeText(getApplicationContext(),
                        Integer.toString(i),
                        Toast.LENGTH_SHORT);

                toast.show();

                Intent result = new Intent(Quiz_list_activity.this,Edit_quiz_home_activity.class);

                Bundle quizForEdit = new Bundle();
                quizForEdit.putInt("chosenQuiz", i);

                result.putExtras(quizForEdit);

                startActivity(result);
            }
        });

    }

    @Override
    public void onClick(View view) {

    }

    private void initMyQuiz(){

        for (QuizDTO quiz: UserSingleton.getUserSingleton().getUser().getQuizzes()) {
            quizNames.add(quiz.getName());
        }

    }
}
