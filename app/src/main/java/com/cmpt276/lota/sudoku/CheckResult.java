package com.cmpt276.lota.sudoku;

public class CheckResult {
    private int puzzleSize=9;


    public CheckResult(){}

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
        int x = puzzleXIndex/3;
        int y = puzzleYIndex/3;

        x*=3;
        y*=3;
        for(int j=0; j<3; j++){
            for(int i=0; i<3; i++){
                if (check[puzzle[y][x+i].getNumber()] == 3 )
                    return false;
                else
                    check[puzzle[y][x+i].getNumber()] = 3;
            }
        }

        return true;
    }
}
