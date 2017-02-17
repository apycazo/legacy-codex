package com.github.apycazo.codex.spring.rest.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.apycazo.codex.spring.rest.user.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockedRestServiceTest
{
    @Autowired
    private RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void interceptRestTemplateCall () throws JsonProcessingException
    {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        // configuration
        String user = mapper.writeValueAsString(UserInfo.builder().id(1).username("test").build());
        String url = UriComponentsBuilder.fromHttpUrl("http://google.com").queryParam("test", "true").toUriString();
        // configure mocked response
        server
                .expect(manyTimes(), requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(user, MediaType.APPLICATION_JSON));
        // the next query will be intercepted by the mocked server
        UserInfo info = restTemplate.getForObject(url, UserInfo.class);
        assertNotNull(info);
        assertEquals(1, info.getId());
        assertEquals("test", info.getUsername());
    }
}
