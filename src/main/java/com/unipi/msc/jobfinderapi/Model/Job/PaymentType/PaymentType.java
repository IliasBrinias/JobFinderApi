package com.unipi.msc.jobfinderapi.Model.Job.PaymentType;

import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentType {
    @Id
    @GeneratedValue
    private Long Id;
    private String type;
}
