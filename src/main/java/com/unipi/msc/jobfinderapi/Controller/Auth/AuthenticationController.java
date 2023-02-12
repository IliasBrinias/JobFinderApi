package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.config.AsyncClient;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
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
    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        return authenticationService.logout();
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return authenticationService.register(request);
    }
    @GetMapping("/enable")
    public ResponseEntity<?> enableAccount(@RequestParam("token") String token) {
        return authenticationService.enableUserAccount(token);
    }
    @GetMapping("/resend-email")
    public ResponseEntity<?> resendEmail(@RequestParam("email") String email) {
        return authenticationService.resendEmail(email);
    }

}
