package com.creswave.blog.service;

import com.creswave.blog.dto.JwtAuthenticationResponse;
import com.creswave.blog.dto.RefreshTokenRequest;
import com.creswave.blog.dto.SignInRequest;
import com.creswave.blog.dto.SignUpRequest;
import com.creswave.blog.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> signup(SignUpRequest signUpRequest);

    ResponseEntity<?> signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
