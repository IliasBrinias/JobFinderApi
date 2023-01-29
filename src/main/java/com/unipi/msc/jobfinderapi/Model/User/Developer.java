package com.unipi.msc.jobfinderapi.Model.User;

import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

@DiscriminatorValue("DEVELOPER")
public class Developer extends User {
    private String dsc;

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Link> link = new java.util.ArrayList<>();
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new java.util.ArrayList<>();

    public Developer(@NonNull String email, @NonNull String username, String password, @NonNull Role role, Gender gender, String firstName, String lastName, Long birthday, Boolean isVerified, String dsc, List<Link> link, List<Skill> skills) {
        super(email, username, password, role, gender, firstName, lastName, birthday, isVerified);
        this.dsc = dsc;
        this.link = link;
        this.skills = skills;
    }
}
