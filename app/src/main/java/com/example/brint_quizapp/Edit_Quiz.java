package com.example.brint_quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brint_quizapp.dal.dao.QuizDAO;
import com.example.brint_quizapp.dal.dao.UserDAO;
import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;
import com.example.brint_quizapp.dal.dto.UserDTO;

import java.sql.Connection;
import java.util.ArrayList;

public class Edit_Quiz extends AppCompatActivity implements View.OnClickListener{

    Button next, prev;

    EditText a1, a2, a3, a4, quizName;

    CheckBox c1, c2, c3, c4;

    TextView questionOnScreen, questionCounter;

    Toast toast1, toast2, toast3, toast4;

    ImageButton edit1, edit2, edit3, edit4, edit5, save, delete;

    int currentQuestion = 0;

    QuizDTO ChosenQuiz;

    Intent goBack;

    ArrayList<QuestionDTO> QuizQuestions;
    ArrayList<AnswerDTO> QuizAnswers;

    SharedPreferences sharedPref;

    String currentTheme, sharedPreference;


    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("End quiz editing");
        builder.setMessage("You are about to end the quiz editing.\nThe changes will not be saved.\nAre you sure you want to quit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Edit_Quiz.this, Homepage_activity.class));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

        goBack = new Intent(this, Homepage_activity.class);

        setContentView(R.layout.edit_question_activity_layout);

        a1 = (EditText) findViewById(R.id.answer1);
        a1.setOnClickListener(this);

        a2 = (EditText) findViewById(R.id.answer2);
        a2.setOnClickListener(this);

        a3 = (EditText) findViewById(R.id.answer3);
        a3.setOnClickListener(this);

        a4 = (EditText) findViewById(R.id.answer4);
        a4.setOnClickListener(this);

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);

        prev = (Button) findViewById(R.id.previous);
        prev.setOnClickListener(this);

        prev.setEnabled(false);

        questionCounter = (TextView) findViewById(R.id.questionCounter);

        questionOnScreen = (EditText) findViewById(R.id.question);
        quizName = (EditText) findViewById(R.id.quiz_navn);

        c1 = (CheckBox) findViewById(R.id.checkBox);
        c2 = (CheckBox) findViewById(R.id.checkBox2);
        c3 = (CheckBox) findViewById(R.id.checkBox3);
        c4 = (CheckBox) findViewById(R.id.checkBox4);

        edit1 = (ImageButton) findViewById(R.id.edit1);
        edit1.setOnClickListener(this);

        edit2 = (ImageButton) findViewById(R.id.edit2);
        edit2.setOnClickListener(this);

        edit3 = (ImageButton) findViewById(R.id.edit3);
        edit3.setOnClickListener(this);

        edit4 = (ImageButton) findViewById(R.id.edit4);
        edit4.setOnClickListener(this);

        edit5 = (ImageButton) findViewById(R.id.edit5);
        edit5.setOnClickListener(this);

        edit1.setEnabled(false);
        edit2.setEnabled(false);
        edit3.setEnabled(false);
        edit4.setEnabled(false);
        edit5.setEnabled(false);

        save = (ImageButton) findViewById(R.id.save);
        save.setOnClickListener(this);

        delete = (ImageButton) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        ChosenQuiz = UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId"));
        QuizQuestions = ChosenQuiz.getQuestions();
        quizName.setText(ChosenQuiz.getName());

        if(QuizQuestions.size() == 0){

            ArrayList<AnswerDTO> tempAnswers = new ArrayList<AnswerDTO>();

            tempAnswers.add(new AnswerDTO("Svar 1", false));
            tempAnswers.add(new AnswerDTO("Svar 2", false));
            tempAnswers.add(new AnswerDTO("Svar 3", false));
            tempAnswers.add(new AnswerDTO("Svar 4", false));

            QuestionDTO blank = new QuestionDTO("Indsæt spørgsmål her", tempAnswers);
            blank.setQuiz_id(UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId")).getId());
            blank.setNumber(currentQuestion);
            QuizQuestions.add(blank);

        }

        QuizAnswers = QuizQuestions.get(currentQuestion).getAnswers();

        showNextQuestion();
        updateCheckBox();

        questionCounter.setText(currentQuestion + 1 + " / " + QuizQuestions.size());

    }

