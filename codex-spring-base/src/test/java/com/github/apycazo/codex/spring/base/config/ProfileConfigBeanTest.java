package com.github.apycazo.codex.spring.base.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(ProfileConfigBeanTest.PROFILE_NAME)
public class ProfileConfigBeanTest
{
    public static final String PROFILE_NAME = "profile-demo";

    @Autowired
    private ProfileConfigBean profileConfigBean;

    @Test
    public void componentIsConfigured ()
    {
        assertNotNull("Config bean not created", profileConfigBean);
        assertEquals(ProfileConfigBean.DEFAULT, profileConfigBean.getDefaultValue());
        assertEquals(ProfileConfigBeanTest.PROFILE_NAME, profileConfigBean.getFirstProfileValue());
    }
}
