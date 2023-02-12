package com.unipi.msc.jobfinderapi.Model.Job;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    @Transactional
    public Integer deleteJob(Long id){
        return jobRepository.deleteJobById(id);
    }
    public Optional<Job> getJobWithId(Long id){
        return jobRepository.findById(id);
    }

    public List<Job> getJobsAuth() {
        return jobRepository.findAllByJobVisibilityNot(Visibility.PRIVATE);
    }
    public List<Job> getJobsPublic() {
        return jobRepository.findAllByJobVisibilityIs(Visibility.PUBLIC);
    }

}
