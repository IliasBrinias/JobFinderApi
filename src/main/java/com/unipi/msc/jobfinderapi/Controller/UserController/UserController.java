package com.unipi.msc.jobfinderapi.Controller.UserController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.Auth.AuthenticationService;
import com.unipi.msc.jobfinderapi.Controller.responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    @GetMapping
    public ResponseEntity<?> getUser() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        return ResponseEntity.ok(authenticationService.getAuthenticationResponse(u));
    }

}
