package com.cmpt276.lota.sudoku;

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
     * constructor
     */
    public UUID getId() {
        return mId;
    }

    /**
     * constructor
     */
    public String getLanguageOne() {
        return languageOne;
    }

    /**
     * constructor
     */
    public void setLanguageOne(String languageOne) {
        this.languageOne = languageOne;
    }

    /**
     * constructor
     */
    public String getLanguageTwo() {
        return languageTwo;
    }

    /**
     * constructor
     * @param languageTwo second language
     */
    public void setLanguageTwo(String languageTwo) {
        this.languageTwo = languageTwo;
    }
}
