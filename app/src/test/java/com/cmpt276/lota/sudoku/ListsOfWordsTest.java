package com.cmpt276.lota.sudoku;

import com.cmpt276.lota.sudoku.model.ListsOfWords;
import com.cmpt276.lota.sudoku.model.Words;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ListsOfWordsTest {
    private ListsOfWords testListOfWords1;
    private ListsOfWords testListOfWords2;
    private Words test1;
    private Words test2;
    private List<Words> testList = new ArrayList<>();//Creating arrayList
    //private List<Words> mWordLists= new ArrayList<>();


    @Before
    public void setUp() throws Exception {

        test1 = new Words("dummy1", "dummy3");
        test2 = new Words("dummy2", "dummy4");
        testList.add(test1);
        testList.add(test2);
        testListOfWords1 = new ListsOfWords(testList, "testname1");

        String[] str1 = new String[]{"testdummy1", "testdummy3"};
        String[] str2 = new String[]{"testdummy2", "testdummy4"};
        testListOfWords2 = new ListsOfWords(str1, str2, "testname2");
//
//        for (int i = 0; i < str1.length; i++) {
//            Words words = new Words(str1[i],str2[i]);
//            mWordLists.add(words);
//        }

    }

    @Test
    public void getId() {
        UUID ID = testListOfWords1.getId();
        assertEquals(ID, testListOfWords1.getId());

        UUID ID2 = testListOfWords2.getId();
        assertEquals(ID2, testListOfWords2.getId());

        assertNotEquals(null,testListOfWords2.getId());

        //not a good way since UUID is generated by the library function
    }

    @Test
    public void getWordLists() {
        //directly compare with the arrayList that we created;
        assertEquals(testList, testListOfWords1.getWordLists());
        assertEquals("testdummy1", testListOfWords2.getWordLists().get(0).getLanguageOne());
        assertNotEquals(null,testListOfWords2.getWordLists().get(0).getLanguageOne());
        //should be no problem
    }

    @Test
    public void getWordListsName() {
        assertEquals("testname1", testListOfWords1.getWordListsName());
        assertEquals("testname2", testListOfWords2.getWordListsName());
        assertNotEquals(null,testListOfWords1.getWordListsName());

    }
}

