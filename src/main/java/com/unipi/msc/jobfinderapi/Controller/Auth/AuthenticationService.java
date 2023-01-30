package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Responses.AuthenticationResponse;
import com.unipi.msc.jobfinderapi.Controller.responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.User.Admin;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import com.unipi.msc.jobfinderapi.config.JwtService;
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
        try {
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
            return ResponseEntity.ok(getAuthenticationResponse(user));
        }catch(Exception e) {
            return ResponseEntity.ok(ErrorResponse.handleSqlError(e));
        }

    }
    public ResponseEntity<?> authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        // double check if the user exists
        if (!userRepository.findByUsername(request.getUsername()).isPresent()) return ResponseEntity.notFound().build();
        User user = userRepository.findByUsername(request.getUsername()).get();
        return ResponseEntity.ok(getAuthenticationResponse(user));
    }
    public AuthenticationResponse getAuthenticationResponse(User user) {
        var jwtToken = jwtService.generateToken(user);
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
        }
        if (user instanceof Developer){
            Developer d = (Developer) user;
            response.setDsc(d.getDsc());
            response.setLink(d.getLink());
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
}
