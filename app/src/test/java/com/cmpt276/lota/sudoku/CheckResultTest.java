package com.cmpt276.lota.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckResultTest {

    private  CheckResult testCheckResult;
    private  Language testWrongPuzzle[][];
    private  Language testCorrectPuzzle[][];
    private Language[][] teatPuzzleForRegion;


    //In the setup function we construct some test objects
    // create the wrong puzzle
        //testing purpose so setting all this puzzle to a wrong puzzle
        //testing purpose setting all the flag to 0 at beginning.
        //testing purpose setting all the languageOne and Two become dummy value since the all the functions
        //in checkResult is not checking for the String Language One and Two.
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
        //an simple correct testing case
        //testing purpose so setting all cells corresponding to the correct puzzle above
        //testing purpose setting all the flag to 0 at beginning.
        //testing purpose setting all the languageOne and Two become dummy value since the all the functions
        //in checkResult is not checking for the String Language One and Two.
        //Current Correct Puzzle
        //            { 1, 2, 3, 4, 5, 6, 7, 8, 9 }
        //            { 4, 5, 6, 7, 8, 9, 1, 2, 3 }
        //            { 7, 8, 9, 1, 2, 3, 4, 5, 6 }
        //            { 2, 1, 4, 3, 6, 5, 8, 9, 7 }
        //            { 3, 6, 5, 8, 9, 7, 2, 1, 4 }
        //            { 8, 9, 7, 2, 1, 4, 3, 6, 5 }
        //            { 5, 3, 1, 6, 4, 2, 9, 7, 8 }
        //            { 6, 4, 2, 9, 7, 8, 5, 3, 1 }
        //            { 9, 7, 8, 5, 3, 1, 6, 4, 2 }

    @Before
    public void setUp() throws Exception {
        testWrongPuzzle = new Language[9][9];
        testCheckResult = new CheckResult();


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testWrongPuzzle[i][j] = new Language(j + 1, "dummy", "dummy", 0);
            }

            testCorrectPuzzle = new Language[9][9];
            int[][] correctPuzzleCopy;
            correctPuzzleCopy = new int[][]{
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 5, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},
                    {2, 1, 4, 3, 6, 5, 8, 9, 7},
                    {3, 6, 5, 8, 9, 7, 2, 1, 4},
                    {8, 9, 7, 2, 1, 4, 3, 6, 5},
                    {5, 3, 1, 6, 4, 2, 9, 7, 8},
                    {6, 4, 2, 9, 7, 8, 5, 3, 1},
                    {9, 7, 8, 5, 3, 1, 6, 4, 2},
            };

            teatPuzzleForRegion = new Language[9][9];
            int puzzleForRegionTest[][] = {
                    {1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 1, 6, 7, 8, 9, 1, 2, 3},
                    {7, 8, 9, 1, 2, 3, 4, 5, 6},
                    {2, 1, 4, 3, 6, 5, 8, 9, 7},
                    {3, 6, 5, 8, 9, 7, 2, 1, 4},
                    {8, 9, 7, 2, 1, 4, 3, 6, 5},
                    {5, 3, 1, 6, 4, 2, 9, 7, 8},
                    {6, 4, 2, 9, 7, 8, 5, 3, 1},
                    {9, 7, 8, 5, 3, 1, 6, 4, 2},
            };

            for (int b = 0; b < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    testCorrectPuzzle[b][j] = new Language(correctPuzzleCopy[b][j], "dummy", "dummy", 0);

                }
            }

            for (int a = 0; a < 9; a++) {
                for (int j = 0; j < 9; j++) {
                    teatPuzzleForRegion[a][j] = new Language(puzzleForRegionTest[a][j], "dummy", "dummy", 0);
                }
            }

        }
    }


    @Test
    // construct 5 test cases to test the checkValid function
    // test for a single cell with repetition
    // test for all the cells with repetition
    // test for a single cell without repetition
    // test for all the cells without repetition
    // after change from one of the cell and make the sudoku invalid and then test it again

    public void checkValid() {


        //Case 1 Testing for one cell
        assertEquals(false, testCheckResult.checkValid(testWrongPuzzle,0,0));

        //Case 2 Testing for every cells
        for (int i = 0; i <9 ; i++)
        {
            for (int j = 0; j < 9; j++) {
                assertEquals(false, testCheckResult.checkValid(testWrongPuzzle,i,j));
            }
        }

        //Case 3 Testing for one cell with correct puzzle
        assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle,0,0));

        //Case 4 Testing for every cells
        for (int i = 0; i <9 ; i++)
        {
            for (int j = 0; j < 9; j++) {
                assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle,i,j));
            }
        }

        //Case 5
        //Randomly change the position [1][5] = 1 (before was 9)
        testCorrectPuzzle[1][5].setNumber(1);
        //should be an repetition right now so compare with false
        assertEquals(false, testCheckResult.checkValid(testCorrectPuzzle,1,5));

        //change it back to 9 for later testing.
        testCorrectPuzzle[1][5].setNumber(9);

    }

    @Test
    // test the checkResult function

    public void checkResult() {
        //Case 1 check if there is an empty cells in both WrongPuzzle and Correct puzzle
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

    @Test
    public void testCheckRegion() {
        assertEquals(false, testCheckResult.checkRegion(teatPuzzleForRegion,1,1));
    }
}
