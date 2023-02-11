package com.unipi.msc.jobfinderapi.Controller.JobController.Request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Range {
    private Long from;
    private Long to;
}
