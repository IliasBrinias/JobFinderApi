package com.unipi.msc.jobfinderapi.Controller.UserController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.Auth.AuthenticationService;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Link.LinkService;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final LinkService linkService;
    private final SkillService skillService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getUser() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        return ResponseEntity.ok(authenticationService.getAuthenticationResponse(u,null));
    }
    @PatchMapping
    public ResponseEntity<?> editUser(@RequestBody UserRequest request) {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (request.getBirthday() != null && request.getBirthday()!=0){
            u.setBirthday(request.getBirthday());
        }
        if (request.getGender()!=null){
            u.setGender(request.getGender());
        }
        if (request.getFirstName()!=null){
            u.setFirstName(request.getFirstName());
        }
        if (request.getLastName()!=null){
            u.setLastName(request.getLastName());
        }
        if (u instanceof Client){
            Client c = (Client) u;
            if (request.getLinks()!=null){
                List<Link> links = linkService.getLinksByIds(request.getLinks()).orElse(null);
                if (links == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.LINK_NOT_FOUND));
                c.setLinks(links);
            }
            if (request.getDsc()!=null){
                c.setDsc(request.getDsc());
            }
        }
        if (u instanceof Developer){
            Developer d = (Developer) u;
            if (request.getLinks()!=null){
                List<Link> links = linkService.getLinksByIds(request.getLinks()).orElse(null);
                if (links == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.LINK_NOT_FOUND));
                d.setLinks(links);
            }
            if (request.getSkills()!=null){
                List<Skill> skills = skillService.getSkillsByIdIn(request.getSkills()).orElse(null);
                if (skills == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.SKILLS_NOT_FOUND));
                d.setSkills(skills);
            }
            if (request.getDsc()!=null){
                d.setDsc(request.getDsc());
            }
        }
        u = userRepository.save(u);
        return ResponseEntity.ok(authenticationService.getAuthenticationResponse(u,null));
    }
}
