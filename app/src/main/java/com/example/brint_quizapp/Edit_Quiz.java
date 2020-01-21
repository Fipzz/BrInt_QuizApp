package com.example.brint_quizapp;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.brint_quizapp.dal.dto.AnswerDTO;
import com.example.brint_quizapp.dal.dto.QuestionDTO;
import com.example.brint_quizapp.dal.dto.QuizDTO;

import java.util.ArrayList;

public class Edit_Quiz extends AppCompatActivity implements View.OnClickListener{

    //TODO add a button to remove a question

    //TODO handle going back on first question

    Button next, prev;

    EditText a1, a2, a3, a4;

    CheckBox c1, c2, c3, c4;

    TextView quizName, questionOnScreen, questionCounter;

    Toast toast1, toast2, toast3, toast4;

    ImageButton edit1, edit2, edit3, edit4, save, delete;

    ArrayList<Question_item> questions = new ArrayList<Question_item>();

    Question_item questionEdit;

    int currentQuestion = 0;

    QuizDTO ChosenQuiz;

    ArrayList<QuestionDTO> QuizQuestions;
    ArrayList<AnswerDTO> QuizAnswers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        quizName = (TextView) findViewById(R.id.name);
        questionCounter = (TextView) findViewById(R.id.questionCounter);
        questionOnScreen = (EditText) findViewById(R.id.question);

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

        edit1.setEnabled(false);
        edit2.setEnabled(false);
        edit3.setEnabled(false);
        edit4.setEnabled(false);

        save = (ImageButton) findViewById(R.id.save);
        save.setOnClickListener(this);

        delete = (ImageButton) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        ChosenQuiz = UserSingleton.getUserSingleton().getUser().getQuizzes().get(getIntent().getExtras().getInt("quizId"));
        QuizQuestions = ChosenQuiz.getQuestions();


        questionEdit = questions.get(currentQuestion);
        showNextQuestion(questionEdit, currentQuestion);
        updateCheckBox();

