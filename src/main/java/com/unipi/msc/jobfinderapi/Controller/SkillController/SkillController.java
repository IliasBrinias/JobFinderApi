package com.unipi.msc.jobfinderapi.Controller.SkillController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Responses.JobPresenter;
import com.unipi.msc.jobfinderapi.Controller.SkillController.Request.SkillRequest;
import com.unipi.msc.jobfinderapi.Controller.responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillRepository;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {
    private final SkillService skillService;
    private final SkillRepository skillRepository;
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
        if (skillService.deleteById(id) == 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.SKILLS_NOT_FOUND));
        }
        return ResponseEntity.ok().build();
    }
}
