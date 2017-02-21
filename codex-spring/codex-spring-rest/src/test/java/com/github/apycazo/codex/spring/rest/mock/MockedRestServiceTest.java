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
import static org.springframework.test.web.client.ExpectedCount.min;
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

    @Test(expected = AssertionError.class)
    public void makeCallsInOrderAndValidatedExpectingError ()
    {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        // first query expected (note that 'manyTimes()' means 1+)
        String firstQueryUrl = "http://127.0.0.1:9000/one";
        String firstQueryResponse = "{\"key\":1}";
        server
                .expect(manyTimes(), requestTo(firstQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(firstQueryResponse, MediaType.APPLICATION_JSON));
        // second query expected
        String secondQueryUrl = "http://127.0.0.1:9000/two";
        String secondQueryResponse = "{\"key\":2}";
        server
                .expect(min(2), requestTo(secondQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(secondQueryResponse, MediaType.APPLICATION_JSON));
        // now make the calls and verify
        String response;
        response = restTemplate.getForObject(firstQueryUrl, String.class);
        assertEquals(firstQueryResponse, response);
        response = restTemplate.getForObject(secondQueryUrl, String.class);
        assertEquals(secondQueryResponse, response);
        // I expect this to throw an exception
        server.verify();
    }

    @Test
    public void makeCallsInOrderAndValidated ()
    {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        // first query expected (note that 'manyTimes()' means 1+)
        String firstQueryUrl = "http://127.0.0.1:9000/one";
        String firstQueryResponse = "{\"key\":1}";
        server
                .expect(manyTimes(), requestTo(firstQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(firstQueryResponse, MediaType.APPLICATION_JSON));
        // second query expected
        String secondQueryUrl = "http://127.0.0.1:9000/two";
        String secondQueryResponse = "{\"key\":2}";
        server
                .expect(min(2), requestTo(secondQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(secondQueryResponse, MediaType.APPLICATION_JSON));
        // now make the calls and verify
        String response;
        response = restTemplate.getForObject(firstQueryUrl, String.class);
        assertEquals(firstQueryResponse, response);
        response = restTemplate.getForObject(secondQueryUrl, String.class);
        assertEquals(secondQueryResponse, response);
        response = restTemplate.getForObject(secondQueryUrl, String.class);
        assertEquals(secondQueryResponse, response);
        // I expect this to succeed
        server.verify();
    }

    @Test(expected = AssertionError.class)
    public void makeCallsNotInOrder ()
    {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        // first query expected (note that 'manyTimes()' means 1+)
        String firstQueryUrl = "http://127.0.0.1:9000/one";
        String firstQueryResponse = "{\"key\":1}";
        server
                .expect(manyTimes(), requestTo(firstQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(firstQueryResponse, MediaType.APPLICATION_JSON));
        // second query expected
        String secondQueryUrl = "http://127.0.0.1:9000/two";
        String secondQueryResponse = "{\"key\":2}";
        server
                .expect(manyTimes(), requestTo(secondQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(secondQueryResponse, MediaType.APPLICATION_JSON));
        // now make the calls and verify
        String response;
        // make the second expected call before
        response = restTemplate.getForObject(secondQueryUrl, String.class);
        assertEquals(secondQueryResponse, response);
        response = restTemplate.getForObject(firstQueryUrl, String.class);
        assertEquals(firstQueryResponse, response);
    }

    @Test
    public void makeCallsAllowingAnyOrder ()
    {
        // configure mocked server to ignore order
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).ignoreExpectOrder(true).build();
        // first query expected (note that 'manyTimes()' means 1+)
        String firstQueryUrl = "http://127.0.0.1:9000/one";
        String firstQueryResponse = "{\"key\":1}";
        server
                .expect(manyTimes(), requestTo(firstQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(firstQueryResponse, MediaType.APPLICATION_JSON));
        // second query expected
        String secondQueryUrl = "http://127.0.0.1:9000/two";
        String secondQueryResponse = "{\"key\":2}";
        server
                .expect(manyTimes(), requestTo(secondQueryUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(secondQueryResponse, MediaType.APPLICATION_JSON));
        // now make the calls and verify
        String response;
        // make the second expected call before
        response = restTemplate.getForObject(secondQueryUrl, String.class);
        assertEquals(secondQueryResponse, response);
        response = restTemplate.getForObject(firstQueryUrl, String.class);
        assertEquals(firstQueryResponse, response);
        // verify
        server.verify();
    }
}
