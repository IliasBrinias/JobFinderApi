package com.unipi.msc.jobfinderapi.Model.Job.JobDuration;

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

public class JobDuration {
    @jakarta.persistence.Id
    @GeneratedValue
    private Long Id;
    private String duration;

}
