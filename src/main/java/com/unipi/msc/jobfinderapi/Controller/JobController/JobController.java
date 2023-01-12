package com.unipi.msc.jobfinderapi.Controller.JobController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.AddJobRequest;
import com.unipi.msc.jobfinderapi.Controller.JobController.Responses.JobPresenter;
import com.unipi.msc.jobfinderapi.Controller.responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryService;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDurationService;
import com.unipi.msc.jobfinderapi.Model.Job.JobRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobService;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentTypeService;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {
    private final JobDurationService jobDurationService;
    private final JobRepository jobRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final JobService jobService;
    private final JobCategoryService jobCategoryService;
    private final SkillService skillService;
    private final PaymentTypeService paymentTypeService;
    @GetMapping
    public ResponseEntity<?> getJobs() {
        List<Job> jobList = jobService.getJobs();
        List<JobPresenter> jobPresenterList = new ArrayList<>();
        for (Job j : jobList){
            jobPresenterList.add(
                    JobPresenter.builder()
                            .Id(j.getId())
                            .title(j.getTitle())
                            .username(j.getUser().getUsername())
                            .price(j.getPrice())
                            .category(j.getCategory())
                            .paymentType(j.getPaymentType())
                            .maxPrice(j.getMaxPrice())
                            .duration(j.getDuration())
                            .skills(j.getSkills())
                            .build()
            );
        }
        return ResponseEntity.ok(jobPresenterList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id) {
        Job j = jobService.getJobWithId(id).orElse(null);
        if (j == null) return ResponseEntity.badRequest().body(ErrorMessages.JOB_NOT_FOUND);
        JobPresenter jobPresenter = JobPresenter.builder()
                .Id(j.getId())
                .title(j.getTitle())
                .username(j.getUser().getUsername())
                .price(j.getPrice())
                .category(j.getCategory())
                .paymentType(j.getPaymentType())
                .maxPrice(j.getMaxPrice())
                .duration(j.getDuration())
                .skills(j.getSkills())
                .build();
        return ResponseEntity.ok(jobPresenter);
    }

    @PostMapping()
    public ResponseEntity<?> getJobs(@RequestBody AddJobRequest request) {
        Developer developer;
        try {
            developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (ClassCastException ignore){
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.MUST_BE_DEV));
        }
        JobDuration jobDuration = null;
        JobCategory jobCategory = null;
        PaymentType paymentType = null;
        List<Skill> skills = new ArrayList<>();
        if (request.getDurationId() != null) {
            jobDuration = jobDurationService.getDurationById(request.getDurationId()).orElse(null);
        }
        if (request.getCategoryId() != null) {
            jobCategory = jobCategoryService.getCategoryById(request.getCategoryId()).orElse(null);
        }
        if (request.getSkillList() != null) {
            skills = skillService.getSkillsByIdIn(request.getSkillList()).orElse(new ArrayList<>());
        }
        if (request.getPaymentTypeId() != null) {
            paymentType = paymentTypeService.getDurationById(request.getPaymentTypeId()).orElse(null);
        }
        Job job = Job.builder()
                .title(request.getTitle())
                .user(developer)
                .jobVisibility(Visibility.valueOf(request.getJobVisibility().toString()))
                .price(request.getPrice())
                .priceVisibility(Visibility.valueOf(request.getPriceVisibility().toString()))
                .category(jobCategory)
                .paymentType(paymentType)
                .maxPrice(request.getMaxPrice())
                .duration(jobDuration)
                .skills(skills)
                .build();
        jobRepository.save(job);
        return ResponseEntity.ok().body(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editJob(@PathVariable Long id, @RequestBody  AddJobRequest request) {
        JobDuration jobDuration = jobDurationService.getDurationById(request.getDurationId()).orElse(null);
        List<Skill> skills = skillService.getSkillsByIdIn(request.getSkillList()).orElse(null);
        PaymentType paymentType = paymentTypeService.getDurationById(request.getPaymentTypeId()).orElse(null);

        Job job = jobService.getJobWithId(id).orElse(null);
        if (job == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_NOT_FOUND));

        if (request.getTitle() != null && !request.getTitle().equals("")){
            job.setTitle(request.getTitle());
        }
        if (request.getJobVisibility()!=null){
            job.setJobVisibility(request.getJobVisibility());
        }
        if (request.getPrice()!=0.0){
            job.setPrice(request.getPrice());
        }
        if (request.getPriceVisibility()!=null){
            job.setPriceVisibility(request.getPriceVisibility());
        }
        if (request.getCategoryId()!=null){
            JobCategory jobCategory = jobCategoryService.getCategoryById(request.getCategoryId()).orElse(null);
            if (jobCategory == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_CATEGORY_NOT_FOUND));
            job.setCategory(jobCategory);
        }
        // TODO: PUT is not completed
        jobRepository.save(job);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        if (jobService.deleteJob(id) == 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_NOT_FOUND));
        }
        return ResponseEntity.ok().build();
    }



}
