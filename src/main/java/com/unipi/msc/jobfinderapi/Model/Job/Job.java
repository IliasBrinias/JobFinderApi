package com.unipi.msc.jobfinderapi.Model.Job;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Skills.Skills;
import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Job {
    @Id
    @GeneratedValue
    private Long Id;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private Visibility jobVisibility;
    private double price;
    private Visibility priceVisibility;
    @OneToOne
    private JobCategory category;
    @OneToOne
    private PaymentType paymentType;
    private double maxPrice;
    @OneToOne
    private JobDuration duration;
    @OneToMany
    private List<Skills> skills;
}
