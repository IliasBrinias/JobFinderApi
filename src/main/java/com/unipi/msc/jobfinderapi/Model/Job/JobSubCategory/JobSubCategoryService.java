package com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSubCategoryService {
    private final JobSubCategoryRepository jobSubCategoryRepository;
    public Optional<JobSubCategory> getSubCategoryById(Long Id){
        return jobSubCategoryRepository.findById(Id);
    }

    public List<JobSubCategory> getSubCategories() {
        return jobSubCategoryRepository.findAll();
    }
    @Transactional
    public int deleteSubCategory(Long id) {
        return jobSubCategoryRepository.deleteJobSubCategoryById(id);
    }
}
