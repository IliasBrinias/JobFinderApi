package com.unipi.msc.jobfinderapi.Model.Job;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Job implements Serializable {
    @Id
    @GeneratedValue
    private Long Id;
    private String title;
    private String description;
    private Long price;
    private Long maxPrice;
    private Long creationDate;
    @Enumerated
    private Visibility jobVisibility;
    @Enumerated
    private Visibility priceVisibility;
    @ManyToOne
    @JsonIgnore
    private Client client;
    @ManyToOne
    @JoinColumn(name = "job_duration_id")
    @JsonBackReference
    private JobDuration jobDuration;
    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    @JsonBackReference
    private PaymentType paymentType;
    @ManyToOne
    @JoinColumn(name = "job_sub_category_id")
    @JsonBackReference
    private JobSubCategory jobSubCategory;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "job_skills",
            joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "skills_id", referencedColumnName = "Id"))
    @JsonManagedReference
    private List<Skill> skills = new ArrayList<>();
}
