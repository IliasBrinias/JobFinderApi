package com.unipi.msc.jobfinderapi.Model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUserByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }
    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
