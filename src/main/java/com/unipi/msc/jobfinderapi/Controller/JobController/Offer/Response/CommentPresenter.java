package com.unipi.msc.jobfinderapi.Controller.JobController.Offer.Response;

import com.unipi.msc.jobfinderapi.Model.Offer.Comment.Comment;
import com.unipi.msc.jobfinderapi.Model.Offer.Offer;
import lombok.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentPresenter {
    private Long id;
    private Long date;
    private String comment;
    private Long userId;

    public static List<CommentPresenter> getCommentsSorted(Offer offer) {
        List<CommentPresenter> comments = new ArrayList<>();
        offer.getComments().stream().sorted(Comparator.comparingLong(Comment::getDate))
                .forEach(c->comments.add(CommentPresenter.builder()
                        .id(c.getId())
                        .userId(c.getUser().getId())
                        .date(c.getDate())
                        .comment(c.getComment())
                        .build()));
        return comments;
    }
}
