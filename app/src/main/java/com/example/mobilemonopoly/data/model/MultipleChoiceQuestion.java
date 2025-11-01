package com.example.mobilemonopoly.data.model;

public class MultipleChoiceQuestion {

    private int mcQuestionID;
    private String mcQuestion;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String mcAnswer;

    public MultipleChoiceQuestion(int mcQuestionID, String mcQuestion, String choice1, String choice2, String choice3, String choice4, String mcAnswer) {
        this.mcQuestionID = mcQuestionID;
        this.mcQuestion = mcQuestion;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.mcAnswer = mcAnswer;
    }

    public int getMcQuestionID() {
        return mcQuestionID;
    }

    public String getMcQuestion() {
        return mcQuestion;
    }

    public String getChoice1() {
        return choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public String getMcAnswer() {
        return mcAnswer;
    }

}
