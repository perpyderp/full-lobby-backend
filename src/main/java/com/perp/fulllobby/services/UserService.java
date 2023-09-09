package com.perp.fulllobby.services;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.perp.fulllobby.exception.EmailAlreadyTakenException;
import com.perp.fulllobby.exception.EmailFailedToSendException;
import com.perp.fulllobby.exception.IncorrectVerificationCodeException;
import com.perp.fulllobby.exception.UserDoesNotExistException;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.RegistrationObject;
import com.perp.fulllobby.model.Role;
import com.perp.fulllobby.repository.RoleRepository;
import com.perp.fulllobby.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, RoleRepository roleRepo, MailService mailService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    public MyUser getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);
    }

    public MyUser updateUser(MyUser user) {
        try {
            return userRepo.save(user);
        }
        catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public MyUser registerUser(RegistrationObject regObject) {

        MyUser user = new MyUser();
        user.setUsername(regObject.getUsername());
        user.setFirstName(regObject.getFirstName());
        user.setLastName(regObject.getLastName());
        user.setEmail(regObject.getEmail());
        user.setDateOfBirth(regObject.getDateOfBirth());

        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepo.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepo.save(user);
        }
        catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public void generateVerification(String username) {
        MyUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        user.setVerification(generateVerificationNumber());

        try {
            mailService.sendEmail(user.getEmail(), "Your Verification Code", "Here is your verification code: " + user.getVerification());
            userRepo.save(user);
        }
        catch (Exception e) {
            throw new EmailFailedToSendException();
        }
        userRepo.save(user);
    }

    public MyUser verifyEmail(String username, Long code) {
        MyUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        if(code.equals(user.getVerification())) {
            user.setEnabled(true);
            user.setVerification(null);
            return userRepo.save(user);
        }
        else {
            throw new IncorrectVerificationCodeException();
        }
    }

    public MyUser setPassword(String username, String password) {
        MyUser user = userRepo.findByUsername(username).orElseThrow(UserDoesNotExistException::new);

        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);

        return userRepo.save(user);
    }

    private Long generateVerificationNumber() {
        return (long) Math.floor(Math.random() * 100_000_000);
    }

}
