package com.cmpt276.lota.sudoku.model;

import java.util.UUID;

public class Words {

    private UUID mId;
    private String languageOne;
    private String languageTwo;

    /**
     * constructor
     */
    public Words(String languageOne, String languageTwo) {
        mId = UUID.randomUUID();
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
    }

    /**
     * getId
     */
    public UUID getId() {
        return mId;
    }

    /**
     * getLanguageOne
     */
    public String getLanguageOne() {
        return languageOne;
    }

    /**
     * setLanguageOne
     * @param languageOne 1st language
     */
    public void setLanguageOne(String languageOne) {
        this.languageOne = languageOne;
    }

    /**
     * getLanguageTwo
     */
    public String getLanguageTwo() {
        return languageTwo;
    }

    /**
     * setLanguageTwo
     * @param languageTwo second language
     */
    public void setLanguageTwo(String languageTwo) {
        this.languageTwo = languageTwo;
    }
}
