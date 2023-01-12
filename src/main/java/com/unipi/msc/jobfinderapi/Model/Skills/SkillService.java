package com.unipi.msc.jobfinderapi.Model.Skills;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    public Optional<List<Skill>> getSkillsByIdIn(List<Long> idList){
        return skillRepository.findByIdIn(idList);
    }
}
