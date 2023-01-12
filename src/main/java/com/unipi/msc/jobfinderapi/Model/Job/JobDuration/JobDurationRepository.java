package com.unipi.msc.jobfinderapi.Model.Job.JobDuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobDurationRepository extends JpaRepository<JobDuration,Long> {
    Optional<JobDuration> findById(Long Id);
}