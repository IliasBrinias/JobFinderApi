package com.unipi.msc.jobfinderapi.Model.Skills;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    public Optional<List<Skill>> getSkillsByIdIn(List<Long> idList){
        return skillRepository.findByIdIn(idList);
    }

    public List<Skill> getSkills(){return skillRepository.findAll();}

    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }
    @Transactional
    public int deleteById(Long id) {
        return skillRepository.deleteSkillById(id);
    }
}
