package com.unipi.msc.jobfinderapi.Model.User.Rating;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Rating {
    @Id
    @GeneratedValue
    private Long Id;
    private Double rating;
    @ManyToMany
    @JoinTable(name = "rating_users",
            joinColumns = @JoinColumn(name = "rating_id"),
            inverseJoinColumns = @JoinColumn(name = "users_"))
    @JsonManagedReference
    private List<User> users = new ArrayList<>();
}
