package com.unipi.msc.jobfinderapi.Controller.JobController.Category;

import com.google.gson.GsonBuilder;
import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.CategoryRequest;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.SubCategoryRequest;
import com.unipi.msc.jobfinderapi.Controller.JobController.Responses.SubCategoryResponse;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryService;
import com.unipi.msc.jobfinderapi.Model.Job.JobRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/job/category")
@RequiredArgsConstructor
public class CategoryController {
    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryService jobCategoryService;

    @GetMapping
    public ResponseEntity<?> getCategories() {
        List<JobCategory> jobCategories = jobCategoryService.getCategories();
        return ResponseEntity.ok(jobCategories);
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
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        JobCategory jobCategory = JobCategory.builder()
                .category(request.getCategory())
                .build();
        return ResponseEntity.ok(jobCategoryRepository.save(jobCategory));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        JobCategory jobCategory = jobCategoryService.getCategoryById(id).orElse(null);
        if (jobCategory == null) return ResponseEntity.badRequest().body(ErrorMessages.JOB_CATEGORY_NOT_FOUND);
        if (request != null) {
            jobCategory.setCategory(request.getCategory());
        }
        return ResponseEntity.ok(jobCategoryRepository.save(jobCategory));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (jobCategoryService.deleteCategory(id) == 0){
            return ResponseEntity.badRequest().body(ErrorMessages.JOB_CATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok().build();
    }
}
