package com.github.apycazo.codex.spring.base.lazy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LazyConfig.class)
public class LazyComponentTest
{
    @Autowired
    private LazyObserver lazyObserver;
    @Autowired
    private Type1 type1;

    /**
     * Even though two @Component has been created (type1 and type2), only one has been instanced, since
     * there is no one requiring (wiring) the other, lazy component.
     */
    @Test
    public void onlyOneIsCreated ()
    {
        assertEquals(1, lazyObserver.getCount());
    }
}