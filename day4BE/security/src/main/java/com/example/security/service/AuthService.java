package com.example.security.service;

import com.example.security.dto.SignInRequest;
import com.example.security.dto.SignInResponse;
import com.example.security.dto.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    SignInResponse signIn(SignInRequest signInRequest);
    String signUp(SignUpRequest signUpRequest);
    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}