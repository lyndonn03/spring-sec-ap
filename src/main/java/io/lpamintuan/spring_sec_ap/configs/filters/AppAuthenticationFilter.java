package io.lpamintuan.spring_sec_ap.configs.filters;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AppAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint entryPoint;

    public AppAuthenticationFilter(AuthenticationEntryPoint entryPoint, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.entryPoint = entryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        try {
            if(authHeader == null)
                this.entryPoint.commence(request, response, new AuthenticationServiceException("Unauthorized"));
            else {
                String[] creds = authHeader.split(" ");
                if(creds[0].equalsIgnoreCase("Basic")) {
                    String[] decodedCreds = new String(Base64.getDecoder().decode(creds[1])).split(":");
                    Authentication auth = new UsernamePasswordAuthenticationToken(decodedCreds[0], decodedCreds[1]);
                    Authentication authenticatedUser = authenticationManager.authenticate(auth);
                    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                }
            }
            filterChain.doFilter(request, response);
        } catch(AuthenticationException ae) {
            this.entryPoint.commence(request, response, ae);
        }
    }
    
}
