package com.unipi.msc.jobfinderapi.Controller;

import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<String> home(){
        User loggedInUserEmail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok("hello");
    }
}
