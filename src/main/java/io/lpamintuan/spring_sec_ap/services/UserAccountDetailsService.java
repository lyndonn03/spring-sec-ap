package io.lpamintuan.spring_sec_ap.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.lpamintuan.spring_sec_ap.repositories.UserAccountDetailsRepository;

@Service
public class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountDetailsRepository userAccountDetailsRepository;

    public UserAccountDetailsService(UserAccountDetailsRepository userAccountDetailsRepository) {
        this.userAccountDetailsRepository = userAccountDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountDetailsRepository.findByUsername(username)
                .orElse(null);
    }
    
}
