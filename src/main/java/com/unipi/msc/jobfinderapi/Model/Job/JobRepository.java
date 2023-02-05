package com.unipi.msc.jobfinderapi.Model.Job;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    Integer deleteJobById(Long Id);
    Optional<Job> findById(Long Id);
    List<Job> findAllByJobVisibilityNot(Visibility jobVisibility);
}