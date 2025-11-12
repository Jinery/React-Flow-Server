package com.kychnoo.react_flow.controller.authentification;

import com.kychnoo.react_flow.dto.request.authentication.AuthRequest;
import com.kychnoo.react_flow.dto.response.ErrorResponse;
import com.kychnoo.react_flow.dto.response.authentication.AuthResponse;
import com.kychnoo.react_flow.exception.UsernameAlreadyExistsException;
import com.kychnoo.react_flow.model.User;
import com.kychnoo.react_flow.model.details.CustomUserDetails;
import com.kychnoo.react_flow.service.authentification.AuthService;
import com.kychnoo.react_flow.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MessageSource messageSource;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            User user = authService.registerUser(
                    request.getUsername(),
                    request.getPassword(),
                    request.getDisplayedName()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (UsernameAlreadyExistsException e) {
            log.error("Registration error: {}", e.getMessage());

            String localizedMessage = messageSource.getMessage(
                    "error.username.taken",
                    null,
                    LocaleContextHolder.getLocale()
            );

            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .error("Conflict")
                    .message(localizedMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            String localizedMessage = messageSource.getMessage(
                    "error.bad.credentials",
                    null,
                    LocaleContextHolder.getLocale()
            );

            ErrorResponse response = ErrorResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .error("Unauthorized")
                    .message(localizedMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        User user = ((CustomUserDetails)userDetails).getUser();

        final String jwt = jwtUtil.generateToken(user.getId(), userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwt, user.getId()));
    }
}
