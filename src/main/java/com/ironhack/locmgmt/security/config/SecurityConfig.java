package com.ironhack.locmgmt.security.config;


import com.ironhack.locmgmt.security.filter.JwtAuthenticationFilter;
import com.ironhack.locmgmt.service.imp.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /*private final CustomLogoutHandler logoutHandler;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomLogoutHandler logoutHandler) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/auth/login/**").permitAll()
                                .requestMatchers("/auth/register/**").hasAuthority("ADMIN")
                                .requestMatchers("/users/**").hasAuthority("ADMIN")
                                .requestMatchers("/clients/save", "/rates/save").hasAuthority("ADMIN")
                                .requestMatchers("/tasks/save", "/dtp-projects/save", "/linguistic-projects/save").hasAuthority("PROJECT_MANAGER")
                                .requestMatchers("/clients/update", "/rates/update").hasAuthority("ADMIN")
                                .requestMatchers("/tasks/update", "/dtp-projects/update", "/linguistic-projects/update").hasAuthority("PROJECT_MANAGER")
                                .requestMatchers("/clients/delete", "/rates/delete").hasAuthority("ADMIN")
                                .requestMatchers("/tasks/delete", "/dtp-projects/delete", "/linguistic-projects/delete").hasAuthority("PROJECT_MANAGER")
                                .requestMatchers("/clients/get/**", "/rates/get/**", "/linguists/get/**", "/project-managers/get/**").hasAnyAuthority("ADMIN", "PROJECT_MANAGER")
                                .requestMatchers("/tasks/get/**", "/dtp-projects/get/**", "/linguistic-projects/get/**", "/projects/get").hasAuthority("PROJECT_MANAGER")
                                .requestMatchers("/admins/get/**").hasAuthority("ADMIN")
                                //Check and add getTasksLinguist/getTasksPM
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(401)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                /*.logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))*/
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
