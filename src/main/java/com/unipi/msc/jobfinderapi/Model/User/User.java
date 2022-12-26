package com.unipi.msc.jobfinderapi.Model.User;

import com.unipi.msc.HouseFindingBack.Model.UserDao;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User{
    @Id
    @GeneratedValue
    @Getter @Setter private Long Id;

    @Column(unique = true)
    @Getter @Setter private String username;

    @Getter private String password;

    @Column(unique = true)
    @Getter private String email;

    @Getter @Setter private String firstName;

    @Getter @Setter private String lastName;

    @Getter @Setter private Long birthday;

    @Enumerated(EnumType.STRING) @Getter @Setter private Authority authority;

    @OneToMany
    @Getter @Setter private List<UserDao> userDaoList;
}
