package com.unipi.msc.jobfinderapi.Controller.LinkController.Request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkRequest {
    private String name;
    private String link;
}
