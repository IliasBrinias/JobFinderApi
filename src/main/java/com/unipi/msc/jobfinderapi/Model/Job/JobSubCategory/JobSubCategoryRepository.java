package com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobSubCategoryRepository extends JpaRepository<JobSubCategory,Long> {
    Optional<JobSubCategory> findById(Long Id);
    List<JobSubCategory> findAll();
    Integer deleteJobSubCategoryById(Long id);
}