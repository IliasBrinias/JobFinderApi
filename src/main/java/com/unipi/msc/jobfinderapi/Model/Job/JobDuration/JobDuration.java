package com.unipi.msc.jobfinderapi.Model.Job.JobDuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class JobDuration {
    @Id
    @GeneratedValue
    private Long Id;
    private String duration;

    @OneToMany(mappedBy = "jobDuration", orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Job> jobs = new java.util.ArrayList<>();
}
