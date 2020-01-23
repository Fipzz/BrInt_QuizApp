package com.example.brint_quizapp;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.brint_quizapp.dal.dao.QuizDAO;
import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.UserDTO;
import com.google.firebase.internal.InternalTokenProvider;

import java.sql.Connection;
import java.util.ArrayList;

public class Quiz_list_activity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;

    private ImageView addQuiz;

    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;

    private ArrayList<String> quizNames;
    private ArrayList<QuizDTO> myQuizDTO;

    CountDownTimer timer;

    Button makequiz;
    EditText quizname;

    boolean making_quiz = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreference = getString(R.string.preferenceFile);

        sharedPref = getSharedPreferences(sharedPreference, MODE_PRIVATE);
        currentTheme = sharedPref.getString("current_theme", "blue_theme");

        if (currentTheme.equals("blue_theme")){

            setTheme(R.style.Theme_App_Blue);


        } else if (currentTheme.equals("purple_theme")) {

            setTheme(R.style.Theme_App_Purple);

        }

        setContentView(R.layout.quiz_list_activity_layout);

        listView = (ListView) findViewById(R.id.quiz_listView);

        quizNames = new ArrayList<String>();

        addQuiz = (ImageView) findViewById(R.id.addQuiz); addQuiz.setOnClickListener(this);

        makequiz = (Button) findViewById(R.id.make_new_quiz);
        makequiz.setOnClickListener(this);

        quizname = (EditText) findViewById(R.id.new_quiz_name);

        quizname.setVisibility(View.INVISIBLE);
        makequiz.setVisibility(View.INVISIBLE);

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

                tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blueButtonText));
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

                Intent result = new Intent(Quiz_list_activity.this,Edit_quiz_home_activity.class);

                Bundle quizForEdit = new Bundle();
                quizForEdit.putInt("chosenQuiz", i);

                result.putExtras(quizForEdit);

                startActivity(result);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == addQuiz.getId() && making_quiz == true){

            making_quiz = false;

            listView.setVisibility(View.VISIBLE);

            quizname.setVisibility(View.INVISIBLE);
            makequiz.setVisibility(View.INVISIBLE);

            addQuiz.setImageResource(android.R.drawable.ic_menu_add);

        } else if (v.getId() == addQuiz.getId() && making_quiz == false){

            making_quiz = true;

            listView.setVisibility(View.INVISIBLE);

            quizname.setVisibility(View.VISIBLE);
            makequiz.setVisibility(View.VISIBLE);

            addQuiz.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);

        }

        if (v.getId() == makequiz.getId()){

            CreateNewQuiz createNewQuiz = new CreateNewQuiz();
            createNewQuiz.execute();

        }

    }

    private void initMyQuiz(){

        UserDTO user = UserSingleton.getUserSingleton().getUser();
        ArrayList<QuizDTO> quizz = user.getQuizzes();
        for (QuizDTO quiz: quizz) {
            quizNames.add(quiz.getName());
        }

    }

    private class CreateNewQuiz extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            Connection connection;
            DBconnector databaseconn = new DBconnector();

            try {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                connection = databaseconn.CONN();
                connection.setAutoCommit(false);

                QuizDAO quizDAO = new QuizDAO();

                QuizDTO newquiz = new QuizDTO();

                newquiz.setUser_id(UserSingleton.getUserSingleton().getUser().getId());
                newquiz.setName(quizname.getText().toString());
                newquiz.setQuestions(new ArrayList<QuestionDTO>());
                quizDAO.createQuiz(newquiz,connection);

                connection.commit();

                UserDAO updateUser = new UserDAO();
                UserDTO updatedUser = new UserDTO();
                updatedUser = updateUser.getUserById(UserSingleton.getUserSingleton().getUser().getId(), connection);

                UserSingleton.getUserSingleton().setUser(updatedUser);

                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            startActivity(new Intent(Quiz_list_activity.this, Quiz_list_activity.class));

        }
    }
}
