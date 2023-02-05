package com.unipi.msc.jobfinderapi.Model.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.unipi.msc.jobfinderapi.Model.Enum.Gender;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Link.Link;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @Column
    private Long Id;
    @Column(name = "email",unique = true)
    @NonNull
    private String email;
    @Column(name = "username",unique = true)
    @NonNull
    private String username;
    private String password;
    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.OTHER;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Long birthday;
    @Column
    private Boolean isVerified = false;

    public User(@NonNull String email, @NonNull String username, String password, @NonNull Role role, Gender gender, String firstName, String lastName, Long birthday, Boolean isVerified) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.isVerified = isVerified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Id != null && Objects.equals(Id, user.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }
}
