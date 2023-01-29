package com.unipi.msc.jobfinderapi.Model.Job.PaymentType;

import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;
    public Optional<PaymentType> getPaymentTypeById(Long Id){
        return paymentTypeRepository.findById(Id);
    }
    public List<PaymentType> getPaymentTypes(){
        return paymentTypeRepository.findAll();
    }
    @Transactional
    public int deleteWithId(Long id) {
        return paymentTypeRepository.deletePaymentTypeById(id);
    }
}