    @Override
    public void onClick(View v) {

        //Handles when the user presses the next and previous buttons
        if (R.id.next == v.getId()) {

            if (currentQuestion == QuizQuestions.size()-1) {

                ArrayList<AnswerDTO> tempAnswers = new ArrayList<AnswerDTO>();

                tempAnswers.add(new AnswerDTO("Answer 1", false));
                tempAnswers.add(new AnswerDTO("Answer 2", false));
                tempAnswers.add(new AnswerDTO("Answer 3", false));
                tempAnswers.add(new AnswerDTO("Answer 4", false));

                QuestionDTO blank = new QuestionDTO("Insert question here", tempAnswers);
                blank.setQuiz_id(UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId")).getId());
                blank.setNumber(currentQuestion+1);
                QuizQuestions.add(blank);
            }
        }

        if (R.id.previous == v.getId() || R.id.next == v.getId()) {

            questionCounter.setText(currentQuestion + 1 + " / " + QuizQuestions.size());

            if (c1.isChecked() == true) {
                QuizAnswers.get(0).setCorrect(true);
            } else {
                QuizAnswers.get(0).setCorrect(false);
            }

            if (c2.isChecked() == true) {
                QuizAnswers.get(1).setCorrect(true);
            } else {
                QuizAnswers.get(1).setCorrect(false);
            }

            if (c3.isChecked() == true) {
                QuizAnswers.get(2).setCorrect(true);
            } else {
                QuizAnswers.get(2).setCorrect(false);
            }

            if (c4.isChecked() == true) {
                QuizAnswers.get(3).setCorrect(true);
            } else {
                QuizAnswers.get(3).setCorrect(false);
            }

            saveQuestions();

            QuizQuestions.get(currentQuestion).setText(questionOnScreen.getText().toString());

            if (R.id.previous == v.getId()) {
                if (currentQuestion == 1) {
                    prev.setEnabled(false);
                }

                currentQuestion--;

                showNextQuestion();

                updateCheckBox();

            } else if (R.id.next == v.getId()) {

                prev.setEnabled(true);

                if (currentQuestion+1 == QuizQuestions.size()) {
                    next.setText("Add");
                    currentQuestion++;
                    showNextQuestion();
                    updateCheckBox();
                } else {
                    currentQuestion++;
                    showNextQuestion();
                    updateCheckBox();
                }
            }
            questionCounter.setText(currentQuestion + 1 + " / " + QuizQuestions.size());

            QuizAnswers = QuizQuestions.get(currentQuestion).getAnswers();
        }

        //Handles when the user presses the save icon
        if (R.id.save == v.getId()) { //http://www.apnatutorials.com/android/android-alert-confirm-prompt-dialog.php?categoryId=2&subCategoryId=34&myPath=android/android-alert-confirm-prompt-dialog.php

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ending editing");
            builder.setMessage("You are about to stop the editing.\nDo you wish to save the changes?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();

                    QuizQuestions.get(currentQuestion).setText(questionOnScreen.getText().toString());
                    saveQuestions();
                    updateCheckBox ();

                    UpdateDatabase update = new UpdateDatabase();
                    update.execute();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Changes are not saved", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }

        //Handles when the user presses the delete icon
        if (R.id.delete == v.getId()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete question");
            builder.setMessage("Are you sure you want to delete the question?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Question deleted", Toast.LENGTH_SHORT).show();
                    if(QuizQuestions.size() == 1){

                    } else if (currentQuestion+1 == QuizQuestions.size()) {
                        currentQuestion--;
                        showNextQuestion();
                        currentQuestion++;
                        QuizQuestions.remove(currentQuestion);
                        currentQuestion--;
                    } else {
                        currentQuestion++;
                        showNextQuestion();
                        currentQuestion--;
                        QuizQuestions.remove(currentQuestion);
                    }
                    questionCounter.setText(currentQuestion + 1 + " / " + QuizQuestions.size());

                    if (currentQuestion+1 == QuizQuestions.size()) {
                        next.setText("Add");
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();

        }

    }

    public void onCheckBox (View v) { //Consider changing the color of the text box

        if (v.getId() == c1.getId() && c1.isChecked() == true) {
            toast1 = Toast.makeText(getApplicationContext(), "Answer 1 is set to correct", Toast.LENGTH_SHORT); toast1.show();
            QuizAnswers.get(0).setCorrect(true);
        } else if (v.getId() == c1.getId() && c1.isChecked() == false) {
            QuizAnswers.get(0).setCorrect(false);
        }

        if (v.getId() == c2.getId() && c2.isChecked() == true){
            toast2 = Toast.makeText(getApplicationContext(), "Answer 2 is set to correct", Toast.LENGTH_SHORT); toast2.show();
            QuizAnswers.get(1).setCorrect(true);
        } else if (v.getId() == c2.getId() && c2.isChecked() == false) {
            QuizAnswers.get(1).setCorrect(false);
        }

        if (v.getId() == c3.getId() && c3.isChecked() == true) {
            toast3 = Toast.makeText(getApplicationContext(), "Answer 3 is set to correct", Toast.LENGTH_SHORT); toast3.show();
            QuizAnswers.get(2).setCorrect(true);
        } else if (v.getId() == c3.getId() && c3.isChecked() == false) {
            QuizAnswers.get(2).setCorrect(false);
        }

        if (v.getId() == c4.getId() && c4.isChecked() == true) {
            toast4 = Toast.makeText(getApplicationContext(), "Answer 4 is set to correct", Toast.LENGTH_SHORT); toast4.show();
            QuizAnswers.get(3).setCorrect(true);
        } else if (v.getId() == c4.getId() && c4.isChecked() == false) {
            QuizAnswers.get(3).setCorrect(false);
        }
    }

    public void updateCheckBox (){

        c1.setChecked(false);
        c2.setChecked(false);
        c3.setChecked(false);
        c4.setChecked(false);

        if (QuizAnswers.get(0).getCorrect()) {
            c1.setChecked(true);
            QuizAnswers.get(0).setCorrect(true);
        }
        if (QuizAnswers.get(1).getCorrect()) {
            c2.setChecked(true);
            QuizAnswers.get(1).setCorrect(true);
        }
        if (QuizAnswers.get(2).getCorrect()) {
            c3.setChecked(true);
            QuizAnswers.get(2).setCorrect(true);
        }
        if (QuizAnswers.get(3).getCorrect()) {
            c4.setChecked(true);
            QuizAnswers.get(3).setCorrect(true);
        }
    }

    public void showNextQuestion(){

        QuizAnswers = QuizQuestions.get(currentQuestion).getAnswers();

        questionOnScreen.setText(QuizQuestions.get(currentQuestion).getText());

        a1.setText(QuizAnswers.get(0).getText());
        a2.setText(QuizAnswers.get(1).getText());
        a3.setText(QuizAnswers.get(2).getText());
        a4.setText(QuizAnswers.get(3).getText());

        if (currentQuestion+1 == QuizQuestions.size()) {
            next.setText("Add");
        } else {
            next.setText("Next");
        }

    }

    public void saveQuestions() {

        QuizAnswers.get(0).setText(a1.getText().toString());
        QuizAnswers.get(1).setText(a2.getText().toString());
        QuizAnswers.get(2).setText(a3.getText().toString());
        QuizAnswers.get(3).setText(a4.getText().toString());

        QuizQuestions.get(currentQuestion).setAnswers(QuizAnswers);

        QuizQuestions.get(currentQuestion).getAnswers().get(0).setText(a1.getText().toString());
        QuizQuestions.get(currentQuestion).getAnswers().get(1).setText(a2.getText().toString());
        QuizQuestions.get(currentQuestion).getAnswers().get(2).setText(a3.getText().toString());
        QuizQuestions.get(currentQuestion).getAnswers().get(3).setText(a4.getText().toString());

    }

    private class UpdateDatabase extends AsyncTask<String, Void, Void> {

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

                quizDAO.deleteQuiz(UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId")).getId(),connection);

                connection.commit();

                QuizDTO newQuiz = new QuizDTO();
                newQuiz.setUser_id(UserSingleton.getUserSingleton().getUser().getId());
                newQuiz.setName(UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId")).getName());
                newQuiz.setQuestions(QuizQuestions);
                newQuiz.setName(quizName.getText().toString());
                newQuiz.setId(UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId")).getId());

                quizDAO.createQuiz(newQuiz,connection);

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

            startActivity(goBack);

        }

    }
}
