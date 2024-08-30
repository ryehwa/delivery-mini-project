package com.lucky_vicky.delivery_project.user.controller;

import com.lucky_vicky.delivery_project.user.domain.RefreshToken;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.dto.JwtAuthenticationResponse;
import com.lucky_vicky.delivery_project.user.dto.LoginRequest;
import com.lucky_vicky.delivery_project.user.dto.RefreshTokenRequest;
import com.lucky_vicky.delivery_project.user.dto.SignUpRequest;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import com.lucky_vicky.delivery_project.user.security.JwtTokenProvider;
import com.lucky_vicky.delivery_project.user.service.RefreshTokenService;
import com.lucky_vicky.delivery_project.user.service.TokenBlacklistService;
import com.lucky_vicky.delivery_project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        user.setNickname(signUpRequest.getNickname());
        user.setAddress(signUpRequest.getAddress());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRoles(Collections.singleton("ROLE_USER")); // 'ROLE_' 접두사는 getAuthorities()에서 추가

        userService.createUser(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshToken.getToken()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = tokenProvider.generateToken(createAuthentication(user));
                    return ResponseEntity.ok(new JwtAuthenticationResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            long expirationTime = tokenProvider.getExpirationFromToken(token) - System.currentTimeMillis();
            tokenBlacklistService.blacklistToken(token, expirationTime);
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("Invalid token");
    }

    private Authentication createAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}