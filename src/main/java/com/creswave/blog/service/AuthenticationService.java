package com.creswave.blog.service;

import com.creswave.blog.dto.SignUpRequest;
import com.creswave.blog.model.User;

public interface AuthenticationService {

    User signup(SignUpRequest signUpRequest);
}
