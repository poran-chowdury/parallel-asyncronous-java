package com.learnjava.parallelstream;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamExampleTest {

    ParallelStreamExample parallelStreamExample = new ParallelStreamExample();

    @Test
    void stringTransFormation() {
        List<String> namesList = DataSet.namesList();
        startTimer();
        List<String> transFormation = parallelStreamExample.stringTransFormation(namesList);
        timeTaken();
        assertEquals(4, transFormation.size());
        transFormation.forEach(name -> assertTrue(name.contains("-")));
    }
}