package com.unipi.msc.jobfinderapi.Controller.JobController.Responses;

import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPresenter {
    private Long Id;
    private String title;
    private String username;
    private double price;
    private JobSubCategory jobSubCategory;
    private PaymentType paymentType;
    private double maxPrice;
    private JobDuration duration;
    private List<Skill> skills;
}
