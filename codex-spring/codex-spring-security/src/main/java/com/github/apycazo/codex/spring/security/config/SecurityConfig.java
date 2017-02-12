package com.github.apycazo.codex.spring.security.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

@Data
@Slf4j
@Configuration
@EnableWebSecurity
@EqualsAndHashCode(callSuper = false)
@ConfigurationProperties(prefix = "security")
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private String realm = "codex-spring-security";
    private boolean stateless = true;
    private boolean csrfDisabled = true;

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
                .antMatchers("/security/logout").permitAll()
                .antMatchers("/private/**").hasRole("wizard")
                .and().httpBasic().realmName(realm).authenticationEntryPoint(basicAuthenticationEntryPoint());

        if (stateless) {
            log.info("Sessions set to be stateless");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        if (csrfDisabled) {
            log.info("CSRF forgery protection is disabled");
            http.csrf().disable();
        }
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
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error on " + getRealmName());
            }

            @Override
            public void afterPropertiesSet() throws Exception
            {
                setRealmName(realm);
                super.afterPropertiesSet();
            }
        };
    }
}
