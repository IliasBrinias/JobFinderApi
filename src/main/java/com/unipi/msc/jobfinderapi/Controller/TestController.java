package com.unipi.msc.jobfinderapi.Controller;

import com.unipi.msc.jobfinderapi.Model.User.Admin;
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
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u instanceof Admin){
            Admin a = (Admin) u;
        }
        return ResponseEntity.ok("hello");
    }
}
