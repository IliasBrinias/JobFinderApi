package com.unipi.msc.jobfinderapi.Model.Job.JobCategory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobCategoryService {
    private final JobCategoryRepository jobCategoryRepository;
    public Optional<JobCategory> getCategoryById(Long Id){
        return jobCategoryRepository.findById(Id);
    }

    public List<JobCategory> getCategories() {
        return jobCategoryRepository.findAll();
    }
    @Transactional
    public int deleteCategory(Long id) {
        return jobCategoryRepository.deleteJobCategoryById(id);
    }
}
