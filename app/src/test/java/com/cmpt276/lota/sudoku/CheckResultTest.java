package com.cmpt276.lota.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckResultTest {

    private  CheckResult testCheckResult;
    private  Language testWrongPuzzle[][];
    private  Language testCorrectPuzzle[][];



    @Before
    public void setUp() throws Exception // have question in here
    {
        testWrongPuzzle = new Language[9][9];
        testCheckResult =new CheckResult();

        //construct a wrong puzzle for testing purpose

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9 ; j++)
            {
                testWrongPuzzle[i][j] = new Language(j+1,"dummy","dummy",0);
                //testing purpose so setting all this puzzle to a wrong puzzle
                //testing purpose setting all the flag to 0 at beginning.
                //testing purpose setting all the languageOne and Two become dummy value since the all the functions
                //in checkResult is not checking for the String Language One and Two.
            }
        }
        //current Wrong Puzzle
//    {
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//        1,2,3,4,5,6,7,8,9
//    }


        //construct a correct puzzle for testing purpose
        testCorrectPuzzle = new Language[9][9];
        int[][] correctPuzzleCopy;
        correctPuzzleCopy = new int[][]{
                { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
                { 4, 5, 6, 7, 8, 9, 1, 2, 3 },
                { 7, 8, 9, 1, 2, 3, 4, 5, 6 },
                { 2, 1, 4, 3, 6, 5, 8, 9, 7 },
                { 3, 6, 5, 8, 9, 7, 2, 1, 4 },
                { 8, 9, 7, 2, 1, 4, 3, 6, 5 },
                { 5, 3, 1, 6, 4, 2, 9, 7, 8 },
                { 6, 4, 2, 9, 7, 8, 5, 3, 1 },
                { 9, 7, 8, 5, 3, 1, 6, 4, 2 },
        };//an simple correct testing case

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9 ; j++)
            {
                testCorrectPuzzle[i][j] = new Language(correctPuzzleCopy[i][j],"dummy","dummy",0);
                //testing purpose so setting all cells corresponding to the correct puzzle above
                //testing purpose setting all the flag to 0 at beginning.
                //testing purpose setting all the languageOne and Two become dummy value since the all the functions
                //in checkResult is not checking for the String Language One and Two.
            }
        }

        //Current Correct Puzzle
//        {
//            { 1, 2, 3, 4, 5, 6, 7, 8, 9 }
//            { 4, 5, 6, 7, 8, 9, 1, 2, 3 }
//            { 7, 8, 9, 1, 2, 3, 4, 5, 6 }
//            { 2, 1, 4, 3, 6, 5, 8, 9, 7 }
//            { 3, 6, 5, 8, 9, 7, 2, 1, 4 }
//            { 8, 9, 7, 2, 1, 4, 3, 6, 5 }
//            { 5, 3, 1, 6, 4, 2, 9, 7, 8 }
//            { 6, 4, 2, 9, 7, 8, 5, 3, 1 }
//            { 9, 7, 8, 5, 3, 1, 6, 4, 2 }
//
//        }


    }


    @Test
    public void checkValid() {
//        for (int i = 0; i < 9; i++)
//        {
//            for (int j = 0; j < 9 ; j++)
//            {
//
//                System.out.print(" " + testWrongPuzzle[i][j].getNumber() + " ");
//            }
//            System.out.println("\n");
//        }

        //Case 1 Testing for one cell
        assertEquals(false, testCheckResult.checkValid(testWrongPuzzle,0,0));
        //Since we are testing the wrong puzzle and then it supposed to be return false;

        //Case 2 Testing for every cells
        for (int i = 0; i <9 ; i++)
        {
            for (int j = 0; j < 9; j++) {
                assertEquals(false, testCheckResult.checkValid(testWrongPuzzle,i,j));
            }
        }
        //Since all the cells are found repetition, so it supposed to return false for every cell


//        for (int i = 0; i < 9; i++)
//        {
//            for (int j = 0; j < 9 ; j++)
//            {
//
//                System.out.print(" " +testCorrectPuzzle[i][j].getNumber() + " ");
//            }
//            System.out.println("\n");
//        }

        //Case 3 Testing for one cell with correct puzzle
        assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle,0,0));

        //Case 4 Testing for every cells
        for (int i = 0; i <9 ; i++)
        {
            for (int j = 0; j < 9; j++) {
                assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle,i,j));
            }
        }
        //Since this time the puzzle is correct, so there should not be any repetition found,
        // so the check function should return true for all the cells


        //Case 5
        //right now change one cells from the correct puzzle, so that let the correct puzzle becomes a wrong puzzle.
        //Randomly change the position [1][5] = 1 (before was 9)
        testCorrectPuzzle[1][5].setNumber(1);
        //should be an repetition right now so compare with false
        assertEquals(false, testCheckResult.checkValid(testCorrectPuzzle,1,5));

        //change it back to 9 for later testing.
        testCorrectPuzzle[1][5].setNumber(9);

        //testing finish
    }

    @Test
    public void checkResult() {
        //Case 1 check if there is an empty cells in both Wrongpuzzle and Correct puzzle
        //There should be no-empty cells in those above puzzles
        assertEquals(true, testCheckResult.checkResult(testCorrectPuzzle));
        assertEquals(true, testCheckResult.checkResult(testWrongPuzzle));

        //Case 2
        //Now randomly make one of the cell become 0, test again

        int temp = testCorrectPuzzle[5][8].getNumber();

        testCorrectPuzzle[5][8].setNumber(0);

        assertEquals(false, testCheckResult.checkResult(testCorrectPuzzle));

        //set the number back
        testCorrectPuzzle[5][8].setNumber(temp);


//test again to see the puzzle if it is correct after all the testing
        for (int i = 0; i <9 ; i++)
        {
            for (int j = 0; j < 9; j++) {
                assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle,i,j));
            }
        }

    }
}
