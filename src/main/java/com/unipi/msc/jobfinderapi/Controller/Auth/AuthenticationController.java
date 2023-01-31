package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
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
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request){
        return authenticationService.authenticate(request);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }
}
