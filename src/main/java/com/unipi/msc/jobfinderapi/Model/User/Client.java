package com.unipi.msc.jobfinderapi.Model.User;

import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "CLIENT")
public class Client extends User {
    private String dsc;
    @OneToMany()
    private List<Link> link = new java.util.ArrayList<>();

    public Client(@NonNull String email, @NonNull String username, @NonNull Role role, String dsc, List<Link> link) {
        super(email, username, role);
        this.dsc = dsc;
        this.link = link;
    }

    public Client(@NonNull String email, @NonNull String username, String password, @NonNull Role role, Gender gender, String firstName, String lastName, Long birthday, Boolean isVerified, String dsc, List<Link> link) {
        super(email, username, password, role, gender, firstName, lastName, birthday, isVerified);
        this.dsc = dsc;
        this.link = link;
    }
}
