package com.unipi.msc.jobfinderapi.Model.Job.PaymentType;

import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
    Optional<PaymentType> findById(Long Id);
    List<PaymentType> findAll();
    Integer deletePaymentTypeById(Long Id);
}