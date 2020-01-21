package com.example.brint_quizapp.dal.dto;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionDTO {

    int id, number, quiz_id;
    String text;
    ArrayList<AnswerDTO> answers;

    public QuestionDTO(){}
    public QuestionDTO(String text, ArrayList<AnswerDTO> answers){

        this.text = text;
        this.answers = answers;

    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerDTO> answers) {
        this.answers = answers;
    }
}
