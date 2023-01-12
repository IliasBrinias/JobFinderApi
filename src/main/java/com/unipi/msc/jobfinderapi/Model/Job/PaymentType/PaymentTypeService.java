package com.unipi.msc.jobfinderapi.Model.Job.PaymentType;

import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;
    public Optional<PaymentType> getDurationById(Long Id){
        return paymentTypeRepository.findById(Id);
    }
}
