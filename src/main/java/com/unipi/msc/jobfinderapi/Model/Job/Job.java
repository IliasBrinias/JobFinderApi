package com.unipi.msc.jobfinderapi.Model.Job;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.User.Client;
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
    private String title;
    private double price;
    private double maxPrice;
    @Enumerated
    private Visibility jobVisibility;
    @Enumerated
    private Visibility priceVisibility;
    @ManyToOne
    private Client client;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private JobSubCategory jobSubCategory;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentType paymentType;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private JobDuration duration;
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new java.util.ArrayList<>();
}
