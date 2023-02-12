package com.unipi.msc.jobfinderapi.Model.User.UserDao;

import com.unipi.msc.jobfinderapi.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDaoRepository extends JpaRepository<UserDao,Long> {
    Optional<UserDao> findFirstByUserOrderByCreatedDesc(User user);
    Optional<UserDao> findUserDaoByTokenOrderByCreatedDesc(String token);

}