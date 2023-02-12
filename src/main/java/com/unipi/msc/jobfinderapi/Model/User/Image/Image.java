package com.unipi.msc.jobfinderapi.Model.User.Image;

import com.unipi.msc.jobfinderapi.Model.Link.Link;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Image {
    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String type;
    @Lob
    @Column(length = 1000)
    private byte[] imageData;
}
