package com.unipi.msc.jobfinderapi.Controller.JobController.Offer;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.JobController.Offer.Request.ApplyRequest;
import com.unipi.msc.jobfinderapi.Controller.JobController.Offer.Response.CommentPresenter;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Enum.OfferStatus;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Job.JobService;
import com.unipi.msc.jobfinderapi.Model.Offer.Comment.Comment;
import com.unipi.msc.jobfinderapi.Model.Offer.Offer;
import com.unipi.msc.jobfinderapi.Model.Offer.OfferRepository;
import com.unipi.msc.jobfinderapi.Model.Offer.OfferService;
import com.unipi.msc.jobfinderapi.Model.User.Client;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OfferController {
    private final JobService jobService;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final OfferService offerService;

    @PostMapping("/job/{id}/apply")
    public ResponseEntity<?> apply(@PathVariable Long id, @RequestBody ApplyRequest request){
        Developer developer;
        try {
            developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (ClassCastException ignore){
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_MUST_BE_DEVELOPER));
        }
        Job j = jobService.getJobWithId(id).orElse(null);
        if (j == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.JOB_NOT_FOUND));
        if (request.getComment() == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.COMMENT_IS_OBLIGATORY));
        if (request.getPrice() == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.PRICE_IS_OBLIGATORY));
        Offer offer = Offer.builder()
                .job(j)
                .status(OfferStatus.PENDING)
                .price(request.getPrice())
                .developer(developer)
                .build();
        offer = offerRepository.save(offer);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(Comment.builder()
                .comment(request.getComment())
                .date(new Date().getTime())
                .user(developer)
                .offer(offer)
                .build());
        offer.setComments(commentList);
        offerRepository.save(offer);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/offer/{id}/accept")
    public ResponseEntity<?> acceptApply(@PathVariable Long id) {
        Client client;
        try {
            client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException ignore) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_MUST_BE_DEVELOPER));
        }
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.OFFER_NOT_FOUND));
        if (!Objects.equals(offer.getJob().getClient().getId(), client.getId())) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.ONLY_THE_JOB_CREATOR_CAN_EDIT_THE_JOB));
        offer.setStatus(OfferStatus.ACCEPT);
        offerRepository.save(offer);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/offer/{id}/reject")
    public ResponseEntity<?> rejectApply(@PathVariable Long id) {
        Client client;
        try {
            client = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException ignore) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_MUST_BE_DEVELOPER));
        }
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.OFFER_NOT_FOUND));
        if (!Objects.equals(offer.getJob().getClient().getId(), client.getId())) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.ONLY_THE_JOB_CREATOR_CAN_EDIT_THE_JOB));
        offer.setStatus(OfferStatus.REJECTED);
        offerRepository.save(offer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/apply/{id}")
    public ResponseEntity<?> cancelApply(@PathVariable Long id) {
        Developer developer;
        try {
            developer = (Developer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException ignore) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_MUST_BE_DEVELOPER));
        }
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.OFFER_NOT_FOUND));
        if (!Objects.equals(offer.getDeveloper().getId(), developer.getId())) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.ONLY_THE_OFFER_CREATOR_CAN_CANCEL_THE_OFFER));
        offerRepository.delete(offer);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/offer/{id}/comment")
    public ResponseEntity<?> comment(@PathVariable Long id, @RequestBody ApplyRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
        } catch (ClassCastException ignore) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_MUST_BE_DEVELOPER));
        }
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.OFFER_NOT_FOUND));
        if (!Objects.equals(offer.getJob().getClient().getId(), user.getId()) && !Objects.equals(offer.getDeveloper().getId(), user.getId())){
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.NOT_AUTHORIZED_TO_POST_A_COMMENT));
        }
        Comment c = Comment.builder()
                .offer(offer)
                .date(new Date().getTime())
                .comment(request.getComment())
                .user(user)
                .build();
        offer.getComments().add(c);
        offerRepository.save(offer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/offer/{id}/comment")
    public ResponseEntity<?> comment(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Offer offer = offerService.getOfferById(id).orElse(null);
        if (offer == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.OFFER_NOT_FOUND));
        if (!Objects.equals(offer.getJob().getClient().getId(), user.getId()) && !Objects.equals(offer.getDeveloper().getId(), user.getId())){
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.NOT_AUTHORIZED_TO_SEE_THE_COMMENTS));
        }
        return ResponseEntity.ok(CommentPresenter.getCommentsSorted(offer));
    }
}
