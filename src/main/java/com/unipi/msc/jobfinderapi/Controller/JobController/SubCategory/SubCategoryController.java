package com.unipi.msc.jobfinderapi.Controller.JobController.SubCategory;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.SubCategoryRequest;
import com.unipi.msc.jobfinderapi.Controller.JobController.Responses.SubCategoryResponse;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryService;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("job/subcategory")
@RequiredArgsConstructor
public class SubCategoryController {
    private final JobSubCategoryRepository jobSubCategoryRepository;
    private final JobSubCategoryService jobSubCategoryService;
    private final JobCategoryService jobCategoryService;
    private final JobCategoryRepository jobCategoryRepository;

    @GetMapping
    public ResponseEntity<?> getSubCategories() {
        return ResponseEntity.ok(jobSubCategoryService.getSubCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubCategory(@PathVariable Long id) {
        JobSubCategory jobSubCategory = jobSubCategoryService.getSubCategoryById(id).orElse(null);
        if (jobSubCategory == null) {
            return ResponseEntity.badRequest().body(ErrorMessages.JOB_SUBCATEGORY_NOT_FOUND);
        }
        return ResponseEntity.ok(SubCategoryResponse.builder()
                .id(jobSubCategory.getId())
                .subCategory(jobSubCategory.getSubCategory())
                .build());
    }
    @PostMapping
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategoryRequest request) {
        // find category
        JobCategory jobCategory = jobCategoryService.getCategoryById(request.getCategoryId()).orElse(null);
        if (jobCategory == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_CATEGORY_NOT_FOUND));

        // build sub category
        JobSubCategory jobSubCategory = JobSubCategory.builder()
                .subCategory(request.getSubCategory())
                .jobCategory(jobCategory)
                .build();

        return ResponseEntity.ok(jobSubCategoryRepository.save(jobSubCategory));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody SubCategoryRequest request) {
        JobSubCategory jobSubCategory = jobSubCategoryService.getSubCategoryById(id).orElse(null);
        if (jobSubCategory == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_SUBCATEGORY_NOT_FOUND));
        if (request.getSubCategory() != null){
            jobSubCategory.setSubCategory(request.getSubCategory());
        }
        jobSubCategoryRepository.save(jobSubCategory);
        return ResponseEntity.ok(SubCategoryResponse.builder()
                .id(jobSubCategory.getId())
                .subCategory(jobSubCategory.getSubCategory())
                .categoryId(jobSubCategory.getJobCategory().getId())
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable Long id) {
        JobSubCategory jobSubCategory = jobSubCategoryService.getSubCategoryById(id).orElse(null);
        if (jobSubCategory == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_SUBCATEGORY_NOT_FOUND));

        JobCategory jobCategory = jobSubCategory.getJobCategory();
        jobCategory.getJobSubCategoryList().remove(jobSubCategory);

        jobCategoryRepository.save(jobCategory);
        jobSubCategoryRepository.delete(jobSubCategory);

        return ResponseEntity.ok().build();
    }

}
