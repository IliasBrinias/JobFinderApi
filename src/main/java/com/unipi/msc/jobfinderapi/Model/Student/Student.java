package com.unipi.msc.jobfinderapi.Model.Student;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @Column(unique = true)
    @NonNull
    private String email;
    @NonNull
    private Integer age;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
