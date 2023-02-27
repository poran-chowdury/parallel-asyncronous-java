package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {
    @Mock
    private HelloWorldService helloWorldService = mock(HelloWorldService.class);

    @InjectMocks
    private CompletableFutureHelloWorldException hwcfe;
    @BeforeEach
    void setUp() {
    }

    @Test
    void helloWorldMultipleAsyncHandleException() {
        // given
          when(helloWorldService.hello())
                  .thenThrow(new RuntimeException("Exception occurred !"));
          when(helloWorldService.world()).thenCallRealMethod();
        // when
        String result = hwcfe.helloWorldMultipleAsyncHandleException();

        // then
        assertEquals(" WORLD!",result);

    }
    @Test
    void helloWorldMultipleAsyncHandleException_2() {
        // given
        when(helloWorldService.hello())
                .thenThrow(new RuntimeException("Exception occurred !"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occurred !"));;
        // when
        String result = hwcfe.helloWorldMultipleAsyncHandleException();

        // then
        assertEquals("",result);

    }
    @Test
    void helloWorldMultipleAsyncHandleException_3() {
        // given
        when(helloWorldService.hello())
                .thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();
        // when
        String result = hwcfe.helloWorldMultipleAsyncHandleException();

        // then
        assertEquals("HELLO WORLD!",result);

    }
}