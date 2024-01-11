package ru.netology.Security_HW67_BasicAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder encoder) {
        UserDetails user1 = User.withUsername("user1")
                .password(encoder.encode("password1"))
                .authorities("read")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(encoder.encode("password2"))
                .authorities("write")
                .build();
        UserDetails user3 = User.withUsername("user3")
                .password(encoder.encode("password3"))
                .authorities("delete")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/welcome").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/write").hasAuthority("write")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/delete").hasAuthority("delete")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/**").hasAuthority("read")
                .and()

                // для метода POST
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/posts").hasAuthority("write")
                .and()

                // для метода DELETE
                .authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/posts/{id}").hasAuthority("delete")

                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();
        return http.build();
    }
}
