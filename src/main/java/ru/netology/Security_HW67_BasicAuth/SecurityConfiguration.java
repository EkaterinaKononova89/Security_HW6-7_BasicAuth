package ru.netology.Security_HW67_BasicAuth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder encoder) {
        UserDetails user1 = User.withUsername("user1")
                .password(encoder.encode("password1"))
                .roles("READ")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(encoder.encode("password2"))
                .roles("WRITE")
                .build();
        UserDetails user3 = User.withUsername("user3")
                .password(encoder.encode("password3"))
                .roles("DELETE")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/posts/welcome").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
        return http.build();
    }
}

//@Bean
//    public UserDetailsService userDetailsService() throws Exception {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User
//                .withUsername("user")
//                .password(encoder().encode("userPass"))
//                .roles("USER").build());
//        manager.createUser(User
//                .withUsername("admin")
//                .password(encoder().encode("adminPass"))
//                .roles("ADMIN").build());
//        return manager;
//    }
