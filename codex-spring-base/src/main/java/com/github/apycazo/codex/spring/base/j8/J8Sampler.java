package com.github.apycazo.codex.spring.base.j8;

import lombok.Builder;

import java.util.function.Supplier;

public class J8Sampler
{
    // Needs to be static to be able to use with '::' operator.
    public static Integer inc (Integer value)
    {
        return value+1;
    }

    interface CountCharacters
    {
        Integer processValue(String text);
    }

    public static class Counter implements CountCharacters
    {
        @Override
        public Integer processValue(String text)
        {
            return text.length();
        }

        public String getValueAsString (Supplier<Integer> supplyValue)
        {
            return "value:" + supplyValue.get();
        }
    }

    public static class CounterWithoutSpaces implements CountCharacters
    {
        @Override
        public Integer processValue(String text)
        {
            return text.replace(" ", "").length();
        }
    }

    @Builder
    public static class NumberHolder
    {
        private Integer value;

        public NumberHolder(int value)
        {
            this.value = value;
        }

        public Integer getValue()
        {
            return value;
        }
    }
}
