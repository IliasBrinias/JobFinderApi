package com.unipi.msc.jobfinderapi.Model.User.UserDao;

import com.unipi.msc.jobfinderapi.Model.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDaoService {
    private final UserDaoRepository userDaoRepository;

    public Optional<UserDao> getLastToken(User u) {
        return userDaoRepository.findFirstByUserOrderByCreatedDesc(u);
    }

    public boolean isTokenEnable(String token) {
        UserDao userDao = userDaoRepository.findUserDaoByTokenOrderByCreatedDesc(token).orElse(null);
        if (userDao == null) return true;
        return userDao.getIsActive();
    }
}
