package com.unipi.msc.jobfinderapi.Model.Link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link,Long> {
    Optional<List<Link>> findByIdIn(List<Long> idList);
    List<Link> findAll();
    Optional<Link> findById(Long id);
}
