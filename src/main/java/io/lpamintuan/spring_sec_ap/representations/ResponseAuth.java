package io.lpamintuan.spring_sec_ap.representations;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseAuth {

    private String accessToken;

    private String refreshToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  LocalDateTime accessTokenExpiry;
    
}
