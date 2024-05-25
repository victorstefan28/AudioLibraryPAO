package org.pao.audiolibrarypao.services;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import org.pao.audiolibrarypao.entities.Token;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.repositories.TokenRepository;
import org.pao.audiolibrarypao.repositories.UserRepository;
import org.pao.audiolibrarypao.utils.classes.AuthResponse;
import org.pao.audiolibrarypao.utils.classes.LoginRequest;
import org.pao.audiolibrarypao.utils.classes.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication-related operations.
 */
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

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return a ResponseEntity indicating the result of the registration
     */
    public ResponseEntity<?> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use");
        }
        if(userRepository.findAll().isEmpty())
            user.setAdmin(true);

        String notHashedPaswword = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        LoginRequest loginRequest = new LoginRequest(user.getEmail(), notHashedPaswword);
        return loginUser(loginRequest);//ResponseEntity.ok("User registered successfully");
    }

    /**
     * Logs in a user.
     *
     * @param loginRequest the login request containing the user's email and password
     * @return a ResponseEntity containing the authentication response or an error status
     */
    public ResponseEntity<?> loginUser(@JsonView(View.Public.class) LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()
                || !passwordEncoder.matches(
                loginRequest.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        User user = userOptional.get();

        String token = generateToken(user);
        // saveUserToken(user, token);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Generates a JWT token for the specified user.
     *
     * @param user the user for whom to generate the token
     * @return the generated JWT token
     */
    private String generateToken(@JsonView(View.Public.class) User user) {
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                        .password(user.getPassword())
                        .authorities("ROLE-USER")
                        .build();
        return this.jwtService.generateToken(userDetails, user);
    }

    /**
     * Saves the user's token to the repository.
     *
     * @param user the user for whom to save the token
     * @param jwtToken the JWT token to save
     */
    private void saveUserToken(User user, String jwtToken) {
        this.tokenRepository.save(new Token(jwtToken, user));
    }

    public ResponseEntity<?> promoteUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        User user = userOptional.get();
        user.setAdmin(true);
        userRepository.save(user);
        return ResponseEntity.ok("User promoted successfully");
    }
}
