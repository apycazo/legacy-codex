package com.github.apycazo.codex.spring.base.mocks;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SomeComplexBean // maybe not so complex :)
{
    AtomicInteger value = new AtomicInteger(0);

    public void reset ()
    {
        value.set(0);
    }

    public int get ()
    {
        return value.get();
    }

    public int inc ()
    {
        return value.incrementAndGet();
    }

    public boolean isOdd (int number)
    {
        return number % 2 == 0;
    }
}
