package com.unipi.msc.jobfinderapi.Model.Link;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String link;
    @ManyToOne
    @JsonBackReference
    @JsonIgnore
    private User user;
}