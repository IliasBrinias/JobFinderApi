package com.unipi.msc.jobfinderapi.Controller.JobController.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DurationRequest {
    private String Id;
    private String duration;
}
