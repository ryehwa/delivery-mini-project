package com.lucky_vicky.delivery_project.user.service;

import com.lucky_vicky.delivery_project.user.security.JwtTokenProvider;
import com.lucky_vicky.delivery_project.user.domain.User;
import com.lucky_vicky.delivery_project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final Set<String> tokenBlacklist = new HashSet<>();

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public Map<String, String> loginUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String accessToken = jwtTokenProvider.createAccessToken(username, user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(username, user.getRoles());

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public void logoutUser(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        tokenBlacklist.add(token);
    }

    public Map<String, String> refreshToken(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken) && !tokenBlacklist.contains(refreshToken)) {
            String username = jwtTokenProvider.getUsernameFromJWT(refreshToken);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

            String newAccessToken = jwtTokenProvider.createAccessToken(username, user.getRoles());

            return Map.of("accessToken", newAccessToken);
        }
        throw new IllegalArgumentException("Invalid refresh token");
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }
}