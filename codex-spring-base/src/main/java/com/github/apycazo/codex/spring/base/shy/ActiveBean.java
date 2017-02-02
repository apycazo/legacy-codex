package com.github.apycazo.codex.spring.base.shy;

import org.springframework.stereotype.Component;

@Component
public class ActiveBean implements Named
{
    @Override
    public String getName()
    {
        return getClass().getSimpleName();
    }
}
