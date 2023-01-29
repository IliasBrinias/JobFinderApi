package com.unipi.msc.jobfinderapi.Model.Link;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    @JsonBackReference
    private User user;
    private String name;
    private String link;
    @ManyToOne
    private Developer developer;
    @ManyToOne
    private Client client;
}