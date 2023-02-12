package com.unipi.msc.jobfinderapi.Model.User.Rating;

import com.unipi.msc.jobfinderapi.Model.User.User;
import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    public List<Rating> getRating() {
        return ratingRepository.findAll();
    }
}
