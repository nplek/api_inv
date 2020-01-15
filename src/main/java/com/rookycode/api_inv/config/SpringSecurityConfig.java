package com.rookycode.api_inv.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select email,password,enabled "
            + "from users "
            + "where email = ?")
        .authoritiesByUsernameQuery("select email,authority "
            + "from authorities "
            + "where email = ?");
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}1111").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }*/

    // Secure the endpoins with HTTP Basic authentication
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/person/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/person").hasAnyRole("ADMIN","SUPER")
                .antMatchers(HttpMethod.PUT, "/persons/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/persons/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/persons/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/cosmetic/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/cosmetic/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/cosmetic/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/cosmetic/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}