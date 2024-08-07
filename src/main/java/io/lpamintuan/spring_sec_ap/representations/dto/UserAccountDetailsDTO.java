package io.lpamintuan.spring_sec_ap.representations.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAccountDetailsDTO {
    
    @NotNull(message = "Username must not be null.")
    @Size(min = 4, message = "Username must be at least 4 characters long.")
    private String username;

    @NotEmpty(message = "Password must not be empty.")
    private String password;

}
