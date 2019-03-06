package com.cmpt276.lota.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleGeneratorTest {

    private PuzzleGenerator testPuzzleGenerator;  //declare  a class member to test
    private  CheckResult testCheckResult;
    private  Language testPuzzle[][];

    @Before
    public void setUp() throws Exception {
        testPuzzleGenerator = new PuzzleGenerator();
        testCheckResult = new CheckResult();
    }


    @Test
    public void generateGrid()
    {
        // since we already test the correctness of the class checkResult
        // so we can directly use that class to get test the correctness of our Generator
        // we firstly using the generateGrid to generate a puzzle and then let the CheckResult to test
        //testPuzzle = testPuzzleGenerator.generateGrid();

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9 ; j++)
            {

                System.out.print(" " + testPuzzle[i][j].getNumber() + " ");
            }
            System.out.println("\n");
        }




        //assertEquals(true, testCheckResult.checkResult(testPuzzle));

        //since this puzzle generator return correct result so it should pass the test

    }

    @Test
    public void getConflict() {

    }

    @Test
    public void getLanOne() {
    }

    @Test
    public void getLanTwo() {
    }


}