package com.german.studentschedule.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.german.studentschedule.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

import static com.german.studentschedule.util.constants.Templates.FORBIDDEN_JSON;
import static com.german.studentschedule.util.constants.Templates.UNAUTHORIZED_JSON;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper mapper;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, ObjectMapper mapper) {
        this.userDetailsService = userDetailsService;
        this.mapper = mapper;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.getPasswordEncoderBean());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/users/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (req, res, e)-> {
                            String json = this.mapper.writeValueAsString(UNAUTHORIZED_JSON);
                            res.setStatus(401);
                            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            res.getWriter().println(json);
                        }
                )
                .accessDeniedHandler(
                        (req, res, e)-> {
                            String json = this.mapper.writeValueAsString(FORBIDDEN_JSON);
                            res.setStatus(403);
                            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            res.getWriter().println(json);
                        }
                )
        ;
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoderBean() {
        return new BCryptPasswordEncoder();
    }


}
