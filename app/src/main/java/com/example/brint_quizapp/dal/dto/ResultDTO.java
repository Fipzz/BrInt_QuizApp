package com.example.brint_quizapp.dal.dto;

public class ResultDTO {

    int question_id, answer_id;
    int user_id;
    boolean correct;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int correct_answer_id) {
        this.answer_id = correct_answer_id;
    }
}
