package com.unipi.msc.jobfinderapi.Model.Job.JobDuration;

import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobDurationService {
    private final JobDurationRepository jobDurationRepository;
    public Optional<JobDuration> getDurationById(Long Id){
        return jobDurationRepository.findById(Id);
    }

    public List<JobDuration> getDurations() {
        return jobDurationRepository.findAll();
    }
    @Transactional
    public Integer deleteJob(Long id) {
        return jobDurationRepository.deleteJobDurationById(id);
    }
}
