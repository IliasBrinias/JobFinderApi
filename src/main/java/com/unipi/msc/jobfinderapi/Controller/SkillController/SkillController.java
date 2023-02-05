package com.unipi.msc.jobfinderapi.Controller.SkillController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.SkillController.Request.SkillRequest;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Job.JobRepository;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillRepository;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {
    private final SkillService skillService;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @GetMapping
    public ResponseEntity<?> getSkills() {
        return ResponseEntity.ok(skillService.getSkills());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getSkill(@PathVariable Long id) {
        Skill skill = skillService.getSkillById(id).orElse(null);
        if (skill == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.SKILLS_NOT_FOUND));
        return ResponseEntity.ok(skill);
    }
    @PostMapping
    public ResponseEntity<?> addSkill(@RequestBody SkillRequest request) {
        Skill skill = Skill.builder()
                .skill(request.getSkill())
                .alias(request.getAlias())
                .build();
        return ResponseEntity.ok(skillRepository.save(skill));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> editSkill(@PathVariable Long id, @RequestBody SkillRequest request) {
        Skill skill = skillService.getSkillById(id).orElse(null);
        if (skill == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.SKILLS_NOT_FOUND));
        if (request.getSkill()!=null){
            skill.setSkill(request.getSkill());
        }
        if (request.getAlias()!=null){
            skill.setAlias(request.getAlias());
        }

        return ResponseEntity.ok(skillRepository.save(skill));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id) {
        Skill skill = skillService.getSkillById(id).orElse(null);
        if (skill == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.SKILL_NOT_FOUND));

        Developer developer = skill.getDeveloper();
        if (developer!=null) {
            developer.getSkills().remove(skill);
            userRepository.save(developer);
        }
        List<Job> jobs = skill.getJobs();
        if (jobs!=null) {
            for (Job j : jobs) {
                j.getSkills().remove(skill);
                jobRepository.save(j);
            }
        }
        skillRepository.delete(skill);
        return ResponseEntity.ok().build();
    }
}
