package com.github.apycazo.codex.spring.base.shy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(Named.class)
public class PassiveBean implements Named
{
    @Override
    public String getName()
    {
        return getClass().getSimpleName();
    }
}
