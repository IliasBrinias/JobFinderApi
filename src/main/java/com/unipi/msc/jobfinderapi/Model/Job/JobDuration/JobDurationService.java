package com.unipi.msc.jobfinderapi.Model.Job.JobDuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobDurationService {
    private final JobDurationRepository jobDurationRepository;
    public Optional<JobDuration> getDurationById(Long Id){
        return jobDurationRepository.findById(Id);
    }
}
