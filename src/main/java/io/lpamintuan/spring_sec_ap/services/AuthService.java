package io.lpamintuan.spring_sec_ap.services;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.lpamintuan.spring_sec_ap.models.UserAccountDetails;
import io.lpamintuan.spring_sec_ap.repositories.UserAccountDetailsRepository;
import io.lpamintuan.spring_sec_ap.representations.ResponseAuth;
import io.lpamintuan.spring_sec_ap.representations.dto.UserAccountDetailsDTO;

@Service
public class AuthService {

    private final UserAccountDetailsService userAccountDetailsService;
    private final UserAccountDetailsRepository userAccountDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey jwtSigningKey;
    
    public AuthService(UserAccountDetailsService userAccountDetailsService, 
            SecretKey jwtSigningKey, 
            PasswordEncoder passwordEncoder, 
            UserAccountDetailsRepository userAccountDetailsRepository) {
        this.userAccountDetailsService = userAccountDetailsService;
        this.userAccountDetailsRepository = userAccountDetailsRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtSigningKey = jwtSigningKey;
    }

    public ResponseAuth login(UserAccountDetailsDTO userAccountDetails) {
        UserDetails user = userAccountDetailsService.loadUserByUsername(userAccountDetails.getUsername());
        if(user == null)
            throw new UsernameNotFoundException("Invalid Credentials.");
        if(passwordEncoder.matches(userAccountDetails.getPassword(), user.getPassword())) {
            String jws = Jwts.builder().subject(user.getUsername())
                .signWith(jwtSigningKey).compact();
            return new ResponseAuth(jws, null, null);
        } else
            throw new BadCredentialsException("Username and Password does not match.");
    }

    public UserAccountDetails save(UserAccountDetailsDTO userAccountDetails) {
        UserAccountDetails newUser = new UserAccountDetails();
        newUser.setUsername(userAccountDetails.getUsername());
        newUser.setPassword(passwordEncoder.encode(userAccountDetails.getPassword()));
        return userAccountDetailsRepository.save(newUser);
    }

}
