package org.pao.audiolibrarypao.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.pao.audiolibrarypao.entities.User;
import org.pao.audiolibrarypao.guards.RequiresAdmin;
import org.pao.audiolibrarypao.services.AuthService;
import org.pao.audiolibrarypao.utils.classes.LoginRequest;
import org.pao.audiolibrarypao.utils.classes.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return authService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @PostMapping("/promote")
    @RequiresAdmin
    public ResponseEntity<?> promote(@RequestParam String email) {
        return authService.promoteUser(email);
    }

}
