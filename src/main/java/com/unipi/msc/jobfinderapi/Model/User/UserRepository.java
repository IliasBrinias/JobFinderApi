package com.unipi.msc.jobfinderapi.Model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username,String Password);
    Optional<User> findByUsername(String username);
}