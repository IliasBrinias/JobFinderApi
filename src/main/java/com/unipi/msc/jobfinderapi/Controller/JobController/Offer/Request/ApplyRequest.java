package com.unipi.msc.jobfinderapi.Controller.JobController.Offer.Request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRequest {
    private String comment;
    private Double price;
}
