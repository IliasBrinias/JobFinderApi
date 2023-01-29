package com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne
    @JsonBackReference
    JobCategory jobCategory;
}
