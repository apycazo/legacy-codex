package com.github.apycazo.codex.spring.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest
@ActiveProfiles("config-reference")
@RunWith(SpringRunner.class)
public class SampleConfigBeanTest
{
    private JacksonTester<SampleConfigBean> json;

    @Autowired
    private SampleConfigBean config;

    @Before
    public void setup()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void checkConfiguredValues() throws IOException
    {
        JsonContent<SampleConfigBean> jsonContent = json.write(config);

        jsonContent.assertThat()
                .hasJsonPathValue("string")
                .extractingJsonPathStringValue("string").isEqualTo("value on config");
        jsonContent.assertThat()
                .hasJsonPathValue("inner.number")
                .extractingJsonPathNumberValue("inner.number").isEqualTo(10);
        jsonContent.assertThat()
                .hasJsonPathValue("inner.valid")
                .extractingJsonPathBooleanValue("inner.valid").isEqualTo(true);
        jsonContent.assertThat()
                .hasJsonPathValue("list")
                .extractingJsonPathStringValue("list[0]").isEqualTo("first value");
        jsonContent.assertThat()
                .hasJsonPathValue("list")
                .extractingJsonPathStringValue("list[1]").isEqualTo("second value");
        jsonContent.assertThat()
                .doesNotHaveJsonPathValue("list[2]");
        jsonContent.assertThat()
                .hasJsonPathValue("map.firstKey")
                .extractingJsonPathStringValue("map.firstKey").isEqualTo("value1");
        jsonContent.assertThat()
                .hasJsonPathValue("map.secondKey.innerKey1")
                .extractingJsonPathStringValue("map.secondKey.innerKey1").isEqualTo("innerValue1");
        jsonContent.assertThat()
                .hasJsonPathValue("map.secondKey.innerKey2")
                .extractingJsonPathStringValue("map.secondKey.innerKey2").isEqualTo("innerValue2");

    }
}