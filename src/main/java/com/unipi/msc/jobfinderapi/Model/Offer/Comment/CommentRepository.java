package com.unipi.msc.jobfinderapi.Model.Offer.Comment;

import com.unipi.msc.jobfinderapi.Model.Offer.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

}