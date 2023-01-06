package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Responses.AuthenticationResponse;
import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import com.unipi.msc.jobfinderapi.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(User.builder()
                        .username(request.getUsername())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .role(request.getRole())
                        .build())
                .build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .user(user)
                .token(jwtToken)
                .build();
    }
}
