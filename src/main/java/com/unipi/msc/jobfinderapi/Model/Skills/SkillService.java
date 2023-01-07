package com.unipi.msc.jobfinderapi.Model.Skills;

import com.unipi.msc.jobfinderapi.Model.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final UserRepository userRepository;

}
