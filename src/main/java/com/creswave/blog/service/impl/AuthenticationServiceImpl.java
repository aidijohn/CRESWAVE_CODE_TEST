package com.creswave.blog.service.impl;

import com.creswave.blog.dto.JwtAuthenticationResponse;
import com.creswave.blog.dto.RefreshTokenRequest;
import com.creswave.blog.dto.SignInRequest;
import com.creswave.blog.dto.SignUpRequest;
import com.creswave.blog.model.Role;
import com.creswave.blog.model.User;
import com.creswave.blog.repository.UserRepository;
import com.creswave.blog.service.AuthenticationService;
import com.creswave.blog.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;


    //sign up
    public ResponseEntity<?> signup(SignUpRequest signUpRequest) {
        try {
            // Validate email format
            if (!EMAIL_PATTERN.matcher(signUpRequest.getEmail()).matches()) {
                JSONObject response = new JSONObject();
                response.put("response", "Signup Failed, Invalid email format");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
            }

            // Validate password length
            String password = signUpRequest.getPassword();
            if (password.length() < 4) {
                JSONObject response = new JSONObject();
                response.put("response", "Signup Failed, Password must be at least 4 characters long");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
            }

            // Check if the email already exists in the database
            Optional<User> existingUser = userRepository.findByEmail(signUpRequest.getEmail());
            if (existingUser.isPresent()) {
                JSONObject response = new JSONObject();
                response.put("response", "Signup Failed, Email already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response.toString());
            }

            // Create a new user if the email does not exist
            User user = new User();
            user.setEmail(signUpRequest.getEmail());
            user.setFirstname(signUpRequest.getFirstName());
            user.setSecondname(signUpRequest.getLastName());
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

            return ResponseEntity.ok(userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            // Handle any potential database integrity violation (e.g., unique constraint violation)
            JSONObject response = new JSONObject();
            response.put("response", "Failed to create user. Please try again.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString());
        }
    }

    //Signin
    public ResponseEntity<?> signin(SignInRequest signInRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                    signInRequest.getPassword()));

            // Fetching user from the db
            var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
            var jwt = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken);
            return ResponseEntity.ok(jwtAuthenticationResponse);
        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.put("response", "Login Failed, Wrong credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString());
        }
    }

    //refresh token
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {

            //user already saved in db, we proceed to create new token
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
