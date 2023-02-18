package com.unipi.msc.jobfinderapi.Model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Offer.Offer;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

@DiscriminatorValue("DEVELOPER")
public class Developer extends User {
    private String dsc;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Link> links = new ArrayList<>();
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Skill> skills = new ArrayList<>();
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Offer> offers = new ArrayList<>();
    public Developer(@NonNull String email, @NonNull String username, String password, @NonNull Role role, Gender gender, String firstName, String lastName, Long birthday, Boolean isVerified, String dsc, List<Link> links, List<Skill> skills) {
        super(email, username, password, role, gender, firstName, lastName, birthday, isVerified);
        this.dsc = dsc;
        this.links = links;
        this.skills = skills;
    }
}
