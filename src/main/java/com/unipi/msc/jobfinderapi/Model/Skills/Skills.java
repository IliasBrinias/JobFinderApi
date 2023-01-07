package com.unipi.msc.jobfinderapi.Model.Skills;

import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Skills {
    @Id
    @GeneratedValue
    private Long Id;
    private String skill;
    private String hint;
}
