package org.pao.audiolibrarypao.utils.classes;

import lombok.Getter;
import lombok.Setter;

public class AuthResponse {
    public AuthResponse(String token) {
        this.token = token;
    }

    @Getter
    private final String token;
}
