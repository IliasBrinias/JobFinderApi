package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

}
