package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.HouseFindingBack.Controller.Auth.Requests.AuthenticationLoginRequest;
import com.unipi.msc.HouseFindingBack.Controller.Auth.Requests.AuthenticationSignUpRequest;
import com.unipi.msc.HouseFindingBack.Model.User.User;
import com.unipi.msc.HouseFindingBack.Model.User.UserService;
import com.unipi.msc.HouseFindingBack.Security.jwtUtils;
import com.unipi.msc.HouseFindingBack.Model.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final jwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationLoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        final User user = userService.getUserByUsername(request.getEmail());
        if (user!=null){
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("Some error has occurred");
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthenticationSignUpRequest request){
        //TODO: Entity Framework Implementation
        return ResponseEntity.noContent().build();
    }

}
