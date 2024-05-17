package org.pao.audiolibrarypao.services;

import jdk.jshell.spi.ExecutionControl;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.NotImplementedException;
import org.pao.audiolibrarypao.entities.Token;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.repositories.TokenRepository;
import org.pao.audiolibrarypao.repositories.UserRepository;
import org.pao.audiolibrarypao.utils.classes.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<?> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userOptional.get();
        String token = generateToken(user); // Implement token generation logic (e.g., JWT)
        saveUserToken(user, token);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    //Generate token
    private String generateToken(User user) {
        //Implementation using JWT or any other token provider
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE-USER")
                .build();
        return this.jwtService.generateToken(userDetails);
    }

    //save user token
    private void saveUserToken(User user, String jwtToken) {
        //save user token to repository

        this.tokenRepository.save(new Token(jwtToken, user));

    }
}

// Helper class for authentication responses:
@Setter
class AuthResponse {
    AuthResponse(String token) {
        this.token = token;
    }
    private String token;

    // Getter and constructor
}

