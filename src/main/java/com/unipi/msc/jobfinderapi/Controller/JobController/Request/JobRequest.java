package com.unipi.msc.jobfinderapi.Controller.JobController.Request;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobRequest {
    private String title;
    private String dsc;
    @Enumerated(EnumType.STRING)
    private Visibility jobVisibility;
    @Enumerated(EnumType.STRING)
    private Visibility priceVisibility;
    private Long price;
    private Long maxPrice;
    private Long subCategoryId;
    private Long paymentTypeId;
    private Long durationId;
    private List<Long> skillList;
}
