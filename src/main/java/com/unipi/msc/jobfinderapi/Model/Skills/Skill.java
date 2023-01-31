package com.unipi.msc.jobfinderapi.Model.Skills;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Skill implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;
    private String skill;
    private String alias;
    @JsonIgnore
    @ManyToOne
    private Developer developer;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonBackReference
    private List<Job> jobs = new ArrayList<>();
}
