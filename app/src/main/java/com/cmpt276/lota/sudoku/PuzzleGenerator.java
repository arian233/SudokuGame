package com.cmpt276.lota.sudoku;

import java.util.ArrayList;

public class PuzzleGenerator {
    private String lan[][];// to record two languages, may needs to change it, if the later iteration requires more than two languages.
    private final int lanSize = 10;
    private final int puzzleSize = 9;

    /**
     * Constructor: to have a 2d array of preset two languages
     */
    PuzzleGenerator(){
        //
        lan = new String[][]{ {"dummy","one", "two","three","four", "five","six","seven", "eight","nine"},{"dummy","一", "二","三","四", "五","六","七", "八","九"} };
    }

    /**
     * to generate 81 Language objects, each one contains the number and two languages.
     * @param  language: an arraylist of Language objects
     * @return an arraylist of Language objects
     */
    public ArrayList<Language> generateLanguage(ArrayList<Language> language){
        for(int i=0; i<lanSize;i++){
            language.add(new Language(i,lan[0][i],lan[1][i],0));
        }
        return language;
    }

    /**
     * to generate puzzle
     * @return a generated puzzle
     */
    public Language[][] generatePuzzle(){
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
                    puzzle[j][i]=new Language(tmp[j][i], lan[0][tmp[j][i]], lan[1][tmp[j][i]],-1);//-1 means preset grid
                else
                    puzzle[j][i]=new Language(tmp[j][i], lan[0][tmp[j][i]], lan[1][tmp[j][i]],0);//0 means dummy
            }
        }
        return puzzle;
    }
}
