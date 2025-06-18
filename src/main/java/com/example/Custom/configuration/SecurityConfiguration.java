package com.example.Custom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.DispatcherType;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.example.Custom.service.CustomUserDetailsService;
import com.example.Custom.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    private final CustomSuccessHandler customSuccessHandler;
    private final UserService userService;

    public SecurityConfiguration(UserService userService, CustomSuccessHandler customSuccessHandler) {
        this.userService = userService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    @Bean
    DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false); // hien thij thong bao sai user

        return authProvider;
    }

    @Bean
    SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);// dat trong 30 ngay
        return rememberMeServices;
    }

@Bean
SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    .dispatcherTypeMatchers(DispatcherType.FORWARD,
                            DispatcherType.INCLUDE)
                    .permitAll()
                    // cho phep truy cap
                    .requestMatchers("/", "/login", "/register", "/product/**", "/client/**", "/css/**", "/js/**",
                            "/images/**", "/submitDesign")
                    .permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated())

                // session
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/logout?expired")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))

                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

                // .rememberMe(r -> r.rememberMeServices(rememberMeServices()))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler)
                        .permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-deny"));

        return http.build();
    }

}
