package com.kychnoo.react_flow.service.authentification;

import com.kychnoo.react_flow.exception.UsernameAlreadyExistsException;
import com.kychnoo.react_flow.model.User;
import com.kychnoo.react_flow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String displayedName) {
        if(userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already taken");
        }

        User newUser = User.builder()
                .username(username)
                .passwordHash(passwordEncoder.encode(password))
                .displayedName(displayedName)
                .build();

        return userRepository.save(newUser);
    }
}
