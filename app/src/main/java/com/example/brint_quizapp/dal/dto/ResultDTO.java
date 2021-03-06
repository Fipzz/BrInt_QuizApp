package com.example.brint_quizapp.dal.dto;

public class ResultDTO {

    int question_id;
    int quiz_id;
    int user_id;
    AnswerDTO answer;

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }



    public AnswerDTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerDTO answer) {
        this.answer = answer;
    }



    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

}
