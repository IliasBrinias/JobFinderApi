package com.unipi.msc.jobfinderapi.Controller.UserController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.Auth.AuthenticationService;
import com.unipi.msc.jobfinderapi.Controller.Auth.Responses.UserPresenter;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Controller.UserController.Request.SearchUserRequest;
import com.unipi.msc.jobfinderapi.Controller.UserController.Request.UserRequest;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Link.LinkService;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.User.*;
import com.unipi.msc.jobfinderapi.Model.User.Image.ImageRepository;
import com.unipi.msc.jobfinderapi.Model.User.Image.ImageService;
import com.unipi.msc.jobfinderapi.Shared.ImageUtils;
import com.unipi.msc.jobfinderapi.Shared.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final LinkService linkService;
    private final SkillService skillService;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping
    public ResponseEntity<?> getUser() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        return ResponseEntity.ok(authenticationService.getAuthenticationResponse(u,null));
    }
    @GetMapping("/{id}/info")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User u = userService.getUser(id).orElse(null);
        if (u == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        return ResponseEntity.ok(authenticationService.getAuthenticationResponse(u,null));
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestBody SearchUserRequest request) {
        List<User> userList = userService.getUsers();
        List<UserPresenter> filteredUsers = new ArrayList<>();
        userList.stream()
                .filter(u-> {
                    if (request.getName()!=null) {
                        if (u.getFirstName()!=null){
                            if (u.getFirstName().contains(request.getName())){
                                return true;
                            }
                        }
                        if (u.getLastName()!=null){
                            return u.getLastName().contains(request.getName());
                        }
                        return false;
                    }
                    return true;
                }).filter(u -> {
                    if (request.getCreationDate()!=null){
                        return Tools.between(request.getCreationDate().getFrom(),request.getCreationDate().getTo(),u.getCreationDate());
                    }
                    return true;
                }).filter(u-> {
                    if (request.getRating()!=null){
                        return Tools.between(request.getRating().getFrom(), request.getRating().getTo(), u.getRating());
                    }
                    return true;
                }).filter(u->{
                    Developer d;
                    if (u instanceof Developer){
                        d = (Developer) u;
                    }else {
                        return true;
                    }
                    // skills
                    if (request.getSkills() != null) {
                        return d.getSkills().stream().anyMatch(skill -> request.getSkills().stream().anyMatch(id -> Objects.equals(id, skill.getId())));
                    }
                    return true;
                }).filter(u->{
                    if (request.getRole()!=null){
                        return u.getRole() == request.getRole();
                    }
                    return true;
                })
                .forEach(user -> filteredUsers.add(authenticationService.getAuthenticationResponse(user,null)));
        return ResponseEntity.ok(filteredUsers);
    }
    @PostMapping("/image")
    public ResponseEntity<?> addImage(@RequestParam("image") MultipartFile file) {
        if (file == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.FILE_NOT_FOUND));
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            u.setImage(imageService.uploadImage(file));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.INVALID_FILE));
        }
        userRepository.save(u);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        User u = userService.getUser(id).orElse(null);
        if (u == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        if (u.getImage()==null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.PROFILE_NOT_FOUND));
        byte[] image = ImageUtils.decompressImage(u.getImage().getImageData());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
    }
    @GetMapping("/image")
    public ResponseEntity<?> getImage() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (u.getImage()==null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.PROFILE_NOT_FOUND));
        byte[] image = ImageUtils.decompressImage(u.getImage().getImageData());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
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
