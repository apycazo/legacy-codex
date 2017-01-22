package com.github.apycazo.codex.spring.base.beans;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeBean
{
    // Silly bean only says one thing
    public int get5 ()
    {
        return 5;
    }
}
