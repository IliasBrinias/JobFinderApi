package com.unipi.msc.jobfinderapi.Controller.UserController.Request;

import com.unipi.msc.jobfinderapi.Controller.JobController.Request.Range;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserRequest {
    private List<Long> skills;
    private Range creationDate;
    private Range rating;
    private String name;
    private Role role;
}
