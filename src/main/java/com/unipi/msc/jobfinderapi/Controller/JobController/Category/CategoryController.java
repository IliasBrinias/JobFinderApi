package com.unipi.msc.jobfinderapi.Controller.JobController.Category;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryService;
import com.unipi.msc.jobfinderapi.Model.Job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job/category")
@RequiredArgsConstructor
public class CategoryController {
    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryService jobCategoryService;
    private final JobRepository jobRepository;

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(jobCategoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategories(@PathVariable Long id) {
        JobCategory jobCategory = jobCategoryService.getCategoryById(id).orElse(null);
        if (jobCategory == null) {
            return ResponseEntity.badRequest().body(ErrorMessages.JOB_CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok(jobCategory);
    }

    @PostMapping
    public ResponseEntity<?> createCategory() {
        List<JobCategory> categoryList = jobCategoryService.getCategories();
        return ResponseEntity.ok(categoryList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (jobCategoryService.deleteCategory(id) == 0){
            return ResponseEntity.badRequest().body(ErrorMessages.JOB_CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody JobCategory request) {
        JobCategory jobCategory = jobCategoryService.getCategoryById(id).orElse(null);
        if (jobCategory == null) return ResponseEntity.badRequest().body(ErrorMessages.JOB_CATEGORY_NOT_FOUND);
        if (request != null) {
            jobCategory.setCategory(request.getCategory());
        }
        return ResponseEntity.ok(jobCategoryRepository.save(jobCategory));
    }
}
