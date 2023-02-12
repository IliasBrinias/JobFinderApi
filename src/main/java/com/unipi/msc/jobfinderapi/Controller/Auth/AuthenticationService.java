package com.unipi.msc.jobfinderapi.Controller.Auth;

import com.unipi.msc.jobfinderapi.Constant.ErrorMessages;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.LoginRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Requests.RegisterRequest;
import com.unipi.msc.jobfinderapi.Controller.Auth.Responses.UserPresenter;
import com.unipi.msc.jobfinderapi.Controller.Responses.ErrorResponse;
import com.unipi.msc.jobfinderapi.Model.Enum.Role;
import com.unipi.msc.jobfinderapi.Model.Skills.Skill;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillRepository;
import com.unipi.msc.jobfinderapi.Model.Skills.SkillService;
import com.unipi.msc.jobfinderapi.Model.User.*;
import com.unipi.msc.jobfinderapi.Model.User.UserDao.UserDao;
import com.unipi.msc.jobfinderapi.Model.User.UserDao.UserDaoRepository;
import com.unipi.msc.jobfinderapi.Model.User.UserDao.UserDaoService;
import com.unipi.msc.jobfinderapi.config.AsyncClient;
import com.unipi.msc.jobfinderapi.config.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SkillService skillService;
    private final SkillRepository skillRepository;
    private final UserDaoService userDaoService;
    private final UserDaoRepository userDaoRepository;

    public ResponseEntity<?> register(RegisterRequest request) {
        // check for empty data
        if (request.getUsername().equals("") || request.getEmail().equals("") || request.getPassword().equals("")) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.FILL_ALL_THE_FIELDS));
        }

        // check if the user exists
        String error_msg = checkIfExist(request);
        if (!error_msg.equals("")) return ResponseEntity.badRequest().body(new ErrorResponse(false,error_msg));


        Role role;
        try {
            role = Role.valueOf(request.getRole());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.ROLE_DOESNT_EXIST));
        }

        // build user object and save it
        User user;
        if (role == Role.CLIENT){
            user = new Client(request.getEmail(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    role,
                    request.getGender(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday(),
                    false,
                    request.getDsc()
                    );
        }
        else if (role==Role.DEVELOPER){
            if (request.getSkills() != null){
                List<Skill> skillList = new ArrayList<>();
                for (Skill s : request.getSkills()) {
                    if (s.getSkill() == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.SKILL_IS_NULL));

                    skillList.add(Skill.builder()
                            .skill(s.getSkill())
                            .alias(s.getAlias()).build());
                }
                request.setSkills(skillRepository.saveAll(skillList));
                System.out.println(request.getSkills());
            }
            user = new Developer(request.getEmail(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    role,
                    request.getGender(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday(),
                    false,
                    request.getDsc(),
                    new ArrayList<>(),
                    request.getSkills()
            );
        }
        else if (role==Role.ADMIN){
            user = new Admin(request.getEmail(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    role,
                    request.getGender(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getBirthday(),
                    false);
        }else {
            return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.ROLE_IS_NULL));
        }
        user = userRepository.save(user);

        String generatedToken = jwtService.generateToken(user);
        userDaoRepository.save(UserDao.builder()
                .token(generatedToken)
                .created(new Date().getTime())
                .user(user)
                .isActive(true)
                .build());

        if (!user.isEnabled()){
//            TODO: Temporary the user is enable
            AsyncClient.sendEmail(user.getEmail(),generatedToken);
        }
//        TODO: Temporary token generated
        return ResponseEntity.ok(getAuthenticationResponse(user, generatedToken));
    }
    public ResponseEntity<?> authenticate(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        // double check if the user exists
        if (!userRepository.findByUsername(request.getUsername()).isPresent()) return ResponseEntity.notFound().build();
        User user = userRepository.findByUsername(request.getUsername()).get();
        return ResponseEntity.ok(getAuthenticationResponse(user, jwtService.generateToken(user)));
    }
    public UserPresenter getAuthenticationResponse(User user, String jwtToken) {
        UserPresenter response = UserPresenter.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .isVerified(user.getIsVerified())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .rating(user.getRating())
                .creationDate(user.getCreationDate())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .token(jwtToken).build();
        if (user instanceof Client){
            Client c = (Client) user;
            response.setDsc(c.getDsc());
            response.setLink(c.getLinks());
            response.setJobs(c.getJobList());
        }
        if (user instanceof Developer){
            Developer d = (Developer) user;
            response.setDsc(d.getDsc());
            response.setLink(d.getLinks());
            response.setSkills(d.getSkills());
        }
        return response;
    }
    public String checkIfExist(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ErrorMessages.USERNAME_EXISTS;
        }else if (userRepository.findByEmail(request.getEmail()).isPresent()){
            return ErrorMessages.EMAIL_EXISTS;
        }
        return "";
    }
    public ResponseEntity<?> enableUserAccount(String token) {
        String userEmail;
        try {
            userEmail = jwtService.extractUsername(token);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.TOKEN_EXPIRED));
        }
        User u = userService.getUserByUsername(userEmail).orElse(null);
        if (u == null)
            return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.USER_NOT_FOUND));
        u.setIsVerified(true);
        userRepository.save(u);
        return ResponseEntity.ok(getAuthenticationResponse(u,jwtService.generateToken(u)));
    }

    public ResponseEntity<?> resendEmail(String email) {
        User u = userService.getUserByEmail(email).orElse(null);
        if (u == null) return ResponseEntity.badRequest().body(new ErrorResponse(false,ErrorMessages.USER_NOT_FOUND));
        AsyncClient.sendEmail(email,jwtService.generateToken(u));
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> logout() {
        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDao userDao = userDaoService.getLastToken(u).orElse(null);
        if (userDao == null) return ResponseEntity.badRequest().body(new ErrorResponse(false, ErrorMessages.NO_TOKEN_FOUND));
        userDao.setIsActive(false);
        userDaoRepository.save(userDao);
        return ResponseEntity.ok().build();
    }
}
