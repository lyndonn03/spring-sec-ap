package io.lpamintuan.spring_sec_ap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import io.lpamintuan.spring_sec_ap.models.UserAccountDetails;
import java.util.Optional;


public interface UserAccountDetailsRepository extends JpaRepository<UserAccountDetails, Long> {

    Optional<UserAccountDetails> findByUsername(String username);
    
}
