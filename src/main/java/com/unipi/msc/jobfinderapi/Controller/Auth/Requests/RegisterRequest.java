package com.unipi.msc.jobfinderapi.Controller.Auth.Requests;

import com.unipi.msc.jobfinderapi.Model.User.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Role role;
}
