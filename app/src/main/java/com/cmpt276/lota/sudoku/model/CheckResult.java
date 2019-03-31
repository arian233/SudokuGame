package com.cmpt276.lota.sudoku.model;

import android.content.Context;

public class CheckResult {
    private Context testContex;
    private WordListLab wordListLab = WordListLab.get(testContex);
    private final int puzzleSize = wordListLab.getPuzzleSize();
    private int regionNumX = 3;
    private int regionNumY = 3;

    /**
     * default constructor
     */
    public CheckResult(){
        if(puzzleSize == 4){
            regionNumX = 2;
            regionNumY = 2;
        }else if(puzzleSize == 6){
            regionNumX = 2;
            regionNumY = 3;
        }else if(puzzleSize == 12) {
            regionNumX = 3;
            regionNumY = 4;
        }
    }

    /**
     * to check repetition.
     * @param puzzle: an 2d array of Language objects
     * @param puzzleYIndex: the y index of 2d array
     * @param puzzleXIndex: the x index of 2d array
     * @return true is no conflict
     */
    public boolean checkValid(Language puzzle[][], int puzzleYIndex, int puzzleXIndex) {
        return checkRow(puzzle,puzzleYIndex,puzzleXIndex) && checkColumn(puzzle,puzzleYIndex,puzzleXIndex) && checkRegion(puzzle,puzzleYIndex,puzzleXIndex);
    }

    /**
     * to check row repetition.
     * @param puzzle: an 2d array of Language objects
     * @param puzzleYIndex: the y index of 2d array
     * @param puzzleXIndex: the x index of 2d array
     * @return true is no conflict
     */
    public boolean checkRow(Language puzzle[][], int puzzleYIndex, int puzzleXIndex) {
        int check[] = new int[puzzleSize+1];
        for(int i=0; i<puzzleSize ; i++){
            if(puzzle[puzzleYIndex][i].getNumber()!=0){
                if (check[puzzle[puzzleYIndex][i].getNumber()] == 1)
                    return false;
                else
                    check[puzzle[puzzleYIndex][i].getNumber()] = 1;
            }
        }
        return true;
    }

    /**
     * to check column repetition.
     * @param puzzle: an 2d array of Language objects
     * @param puzzleYIndex: the y index of 2d array
     * @param puzzleXIndex: the x index of 2d array
     * @return true is no conflict
     */
    public boolean checkColumn(Language puzzle[][], int puzzleYIndex, int puzzleXIndex) {
        int check[] = new int[puzzleSize+1];
        for(int i=0; i<puzzleSize ; i++){
            if(puzzle[i][puzzleXIndex].getNumber()!=0){
                if (check[puzzle[i][puzzleXIndex].getNumber()] == 2 )
                    return false;
                else
                    check[puzzle[i][puzzleXIndex].getNumber()] = 2;
            }
        }
        return true;
    }

    /**
     * to check region repetition.
     * @param puzzle: an 2d array of Language objects
     * @param puzzleYIndex: the y index of 2d array
     * @param puzzleXIndex: the x index of 2d array
     * @return true is no conflict
     */
    public boolean checkRegion(Language puzzle[][], int puzzleYIndex, int puzzleXIndex) {
        int check[] = new int[puzzleSize+1];
        //check 9 grid
        int x = puzzleXIndex/regionNumX;
        int y = puzzleYIndex/regionNumY;

        x *= regionNumX;
        y *= regionNumY;
        for(int j=0; j<regionNumY; j++){
            for(int i=0; i<regionNumX; i++){
                if(puzzle[y+j][x+i].getNumber()!=0){
                    if (check[puzzle[y+j][x+i].getNumber()] == 3 )
                        return false;
                    else
                        check[puzzle[y+j][x+i].getNumber()] = 3;
                }
            }
        }
        return true;
    }

    /**
     * to check if there's an empty grid.
     * @param puzzle: an 2d array of Language objects
     * @return boolean
     */
    public boolean checkResult(Language puzzle[][]){
        for(int j=0; j<puzzleSize ; j++) {
            for (int i = 0; i < puzzleSize; i++) {
                if (puzzle[j][i].getNumber() == 0 )
                    return false;
            }
        }
        return true;
    }
}