        questionCounter.setText(currentQuestion + 1 + " / " + questions.size());

    }

    @Override
    public void onClick(View v) {

        QuizAnswers = QuizQuestions.get(currentQuestion).getAnswers();

        //Handles when the user presses the next and previous buttons
        if (R.id.next == v.getId()) {
            if (next.getText().toString() == "Tilføj") {

                ArrayList<AnswerDTO> tempAnswers = new ArrayList<AnswerDTO>();

                tempAnswers.add(new AnswerDTO("Svar 1", false));
                tempAnswers.add(new AnswerDTO("Svar 2", false));
                tempAnswers.add(new AnswerDTO("Svar 3", false));
                tempAnswers.add(new AnswerDTO("Svar 4", false));

                QuestionDTO blank = new QuestionDTO("Indsæt spørgsmål her", tempAnswers);
                QuizQuestions.add(blank);
            }
        }

        if (R.id.previous == v.getId() || R.id.next == v.getId()) {

            if (c1.isChecked() == true) {
                QuizAnswers.get(1).setCorrect(true);
            } else {
                QuizAnswers.get(1).setCorrect(false);
            }

            if (c2.isChecked() == true) {
                QuizAnswers.get(2).setCorrect(true);
            } else {
                QuizAnswers.get(2).setCorrect(false);
            }

            if (c3.isChecked() == true) {
                QuizAnswers.get(3).setCorrect(true);
            } else {
                QuizAnswers.get(3).setCorrect(false);
            }

            if (c4.isChecked() == true) {
                QuizAnswers.get(4).setCorrect(true);
            } else {
                QuizAnswers.get(4).setCorrect(false);
            }

            saveQuestions(QuizQuestions.get(currentQuestion));

            if (next.getText().toString() == "Tilføj") {
                next.setText("Næste");
            }

            if (R.id.previous == v.getId()) {
                if (currentQuestion == 1) {
                    prev.setEnabled(false);
                }

                currentQuestion--;
                showNextQuestion(questions.get(currentQuestion), currentQuestion);
                updateCheckBox();
            } else if (R.id.next == v.getId()) {

                prev.setEnabled(true);

                if (currentQuestion == questions.size()-2) {
                    next.setText("Tilføj");
                    currentQuestion++;
                    showNextQuestion(questions.get(currentQuestion), currentQuestion);
                    updateCheckBox();
                } else {
                    currentQuestion++;
                    showNextQuestion(questions.get(currentQuestion), currentQuestion);
                    updateCheckBox();
                }
            }
            questionCounter.setText(currentQuestion + 1 + " / " + questions.size());
        }

        //Handles when the user presses the save icon
            if (R.id.save == v.getId()) { //http://www.apnatutorials.com/android/android-alert-confirm-prompt-dialog.php?categoryId=2&subCategoryId=34&myPath=android/android-alert-confirm-prompt-dialog.php

            //TODO In this if statement, if the user presses to save in the prompt menu, the current arraylist is to be uploaded to the database.

            final Intent goBack = new Intent(this, Homepage_activity.class);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Afslutning af redigering");
            builder.setMessage("Du er ved afslutte redigeringen.\nØnsker du at gemme dine ændringer?   ");
            builder.setCancelable(true);
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Ændringer gemt", Toast.LENGTH_SHORT).show();
                    startActivity(goBack);
                }
            });
            builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Ændringer ikke gemt", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }

        //Handles when the user presses the delete icon
        if (R.id.delete == v.getId()) {

            //TODO In this if statement, if the user presses to delete in the prompt menu, the currentQuestion is to be dropped from the table in the database. Consider adding an animation.

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Slet spørgsmål");
            builder.setMessage("Er du sikker på at du ønsker at slette spørgsmålet?");
            builder.setCancelable(true);
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "Spørgsmål Slettet", Toast.LENGTH_SHORT).show();
                    if (currentQuestion == questions.size()-1) {
                        currentQuestion--;
                        showNextQuestion(questions.get(currentQuestion), currentQuestion);
                        currentQuestion++;
                        questions.remove(currentQuestion);
                        currentQuestion--;
                    } else {
                        currentQuestion++;
                        showNextQuestion(questions.get(currentQuestion), currentQuestion);
                        currentQuestion--;
                        questions.remove(currentQuestion);
                    }
                    questionCounter.setText(currentQuestion + 1 + " / " + questions.size());
                    if (currentQuestion == questions.size()-1) {
                        next.setText("Tilføj");
                    }
                }
            });
            builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }

    public void onCheckBox (View v) { //Consider changing the color of the text box

        if (R.id.checkBox == v.getId()) {
            c2.setChecked(false);
            c3.setChecked(false);
            c4.setChecked(false);
        } else if (R.id.checkBox2 == v.getId()) {
            c1.setChecked(false);
            c3.setChecked(false);
            c4.setChecked(false);
        } else if (R.id.checkBox3 == v.getId()) {
            c1.setChecked(false);
            c2.setChecked(false);
            c4.setChecked(false);
        } else if (R.id.checkBox4 == v.getId()) {
            c1.setChecked(false);
            c2.setChecked(false);
            c3.setChecked(false);
        }

        if (c1.isChecked()) {
            toast1 = Toast.makeText(getApplicationContext(), "Svar 1 sat til rigtigt", Toast.LENGTH_SHORT); toast1.show();

        } else if (c2.isChecked()){
            toast2 = Toast.makeText(getApplicationContext(), "Svar 2 sat til rigtigt", Toast.LENGTH_SHORT); toast2.show();

        } else if (c3.isChecked()) {
            toast3 = Toast.makeText(getApplicationContext(), "Svar 3 sat til rigtigt", Toast.LENGTH_SHORT); toast3.show();

        } else if (c4.isChecked()) {
            toast4 = Toast.makeText(getApplicationContext(), "Svar 4 sat til rigtigt", Toast.LENGTH_SHORT); toast4.show();
        }
    }

    public void updateCheckBox (){

            int isCorrect = 1;
        if (isCorrect == 1) {
            c1.setChecked(true);
            c2.setChecked(false);
            c3.setChecked(false);
            c4.setChecked(false);
        } else if (isCorrect == 2) {
            c2.setChecked(true);
            c1.setChecked(false);
            c3.setChecked(false);
            c4.setChecked(false);
        } else if (isCorrect == 3) {
            c3.setChecked(true);
            c1.setChecked(false);
            c2.setChecked(false);
            c4.setChecked(false);
        } else if (isCorrect == 4) {
            c4.setChecked(true);
            c1.setChecked(false);
            c2.setChecked(false);
            c3.setChecked(false);
        }
    }

    public void showNextQuestion(Question_item questionEdit, int currentQuestion){

        questionOnScreen.setText(questionEdit.getQuestion());

        a1.setText(questionEdit.getAnswer1());
        a2.setText(questionEdit.getAnswer2());
        a3.setText(questionEdit.getAnswer3());
        a4.setText(questionEdit.getAnswer4());
        int isCorrect = 1;
        isCorrect = questionEdit.getIsCorrect();
    }

    public void saveQuestions(QuestionDTO questions) {

        questions.getAnswers().get(0).setText(a1.getText().toString());
        questions.getAnswers().get(1).setText(a2.getText().toString());
        questions.getAnswers().get(2).setText(a3.getText().toString());
        questions.getAnswers().get(3).setText(a4.getText().toString());


        questionEdit.setQuestion(questionOnScreen.getText().toString());

        questionEdit.setAnswer1(a1.getText().toString());
        questionEdit.setAnswer2(a2.getText().toString());
        questionEdit.setAnswer3(a3.getText().toString());
        questionEdit.setAnswer4(a4.getText().toString());
    }
}
