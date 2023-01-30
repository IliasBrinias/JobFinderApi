package com.unipi.msc.jobfinderapi.Model.Job.PaymentType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @OneToMany(mappedBy = "paymentType", orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    List<Job> jobs = new java.util.ArrayList<>();
}
