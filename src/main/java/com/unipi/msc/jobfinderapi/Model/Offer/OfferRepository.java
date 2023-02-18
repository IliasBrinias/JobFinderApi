package com.unipi.msc.jobfinderapi.Model.Offer;

import com.unipi.msc.jobfinderapi.Model.Enum.Visibility;
import com.unipi.msc.jobfinderapi.Model.Job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {
    Optional<Offer> findById(Long id);
}