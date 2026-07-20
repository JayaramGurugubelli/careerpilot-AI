package careerpilot_parent.security.config;


import careerpilot_parent.security.filter.JwtAuthenticationFilter;
import careerpilot_parent.security.handler.JwtAccessDeniedHandler;
import careerpilot_parent.security.handler.JwtAuthenticationEntryPoint;
import careerpilot_parent.security.service.CustomUserDetailsService;


import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAccessDeniedHandler accessDeniedHandler;




    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(12);

    }




    @Bean
    public AuthenticationProvider authenticationProvider() {


        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();


        provider.setUserDetailsService(
                customUserDetailsService
        );


        provider.setPasswordEncoder(
                passwordEncoder()
        );


        return provider;

    }





    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    )
            throws Exception {


        return configuration.getAuthenticationManager();

    }





    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    )
            throws Exception {


        http

                .csrf(csrf -> csrf.disable())


                .cors(Customizer.withDefaults())


                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                .exceptionHandling(exception -> exception

                        .authenticationEntryPoint(
                                authenticationEntryPoint
                        )

                        .accessDeniedHandler(
                                accessDeniedHandler
                        )
                )


                .authenticationProvider(
                        authenticationProvider()
                )


                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(

                                "/api/auth/register",

                                "/api/auth/login",

                                "/api/auth/logout",

                                "/api/auth/verify-email",

                                "/api/auth/forgot-password",

                                "/api/auth/reset-password",

                                "/api/auth/refresh-token"

                        )
                        .permitAll()



                        .requestMatchers(

                                "/swagger-ui/**",

                                "/v3/api-docs/**",

                                "/swagger-ui.html"

                        )
                        .permitAll()



                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        )
                        .permitAll()



                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")



                        .requestMatchers("/api/recruiter/**")
                        .hasRole("RECRUITER")



                        .requestMatchers("/api/student/**")
                        .hasRole("STUDENT")



                        .anyRequest()
                        .authenticated()

                )


                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );



        return http.build();

    }





    /**
     * Angular Frontend CORS Configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){


        CorsConfiguration configuration =
                new CorsConfiguration();



        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:4200"
                )
        );



        configuration.setAllowedMethods(
                List.of(

                        "GET",

                        "POST",

                        "PUT",

                        "DELETE",

                        "PATCH",

                        "OPTIONS"

                )
        );



        configuration.setAllowedHeaders(
                List.of("*")
        );



        configuration.setAllowCredentials(true);



        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();



        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;

    }

}