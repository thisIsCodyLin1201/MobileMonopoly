package com.example.mobilemonopoly.data.model;

public class TrueFalseQuetion {

    private int tfQuestionID;
    private String tfQuestion;
    private String tfAnswer;

    public TrueFalseQuetion(int tfQuestionID, String tfQuestion, String tfAnswer){
        this.tfQuestionID = tfQuestionID;
        this.tfQuestion = tfQuestion;
        this.tfAnswer = tfAnswer;
    }

    public int getTfQuestionID(){
        return tfQuestionID;
    }

    public String getTfQuestion(){
        return tfQuestion;
    }

    public String getTfAnswer(){
        return tfAnswer;
    }

}
