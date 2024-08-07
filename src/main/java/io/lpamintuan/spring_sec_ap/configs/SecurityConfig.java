package io.lpamintuan.spring_sec_ap.configs;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.lpamintuan.spring_sec_ap.configs.authErrors.AppAuthenticationEntryPoint;
import io.lpamintuan.spring_sec_ap.configs.authProviders.BasicAuthenticationProvider;
import io.lpamintuan.spring_sec_ap.configs.authProviders.JwtAuthenticationProvider;
import io.lpamintuan.spring_sec_ap.configs.filters.AppAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(BasicAuthenticationProvider bap,
            JwtAuthenticationProvider jap) {
        return new ProviderManager(bap, jap);
    }

    @Bean
    SecretKey jwtSigningKey() {
        return Jwts.SIG.HS256.key().build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, AppAuthenticationFilter aaf, 
            AppAuthenticationEntryPoint aae) throws Exception {

        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/account/login")
                .permitAll();
            c.requestMatchers(HttpMethod.GET, "/")
                .authenticated();
        });

        http.csrf(c -> c.disable());

        http.exceptionHandling(c -> {
            c.authenticationEntryPoint(aae);
        });

        http.addFilterAt(aaf, BasicAuthenticationFilter.class);

        return http.build();
    }
    
}
