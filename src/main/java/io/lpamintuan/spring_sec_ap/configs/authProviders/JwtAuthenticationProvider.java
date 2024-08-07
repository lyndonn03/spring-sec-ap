package io.lpamintuan.spring_sec_ap.configs.authProviders;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final SecretKey jwtSigningKey;

    public JwtAuthenticationProvider(SecretKey jwtSigningKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtSigningKey = jwtSigningKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(!authentication.getPrincipal().toString().equalsIgnoreCase("Bearer"))
            return null;
        try {
            Jws<Claims> jwt = Jwts.parser().verifyWith(jwtSigningKey).build().parseSignedClaims(authentication.getCredentials().toString());
            String username = jwt.getPayload().getSubject();
            UserDetails user = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
        } catch(JwtException ex) {
            throw new BadCredentialsException("Invalid authorization token.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
