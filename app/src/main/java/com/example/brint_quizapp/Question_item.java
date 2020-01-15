package com.example.brint_quizapp;

public class Question_item {

    //TODO Updateres til at inkludere quiz navn
    //TODO Evt, added rigtige og forkerte svar hertil eller oprette nye klasse til at håndtere dette

    private String question,answer1,answer2,answer3,answer4;
    private int isCorrect;

    Question_item(String question, String answer1, String answer2, String answer3, String answer4, int isCorrect){

        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.isCorrect = isCorrect;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
}
