package com.unipi.msc.jobfinderapi.Model.User.Image;

import com.unipi.msc.jobfinderapi.Model.Link.Link;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.descriptor.jdbc.LobTypeMappings;

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
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="longblob not null")
    private byte[] imageData;
}
