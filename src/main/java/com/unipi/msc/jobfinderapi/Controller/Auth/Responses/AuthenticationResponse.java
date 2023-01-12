package com.unipi.msc.jobfinderapi.Controller.Auth.Responses;

import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long id;
    private String email;
    private String username;
    private Role role;
    private Boolean isVerified;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Long birthday;
    private String token;

    // Client
    private String dsc;
    private List<Link> link;

    //Dev
    private List<Skill> skills;
}
