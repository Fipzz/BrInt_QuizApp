package com.example.brint_quizapp.dal.dto;

public class AnswerDTO {

    public AnswerDTO(){}

    public AnswerDTO(String text, boolean correct){

        this.text = text;
        this.correct = correct;

    }

    int id;
    int question_id;
    String text;
    Boolean correct;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
