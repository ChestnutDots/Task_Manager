package com.TaskManager.TaskManager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Spring Security configuration for the Task Manager application.
 */
@Configuration
public class SecurityConfiguration {


    /**
     * Retrieve user and its roles based on username.
     * Customizes default queries for users and authorities to match database schema.
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.role FROM users u JOIN roles r ON u.id=r.user_id WHERE u.username=?");

        return jdbcUserDetailsManager;
    }


    /**
     * Define URL access rules based on roles and user authentication status.
     * - Accessible for all: login, registration, static resources
     * - Accessible only for users: task management
     * - Accessible only for admin: user management
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers("/login","/addUser","/saveUser", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/showTasks", "/addTasks", "/save", "/update", "/delete").hasRole("USER")
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

    /**
     * Encrypt passwords using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
