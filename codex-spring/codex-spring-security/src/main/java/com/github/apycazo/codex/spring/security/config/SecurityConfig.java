package com.github.apycazo.codex.spring.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    static final String REALM = "codex-spring-security";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("gandalf").password("the-grey").roles("wizard")
                .and()
                .withUser("boromir").password("of-gondor").roles("warrior");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        log.info("Configuring security");
        http
                .authorizeRequests()
                .antMatchers("/private/**").hasRole("wizard")
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(basicAuthenticationEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint ()
    {
        return new BasicAuthenticationEntryPoint(){
            @Override
            public void commence(final HttpServletRequest request,
                                 final HttpServletResponse response,
                                 final AuthenticationException authException) throws IOException, ServletException
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error on realm:" + getRealmName());
            }

            @Override
            public void afterPropertiesSet() throws Exception
            {
                setRealmName(SecurityConfig.REALM);
                super.afterPropertiesSet();
            }
        };
    }
}
