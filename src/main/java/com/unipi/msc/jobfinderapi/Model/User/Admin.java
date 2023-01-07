package com.unipi.msc.jobfinderapi.Model.User;

import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "ADMIN")
public class Admin extends User {
    public Admin(@NonNull String email, @NonNull String username, String password, @NonNull Role role, Gender gender, String firstName, String lastName, Long birthday, Boolean isVerified) {
        super(email, username, password, role, gender, firstName, lastName, birthday, isVerified);
    }
}
