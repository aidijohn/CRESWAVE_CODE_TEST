package com.creswave.blog.service;

import com.creswave.blog.dto.JwtAuthenticationResponse;
import com.creswave.blog.dto.RefreshTokenRequest;
import com.creswave.blog.dto.SignInRequest;
import com.creswave.blog.dto.SignUpRequest;
import com.creswave.blog.model.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
