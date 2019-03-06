package com.cmpt276.lota.sudoku;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageTest {

    private Language testLanguage;

    @Before
    public void setUp() throws Exception {
        // declare a class to test
        testLanguage = new Language(0,"dummy1","dummy2",0);
    }

    @Test
    public void getNumber()
    {
        assertEquals(0,testLanguage.getNumber());
    }

    @Test
    public void getLanguageOne()
    {
        assertEquals("dummy1",testLanguage.getLanguageOne());
    }

    @Test
    public void getLanguageTwo()
    {
        assertEquals("dummy2",testLanguage.getLanguageTwo());
    }

    @Test
    public void getFlag()
    {
        assertEquals(0,testLanguage.getFlag());
        assertNotEquals(1,testLanguage.getFlag());
    }

    @Test
    public void setNumber()
    {
        testLanguage.setNumber(1);//set another number for testing purpose
        assertEquals(1,testLanguage.getNumber());
        testLanguage.setNumber(0);//set it back
    }

    @Test
    public void setFlag()
    {
        testLanguage.setFlag(1); //set another flag number
        assertEquals(1,testLanguage.getFlag());
        testLanguage.setFlag(0);
    }
}