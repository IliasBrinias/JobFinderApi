package com.unipi.msc.jobfinderapi.Controller.JobController.Request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchJobRequest {
    private List<Long> skillList;
    private Range creationDate;
    private Long category;
    private String title;
    private Long paymentType;
    private Range price;
}
