package com.github.apycazo.codex.spring.base.j8;

import lombok.Builder;

public class J8Sampler
{

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
