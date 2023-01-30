package com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobSubCategory{
    @Id
    @GeneratedValue
    private Long Id;
    private String subCategory;
    @JsonIgnore
    @ManyToOne
    @JsonBackReference
    JobCategory jobCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "jobSubCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Job> jobs = new java.util.ArrayList<>();
}
