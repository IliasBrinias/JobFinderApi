package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Responses.AuthenticationResponse;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.User.*;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.config.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public ResponseEntity<?> register(RegisterRequest request) {
        // check for empty data
        if (request.getUsername().equals("") || request.getEmail().equals("") || request.getPassword().equals("")) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.FILL_ALL_THE_FIELDS));
        }

        // check if the user exists
        String error_msg = checkIfExist(request);
        if (!error_msg.equals("")) return ResponseEntity.badRequest().body(new ErrorResponse(false,error_msg));

        // build user object and save it
        User user;
        if (request.getRole() == Role.CLIENT){
            user = new Client(request.getEmail(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getRole(),
                    request.getGender(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday(),
                    false,
                    request.getDsc()
                    );
        }
        else if (request.getRole()==Role.DEVELOPER){
            user = new Developer(request.getEmail(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getRole(),
                    request.getGender(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday(),
                    false,
                    request.getDsc(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }
        else if (request.getRole()==Role.ADMIN){
            user = new Admin(request.getEmail(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getRole(),
                    request.getGender(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday(),
                    false);
        }else {
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.ROLE_IS_NULL));
        }
        userRepository.save(user);
        return ResponseEntity.ok(getAuthenticationResponse(user, jwtService.generateToken(user)));
    }
    public ResponseEntity<?> authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        // double check if the user exists
        if (!userRepository.findByUsername(request.getUsername()).isPresent()) return ResponseEntity.notFound().build();
        User user = userRepository.findByUsername(request.getUsername()).get();
        return ResponseEntity.ok(getAuthenticationResponse(user, jwtService.generateToken(user)));
    }
    public AuthenticationResponse getAuthenticationResponse(User user, String jwtToken) {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .token(jwtToken).build();
        if (user instanceof Client){
            Client c = (Client) user;
            response.setDsc(c.getDsc());
            response.setLink(c.getLinks());
            response.setJobs(c.getJobList());
        }
        if (user instanceof Developer){
            Developer d = (Developer) user;
            response.setDsc(d.getDsc());
            response.setLink(d.getLinks());
            response.setSkills(d.getSkills());
        }
        return response;
    }
    public String checkIfExist(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ErrorMessages.USERNAME_EXISTS;
        }else if (userRepository.findByEmail(request.getEmail()).isPresent()){
            return ErrorMessages.EMAIL_EXISTS;
        }
        return "";
    }
    public ResponseEntity<?> enableUserAccount(String token) {
        String jwt, userEmail;
        // check if the header has Bearer token
        if (token == null || !token.startsWith("Bearer "))
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.NO_HEADER_FOUND));
        // get the token from the Bearer and extract the username
        jwt = token.substring(7);
        try {
            userEmail = jwtService.extractUsername(jwt);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.TOKEN_EXPIRED));
        }
        User u = userService.getUserByUsername(userEmail).orElse(null);
        if (u == null)
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        u.setIsVerified(true);
        userRepository.save(u);
        return ResponseEntity.ok(getAuthenticationResponse(u,jwtService.generateToken(u)));
    }

    private void sendVerificationEmail(){
        
    }
}
