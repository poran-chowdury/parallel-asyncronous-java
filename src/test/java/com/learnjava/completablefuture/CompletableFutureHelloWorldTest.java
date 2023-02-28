package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {
    private HelloWorldService helloWorldService;
    CompletableFutureHelloWorld cfhw;

    @BeforeEach
    void setUp() {
        this.helloWorldService = new HelloWorldService();
        this.cfhw = new CompletableFutureHelloWorld(helloWorldService);
    }

    @Test
    void helloWorld() {
        // given


        // when
        CompletableFuture<String> stringCompletableFuture = cfhw.helloWorld();

        // then
        stringCompletableFuture.thenAccept(s -> {
            assertEquals("HELLO WORLD", s);
        }).join();
    }

    @Test
    void helloWorldMultipleAsync() {
        // given

        // when
        String helloWorld = cfhw.helloWorldMultipleAsync();

        // then
        assertEquals("HELLO WORLD!", helloWorld);
    }


    @Test
    void helloWorldThenCompose() {
        // given

        startTimer();
        // when
        CompletableFuture<String> stringCompletableFuture = cfhw.helloWorldThenCompose();

        // then
        stringCompletableFuture.thenAccept(s -> {
            assertEquals("HELLO WORLD!", s);
        }).join();
        timeTaken();
    }


    @Test
    void helloWorldMultipleAsyncWithLog() {
        // given
        // when
        String result = cfhw.helloWorldMultipleAsyncWithLog();

        // then
        assertEquals("HELLO WORLD!", result);


    }

    @Test
    void helloWorldMultipleAsyncWithLog_Async() {
        // given
        // when
        String result = cfhw.helloWorldMultipleAsyncWithLog_Async();

        // then
        assertEquals("HELLO WORLD!", result);
    }
}