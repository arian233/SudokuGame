package com.cmpt276.lota.sudoku.model;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordListLab implements Serializable {
    private static WordListLab mWordListLab;//class itself
    private List<ListsOfWords> mListsOfWord;//the big list of wordlists
    private UUID id = UUID.randomUUID();
    private String notFamiliarWord[][];// to save 3 not familiar words

    private int hasSetId = -1;//-1 is not set
    private int hasSetFamiliar = -1;//-1 is not set familiar
    private int puzzleSize = 9;

    /**
     * get a new WordListLab singleton object
     * @param context context
     */
    public static WordListLab get(Context context) {
        if (mWordListLab == null) {
            mWordListLab = new WordListLab(context);
        }
        return mWordListLab;
    }

    /**
     * constructor
     * @param context context
     */
    private WordListLab(Context context) {
        String[] lan1 = new String[] {"one", "two","three","four", "five","six","seven", "eight","nine","ten","eleven","twelve","thirteen"};
        String[] lan2 = new String[] {"一", "二","三","四", "五","六","七", "八","九","十","十一","十二","十三"};
        String[] lan3 = new String[] {"guitar", "sing","swim","dance", "draw","chess","speak", "join","club","story","write","show","kungfu","drum","violin","piano"};
        String[] lan4 = new String[] {"吉他", "唱歌","游泳","跳舞", "画","国际象棋","说话", "加入","社团","故事","写字","展示","功夫","鼓","小提琴","钢琴"};
        mListsOfWord = new ArrayList<>();
        ListsOfWords listsOfWords = new ListsOfWords(lan1, lan2, "chapter1"); //here generate lists of the name of each preset wordslist
        mListsOfWord.add(listsOfWords);
        listsOfWords = new ListsOfWords(lan3, lan4, "chapter2");
        mListsOfWord.add(listsOfWords);
        //mListsOfWord.sort
    }

    /**
     * return the big list of wordlists
     */
    public List<ListsOfWords> getListsOfWord() {
        return mListsOfWord;
    }

    /**
     * return a specific one word list from the big lists
     * @param id id of user chosen wordlist
     */
    public ListsOfWords getListsOfWords(UUID id) {
        for (ListsOfWords listsOfWord : mListsOfWord) {
            if (listsOfWord.getId().equals(id)) {
                return listsOfWord;
            }
        }
        return null;
    }

    /**
     * add one new list to the big list of wordLists
     * @param listsOfWords add new ListsOfWords into class
     */
    public void addListsOfWords(ListsOfWords listsOfWords) {
        mListsOfWord.add(listsOfWords);
    }

    /**
     * set the id for a userchosen word list which is to be used for generate puzzle
     * @param id id of user chosen wordlist
     */
    public void setId(UUID id){
        this.id = id;
        hasSetId = 1;
    }

    /**
     * get the id for a userchosen word list which is to be used for generate puzzle
     */
    public UUID getId() {
        return id;
    }

    /**
     * get the string of three most unfamiliar words
     */
    public String[][] getUnfamiliarWord()
    {
        return notFamiliarWord;
    }

    /**
     * set the string of three most unfamiliar words
     */
    public void setUnfamiliarWord(String[][] notFamiliarWord) {
        this.notFamiliarWord = notFamiliarWord;
        hasSetFamiliar = 1;
    }

    /**
     * set the flag that userr has chosen a word list to be shown
     */
    public int getHasSetId() {
        return hasSetId;
    }

    /**
     * set the flag that userr has set three most unfamiliar words
     */
    public int getHasSetFamiliar() {
        return hasSetFamiliar;
    }

    /**
     * get the PuzzleSize
     */
    public int getPuzzleSize() {
        return puzzleSize;
    }

    /**
     * set the PuzzleSize
     */
    public void setPuzzleSize(int puzzleSize) {
        this.puzzleSize = puzzleSize;
    }

    /**
     * reset
     */
    public void setNull() {
        mWordListLab = null;
    }
}
