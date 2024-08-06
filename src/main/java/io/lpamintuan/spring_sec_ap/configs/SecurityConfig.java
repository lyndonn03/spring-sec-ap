package io.lpamintuan.spring_sec_ap.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.lpamintuan.spring_sec_ap.configs.authErrors.AppAuthenticationEntryPoint;
import io.lpamintuan.spring_sec_ap.configs.authProviders.BasicAuthenticationProvider;
import io.lpamintuan.spring_sec_ap.configs.filters.AppAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(BasicAuthenticationProvider bap) {
        return new ProviderManager(bap);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AppAuthenticationFilter aaf, 
            AppAuthenticationEntryPoint aae) throws Exception {

        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.GET, "/")
                .authenticated();
        });

        http.exceptionHandling(c -> {
            c.authenticationEntryPoint(aae);
        });

        http.addFilterAt(aaf, BasicAuthenticationFilter.class);

        return http.build();
    }
    
}
