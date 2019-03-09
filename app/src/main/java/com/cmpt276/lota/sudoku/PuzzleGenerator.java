package com.cmpt276.lota.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PuzzleGenerator {
    private static final int lanSize = 10;
    private static final int puzzleSize = 9;
    private static final int emptyGridNum = 10;
    private static final int regionNum = 3;
    private static String lan1[] = new String[lanSize];// to record two languages, may needs to change it, if the later iteration requires more than two languages.
    private static String lan2[] = new String[lanSize];
    private ArrayList<ArrayList<Integer>> available = new ArrayList<ArrayList<Integer>>();
    private Random rand;
    public static boolean conflict = true; //True if there is a conflict
    private WordListLab wordListLab = WordListLab.getWordListLab();

    /**
     * Constructor: to have a 2d array of preset two languages
     */
    public PuzzleGenerator(){
        lan1[0] = "";
        lan2[0] = "";

        int i = 1;
        for(int j=0 ; j < 3; j++){
            if( wordListLab.getHasSetFamiliar() != -1 && wordListLab.getNotFamiliarWord()[j][0] != "" ){
                lan1[i] = wordListLab.getNotFamiliarWord()[j][0];
                lan2[i] = wordListLab.getNotFamiliarWord()[j][1];
                i++;
            }
        }

        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        Random random=new Random();
        int size;
        if(wordListLab.getHasSetId() != -1){
            size = wordListLab.getListsOfWords( wordListLab.getId() ).getWordLists().size();//to chosen wordslist size
        }else{
            size = wordListLab.getListsOfWord().get(0).getWordLists().size();// get 1st wordlist size
        }

        while(i<lanSize){//filling remaining empty language array(randomly choose words from wordlist)
            int randomInt=random.nextInt( size );// get a random number between 0~n-1
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);

                if(wordListLab.getHasSetId() != -1){
                    ListsOfWords list = wordListLab.getListsOfWords( wordListLab.getId() );//to get words from chosen wordslist
                    lan1[i] = list.getWordLists().get(randomInt).getLanguageOne();
                    lan2[i] = list.getWordLists().get(randomInt).getLanguageTwo();
                }else{
                    lan1[i] = wordListLab.getListsOfWord().get(0).getWordLists().get(randomInt).getLanguageOne();// get words from 1st wordlist
                    lan2[i] = wordListLab.getListsOfWord().get(0).getWordLists().get(randomInt).getLanguageTwo();
                }
                i++;
            }
        }

    }

    /**
     * Constructor: to have a 2d array of preset two languages
     */
    public Language[][] generateGrid(){
        int[][] tmp = new int[puzzleSize][puzzleSize];
        int currentPos = 0;
        clearGrid(tmp);
        rand = new Random();
        while (currentPos < puzzleSize*puzzleSize){
            if (available.get(currentPos).size() != 0){ //if arraylist is not empty
                int i = rand.nextInt(available.get(currentPos).size()); // get random number
                int number = available.get(currentPos).get(i);

                if (!checkConflict(tmp, currentPos, number)){
                    conflict = false;
                    int xPos = currentPos % puzzleSize;
                    int yPos = currentPos / puzzleSize;
                    tmp[xPos][yPos] = number;
                    available.get(currentPos).remove(i);
                    currentPos++;
                }
                else{
                    available.get(currentPos).remove(i);
                }
            }else{
                for (int i = 1; i <= puzzleSize; i++){
                    available.get(currentPos).add(i);
                }
                currentPos--;
            }
            if(currentPos<0)
                currentPos = 0;
        }

        removeElments(tmp);

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
     * To randomly remove the cells (set the cell to 0)
     * @param puzzle: an 2d array of int
     */
    private void removeElments(int[][] puzzle)
    {
        int removeNumber = 0;
        while(removeNumber < emptyGridNum)
        {
            int x_location = rand.nextInt(puzzleSize);
            int y_location = rand.nextInt(puzzleSize);
            if (puzzle[x_location][y_location] != 0)
            {
                puzzle[x_location][y_location] = 0;
                removeNumber++;
            }
        }
        conflict = getConflict(puzzle);
    }

    /**
     * To clear the Grid(set all the cells of the gird to -1)
     * @param puzzle: an 2d array of int
     */
    private void clearGrid(int[][] puzzle){
        available.clear();                  //Clears the arrayList

        for (int y = 0; y < puzzleSize; y++){
            for (int x = 0; x < puzzleSize; x++){
                puzzle[x][y] = -1;
            }
        }

        for (int x = 0; x < puzzleSize*puzzleSize; x++){       // add 1 to 9 to arraylist
            available.add(new ArrayList<Integer>());
            for (int i = 1; i <= puzzleSize; i++){
                available.get(x).add(i);
            }
        }
    }

    /**
     * To check if the current puzzle is a complete and valid puzzle
     * if there is conflict return true otherwise return false
     * @param puzzle: an 2d array of int
     * @return boolean
     */
    public boolean getConflict(int[][] puzzle){
        for (int currentPos = 0; currentPos < puzzleSize*puzzleSize; currentPos++) {
            int xPos = currentPos % puzzleSize;
            int yPos = currentPos / puzzleSize;
            if (puzzle[xPos][yPos] != -1){
                if (checkConflict(puzzle, currentPos, puzzle[xPos][yPos])){
                    return true;
                }
            }
            else{
                return true;
            }
        }
        return false;
    }

    /**
     * To check if the current position cell crates conflict
     * if there is conflict return true otherwise return false
     * @param puzzle: an 2d array of int
     * @param currentPos: the number to define the position of the cell
     * @param number: the current cell number that needs to be checked
     * @return boolean
     */
    private boolean checkConflict(int[][] puzzle, int currentPos, final int number){
        int xPos = currentPos % puzzleSize;
        int yPos = currentPos / puzzleSize;

        if (checkHorizontalConflict(puzzle, xPos, yPos, number) || checkVerticalConflict(puzzle, xPos, yPos, number)
                || checkRegionConflict(puzzle, xPos, yPos, number)){
            return true;
        }

        return false;
    }

    /**
     * Return true if there is a conflict in the horizontal direction
     * if there is conflict return true otherwise return false
     * @param puzzle:an 2d array of int
     * @param xPos: the x position of the cell that needs to be checked
     * @param yPos: the y position of the cell that needs to be checked
     * @param number: the number needs to  be checked
     * @return
     */
    private boolean checkHorizontalConflict(final int[][] puzzle, final int xPos, final int yPos, final int number){
        for (int x = xPos -1; x >= 0; x--){
            if (number == puzzle[x][yPos]){
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if there is a conflict in the Vertical direction
     * if there is conflict return true otherwise return false
     * @param Sudoku:an 2d array of int
     * @param xPos: the x position of the cell that needs to be checked
     * @param yPos: the y position of the cell that needs to be checked
     * @param number: the number needs to  be checked
     * @return
     */
    private boolean checkVerticalConflict(final int[][] Sudoku, final int xPos, final int yPos, final int number){
        for (int y = yPos - 1; y >= 0; y--){
            if (number == Sudoku[xPos][y]){
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if there is a conflict in a small 3*3 region
     * * if there is conflict return true otherwise return false
     * @param Sudoku:an 2d array of int
     * @param xPos: the x position of the cell that needs to be checked
     * @param yPos: the y position of the cell that needs to be checked
     * @param number: the number needs to  be checked
     * @return
     */
    private boolean checkRegionConflict(final int[][] Sudoku, final int xPos, final int yPos, final int number){
        int xRegion = xPos / regionNum;
        int yRegion = yPos / regionNum;

        for (int x = xRegion * regionNum; x < xRegion * regionNum + regionNum; x++){
            for (int y = yRegion * regionNum; y < yRegion * regionNum + regionNum; y++){
                if ((x != xPos || y != yPos) && number == Sudoku[x][y]){
                    return true;
                }
            }
        }
        return false;
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




