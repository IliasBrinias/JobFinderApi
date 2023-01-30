package com.unipi.msc.jobfinderapi.Controller.JobController.Responses;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPresenter {
    private Long Id;
    private String title;
    private double price;
    private double maxPrice;
    private Visibility jobVisibility;
    private Visibility priceVisibility;
    private String username;
    private JobSubCategory jobSubCategory;
    private PaymentType paymentType;
    private JobDuration duration;
    private Set<Skill> skills;
}
