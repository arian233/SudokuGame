package com.cmpt276.lota.sudoku;

import java.util.Date;
import java.util.UUID;

public class Words {

    private UUID mId;
    private String languageOne;
    private String languageTwo;

    public Words(String languageOne, String languageTwo) {
        mId = UUID.randomUUID();
        this.languageOne = languageOne;
        this.languageTwo = languageTwo;
    }

    public UUID getId() {
        return mId;
    }

    public String getLanguageOne() {
        return languageOne;
    }

    public void setLanguageOne(String languageOne) {
        this.languageOne = languageOne;
    }

    public String getLanguageTwo() {
        return languageTwo;
    }

    public void setLanguageTwo(String languageTwo) {
        this.languageTwo = languageTwo;
    }
}
