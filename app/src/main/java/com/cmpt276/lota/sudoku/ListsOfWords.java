package com.cmpt276.lota.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListsOfWords {
    private List<Words> mWordLists;

    private String mWordListsName;
    private UUID mId;

    public ListsOfWords(List<Words> mWordLists, String str){
        this.mWordLists = mWordLists;
        mWordListsName = str;
        mId = UUID.randomUUID();
    }

    public ListsOfWords(String[] str1, String[] str2, String name) {
        mWordLists = new ArrayList<>();
        mId = UUID.randomUUID();
        for (int i = 0; i < str1.length; i++) {
            Words words = new Words(str1[i],str2[i]); //here generate each preset list of words
            mWordLists.add(words);
        }
        mWordListsName = name;
    }

    public UUID getId() {
        return mId;
    }

    public List<Words> getWordLists() {
        return mWordLists;
    }

    public String getWordListsName() {
        return mWordListsName;
    }


}
