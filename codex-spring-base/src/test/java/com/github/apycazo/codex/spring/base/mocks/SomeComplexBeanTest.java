package com.github.apycazo.codex.spring.base.mocks;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SomeComplexBeanTest
{
    @Mock
    private SomeComplexBean bean;

    @Test
    public void mockedBeanBehaviour ()
    {
        when(bean.get()).thenReturn(10);
        // this is not really the bean behavior, to prove it is being mocked
        when(bean.inc()).thenReturn(1).thenReturn(3).thenReturn(5);

        // no matter how many time I call 'get' I will only get 10 all the time
        IntStream.rangeClosed(1,10).forEach(__ -> assertEquals(10, bean.get()));

        // and the first three times I call 'inc' i will get values 1,3,5
        assertEquals(1, bean.inc());
        assertEquals(3, bean.inc());
        assertEquals(5, bean.inc());

        // and if I try again, I will get the same value (the last one)
        assertEquals(5, bean.inc());
        assertEquals(5, bean.inc());
        assertEquals(5, bean.inc());

        // I can also provide a 'any' matcher for parameters
        when(bean.isOdd(anyInt())).thenReturn(true);
        assertTrue(bean.isOdd(0));
        assertTrue(bean.isOdd(1));
        assertTrue(bean.isOdd(2));
    }
}