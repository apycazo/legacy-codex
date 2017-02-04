package com.github.apycazo.codex.spring.base.shy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShyBeanConfig.class)
public class ShyComponentsTest
{
    @Autowired(required = false)
    private Named [] namedBeans;

    @Test
    public void testShyBeanNotCreated ()
    {
        assertNotNull(namedBeans);
        assertEquals(1, namedBeans.length);
        assertEquals("ActiveBean", namedBeans[0].getName());
    }
}
