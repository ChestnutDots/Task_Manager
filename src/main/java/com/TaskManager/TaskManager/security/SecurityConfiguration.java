package com.TaskManager.TaskManager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {


    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        // tell spring security to use JDBC authentication with our data source:
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // define query to retrieve a user by username (in quotes its just simple sql)
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username=?");

        // define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.role FROM users u JOIN roles r ON u.id=r.user_id WHERE u.username=?");

        return jdbcUserDetailsManager;
    }


    // configure spring security to use a custom login form:
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/login","/addUser","/saveUser", "/css/**").permitAll()
                        .requestMatchers("/showTasks").authenticated()
                        .anyRequest().authenticated()
        )
                .formLogin(form ->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .defaultSuccessUrl("/showTasks", true)
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
