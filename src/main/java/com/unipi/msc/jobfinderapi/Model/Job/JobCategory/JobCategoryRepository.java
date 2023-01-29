package com.unipi.msc.jobfinderapi.Model.Job.JobCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategory,Long> {
    Optional<JobCategory> findJobCategoryById(Long Id);
    List<JobCategory> findAll();
    Integer deleteJobCategoryById(Long id);

}