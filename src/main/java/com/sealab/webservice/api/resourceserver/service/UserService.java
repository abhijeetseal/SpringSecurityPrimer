package com.sealab.webservice.api.resourceserver.service;


import com.sealab.webservice.api.resourceserver.entity.User;
import com.sealab.webservice.api.resourceserver.repository.ActivationTokenRepository;
import com.sealab.webservice.api.resourceserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public User saveUser(User user) {
        log.info("inside saveUser :: service");
        if(checkIfUserExist(user.getEmail()) && !user.isActive()){
            log.info("User Already Exists");
            return null;
        }
        log.info("inside saveUser :: service");
        if(!user.isActive()) {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {

        return userRepository.findByUserId(userId);
    }

    private void encodePassword(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
    }

    public boolean checkIfUserExist(String email) {
        return (userRepository.findByEmail(email) != null) ? true : false;

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
