package com.github.apycazo.codex.spring.rest.feign;

import feign.Headers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * A couple notes:
 * <ul>
 *     <li>New spring boot annotations, like '@GetMapping' are not supported</li>
 *     <li>@PathVariable annotations and such need to be explicitly named (like the 'echo' method below)</li>
 * </ul>
 */
public interface FeignedServiceAPI
{
    @RequestMapping(value = "feign/echo/{text}", method = RequestMethod.GET)
    @Headers("Content-Type: application/json") // only to show how to send headers
    String echo (@PathVariable(value = "text") String text);
    @RequestMapping(value = "feign/reverse", method = RequestMethod.POST)
    String reverse(@RequestBody String text);
}
