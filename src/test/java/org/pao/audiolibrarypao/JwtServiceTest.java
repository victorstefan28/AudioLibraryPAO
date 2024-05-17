package org.pao.audiolibrarypao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.pao.audiolibrarypao.services.JwtService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        // Manually set the values for the properties
        jwtService.setSecretKey(";!}3}gohwU=fJtKXl,hbho,[b`aum+-Q(|<Y\"75)^(covWExaDZ2iQ%cqge7}}");
        jwtService.setJwtExpiration(8640000000L);
    }

    @Test
    void testGenerateAndValidateToken() {
        UserDetails userDetails = User.withUsername("testuser")
                .password("password")
                .authorities("USER")
                .build();

        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertFalse(token.isEmpty());

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertTrue(isValid);
    }

    @Test
    void testExpiredToken() {

        UserDetails userDetails = User.withUsername("testuser")
                .password("password")
                .authorities("USER")
                .build();

        jwtService.setJwtExpiration(0L);
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertFalse(token.isEmpty());

        boolean isValid = jwtService.isTokenValid(token, userDetails);
        assertFalse(isValid);

    }
}
