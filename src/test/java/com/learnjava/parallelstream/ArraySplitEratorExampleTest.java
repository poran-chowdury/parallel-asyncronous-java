package com.learnjava.parallelstream;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArraySplitEratorExampleTest {

     private ArraySplitEratorExample splitEratorExample;

    @BeforeEach
    void setUp(){
        this.splitEratorExample= new ArraySplitEratorExample();
    }
    @RepeatedTest(5)
    void multiplyEachValueArraySpliteratorExample_Sequential() {
        int size= 1000000; //1M
        ArrayList<Integer> inputList= DataSet.generateArrayList(size);
        List<Integer> result = splitEratorExample.multiplyEachValue(inputList, 2,false);
        assertEquals(size,result.size());
    }
    @RepeatedTest(5)
    void multiplyEachValueArraySpliteratorExample_Parallel() {
        int size= 1000000; //1M
        ArrayList<Integer> inputList= DataSet.generateArrayList(size);
        List<Integer> result = splitEratorExample.multiplyEachValue(inputList, 2,true);
        assertEquals(size,result.size());
    }
}