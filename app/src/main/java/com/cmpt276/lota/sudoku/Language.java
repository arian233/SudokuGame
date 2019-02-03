package com.cmpt276.lota.sudoku;

public class Language {
    private int number;
    private String languageOne;
    private String languageTwo;
    private int flag=0;//0 is first langauge(preset), 1 is user entered

    public Language(int number, String languageOne, String languageTwo, int flag){
        this.number = number;
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
        this.flag = flag;
    }

    public int getNumber(){
        return number;
    }

    public String getLanguageOne() {
        return languageOne;
    }

    public String getLanguageTwo() {
        return languageTwo;
    }

    public int getFlag() {
        return flag;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean equals(Language obj) {
        return this.number==obj.number;
    }
}
