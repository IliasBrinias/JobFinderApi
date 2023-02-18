package com.unipi.msc.jobfinderapi.Model.Offer.Comment;

import com.unipi.msc.jobfinderapi.Model.Offer.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
}
