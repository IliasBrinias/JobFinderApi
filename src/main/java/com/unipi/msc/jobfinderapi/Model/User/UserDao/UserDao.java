package com.unipi.msc.jobfinderapi.Model.User.UserDao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserDao {
    @Id
    @GeneratedValue
    private Long Id;
    @Column(unique = true)
    private String token;
    private Long created;
    private Boolean isActive;

    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    private User user;
}
