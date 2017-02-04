package com.github.apycazo.codex.spring.base.lazy;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class Type2 implements IDHolder
{
    public Type2 (LazyObserver lazyObserver)
    {
        lazyObserver.inc();
    }

    @Override
    public String id()
    {
        return getClass().getSimpleName();
    }
}
