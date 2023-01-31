package com.unipi.msc.jobfinderapi.Controller.UserController;

import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String password;
    private String oldPassword;
    private String dsc;
    @Enumerated(EnumType.STRING)

    private Gender gender = Gender.OTHER;
    private String firstName;
    private String lastName;
    private Long birthday;
    private List<Long> links = new ArrayList<>();
    private List<Long> skills = new ArrayList<>();

}
