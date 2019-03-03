package com.cmpt276.lota.sudoku;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordListLab implements Serializable {
    private static WordListLab sWordListLab;//class itself
    private List<ListsOfWords> mListsOfWord;//the big list of wordlists
    private UUID id = null;
    private String notFamiliarWord[][] = null;// to save 3 not familiar words

    //get class itself
    public static WordListLab get(Context context) {
        if (sWordListLab == null) {
            sWordListLab = new WordListLab(context);
        }
        return sWordListLab;
    }

    //get class itself
    public static WordListLab getWordListLab() {
        return sWordListLab;
    }

    private WordListLab(Context context) {
        String[] lan1 = new String[] {"one", "two","three","four", "five","six","seven", "eight","nine","ten","eleven",};
        String[] lan2 = new String[] {"一", "二","三","四", "五","六","七", "八","九","十","十一"};
        String[] lan3 = new String[] {"guitar", "sing","swim","dance", "draw","chess","speak", "join","club","story","write","show","kungfu","drum","violin","piano"};
        String[] lan4 = new String[] {"吉他", "唱歌","游泳","跳舞", "画","国际象棋","说话", "加入","社团","故事","写字","展示","功夫","鼓","小提琴","钢琴"};
        mListsOfWord = new ArrayList<>();
        ListsOfWords listsOfWords = new ListsOfWords(lan1, lan2, "chapter1"); //here generate lists of the name of each preset wordslist
        mListsOfWord.add(listsOfWords);
        listsOfWords = new ListsOfWords(lan3, lan4, "chapter2");
        mListsOfWord.add(listsOfWords);
        //mListsOfWord.sort
    }

    //return the big list of wordlists
    public List<ListsOfWords> getListsOfWord() {
        return mListsOfWord;
    }

    //return a specific one word list from the big lists
    public ListsOfWords getListsOfWords(UUID id) {
        for (ListsOfWords listsOfWord : mListsOfWord) {
            if (listsOfWord.getId().equals(id)) {
                return listsOfWord;
            }
        }
        return null;
    }

    //add one new list to the big list of wordLists
    public void addListsOfWords(ListsOfWords listsOfWords) {
        mListsOfWord.add(listsOfWords);
    }

    //set the id for a specific word list which is to be used for generate puzzle(user chosen)
    public void setId(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }


    public String[][] getNotFamiliarWord() {
        return notFamiliarWord;
    }

    public void setNotFamiliarWord(String[][] notFamiliarWord) {
        this.notFamiliarWord = notFamiliarWord;
    }

}
