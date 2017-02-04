package com.github.apycazo.codex.spring.rest.feign;


import feign.Logger;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableFeignClients
public class FeignConfig
{
    /**
     * Configures every feign logger level.
     * An alternative whould be to include a property like: com.github.apycazo.codex.spring.rest.feign.FeignedServiceClient=DEBUG
     * (Note that is is the fully qualified name for the client, and that only responds to 'DEBUG' level).
     * @return The configured level
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
