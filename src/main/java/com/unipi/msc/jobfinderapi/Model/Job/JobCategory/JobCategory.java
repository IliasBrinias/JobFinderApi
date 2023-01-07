package com.unipi.msc.jobfinderapi.Model.Job.JobCategory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCategory {
    @Id
    @GeneratedValue
    private Long Id;
    private String category;
}
