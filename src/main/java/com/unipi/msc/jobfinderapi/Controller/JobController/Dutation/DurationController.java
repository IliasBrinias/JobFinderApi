package com.unipi.msc.jobfinderapi.Controller.JobController.Dutation;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Request.DurationRequest;
import com.unipi.msc.jobfinderapi.Controller.responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDuration;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDurationRepository;
import com.unipi.msc.jobfinderapi.Model.Job.JobDuration.JobDurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job/duration")
public class DurationController {
    private final JobDurationRepository jobDurationRepository;
    private final JobDurationService jobDurationService;
    @GetMapping
    public ResponseEntity<?> getDurations() {
        return ResponseEntity.ok(jobDurationService.getDurations());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getDuration(@PathVariable Long id) {
        JobDuration jobDuration = jobDurationService.getDurationById(id).orElse(null);
        if (jobDuration == null) return ResponseEntity.badRequest().body(ErrorMessages.JOB_DURATION_NOT_FOUND);
        return ResponseEntity.ok(jobDurationService.getDurationById(id));
    }
    @PostMapping
    public ResponseEntity<?> addDuration(@RequestBody DurationRequest request) {
        JobDuration jobDuration = JobDuration.builder()
                .duration(request.getDuration())
                .build();
        return ResponseEntity.ok(jobDurationRepository.save(jobDuration));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editDuration(@PathVariable Long id, @RequestBody DurationRequest request) {
        JobDuration jobDuration = jobDurationService.getDurationById(id).orElse(null);
        if (jobDuration == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.JOB_DURATION_NOT_FOUND));
        if (request.getDuration() != null){
            jobDuration.setDuration(request.getDuration());
        }
        return ResponseEntity.ok(jobDurationRepository.save(jobDuration));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDuration(@PathVariable Long id) {
        if (jobDurationService.deleteJob(id) == 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_DURATION_NOT_FOUND));
        }
        return ResponseEntity.ok().build();
    }
}
