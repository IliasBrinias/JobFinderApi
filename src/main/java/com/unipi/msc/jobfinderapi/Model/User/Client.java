package com.unipi.msc.jobfinderapi.Model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "CLIENT")
public class Client extends User {
    private String dsc;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user", cascade ={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private List<Link> links = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonManagedReference
    private List<Job> jobList;

    public Client(@NonNull String email, @NonNull String username, @NonNull Role role, String dsc) {
        super(email, username, role);
        this.dsc = dsc;
    }

    public Client(@NonNull String email, @NonNull String username, String password, @NonNull Role role, Gender gender, String firstName, String lastName, Long birthday, Boolean isVerified, String dsc) {
        super(email, username, password, role, gender, firstName, lastName, birthday, isVerified);
        this.dsc = dsc;
    }
}
