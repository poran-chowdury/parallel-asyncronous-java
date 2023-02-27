package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorldException {
    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorldMultipleAsyncHandleException() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        String hellowrldString = hello
                .handle((res, e) -> {
                    if (e != null) {
                        log("Exception is : " + e.getMessage());
                        return "";
                    }
                    return res;
                })
                .thenCombine(world, (h, w) -> h + w)
                .handle((res, e) -> {
                    if (e != null) {
                        log("Exception in world  : " + e);
                        return "";
                    }
                    return res;

                })
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hellowrldString;
    }
}
