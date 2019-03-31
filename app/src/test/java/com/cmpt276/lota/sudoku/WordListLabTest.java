package com.cmpt276.lota.sudoku;

import android.content.Context;

import com.cmpt276.lota.sudoku.model.ListsOfWords;
import com.cmpt276.lota.sudoku.model.WordListLab;
import com.cmpt276.lota.sudoku.model.Words;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class WordListLabTest {

    private ListsOfWords testListOfWords1;
    //private ListsOfWords testListOfWords2;
    private List<Words> testList = new ArrayList<>();//Creating arrayList

    private Words test1;
    private Words test2;

    private Context testContex;
    private WordListLab testWordListLab = WordListLab.get(testContex);

    String[][] arr = new String[1][3];

    @Before
    public void setUp() throws Exception
    {
        test1 = new Words("dummy1", "dummy3");
        test2 = new Words("dummy2", "dummy4");
        testList.add(test1);
        testList.add(test2);
        testListOfWords1 = new ListsOfWords(testList, "testname1");

        for (int i = 0; i < 3; i++)
        {
            arr[0][i] = "testString";
        }
    }

    @After
    public void clean() throws Exception
    {
        testWordListLab.setNull();
    }

    @Test
    //since we added the word defined by ourself at the end
    //so we can directly match those words that we just added
    public void addListsOfWords() {
        testWordListLab.addListsOfWords(testListOfWords1);

        assertEquals(testListOfWords1,testWordListLab.getListsOfWord().get(testWordListLab.getListsOfWord().size() - 1));
    }

    @Test
    public void setId()
    {
        UUID testID = UUID.randomUUID();
        testWordListLab.setId(testID);
        assertEquals(testID, testWordListLab.getId());
        assertEquals(1, testWordListLab.getHasSetId());

    }

    @Test
    public void getNotFamiliarWord()
    {
        //since in the setup function we set the NotFamiliarWord with our
        //pre-defined array
        testWordListLab.setUnfamiliarWord(arr);
        //right now we just directly compare with this two array
        Assert.assertArrayEquals(arr, testWordListLab.getUnfamiliarWord());
    }


//    @Test
//    public void getHasSetId()
//    {
//        //since in the initialization the default value is -1
//        //so the getHasSetID should return -1;
//        assertEquals(-1, testWordListLab.getHasSetId());
//    }

//    @Test
//    public void getHasSetFamiliar()
//    {
//        //since in the initialization the default value is -1
//        //so the getHasSetFamiliar should return -1;
//        assertEquals(-1, testWordListLab.getHasSetFamiliar());
//    }

    @Test
    public void setPuzzleSize()
    {
        testWordListLab.setPuzzleSize(6);
        assertEquals(6, testWordListLab.getPuzzleSize());
    }

    @Test
    public void getListsOfWords()
    {
        testWordListLab.addListsOfWords(testListOfWords1);
        assertEquals(testListOfWords1, testWordListLab.getListsOfWords(testListOfWords1.getId()));
        UUID testID = UUID.randomUUID();
        assertEquals(null, testWordListLab.getListsOfWords(testID));
    }

}