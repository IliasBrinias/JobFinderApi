package com.unipi.msc.jobfinderapi.Model.Offer.Comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unipi.msc.jobfinderapi.Model.Offer.Offer;
import com.unipi.msc.jobfinderapi.Model.User.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue
    private Long Id;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    private String comment;
    private Long date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "offer_id")
    @JsonBackReference
    private Offer offer;

}
