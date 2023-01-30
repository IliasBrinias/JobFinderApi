package com.unipi.msc.jobfinderapi.Model.Skills;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {
    Optional<Set<Skill>> findByIdIn(List<Long> idList);
    Optional<Skill> findById(Long id);
    List<Skill> findAll();
    Integer deleteSkillById(Long id);
}
