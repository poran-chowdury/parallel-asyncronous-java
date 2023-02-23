package com.learnjava.parallelstream;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinedListExampleTest {
    private LinedListExample listExample;
    @BeforeEach
    void setUp() {
        this.listExample= new LinedListExample();
    }

    @RepeatedTest(5)
    void multiplyEachValueArraySpliteratorExample_Sequential() {
        int size= 1000000; //1M
        LinkedList<Integer> inputList= DataSet.generateIntegerLinkedList(size);
        List<Integer> result = listExample.multiplyEachValue(inputList, 2,false);
        assertEquals(size,result.size());
    }
    @RepeatedTest(5)
    void multiplyEachValueArraySpliteratorExample_Parallel() {
        int size= 1000000; //1M
        LinkedList<Integer> inputList= DataSet.generateIntegerLinkedList(size);
        List<Integer> result = listExample.multiplyEachValue(inputList, 2,true);
        assertEquals(size,result.size());
    }
}