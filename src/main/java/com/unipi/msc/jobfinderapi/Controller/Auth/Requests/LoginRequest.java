package com.unipi.msc.jobfinderapi.Controller.Auth.Requests;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
