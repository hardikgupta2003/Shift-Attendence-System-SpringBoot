package com.hardik.auth.controller;

import com.hardik.auth.dto.LoginRequest;
import com.hardik.auth.dto.LoginResponse;
import com.hardik.auth.security.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        // 1️⃣ Authenticate (email + password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // 2️⃣ JWT generate
        String token = jwtUtil.generateToken(
                (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal());

        // 3️⃣ Return token
        return new LoginResponse(token);
    }
}
