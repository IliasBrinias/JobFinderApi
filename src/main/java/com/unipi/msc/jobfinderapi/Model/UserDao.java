package com.unipi.msc.jobfinderapi.Model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//@Repository
//@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDao {
    private String token;
    private Long generated;
    //TODO: Entity with user Info
//    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
//            new User("ilias@gmail.com", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
//            new User("nikos@gmail.com", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
//    );
//    public UserDetails findUserByEmail(String email) {
//        return APPLICATION_USERS
//                .stream()
//                .filter(u -> u.getUsername().equals(email))
//                .findFirst()
//                .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
//    }

}
