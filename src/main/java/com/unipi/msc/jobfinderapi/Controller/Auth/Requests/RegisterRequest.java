package com.unipi.msc.jobfinderapi.Controller.Auth.Requests;

import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NonNull
    private String email;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String firstName;
    private String lastName;
    private Long birthday;
    private String dsc;

    private String role;
    private String gender;
    private List<Skill> skills;
}
