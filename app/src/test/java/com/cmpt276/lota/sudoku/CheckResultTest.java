package com.cmpt276.lota.sudoku;

import android.content.Context;

import com.cmpt276.lota.sudoku.model.CheckResult;
import com.cmpt276.lota.sudoku.model.Language;
import com.cmpt276.lota.sudoku.model.WordListLab;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckResultTest {

    private CheckResult testCheckResult;
    private Language testWrongPuzzle[][];
    private Language testCorrectPuzzle[][];
    private Language[][] teatPuzzleForRegion;
    private Language[][] teatPuzzleForRow;
    private Language[][] teatPuzzleForColumn;
    private int puzzleSize;
    private Context testContex;
    private WordListLab testWordListLab = WordListLab.get(testContex);
    private Language[][] teatPuzzleForDifferentSize;

    @Before
    public void setUp() throws Exception // have question in here
    {
        puzzleSize = 9;
        testWrongPuzzle = new Language[puzzleSize][puzzleSize];
        testCheckResult = new CheckResult();

        //construct a wrong puzzle for testing purpose

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                testWrongPuzzle[i][j] = new Language(j + 1, "dummy", "dummy", 0);
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
        testCorrectPuzzle = new Language[puzzleSize][puzzleSize];
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
        };//an simple correct testing case

        teatPuzzleForRegion = new Language[puzzleSize][puzzleSize];
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

        teatPuzzleForRow = new Language[puzzleSize][puzzleSize];
        int puzzleForRowTest[][] = {
                {1, 1, 3, 4, 5, 6, 7, 8, 9},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {2, 1, 4, 3, 6, 5, 8, 9, 7},
                {3, 6, 5, 8, 9, 7, 2, 1, 4},
                {8, 9, 7, 2, 1, 4, 3, 6, 5},
                {5, 3, 1, 6, 4, 2, 9, 7, 8},
                {6, 4, 2, 9, 7, 8, 5, 3, 1},
                {9, 7, 8, 5, 3, 1, 6, 4, 2},
        };

        teatPuzzleForColumn = new Language[puzzleSize][puzzleSize];
        int puzzleForColTest[][] = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {1, 5, 6, 7, 8, 9, 1, 2, 3},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {2, 1, 4, 3, 6, 5, 8, 9, 7},
                {3, 6, 5, 8, 9, 7, 2, 1, 4},
                {8, 9, 7, 2, 1, 4, 3, 6, 5},
                {5, 3, 1, 6, 4, 2, 9, 7, 8},
                {6, 4, 2, 9, 7, 8, 5, 3, 1},
                {9, 7, 8, 5, 3, 1, 6, 4, 2},
        };

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                testCorrectPuzzle[i][j] = new Language(correctPuzzleCopy[i][j], "dummy", "dummy", 0);
                //testing purpose so setting all cells corresponding to the correct puzzle above
                //testing purpose setting all the flag to 0 at beginning.
                //testing purpose setting all the languageOne and Two become dummy value since the all the functions
                //in checkResult is not checking for the String Language One and Two.
            }
        }

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                teatPuzzleForRegion[i][j] = new Language(puzzleForRegionTest[i][j], "dummy", "dummy", 0);
            }
        }

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                teatPuzzleForRow[i][j] = new Language(puzzleForRowTest[i][j], "dummy", "dummy", 0);
            }
        }

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                teatPuzzleForColumn[i][j] = new Language(puzzleForColTest[i][j], "dummy", "dummy", 0);
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

    @After
    public void clean(){
        testWordListLab.setNull();
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
        assertEquals(false, testCheckResult.checkValid(testWrongPuzzle, 0, 0));
        //Since we are testing the wrong puzzle and then it supposed to be return false;

        //Case 2 Testing for every cells
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                assertEquals(false, testCheckResult.checkValid(testWrongPuzzle, i, j));
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
        assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle, 0, 0));

        //Case 4 Testing for every cells
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle, i, j));
            }
        }
        //Since this time the puzzle is correct, so there should not be any repetition found,
        // so the check function should return true for all the cells

        //Case 5
        //right now change one cells from the correct puzzle, so that let the correct puzzle becomes a wrong puzzle.
        //Randomly change the position [1][5] = 1 (before was 9)
        testCorrectPuzzle[1][5].setNumber(1);
        //should be an repetition right now so compare with false
        assertEquals(false, testCheckResult.checkValid(testCorrectPuzzle, 1, 5));

        //change it back to 9 for later testing.
        testCorrectPuzzle[1][5].setNumber(9);

        //case6- row conflict
        assertEquals(false, testCheckResult.checkRow(teatPuzzleForRow, 0, 1));

        //case7- column conflict
        assertEquals(false, testCheckResult.checkColumn(teatPuzzleForColumn, 1, 0));
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
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                assertEquals(true, testCheckResult.checkValid(testCorrectPuzzle, i, j));
            }
        }

    }

    @Test
    public void testCheckRegion() {
        assertEquals(false, testCheckResult.checkRegion(teatPuzzleForRegion, 1, 1));
    }

    @Test
    public void testDifferentSize() {
        puzzleSize = 4;
        testWordListLab.setPuzzleSize(puzzleSize);
        testCheckResult = new CheckResult();

        teatPuzzleForDifferentSize = new Language[puzzleSize][puzzleSize];
        int puzzleForDifferentSizeTest4[][] = {
                {1, 2, 3, 4},
                {4, 3, 2, 1},
                {3, 4, 1, 2},
                {2, 1, 4, 3},
        };

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                teatPuzzleForDifferentSize[i][j] = new Language(puzzleForDifferentSizeTest4[i][j], "dummy", "dummy", 0);
            }
        }

        assertEquals(true, testCheckResult.checkValid(teatPuzzleForDifferentSize, 1, 1));

        puzzleSize = 6;
        testWordListLab.setPuzzleSize(puzzleSize);
        testCheckResult = new CheckResult();

        teatPuzzleForDifferentSize = new Language[puzzleSize][puzzleSize];
        int puzzleForDifferentSizeTest6[][] = {
                {1, 2, 3, 4, 5, 6},
                {5, 6, 4, 3, 2, 1},
                {3, 4, 5, 6, 1, 2},
                {2, 1, 4, 3, 1, 1},
                {2, 1, 4, 3, 1, 1},
                {2, 1, 4, 3, 1, 1},
        };

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                teatPuzzleForDifferentSize[i][j] = new Language(puzzleForDifferentSizeTest6[i][j], "dummy", "dummy", 0);
            }
        }

        assertEquals(true, testCheckResult.checkRegion(teatPuzzleForDifferentSize, 0, 1));

        puzzleSize = 12;
        testWordListLab.setPuzzleSize(puzzleSize);
        testCheckResult = new CheckResult();

        teatPuzzleForDifferentSize = new Language[puzzleSize][puzzleSize];
        int puzzleForDifferentSizeTest12[][] = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12},
                {4, 5, 6, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {7, 8, 9, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {10, 11, 12, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
                {1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4},
        };

        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                teatPuzzleForDifferentSize[i][j] = new Language(puzzleForDifferentSizeTest12[i][j], "dummy", "dummy", 0);
            }
        }

        assertEquals(true, testCheckResult.checkRegion(teatPuzzleForDifferentSize, 0, 1));
    }
}
