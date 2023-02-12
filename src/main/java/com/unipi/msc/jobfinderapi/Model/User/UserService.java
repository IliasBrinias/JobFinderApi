package com.unipi.msc.jobfinderapi.Model.User;

import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public List<User> getUsers() {
        return userRepository.findAllByRoleNot(Role.ADMIN);
    }

    public Optional<User> getUser(Long id) {
        return userRepository.findAllByIdIs(id);
    }
}
