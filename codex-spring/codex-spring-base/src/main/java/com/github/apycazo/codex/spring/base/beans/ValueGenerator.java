package com.github.apycazo.codex.spring.base.beans;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Some simple number provider, to be used in the bean constructor implicit autowire example.
 */
@Component
public class ValueGenerator
{
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public int randomInt (int min, int max)
    {
        return new Random().ints(min, (max+1)).findFirst().getAsInt();
    }

    public int nextAtomicInt ()
    {
        return atomicInteger.getAndIncrement();
    }

    public void setAtomicIntValue (int newValue)
    {
        atomicInteger.set(newValue);
    }
}
