package com.unipi.msc.jobfinderapi.Model.Skills;

import com.unipi.msc.jobfinderapi.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<Skills,Long> {
}
