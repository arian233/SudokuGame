package com.cmpt276.lota.sudoku.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListsOfWords {
    private List<Words> mWordLists;
    private String mWordListsName;
    private UUID mId = UUID.randomUUID();

    /**
     * constructor
     * @param mWordLists a list of word
     * @param str name of this list
     */
    public ListsOfWords(List<Words> mWordLists, String str){
        this.mWordLists = mWordLists;
        mWordListsName = str;
    }

    /**
     *constructor
     * @param str1 string of 1st language
     * @param str2 string of 2nd language
     * @param name name of this list
     */
    public ListsOfWords(String[] str1, String[] str2, String name) {
        mWordLists = new ArrayList<>();
        mId = UUID.randomUUID();
        for (int i = 0; i < str1.length; i++) {
            Words words = new Words(str1[i],str2[i]); //here generate each preset list of words
            mWordLists.add(words);
        }
        mWordListsName = name;
    }

    /**
     * get id
     * @return id
     */
    public UUID getId() {
        return mId;
    }

    /**
     * getWordLists
     * @return WordLists
     */
    public List<Words> getWordLists() {
        return mWordLists;
    }

    /**
     * getWordListsName
     * @return name of WordLists
     */
    public String getWordListsName() {
        return mWordListsName;
    }
}
