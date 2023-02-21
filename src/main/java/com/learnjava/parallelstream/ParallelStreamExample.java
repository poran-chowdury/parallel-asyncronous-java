package com.learnjava.parallelstream;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamExample {

    public List<String> stringTransFormation(List<String> nameList) {
        return nameList.parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
        ParallelStreamExample streamExample = new ParallelStreamExample();
        startTimer();
        List<String> resultList = streamExample.stringTransFormation(namesList);
        log("result is : "+resultList);
        timeTaken();
    }

    private  String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }
}
