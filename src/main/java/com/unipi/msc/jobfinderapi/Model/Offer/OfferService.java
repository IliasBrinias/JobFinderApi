package com.unipi.msc.jobfinderapi.Model.Offer;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import com.unipi.msc.jobfinderapi.Model.Job.JobRepository;
import com.unipi.msc.jobfinderapi.Model.Offer.OfferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }
}
