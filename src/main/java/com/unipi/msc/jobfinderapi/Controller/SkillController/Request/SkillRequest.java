package com.unipi.msc.jobfinderapi.Controller.SkillController.Request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SkillRequest {
    private String skill;
    private String alias;
}
