package com.example.librarykiosk.mbti;
public class QuestionList {
    private String category;
    private String questionText;
    private String answer1;
    private String answer2;
    private String index;

    public QuestionList(String index, String category, String questionText, String answer1, String answer2) {
        this.index = index;
        this.category = category;
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    // Getters
    public String getIndex() {
        return index;
    }
    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }
}
