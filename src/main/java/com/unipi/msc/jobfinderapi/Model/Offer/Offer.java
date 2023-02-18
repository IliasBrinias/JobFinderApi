package com.unipi.msc.jobfinderapi.Model.Offer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Enum.OfferStatus;
import com.unipi.msc.jobfinderapi.Model.Offer.Comment.Comment;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.User.Developer;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {
    @Id
    @GeneratedValue
    private Long Id;
    private OfferStatus status;
    private Double price;

    @OneToOne
    @JoinColumn(name = "job_id")
    @JsonManagedReference
    private Job job;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "developer_id")
    @JsonBackReference
    private Developer developer;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();
}
