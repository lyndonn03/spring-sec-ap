package io.lpamintuan.spring_sec_ap.validations.validators;

import org.springframework.stereotype.Component;

import io.lpamintuan.spring_sec_ap.services.UserAccountDetailsService;
import io.lpamintuan.spring_sec_ap.validations.constraints.UniqueUsernameConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameConstraint, String> {

    private final UserAccountDetailsService userAccountDetailsService;

    public UniqueUsernameValidator(UserAccountDetailsService userAccountDetailsService) {
        this.userAccountDetailsService = userAccountDetailsService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userAccountDetailsService.loadUserByUsername(username) == null;
    }
    
}
