package com.unipi.msc.jobfinderapi.Model.User;

import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByRoleNot(@NonNull Role role);
    Optional<User> findAllByIdIs(Long id);
}