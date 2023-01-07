package com.unipi.msc.jobfinderapi.Model.Link;

import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String name;
    private String link;
}