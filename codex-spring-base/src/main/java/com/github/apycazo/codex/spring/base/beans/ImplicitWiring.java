package com.github.apycazo.codex.spring.base.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ImplicitWiring
{
    private ValueGenerator valueGenerator;
    private PrototypeBean prototypeBean;

    public ImplicitWiring(ValueGenerator valueGenerator, PrototypeBean prototypeBean)
    {
        this.valueGenerator = valueGenerator;
        this.prototypeBean = prototypeBean;
    }

    public String logNextValue ()
    {
        String msg = "Next value is:" + valueGenerator.nextAtomicInt();
        log.info(msg);
        return msg;
    }

    public ValueGenerator getIntervalGenerator ()
    {
        return this.valueGenerator;
    }

    public PrototypeBean getPrototype ()
    {
        return this.prototypeBean;
    }

    @PostConstruct
    protected void logSomeValue ()
    {
        logNextValue();
        this.valueGenerator.setAtomicIntValue(0);
    }
}
