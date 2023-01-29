package com.unipi.msc.jobfinderapi.Controller.JobController.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponse {
    private Long id;
    private String subCategory;
    private Long categoryId;
}
