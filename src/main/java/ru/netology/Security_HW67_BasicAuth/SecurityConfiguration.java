package ru.netology.Security_HW67_BasicAuth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeRequests(authorize -> authorize
                        .antMatchers(HttpMethod.GET, "/api/posts/welcome").permitAll()
                        .antMatchers(HttpMethod.GET, "/api/posts/write").hasAuthority("write")
                        .antMatchers(HttpMethod.GET, "/api/posts/delete").hasAuthority("delete")
                        .antMatchers(HttpMethod.GET, "/api/posts/**").hasAuthority("read")

                        // для метода POST
                        .antMatchers(HttpMethod.POST, "/api/posts").hasAuthority("write")

                        // для метода DELETE
                        .antMatchers(HttpMethod.DELETE, "/api/posts/{id}").hasAuthority("delete")

                        .anyRequest().authenticated())
                .csrf().disable();
    }
}
