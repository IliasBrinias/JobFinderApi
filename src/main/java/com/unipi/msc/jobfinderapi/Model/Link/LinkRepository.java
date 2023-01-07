package com.unipi.msc.jobfinderapi.Model.Link;

import com.unipi.msc.jobfinderapi.Model.Skills.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link,Long> {
}
