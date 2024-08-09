package io.lpamintuan.spring_sec_ap.representations.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.lpamintuan.spring_sec_ap.validations.constraints.UniqueUsernameConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAccountDetailsDTO {
    
    @NotNull(message = "Username must not be null.")
    @Size(min = 4, message = "Username must be at least 4 characters long.")
    @UniqueUsernameConstraint
    private String username;

    @NotEmpty(message = "Password must not be empty.")
    private String password;

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
