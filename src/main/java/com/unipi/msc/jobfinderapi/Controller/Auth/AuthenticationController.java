package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import com.unipi.msc.jobfinderapi.Model.User.UserService;
import com.unipi.msc.jobfinderapi.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/enable")
    public ResponseEntity<?> enableAccount(@RequestHeader("Authorization") String token) {
        return authenticationService.enableUserAccount(token);
    }
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new ErrorResponse(true,"ok"));
    }

}
