package com.unipi.msc.jobfinderapi.Controller.JobController.Request;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobRequest {
    private String title;
    @Enumerated(EnumType.STRING)
    private Visibility jobVisibility;
    @Enumerated(EnumType.STRING)
    private Visibility priceVisibility;
    private double price;
    private double maxPrice;
    private Long subCategoryId;
    private Long paymentTypeId;
    private Long durationId;
    private List<Long> skillList;
}
