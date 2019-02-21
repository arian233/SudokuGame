package com.cmpt276.lota.sudoku;

import java.util.ArrayList;

public class PuzzleGenerator {
    private static String lan1[];// to record two languages, may needs to change it, if the later iteration requires more than two languages.
    private static String lan2[];
    private static final int lanSize = 10;
    private static final int puzzleSize = 9;

    /**
     * Constructor: to have a 2d array of preset two languages
     */
    PuzzleGenerator(){
        //
        lan1 = new String[] {"dummy","one", "two","three","four", "five","six","seven", "eight","nine"};
        lan2 = new String[] {"dummy","一", "二","三","四", "五","六","七", "八","九"};
    }

    /**
     * to generate puzzle
     * @return a generated puzzle
     */
    public static Language[][] generatePuzzle(){
        int tmp[][] = new int[][]{
                { 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 4, 5, 6, 0, 8, 0, 1, 0, 0 },
                { 7, 8, 9, 1, 0, 3, 0, 0, 6 },
                { 0, 1, 0, 3, 6, 0, 0, 9, 7 },
                { 3, 6, 5, 8, 9, 7, 2, 1, 0 },
                { 8, 0, 7, 2, 1, 4, 3, 6, 5 },
                { 5, 0, 1, 0, 4, 2, 9, 0, 8 },
                { 6, 4, 2, 9, 0, 8, 5, 3, 1 },
                { 0, 7, 8, 5, 0, 1, 0, 4, 0 } };

        Language puzzle[][]=new Language[puzzleSize][puzzleSize];
        for(int j=0;j<puzzleSize;j++) {
            for (int i = 0; i < puzzleSize; i++) {
                if( tmp[j][i] != 0 )
                    puzzle[j][i]=new Language(tmp[j][i], lan1[tmp[j][i]], lan2[tmp[j][i]],-1);//-1 means preset grid
                else
                    puzzle[j][i]=new Language(tmp[j][i], lan1[tmp[j][i]], lan2[tmp[j][i]],0);//0 means dummy
            }
        }
        return puzzle;
    }

    /**
     * to return LanguageOne array
     * @return an array of Language one
     */
    public static String[] getLanOne(){
        String str[]=new String[puzzleSize];
        for(int i=0; i<puzzleSize;i++){
            str[i]=lan1[i+1];
        }
        return str;
    }

    /**
     * to return LanguageTwo array
     * @return an array of Language two
     */
    public static String[] getLanTwo(){
        String str[]=new String[puzzleSize];
        for(int i=0; i<puzzleSize;i++){
            str[i]=lan2[i+1];
        }
        return str;
    }
}
