package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {
    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }
    public CompletableFuture<String> helloWorldThenCompose() {
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose((previous)-> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }

    public String helloWorldMultipleAsync(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        String hellowrldString = hello.thenCombine(world, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();
        return hellowrldString;
    }
    public String helloWorldMultipleAsyncWithLog(){
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world());
        String hellowrldString = hello
                .thenCombine(world, (h, w) -> {
                    log("Then combine h/w");
                   return h + w;
                })
                .thenApply(s -> {
                    log("Then apply ");
                    return  s.toUpperCase();
                })
                .join();
        timeTaken();
        return hellowrldString;
    }
    public String helloWorldMultipleAsyncWithLog_Async(){
        startTimer();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.hello(),executorService);
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.world(),executorService);
        String hellowrldString = hello
                .thenCombineAsync(world, (h, w) -> {
                    log("Then combine h/w");
                    return h + w;
                },executorService)
                .thenApplyAsync(s -> {
                    log("Then apply ");
                    return  s.toUpperCase();
                },executorService)
                .join();
        timeTaken();
        return hellowrldString;
    }

    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenAccept(LoggerUtil::log)
                .join();
        log("Done!");
//        delay(2000);
    }
}
