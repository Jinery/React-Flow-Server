package com.kychnoo.react_flow.dto.response.authentication;

import lombok.*;

@Data
@Getter
public class AuthResponse {
    private final String jwt;
    private final Long userId;
}
