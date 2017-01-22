package com.github.apycazo.codex.spring.base.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ImplicitWiringTest
{
    @Autowired
    private ValueGenerator valueGenerator;
    @Autowired
    private ImplicitWiring implicitWiring;
    @Autowired
    private PrototypeBean prototypeBean;

    @Test
    public void assertThatAValueGeneratorExistsAndHasBeenWired ()
    {
        assertNotNull("ValueGenerator bean not created", valueGenerator);
        assertNotNull("ValueGenerator bean not wired", implicitWiring.getIntervalGenerator());
        assertEquals(valueGenerator, implicitWiring.getIntervalGenerator());
        assertNotNull(prototypeBean);
        assertNotNull(implicitWiring.getPrototype());
        assertNotEquals("Prototypes should be different", prototypeBean, implicitWiring.getPrototype());
    }
}