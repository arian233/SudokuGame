package com.cmpt276.lota.sudoku.model;

public class Language {
    private int number;
    private String languageOne;
    private String languageTwo;
    private int flag=0;//0 is first langauge(preset), 1 is user entered

    /**
     * constructor
     * @param  number: a int of number of a grid
     * @param  languageOne: a String of 1st Language of a grid
     * @param  languageTwo: a String of 2nd Language of a grid
     * @param  flag: a int of flag of a grid
     */
    public Language(int number, String languageOne, String languageTwo, int flag){
        this.number = number;
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
        this.flag = flag;
    }

    /**
     * get number
     * @return number of a grid
     */
    public int getNumber(){
        return number;
    }

    /**
     * get 1st langauage
     * @return String of 1st Language of a grid
     */
    public String getLanguageOne() {
        return languageOne;
    }

    /**
     * get 2nd langauage
     * @return String of 2nd Language of a grid
     */
    public String getLanguageTwo() {
        return languageTwo;
    }

    /**
     * get flag
     * @return int of flag of a grid
     */
    public int getFlag() {
        return flag;
    }

    /**
     * set number
     * @param number: a int of number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * set number
     * @param flag: a int of number
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

}
