package com.learnjava.parallelstream;

import com.learnjava.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;

public class ArraySplitEratorExample {

    public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue,boolean isParallel){
        startTimer();
        Stream<Integer> integerStream = inputList.stream();
        if (isParallel)
            integerStream.parallel();

        List<Integer> integerList = integerStream
                .map(integer -> integer * multiplyValue)
                .collect(Collectors.toList());
        timeTaken();
        return integerList;
    }
}
