package com.cmpt276.lota.sudoku;

public class CheckResult {
    private WordListLab wordListLab = WordListLab.getWordListLab();
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
     * @return boolean
     */
    public boolean checkValid(Language puzzle[][], int puzzleYIndex, int puzzleXIndex) {
        int check[]=new int[puzzleSize+1];

        //check row
        for(int i=0; i<puzzleSize ; i++){
            if(puzzle[puzzleYIndex][i].getNumber()!=0){
                if (check[puzzle[puzzleYIndex][i].getNumber()] == 1 )
                    return false;
                else
                    check[puzzle[puzzleYIndex][i].getNumber()] = 1;
            }
        }
        //check column
        for(int i=0; i<puzzleSize ; i++){
            if(puzzle[i][puzzleXIndex].getNumber()!=0){
                if (check[puzzle[i][puzzleXIndex].getNumber()] == 2 )
                    return false;
                else
                    check[puzzle[i][puzzleXIndex].getNumber()] = 2;
            }
        }
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
