package com.unipi.msc.jobfinderapi.Controller.LinkController;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.LinkController.Request.LinkRequest;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Job.JobCategory.JobCategoryRepository;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import com.unipi.msc.jobfinderapi.Model.Link.LinkRepository;
import com.unipi.msc.jobfinderapi.Model.Link.LinkService;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/link")
public class LinkController {
    private final LinkService linkService;
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final JobCategoryRepository jobCategoryRepository;

    @GetMapping
    public ResponseEntity<?> getLinks() {
        return ResponseEntity.ok(linkService.getLinks());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLink(@PathVariable Long id) {
        Link link = linkService.getLinkById(id).orElse(null);
        if (link == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.LINK_NOT_FOUND));
        return ResponseEntity.ok(link);
    }
    @PostMapping
    public ResponseEntity<?> addLink(@RequestBody LinkRequest request) {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Link link = Link.builder()
                .link(request.getLink())
                .name(request.getName())
                .user(u)
                .build();
//        if (u instanceof Client){
//            Client c = (Client) u;
//            c.getLinks().add(link);
//        }else if (u instanceof Developer){
//            Developer d = (Developer) u;
//            d.getLinks().add(link);
//        }
//        userRepository.save(u);
        return ResponseEntity.ok(linkRepository.save(link));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editLink(@PathVariable Long id, @RequestBody LinkRequest request) {
        Link link = linkService.getLinkById(id).orElse(null);
        if (link == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.LINK_NOT_FOUND));
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!u.equals(link.getUser())) return ResponseEntity.status(403).body(new ErrorResponse(false,ErrorMessages.NOT_AUTHORIZED));

        if (request.getLink()!=null){
            link.setLink(request.getLink());
        }
        if (request.getName()!=null){
            link.setName(request.getName());
        }
        linkRepository.save(link);
        return ResponseEntity.ok(link);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLink(@PathVariable Long id) {
        Link link = linkService.getLinkById(id).orElse(null);
        if (link == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.LINK_NOT_FOUND));
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!u.equals(link.getUser())) return ResponseEntity.status(403).body(new ErrorResponse(false,ErrorMessages.NOT_AUTHORIZED));

        User user = link.getUser();
        if (user instanceof Client){
            Client c = (Client) user;
            c.getLinks().remove(link);
            userRepository.save(c);
        }else if (user instanceof Developer){
            Developer developer = (Developer) user;
            developer.getLinks().remove(link);
            userRepository.save(developer);
        }

        linkRepository.delete(link);
        return ResponseEntity.ok().build();
    }
}
