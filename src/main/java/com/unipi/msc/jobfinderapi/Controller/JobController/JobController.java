package com.unipi.msc.jobfinderapi.Controller.JobController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.JobRequest;
import com.unipi.msc.jobfinderapi.Controller.JobController.Responses.JobPresenter;
import com.unipi.msc.jobfinderapi.Controller.responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDurationService;
import com.unipi.msc.jobfinderapi.Model.Job.JobRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobService;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategory;
import com.unipi.msc.jobfinderapi.Model.Job.JobSubCategory.JobSubCategoryService;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentType;
import com.unipi.msc.jobfinderapi.Model.Job.PaymentType.PaymentTypeService;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job")
public class JobController {
    private final JobDurationService jobDurationService;
    private final JobRepository jobRepository;
    private final JobSubCategoryService jobSubCategoryService;
    private final JobService jobService;
    private final SkillService skillService;
    private final PaymentTypeService paymentTypeService;
    private final JobCategoryRepository jobCategoryRepository;

    @GetMapping
    public ResponseEntity<?> getJobs() {
        List<Job> jobList = jobService.getJobs();
        List<JobPresenter> jobPresenterList = new ArrayList<>();
        for (Job j : jobList){
            jobPresenterList.add(
                    JobPresenter.builder()
                            .Id(j.getId())
                            .title(j.getTitle())
                            .username(j.getClient().getUsername())
                            .price(j.getPrice())
                            .jobSubCategory(j.getJobSubCategory())
                            .paymentType(j.getPaymentType())
                            .maxPrice(j.getMaxPrice())
                            .duration(j.getJobDuration())
                            .skills(j.getSkills())
                            .build()
            );
        }
        return ResponseEntity.ok(jobPresenterList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getJob(@PathVariable Long id) {
        Job j = jobService.getJobWithId(id).orElse(null);
        if (j == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_NOT_FOUND));
        JobPresenter jobPresenter = JobPresenter.builder()
                .Id(j.getId())
                .title(j.getTitle())
                .username(j.getClient().getUsername())
                .price(j.getPrice())
                .jobSubCategory(j.getJobSubCategory())
                .paymentType(j.getPaymentType())
                .maxPrice(j.getMaxPrice())
                .duration(j.getJobDuration())
                .skills(j.getSkills())
                .build();
        return ResponseEntity.ok(jobPresenter);
    }
    @PostMapping
    public ResponseEntity<?> addJob(@RequestBody JobRequest request) {
        Client client;
        try {
            client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (ClassCastException ignore){
            return ResponseEntity.status(403).body(new ErrorResponse(false,ErrorMessages.NotAuthorized));
        }

        JobDuration jobDuration = null;
        JobSubCategory jobSubCategory = null;
        PaymentType paymentType = null;
        Set<Skill> skills = new HashSet<>();

        if (request.getDurationId() != null) {
            jobDuration = jobDurationService.getDurationById(request.getDurationId()).orElse(null);
        }
        if (request.getSubCategoryId() != null) {
            jobSubCategory = jobSubCategoryService.getSubCategoryById(request.getSubCategoryId()).orElse(null);
        }
        if (request.getSkillList() != null) {
            skills = skillService.getSkillsByIdIn(request.getSkillList()).orElse(null);
        }
        if (request.getPaymentTypeId() != null) {
            paymentType = paymentTypeService.getPaymentTypeById(request.getPaymentTypeId()).orElse(null);
        }
        Job job = Job.builder()
                .title(request.getTitle())
                .client(client)
                .jobVisibility(Visibility.valueOf(request.getJobVisibility().toString()))
                .price(request.getPrice())
                .priceVisibility(Visibility.valueOf(request.getPriceVisibility().toString()))
                .jobSubCategory(jobSubCategory)
                .paymentType(paymentType)
                .maxPrice(request.getMaxPrice())
                .jobDuration(jobDuration)
                .skills(skills)
                .build();
        job = jobRepository.save(job);
        JobPresenter jobPresenter = JobPresenter.builder()
                .Id(job.getId())
                .title(job.getTitle())
                .username(job.getClient().getUsername())
                .price(job.getPrice())
                .jobSubCategory(job.getJobSubCategory())
                .paymentType(job.getPaymentType())
                .maxPrice(job.getMaxPrice())
                .duration(job.getJobDuration())
                .skills(job.getSkills())
                .build();

        return ResponseEntity.ok().body(jobPresenter);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editJob(@PathVariable Long id, @RequestBody JobRequest request) {

        Job job = jobService.getJobWithId(id).orElse(null);
        if (job == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_NOT_FOUND));

        if (request.getTitle() != null && !request.getTitle().equals("")){
            job.setTitle(request.getTitle());
        }
        if (request.getJobVisibility()!=null){
            job.setJobVisibility(request.getJobVisibility());
        }
        if (request.getPriceVisibility()!=null){
            job.setPriceVisibility(request.getPriceVisibility());
        }
        if (request.getPrice()!=0.0){
            job.setPrice(request.getPrice());
        }
        if (request.getMaxPrice()!=0.0){
            job.setMaxPrice(request.getMaxPrice());
        }
        if (request.getSubCategoryId()!=null && request.getSubCategoryId()!=0){
            JobSubCategory jobSubCategory = jobSubCategoryService.getSubCategoryById(request.getSubCategoryId()).orElse(null);
            if (jobSubCategory == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_CATEGORY_NOT_FOUND));
            job.setJobSubCategory(jobSubCategory);
        }
        if (request.getPaymentTypeId() != null && request.getPaymentTypeId() != 0){
            PaymentType paymentType = paymentTypeService.getPaymentTypeById(request.getPaymentTypeId()).orElse(null);
            if (paymentType == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.PAYMENT_TYPE_NOT_FOUND));
            job.setPaymentType(paymentType);
        }
        if (request.getDurationId() != null && request.getDurationId() != 0){
            JobDuration jobDuration = jobDurationService.getDurationById(request.getDurationId()).orElse(null);
            if (jobDuration == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_DURATION_NOT_FOUND));
            job.setJobDuration(jobDuration);
        }
        if (request.getSkillList() != null){
            Set<Skill> skills = skillService.getSkillsByIdIn(request.getSkillList()).orElse(null);
            if (skills == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.SKILLS_NOT_FOUND));
            job.setSkills(skills);
        }
        job = jobRepository.save(job);
        JobPresenter jobPresenter = JobPresenter.builder()
                .Id(job.getId())
                .title(job.getTitle())
                .username(job.getClient().getUsername())
                .price(job.getPrice())
                .jobSubCategory(job.getJobSubCategory())
                .paymentType(job.getPaymentType())
                .maxPrice(job.getMaxPrice())
                .duration(job.getJobDuration())
                .skills(job.getSkills())
                .build();

        return ResponseEntity.ok(jobPresenter);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        Job job = jobService.getJobWithId(id).orElse(null);
        if (job == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_NOT_FOUND));
        }
        Client client = job.getClient();
        client.getJobList().remove(job);

        jobRepository.delete(job);
        return ResponseEntity.ok().build();
    }
}
