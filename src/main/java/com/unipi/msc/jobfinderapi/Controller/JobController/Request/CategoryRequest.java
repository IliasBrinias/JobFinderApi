package com.unipi.msc.jobfinderapi.Controller.JobController.Request;

import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    private Long id;
    private String category;
    private List<JobSubCategory> subCategories;
}
