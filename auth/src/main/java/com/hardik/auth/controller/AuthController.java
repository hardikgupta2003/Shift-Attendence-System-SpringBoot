package com.hardik.auth.controller;

import com.hardik.auth.dto.LoginRequest;
import com.hardik.auth.dto.LoginResponse;
import com.hardik.auth.dto.RegisterRequest;
import com.hardik.auth.dto.RegisterResponse;
import com.hardik.auth.entitiy.Role;
import com.hardik.auth.entitiy.User;
import com.hardik.auth.repository.RoleRepository;
import com.hardik.auth.repository.UserRepository;
import com.hardik.auth.security.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        // 1️⃣ Authenticate (email + password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        // 2️⃣ JWT generate
        String token = jwtUtil.generateToken(
                (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal());

        // 3️⃣ Return token
        return ResponseEntity.ok(new LoginResponse("User Logged In Successfully",token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse<User>> register(
            @Valid @RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);
        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterResponse<>("User Registered Successfully", user));
    }

}
