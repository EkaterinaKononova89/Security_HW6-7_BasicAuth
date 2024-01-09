package ru.netology.Security_HW67_BasicAuth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user1")
                .password("{noop}password1")
                .authorities("read")
                .and()
                .withUser("user2")
                .password("{noop}password2")
                .authorities("write")
                .and()
                .withUser("user3")
                .password("{noop}password3")
                .authorities("delete");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/welcome").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/all").hasAuthority("read")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/{id}").hasAuthority("read")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/write").hasAuthority("write")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/delete").hasAuthority("delete")
                .and()

                // для метода POST
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/posts").hasAuthority("write")
                .and()

                // для метода DELETE
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/posts/{id}").hasAuthority("delete")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }
}
