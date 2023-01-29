package com.unipi.msc.jobfinderapi.Model.Skills;

import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Skill {
    @Id
    @GeneratedValue
    private Long Id;
    private String skill;
    private String alias;
    @ManyToOne
    private Developer developer;
    @ManyToOne
    private Job job;
}
