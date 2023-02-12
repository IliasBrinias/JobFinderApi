package com.unipi.msc.jobfinderapi.Model.User.Image;

import com.unipi.msc.jobfinderapi.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}