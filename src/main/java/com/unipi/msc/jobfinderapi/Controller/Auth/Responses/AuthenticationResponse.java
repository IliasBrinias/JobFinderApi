package com.unipi.msc.jobfinderapi.Controller.Auth.Responses;

import com.unipi.msc.jobfinderapi.Model.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private User user;
    private String token;

}
