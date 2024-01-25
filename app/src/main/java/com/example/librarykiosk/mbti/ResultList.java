package com.example.librarykiosk.mbti;

public class ResultList {
    private String result;
    private String who;
    private String book;
    private String info;
    private String word;

    public ResultList(String result, String who, String book, String info, String word) {
        this.who = who;
        this.result = result;
        this.book = book;
        this.info = info;
        this.word = word;
    }

    public String getResult() {
        return result;
    }

    public String getWho() {
        return who;
    }

    public String getBook() {
        return book;
    }

    public String getInfo() {
        return info;
    }

    public String getWord() {
        return word;
    }
}
