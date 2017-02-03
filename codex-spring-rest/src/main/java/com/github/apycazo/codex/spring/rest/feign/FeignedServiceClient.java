package com.github.apycazo.codex.spring.rest.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * If Eureka is present, will try to find a service named 'feigned-service'. Otherwise, will use the URL provided.
 * When Hystrix is available, a fallback can be provided like:
 *
 * @FeignClient(name = "hello", fallback = HystrixClientFallback.class)
 * protected interface HystrixClient {
 * @RequestMapping(method = RequestMethod.GET, value = "/hello")
 *      Hello iFailSometimes();
 * }
 *
 * static class HystrixClientFallback implements HystrixClient {
 *      @Override
 *      public Hello iFailSometimes() {
 *          return new Hello("fallback");
 *      }
 * }
 */
@FeignClient(value = "http://feigned-service", url = "http://127.0.0.1:${server.port:8080}")
interface FeignedServiceClient extends FeignedServiceAPI
{
}
