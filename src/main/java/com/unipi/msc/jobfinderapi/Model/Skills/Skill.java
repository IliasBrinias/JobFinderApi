package com.unipi.msc.jobfinderapi.Model.Skills;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    private String hint;
}
