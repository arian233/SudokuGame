package com.cmpt276.lota.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleGeneratorTest {

    private PuzzleGenerator testPuzzleGenerator;  //declare  a class member to test
    private CheckResult testCheckResult;
    private Language testPuzzle[][];
    private int testConflictPuzzle[][];


    @Before
    public void setUp() throws Exception {

        testPuzzleGenerator = new PuzzleGenerator();
        testCheckResult = new CheckResult();

        //initialize a correct puzzle to test the checkConflict
        testConflictPuzzle = new int[][]{
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
    }

        @Test
        public void generateGrid ()
        {
            // since we already test the correctness of the class checkResult
            // so we can directly use the checkResult class to get test the correctness of our Generator
            // we firstly using the generateGrid to generate a puzzle and then let the CheckResult to test
            testPuzzle = testPuzzleGenerator.generateGrid();


            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    assertEquals(true, testCheckResult.checkValid(testPuzzle, j, i));
                    //System.out.print(" " + testPuzzle[i][j].getNumber() + " ");
                }
                System.out.println("\n");
            }
            //after the generator generates a puzzle, then we just use the checkResult function to check
            //assertEquals(true, testCheckResult.checkResult(testPuzzle));

            //since this puzzle generator return correct result so it should pass the test

        }

        @Test
        public void getConflict ()
        {
            // getConflict's input is an int array
            // so we can create a complete int array to check if the check conflict work
            // Examples that I create in the setup function
//        { 1, 2, 3, 4, 5, 6, 7, 8, 9 }
//        { 4, 5, 6, 7, 8, 9, 1, 2, 3 }
//        { 7, 8, 9, 1, 2, 3, 4, 5, 6 }
//        { 2, 1, 4, 3, 6, 5, 8, 9, 7 }
//        { 3, 6, 5, 8, 9, 7, 2, 1, 4 }
//        { 8, 9, 7, 2, 1, 4, 3, 6, 5 }
//        { 5, 3, 1, 6, 4, 2, 9, 7, 8 }
//        { 6, 4, 2, 9, 7, 8, 5, 3, 1 }
//        { 9, 7, 8, 5, 3, 1, 6, 4, 2 }

//         For testing purpose
//            for (int i = 0; i < 9; i++) //casted to a int 2-d array
//            {
//                for (int j = 0; j < 9; j++) {
//                    testConflictPuzzle[i][j] = testConflictPuzzle [i][j].;
//                    //System.out.print(" " + testConflictPuzzle[i][j] + " ");
//                }
//                System.out.println("\n");



            assertEquals(false, testPuzzleGenerator.getConflict(testConflictPuzzle));
            //since the puzzle that we provided is correct, so there is no conflict so it
            //return false which expected


            //right now we randomly change a cell then this puzzle will become a invalid puzzle
            int temp = testConflictPuzzle[2][2];
            testConflictPuzzle[2][2] = testConflictPuzzle[2][3];
            //gonna crate a conflict

            assertEquals(true, testPuzzleGenerator.getConflict(testConflictPuzzle));
            //expected return true which represents a conflict

            //change it back
            testConflictPuzzle[2][2] = temp;
            assertEquals(false, testPuzzleGenerator.getConflict(testConflictPuzzle));
            //should not return any problem


        }
//
//    @Test
//    public void getLanOne()
//    {
//        String test1String[];
//        String test2String[];
        //since each time the Lan one gonna return different output and it does not provided a getter
        //we can not test it
        //Use two string to compare teach time if it is the same

//        test1String = testPuzzleGenerator.getLanOne();
//        test2String = testPuzzleGenerator.getLanOne();
//        for (int j = 0; j < 9 ; j++)
//        {
//
//                System.out.print(" " + test1String[j] + " ");
//        }
//
//        System.out.print("\n");
//
//
//        for (int j = 0; j < 9 ; j++)
//        {
//
//            System.out.print(" " + test2String[j] + " ");
//
//        }

        //two output is the same

        //test in this way
        //assertEquals(test1String,testPuzzleGenerator.getLanOne());
        //assertEquals(true,test1String.equals(testPuzzleGenerator.getLanOne()));

//    }


    }